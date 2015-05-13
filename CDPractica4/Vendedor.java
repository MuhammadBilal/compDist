import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.*;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Date;

public class Vendedor extends Agent {

	//private HashMap<Subasta, ArrayList<AID>> subastas;
	public HashMap<String, GUISubasta> frames;
	public ArrayList<Subasta> subastas;
	private final Integer DELAY = 10000;
	public GUIVendedor gui;

	protected void setup(){
		subastas = new ArrayList();
		frames = new HashMap();

		gui = new GUIVendedor(this);
		gui.setTitle("Vendedor: "+getLocalName());
		gui.setVisible(true);

		//nuevaSubasta("Macbeth", 20, 3);

	} // END SETUP

	protected void takeDown(){
		System.out.println(getLocalName()+": No mas subastas por hoy");
	}

	// FUNCIONES =====================================================================
	public void nuevaSubasta(String libro, Integer precio, Integer incremento){
		Subasta subasta = new Subasta(libro, precio, incremento);
		GUISubasta guiSubasta = new GUISubasta(this, subasta);
		guiSubasta.setVisible(true);
		frames.put(subasta.getTituloLibro(), guiSubasta);

		ServiceDescription servicio = new ServiceDescription();
		servicio.setType(libro);
		servicio.setName("Subasta "+libro);

		DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.addLanguages("Español");
		descripcion.addServices(servicio);

		subasta.setDescripcion(descripcion);

		buscarCompradores(subasta);											// Busca participantes para comenzar la subasta

		if(subasta.getParticipantes().size() > 0){
			ACLMessage mensajeInfo = new ACLMessage(ACLMessage.INFORM);		// Se crea el mensaje inform

			for(AID idComprador : subasta.getParticipantes()){
				mensajeInfo.addReceiver(idComprador);
			}

			mensajeInfo.setLanguage("Español");
			mensajeInfo.setContent("Hola, se va a iniciar una subasta del libro '"+subasta.getTituloLibro()+"'.");
			send(mensajeInfo);
		}else{
			System.out.println(getLocalName()+": Todavia no hay suficientes participantes para iniciar una subasta");
			guiSubasta.addMensaje(getLocalName()+": Todavia no hay suficientes participantes para iniciar una subasta");
		}
		guiSubasta.addMensaje("Creada una subasta por el libro '"+libro+"'\n Precio salida: "+precio+" Incrementos: "+incremento);
		System.out.println("Creada una subasta por el libro '"+libro+"'\n Precio salida: "+precio+" Incrementos: "+incremento);
		subastas.add(subasta);
		addBehaviour(new GestorSubasta(this, DELAY, subasta));
	}

	private void buscarCompradores(Subasta subasta){
		GUISubasta guiSubasta = frames.get(subasta.getTituloLibro());

		System.out.println(getLocalName()+": Comprobando si ha entrado gente nueva en la sala..");
		guiSubasta.addMensaje("Comprobando si ha entrado gente nueva en la sala..");
		try{																	// Busca a todos los compradores interesados en esta subasta
			DFAgentDescription[] resultado = DFService.search(this, subasta.getDescripcion());

			if(resultado.length <= 0){
				guiSubasta.addMensaje("No hay clientes interesados en "+subasta.getTituloLibro());
				System.out.println(getLocalName()+": No hay clientes interesados en "+subasta.getTituloLibro());
			}else{
																				
				ArrayList<AID> compradores = new ArrayList();
				for(DFAgentDescription agente : resultado){						// Añade cada destinatario a los mensajes (multicast)
					compradores.add(agente.getName());
				}
				subasta.setParticipantes(compradores);
			}
		}catch(Exception e){
			System.out.println(getLocalName()+": Error realizando la busqueda de compradores: "+e.getMessage());
			guiSubasta.addMensaje("Error realizando la busqueda de compradores: "+e.getMessage());
		}						// Mira si hay agentes registrados para la subasta y los añade
	}

	private void nuevaPuja(Subasta subasta){
		GUISubasta guiSubasta = frames.get(subasta.getTituloLibro());
		ACLMessage mensajeCFP = new ACLMessage(ACLMessage.CFP);

		for(AID idComprador : subasta.getParticipantes()){
			mensajeCFP.addReceiver(idComprador);
		}

		mensajeCFP.setLanguage("Español");
		mensajeCFP.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		mensajeCFP.setContent("Subasta en marcha - Precio actual: "+subasta.getPrecioActual());
		guiSubasta.addMensaje("Subasta en marcha - Precio actual: "+subasta.getPrecioActual());
		// Tiempo de espera por las pujas 
		mensajeCFP.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		addBehaviour(new GestorRespuestas(this, mensajeCFP, subasta));								
	}

	private void finalizarSubasta(Subasta subasta){
		GUISubasta guiSubasta = frames.get(subasta.getTituloLibro());
		guiSubasta.addMensaje("Subasta por el libro '"+subasta.getTituloLibro()+"' FINALIZADA");
		System.out.println(getLocalName()+": Subasta por el libro '"+subasta.getTituloLibro()+"' FINALIZADA");
		guiSubasta.addMensaje("Ganador de la subasta: "+subasta.getGanador().getLocalName());	
		System.out.println(getLocalName()+": Ganador de la subasta: "+subasta.getGanador().getLocalName());	

		ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
		mensaje.setLanguage("Español");
		mensaje.setContent("GANADA");
		mensaje.addReceiver(subasta.getGanador());
		send(mensaje);															// Envia un mensaje al ganador 

		mensaje.clearAllReceiver();

		for(AID idComprador : subasta.getParticipantes()){
			mensaje.addReceiver(idComprador);
		}

		mensaje.setPerformative(ACLMessage.INFORM);
		mensaje.setContent("FINALIZADA");
		send(mensaje);															// Envia un mensaje con FINALIZADA a todos los participantes
	}	

