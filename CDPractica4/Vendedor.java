
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Date;
import java.util.Vector;
import java.util.HashMap;

public class Vendedor extends Agent {

	private GUIVendedor interfaz;
	private int precioSalida;												// Precio de salida del libro
	private int incremento;													// Incremento entre dos pujas
	public boolean continuar = false;												
	public String titulo;

	private HashMap<String, Subasta> catalogo;								// Catalogo de libros a la venta titulo-precio

	protected void setup(){
		//interfaz = new GUIVendedor(this);
		//interfaz.setVisible(true);

		System.out.println(getLocalName()+": INICIADO!");
		catalogo = new HashMap();

		//cargarDatos();													  // Obtiene los datos por parametros (titulo, precio, incremento)
		/*
		ServiceDescription servicio = new ServiceDescription();				// Crea el servicio - subasta del libro
		servicio.setType("subasta");
		servicio.setName("Subasta de libros");

		DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.addLanguages("Español");
		descripcion.addServices(servicio);
		*/
		nuevaSubasta("El principe", 20, 1);

		/*
		try{	// SI EL VENDEDOR HACE EL PRIMER CFP SE TIENE QUE REGISTRAR LOS CLIENTES EN LAS PAGINAS,NO EL VENDEDOR!!!!!
			DFService.register(this, descripcion);							// Registra el servicio de subasta en las paginas amarillas
		}catch(FIPAException e){
			e.printStackTrace();
		}*/

		//buscaCompradores();
	}

	protected void takeDown(){
		try{
			DFService.deregister(this);
		} catch(FIPAException e){
			e.printStackTrace();
		}
		System.out.println(this.getLocalName()+": eliminado de las paginas amarillas");
	}

	// FUNCIONES =============================================================

	public void nuevaSubasta(String tituloLibro, Integer precioSalida, Integer incremento){

		System.out.println(getLocalName()+": Creada nueva subasta\n->"+tituloLibro+", "+precioSalida+"€ i:"+incremento);

		Subasta subasta = new Subasta(tituloLibro, precioSalida, incremento);

		ServiceDescription servicio = new ServiceDescription();
		servicio.setType(tituloLibro);
		servicio.setName("Subasta del libro: '"+tituloLibro+"'");

		DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.addLanguages("Español");
		descripcion.addServices(servicio);

		this.addBehaviour(new GestionSubasta(descripcion, subasta));
	}


	public void actualizarCatalogo(String titulo, int precio){				// Actualiza el catalogo del vendedor insertando un libro nuevo
		/*
		addBehaviour(new OneShotBehaviour(){
			public void action(){
				catalogo.put(titulo, new Integer(precio));
				System.out.println(myAgent.getLocalName()+": Añadido '"+titulo+"' al catalogo. Precio = "+precio);
			}
		});
		*/
	}

	public void cargarDatos(){
		Object[] args = this.getArguments();

		if(args != null && args.length == 3){
			this.titulo = (String) args[0];
			this.precioSalida = Integer.parseInt((String) args[1]);
			this.incremento = Integer.parseInt((String) args[2]);

		}else{
			System.out.println("ERROR: Introducir tres argumentos (titulo, precio, incremento)");
		}
	}

	public void setPrecioSalida(int precioSalida){
		this.precioSalida = precioSalida;
	}

	public void setIncremento(int incremento){
		this.incremento = incremento;
	}

	// COMPORTAMIENTOS=============================================================

	private class GestionSubasta extends SimpleBehaviour {

		private DFAgentDescription descripcion;
		private Subasta subasta;
		private AID id;

		public GestionSubasta(DFAgentDescription descripcion, Subasta subasta){
			super();
			this.descripcion = descripcion;
			this.subasta  = subasta;
		}

