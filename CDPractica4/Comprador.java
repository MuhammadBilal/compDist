
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.*;
import jade.proto.ContractNetResponder;

import java.util.ArrayList;

public class Comprador extends Agent {

	public ArrayList<String> subastas;
	private GUIComprador gui;

	protected void setup() {
		gui = new GUIComprador(this);
		gui.setTitle("Comprador "+getLocalName());
		gui.setVisible(true);

		this.subastas = new ArrayList();

		//String libro = "Macbeth";

		//cargaDatos();
		
	}

	protected void takeDown(){
		System.out.println(getLocalName()+": Me voy de la casa de subastas!");
	}

	// FUNCIONES ===============================================================

	private void cargaDatos(){
		Object[] args = this.getArguments();
		String titulo;
		Integer precioMax;

		if(args != null && args.length == 2){
			titulo = (String) args[0];
			precioMax = Integer.parseInt((String) args[1]);
			nuevaSubasta(titulo, precioMax);
		}else{
			System.out.println("efnaofbjaiksfaobvdoui");
		}
		
	}

	public void nuevaSubasta(String libro, Integer credito){

		ServiceDescription servicio = new ServiceDescription();
		servicio.setType(libro);
		servicio.setName("Subasta "+libro);

		DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.addLanguages("Español");
		descripcion.addServices(servicio);

		try {																
			DFService.register(this, descripcion);								// Registra el servicio
			subastas.add(libro);
		}catch(FIPAException e){
			e.printStackTrace();
		}

		MessageTemplate plantilla = ContractNetResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);

		addBehaviour(new GestorRespuestas(this, plantilla, credito));
		//addBehaviour(new GestorMensajes(this, 500, subasta));
	}

	private void finalizadaSubasta(Subasta subasta){

	}

	// COMPORTAMIENTOS ==========================================================

	private class GestorRespuestas extends ContractNetResponder {

		private Integer credito;

		public GestorRespuestas(Agent agente, MessageTemplate plantilla, Integer credito){
			super(agente, plantilla);
			this.credito = credito;
		}

		protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
			//System.out.println(myAgent.getLocalName()+": puja actual :"+cfp.getContent());

			ACLMessage respuesta = cfp.createReply();
			respuesta.setPerformative(ACLMessage.PROPOSE);
			respuesta.setContent(""+credito);

			return respuesta;
		}

		protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject){
			System.out.println(myAgent.getLocalName()+": La puja excede mi credito maximo de compra");
		}

		protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) { 
			System.out.println(myAgent.getLocalName()+": PUJO");
			return accept;
		}
	}

	private class GestorMensajes extends TickerBehaviour {

		private MessageTemplate plantillaSTART;
		private MessageTemplate plantillaGANADA;
		private MessageTemplate plantillaFIN;
		private Subasta subasta;

		public GestorMensajes(Agent agente, Integer delay, Subasta subasta){

			super(agente, delay);
			this.subasta = subasta;

			MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			MessageTemplate filtroRequest = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			MessageTemplate filtroGanada = MessageTemplate.MatchContent("GANADA");
			MessageTemplate filtroIdioma = MessageTemplate.MatchLanguage("Español");
			MessageTemplate filtroFin = MessageTemplate.MatchContent("FINALIZADA");

			plantillaSTART = MessageTemplate.and(plantillaSTART, filtroInform);
			plantillaGANADA = MessageTemplate.and(filtroGanada, filtroIdioma);
			plantillaGANADA = MessageTemplate.and(plantillaGANADA, filtroRequest);
			plantillaFIN = MessageTemplate.and(plantillaFIN, filtroFin);
		}

		public void onTick(){

			ACLMessage mensajeSTART = receive(plantillaSTART);
			ACLMessage mensajeGANADA = receive(plantillaGANADA);
			ACLMessage mensajeFIN = receive(plantillaFIN);

			if(mensajeSTART != null){
				System.out.println(myAgent.getLocalName()+": RECIBIDO: "+mensajeSTART.getContent());
			}

			if(mensajeGANADA != null){
				System.out.println(myAgent.getLocalName()+": He ganado la subasta!");
			}

			if(mensajeFIN != null){
				System.out.println(myAgent.getLocalName()+": SUBASTA FINALIZA");
				Comprador.this.finalizadaSubasta(subasta);
				stop();
			}
		}

	}
}