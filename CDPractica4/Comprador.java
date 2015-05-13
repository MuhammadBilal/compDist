
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
import java.util.HashMap;

public class Comprador extends Agent {

	public ArrayList<String> subastas;
	public HashMap<String, GUISubasta> frames;
	private GUIComprador gui;

	protected void setup() {
		gui = new GUIComprador(this);
		gui.setTitle("Comprador "+getLocalName());
		gui.setVisible(true);

		frames = new HashMap();

		this.subastas = new ArrayList();		
	}

	protected void takeDown(){
		System.out.println(getLocalName()+": Me voy de la casa de subastas!");
	}

	// FUNCIONES ===============================================================

	public void nuevaSubasta(String libro, Integer credito){
		Subasta subasta = new Subasta(libro);
		GUISubasta guiSubasta = new GUISubasta(this, subasta);
		guiSubasta.setVisible(true);
		frames.put(libro, guiSubasta);

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

		addBehaviour(new GestorRespuestas(this, plantilla, credito, subasta));

		//addBehaviour(new GestorMensajes(this, 500, subasta));
	}

	private void finalizadaSubasta(Subasta subasta){

	}

	// COMPORTAMIENTOS ==========================================================

	private class GestorRespuestas extends ContractNetResponder {

		private Integer credito;
		private GUISubasta guiSubasta;
		private Subasta subasta;

		public GestorRespuestas(Agent agente, MessageTemplate plantilla, Integer credito, Subasta subasta){
			super(agente, plantilla);
			this.credito = credito;
			this.subasta = subasta;
			this.guiSubasta = Comprador.this.frames.get(subasta.getTituloLibro());
		}

		protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
			//System.out.println(myAgent.getLocalName()+": puja actual :"+cfp.getContent());
			guiSubasta.addMensaje(cfp.getSender().getLocalName()+": "+cfp.getContent());
			guiSubasta.addMensaje("Mi credito para esta subasta = "+credito);
			ACLMessage respuesta = cfp.createReply();
			respuesta.setPerformative(ACLMessage.PROPOSE);
			respuesta.setContent(""+credito);

			return respuesta;
		}

		protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject){
			guiSubasta.addMensaje(reject.getContent());
			guiSubasta.addMensaje("La puja excede mi credito maximo de compra");
			System.out.println(myAgent.getLocalName()+": La puja excede mi credito maximo de compra");
		}

		protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
			guiSubasta.addMensaje(accept.getContent()); 
			guiSubasta.addMensaje("Yo: PUJO");
			System.out.println(myAgent.getLocalName()+": PUJO");
			return accept;
		}
	}

	private class GestorMensajes extends TickerBehaviour {

		private MessageTemplate plantillaSTART;
		private MessageTemplate plantillaGANADA;
		private MessageTemplate plantillaFIN;
		private Subasta subasta;
		private GUISubasta guiSubasta;

		public GestorMensajes(Agent agente, Integer delay, Subasta subasta){

			super(agente, delay);
			this.subasta = subasta;
			this.guiSubasta = Comprador.this.frames.get(subasta.getTituloLibro());

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
				guiSubasta.addMensaje("RECIBIDO: "+mensajeSTART.getContent());
				System.out.println(myAgent.getLocalName()+": RECIBIDO: "+mensajeSTART.getContent());
			}

			if(mensajeGANADA != null){
				guiSubasta.addMensaje("He ganado la subasta!");
				System.out.println(myAgent.getLocalName()+": He ganado la subasta!");
			}

			if(mensajeFIN != null){
				guiSubasta.addMensaje("La subasta ha finalizado.");
				System.out.println(myAgent.getLocalName()+": SUBASTA FINALIZA");
				Comprador.this.finalizadaSubasta(subasta);
				stop();
			}
		}

	}
}