		public void action(){

			//new AID

			try{
				DFAgentDescription[] resultado = DFService.search(myAgent, descripcion);
																				// Busca los clientes que quieran participar en la subasta
				if(resultado.length <= 0){
					System.out.println("No hay compradores de subastas.");
					block(3000);												// Si no hay clientes espera 3 segundos antes de volver a probar
				} else{
					System.out.printf("Hay %d clientes en la sala.", resultado.length);

					ACLMessage mensajeInfo = new ACLMessage(ACLMessage.INFORM);	// Se crea un mensaje INFORM
					ACLMessage mensajeCFP = new ACLMessage(ACLMessage.CFP);		// Se crea el mensaje CFP (Call For Proposal)
					
					

					for(DFAgentDescription agente : resultado){
						mensajeCFP.addReceiver(agente.getName());				// Establece los destinatarios del mensaje (multicast)
						mensajeInfo.addReceiver(agente.getName());
					}

					mensajeInfo.setLanguage("Español");
					mensajeInfo.setContent("Hola, se va a iniciar una subasta del libro '"+subasta.getTituloLibro()+"'.");
					send(mensajeInfo);

					mensajeCFP.setLanguage("Español");
					mensajeCFP.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
					mensajeCFP.setContent("Subasta en marcha - Precio actual: "+subasta.getPrecioActual());

					// Tiempo de espera por las pujas - 10 segundos hasta realizar la siguiente
					mensajeCFP.setReplyByDate(new Date(System.currentTimeMillis() + 10000));

					// Añade comportamiento que gestiona las respuestas de los clientes
					myAgent.addBehaviour(new HandlerRespuestas(myAgent, mensajeCFP, subasta));

					// DOMIR 10 SEGUNDOS ? block(10000);
					block(5000);
					subasta.incrementar();

				}
			} catch(Exception e){
				e.printStackTrace();
			}	
		}

		public boolean done(){
			if(subasta.terminada()){
				return true;
			}else{
				reset();
				return false;
			}
		}
		/*
		public int onEnd(){
			System.out.println("El ganador de la subasta es :"+subasta.getGanador().getLocalName());
			System.out.println("Precio final: "+subasta.getPrecioActual());
			return 1; 
		}
		*/

	}

	private class HandlerRespuestas extends ContractNetInitiator {		// Gestiona todas las respuestas de las CFP del vendedor

		private Subasta subasta;

		public HandlerRespuestas(Agent agente, ACLMessage mensajeCFP, Subasta subasta){
			super(agente, mensajeCFP);
			this.subasta = subasta;
		}

		// Maneja las propuestas de los clientes
		protected void handlePropose(ACLMessage propuesta, Vector aceptadas){
			if(aceptadas.size() == 1){	// Solo ha pujado un cliente en esta ronda - el primero
				subasta.setGanador(propuesta.getSender());				// Obtiene el AID del primer pujador y lo declara como ganador de la ronda
			}
			//System.out.println(propuesta.getSender().getLocalName()+" ha pujado.");
		}
		// Maneja las respuestas de fallo
		protected void handleFailure(ACLMessage fallo){
			if(fallo.getSender().equals(myAgent.getAMS())){
				System.out.println("AMS: Este cliente no es accesible o no existe");
			} else{
				System.out.println("Se ha producido un error en: "+fallo.getSender().getLocalName());
			}
		}

		// Metodo colectivo llamado tras finalizar el tiempo de espera o recibir todas las respuestas
		protected void handleAllResponses(Vector respuestas, Vector aceptadas){
			int nPujas =0;

			for(Object resp : respuestas){
				ACLMessage mensaje = (ACLMessage) resp;

				if(mensaje.getPerformative() == ACLMessage.PROPOSE){
					ACLMessage respuesta = mensaje.createReply();

					int puja = Integer.parseInt(mensaje.getContent());	// Cada comprador en el mensaje debe indicar el precio maximo a pujar

					if(puja >= subasta.getPrecioActual()){
						respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						System.out.println(respuesta.getSender().getLocalName()+" ha pujado");
						nPujas++;
					}else{
						respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
					}
					aceptadas.add(respuesta);
				}
			}

			if(nPujas == 1){
				myAgent.addBehaviour(new FinalizarSubasta(subasta));
				// solo ha pujado uno en esta ronda - fin de la subasta
				//subasta.terminada(true);
				//System.out.println("-Fin de la subasta-");

				// INFORM - RESTO PARTICIPANTES
				// REQUEST - GANADOR
			}

		}
	}

	private class FinalizarSubasta extends OneShotBehaviour {

		private Subasta subasta;

		public FinalizarSubasta(Subasta subasta){
			this.subasta = subasta;
		}

		public void action(){
			System.out.println("Finalizada la subasta de '"+subasta.getTituloLibro()+"'");
			System.out.println("Ganador: "+subasta.getGanador().getLocalName());
			System.out.println("Precio final: "+subasta.getPrecioActual());
			System.out.println("Numero de pujas: "+subasta.getNumeroPujas());

			subasta.terminada(true);
		}
	}
}