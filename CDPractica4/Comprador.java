
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


public class Comprador extends Agent {


	protected void setup() {

		//String libro = "Macbeth";

		//nuevaSubasta(libro, 30);	
		cargaDatos();
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

	private void nuevaSubasta(String libro, Integer credito){

		ServiceDescription servicio = new ServiceDescription();
		servicio.setType(libro);
		servicio.setName("Subasta "+libro);

		DFAgentDescription descripcion = new DFAgentDescription();
		descripcion.addLanguages("Español");
		descripcion.addServices(servicio);

		try {																
			DFService.register(this, descripcion);								// Registra el servicio
		}catch(FIPAException e){
			e.printStackTrace();
		}
		/*
		ACLMessage mensaje = blockingReceive();
		if(mensaje != null){
			System.out.println(getLocalName()+": Recibido mensaje: "+mensaje.getContent());
		}
		*/
		MessageTemplate plantilla = ContractNetResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);

		addBehaviour(new GestorRespuestas(this, plantilla, credito));
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
}