	// COMPORTAMIENTOS ===============================================================

	private class GestorRespuestas extends ContractNetInitiator {

		private Subasta subasta;
		private GUISubasta guiSubasta;

		public GestorRespuestas(Agent agente, ACLMessage cfp, Subasta subasta){
			super(agente, cfp);
			this.subasta = subasta;
			this.guiSubasta = Vendedor.this.frames.get(subasta.getTituloLibro());
		}

		// Maneja las propuestas de los clientes - PROPOSE
		protected void handlePropose(ACLMessage propuesta, Vector aceptadas){
			/*
			if(aceptadas.size() == 1){									// Solo ha pujado un cliente en esta ronda - el primero
				int puja = Integer.parseInt(propuesta.getContent());
				if(puja)
				subasta.setGanador(propuesta.getSender());				// Obtiene el AID del primer pujador y lo declara como ganador de la ronda
			}*/
		}

		// Maneja las respuestas de fallo	- FAILURE
		protected void handleFailure(ACLMessage fallo){
			if(fallo.getSender().equals(myAgent.getAMS())){
				guiSubasta.addMensaje("AMS: Este cliente ("+fallo.getSender()+") no es accesible o no existe");
				System.out.println("AMS: Este cliente ("+fallo.getSender()+") no es accesible o no existe");
				subasta.eliminarParticipante(fallo.getSender());
				System.out.println(fallo.getSender()+" ha sido expulsado de la sala de subastas");
				guiSubasta.addMensaje(fallo.getSender()+" ha sido expulsado de la sala de subastas");
			} else{
				System.out.println("Se ha producido un error en: "+fallo.getSender().getLocalName());
				guiSubasta.addMensaje("Se ha producido un error en: "+fallo.getSender().getLocalName());
			}
		}

		// Maneja todas las respuestas, es llamado cuando se reciben todas o se acaba el tiempo
		protected void handleAllResponses(Vector respuestas, Vector aceptadas){
			int nPujas = 0;
			//String pujas = getPujas(respuestas);

			for(Object resp : respuestas){
				ACLMessage mensaje = (ACLMessage) resp;

				if(mensaje.getPerformative() == ACLMessage.PROPOSE){	// Si los mensajes son proposiciones
					ACLMessage respuesta = mensaje.createReply();

					int puja = Integer.parseInt(mensaje.getContent());

					if(puja >= subasta.getPrecioActual()){				// El credito del comprador es superior al pedido en la puja - PUJA
						respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						// Se le envia la informacion de las pujas a cada comprador
						respuesta.setContent(getPujas(respuestas, mensaje.getSender()));
						System.out.println(myAgent.getLocalName()+": "+mensaje.getSender().getLocalName()+" ha pujado");
						guiSubasta.addMensaje(mensaje.getSender().getLocalName()+" ha pujado");

						if(nPujas == 0) subasta.setGanador(mensaje.getSender());
						nPujas++;

					}else{												// El credito del comprador es inferior al pedido - NO PUEDE PUJAR
						respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);	
						respuesta.setContent("Su puja ha sido rechazada");				
					}
					aceptadas.add(respuesta);
				}
			}

			if(nPujas  <= 1){
				subasta.terminada(true);
			}
		}

		private String getPujas(Vector respuestas, AID destinatario){
			String pujas = "";

			for(Object resp : respuestas){
				ACLMessage mensaje = (ACLMessage) resp;
				if(!mensaje.getSender().equals(destinatario)){
					if(mensaje.getPerformative() == ACLMessage.PROPOSE){
						int puja = Integer.parseInt(mensaje.getContent());
						if(puja >= subasta.getPrecioActual()){
							pujas += mensaje.getSender().getLocalName()+" PUJA\n";
						}else{
							pujas += mensaje.getSender().getLocalName()+" no ha pujado\n";
						}
					}
				}	
			}
			return pujas;
		}
	}

	private class GestorSubasta extends TickerBehaviour {

		private Subasta subasta;
		private GUISubasta guiSubasta;

		public GestorSubasta(Agent agente, long delay, Subasta subasta){
			super(agente, delay);
			this.subasta = subasta;
			this.guiSubasta = Vendedor.this.frames.get(subasta.getTituloLibro());
		}

		public void onTick(){
			if(!subasta.terminada()){	
				System.out.println(myAgent.getLocalName()+": NUEVA PUJA\t-\tNUEVO PRECIO: "+subasta.getPrecioActual());	
				guiSubasta.addMensaje("NUEVA PUJA\t-\tNUEVO PRECIO: "+subasta.getPrecioActual());				
				Vendedor.this.nuevaPuja(subasta);								// Se realiza una nueva puja
				Vendedor.this.buscarCompradores(subasta);						// Comprueba si han entrado nuevos compradores que participaran
																				// en la puja siguiente
				subasta.incrementar();											// Incrementa el importe del libro para la siguiente puja
			}else{		
				Vendedor.this.finalizarSubasta(subasta);											
				stop();															// Termina el comportamiento
			}
		}
	}

}

