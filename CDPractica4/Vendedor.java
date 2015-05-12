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
	private ArrayList<Subasta> subastas;
	private final Integer DELAY = 10000;

	protected void setup(){

		nuevaSubasta("Macbeth", 20, 3);

	} // END SETUP

	protected void takeDown(){
		System.out.println(getLocalName()+": No mas subastas por hoy");
	}

	// FUNCIONES =====================================================================
	private void nuevaSubasta(String libro, Integer precio, Integer incremento){
		Subasta subasta = new Subasta(libro, precio, incremento);

		ServiceDescription servicio = new ServiceDescription();
		servicio.setType(libro);
		servicio.setName("Subasta "+libro);

		DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.addLanguages("Español");
		descripcion.addServices(servicio);

		subasta.setDescripcion(descripcion);

		buscarCompradores(subasta);

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
		}
		System.out.println("Creada una subasta por el libro '"+libro+"'\n Precio salida: "+precio+" Incrementos: "+incremento);
		addBehaviour(new GestorSubasta(this, DELAY, subasta));
	}

	private void buscarCompradores(Subasta subasta){
		System.out.println(getLocalName()+": Comprobando si ha entrado gente nueva en la sala..");
		try{																	// Busca a todos los compradores interesados en esta subasta
			DFAgentDescription[] resultado = DFService.search(this, subasta.getDescripcion());

			if(resultado.length <= 0){
				System.out.println(getLocalName()+": No hay clientes interesados en "+subasta.getTituloLibro());
			}else{
																				
				ArrayList<AID> compradores = new ArrayList();
				for(DFAgentDescription agente : resultado){						// Añade cada destinatario a los mensajes (multicast)
					compradores.add(agente.getName());
				}
				subasta.setParticipantes(compradores);
			}
		}catch(Exception e){
			System.out.println(getLocalName()+"Error realizando la busqueda de compradores: "+e.getMessage());
		}
	}

	private void nuevaPuja(Subasta subasta){

		ACLMessage mensajeCFP = new ACLMessage(ACLMessage.CFP);

		for(AID idComprador : subasta.getParticipantes()){
			mensajeCFP.addReceiver(idComprador);
		}

		mensajeCFP.setLanguage("Español");
		mensajeCFP.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		mensajeCFP.setContent("Subasta en marcha - Precio actual: "+subasta.getPrecioActual());

		// Tiempo de espera por las pujas 
		mensajeCFP.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		addBehaviour(new GestorRespuestas(this, mensajeCFP, subasta));
	}

	// COMPORTAMIENTOS ===============================================================

	private class GestorRespuestas extends ContractNetInitiator {

		private Subasta subasta;

		public GestorRespuestas(Agent agente, ACLMessage cfp, Subasta subasta){
			super(agente, cfp);
			this.subasta = subasta;
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
				System.out.println("AMS: Este cliente ("+fallo.getSender()+") no es accesible o no existe");
				subasta.eliminarParticipante(fallo.getSender());
				System.out.println(fallo.getSender()+" ha sido expulsado de la sala de subastas");
			} else{
				System.out.println("Se ha producido un error en: "+fallo.getSender().getLocalName());
			}
		}

		// Maneja todas las respuestas, es llamado cuando se reciben todas o se acaba el tiempo
		protected void handleAllResponses(Vector respuestas, Vector aceptadas){
			int nPujas = 0;

			for(Object resp : respuestas){
				ACLMessage mensaje = (ACLMessage) resp;

				if(mensaje.getPerformative() == ACLMessage.PROPOSE){	// Si los mensajes son proposiciones
					ACLMessage respuesta = mensaje.createReply();

					int puja = Integer.parseInt(mensaje.getContent());

					if(puja >= subasta.getPrecioActual()){				// El credito del comprador es superior al pedido en la puja - PUJA
						respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						respuesta.setContent("Su puja ha sido aceptada");
						System.out.println(myAgent.getLocalName()+": "+mensaje.getSender().getLocalName()+" ha pujado");

						if(nPujas == 0) subasta.setGanador(mensaje.getSender());
						nPujas++;

					}else{												// El credito del comprador es inferior al pedido - NO PUEDE PUJAR
						respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);	
						respuesta.setContent("Su puja ha sido rechazada");				
					}
					aceptadas.add(respuesta);
				}
			}
			/*
			if(nPujas == 1){											// Solo ha pujado un comprador - gana la subasta - FIN

				for(Object resp : respuestas){
					ACLMessage mensaje = (ACLMessage) resp;

					if(mensaje.getPerformative() == ACLMessage.PROPOSE){
						ACLMessage respuesta = mensaje.createReply();

						if(mensaje.getSender().equals(subasta.getGanador())){
							// GANADOR - ENVIAR REQUEST
							respuesta.setPerformative(ACLMessage.REQUEST);
							respuesta.setContent("Enhorabuena ha ganado la subasta!");
							send(respuesta);
						}

						respuesta.setPerformative(ACLMessage.INFORM);
						respuesta.setContent("La subasta ha finalizado.");
						aceptadas.add(respuesta);
					}
				}

			}else*/
			if(nPujas == 0){
				subasta.terminada(true);
			}
		}

	}

	private class GestorSubasta extends TickerBehaviour {

		private Subasta subasta;

		public GestorSubasta(Agent agente, long delay, Subasta subasta){
			super(agente, delay);
			this.subasta = subasta;
		}

		public void onTick(){
			if(!subasta.terminada()){	
				System.out.println(myAgent.getLocalName()+": NUEVA PUJA\t-\tNUEVO PRECIO: "+subasta.getPrecioActual());					
				Vendedor.this.nuevaPuja(subasta);								// Se realiza una nueva puja
				Vendedor.this.buscarCompradores(subasta);						// Comprueba si han entrado nuevos compradores que participaran
																				// en la puja siguiente
				subasta.incrementar();											// Incrementa el importe del libro para la siguiente puja
			}else{		
				System.out.println(myAgent.getLocalName()+": Subasta por el libro '"+subasta.getTituloLibro()+"' FINALIZADA");	
				System.out.println(myAgent.getLocalName()+": Ganador de la subasta: "+subasta.getGanador().getLocalName());													
				stop();															// Termina el comportamiento
			}
		}
	}

}

