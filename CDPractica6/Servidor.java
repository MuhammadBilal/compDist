import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionResponder;
import jade.proto.SubscriptionResponder.Subscription;
import jade.proto.SubscriptionResponder.SubscriptionManager;

import java.util.HashSet;
import java.util.Set;

public class Servidor extends Agent {

	private Set<Subscription> suscripciones = new HashSet<Subscription>();	// Tabla que contiene las suscripciones.

	protected void setup(){
		System.out.println(this.getLocalName()+": Esperando suscripciones...");
																			// Plantilla para admitir solo mensajes SUBSCRIBE
        MessageTemplate plantilla = SubscriptionResponder.createMessageTemplate(ACLMessage.SUBSCRIBE);

        this.addBehaviour(new EnviarDatos(this, (long) 500));				// Comportamiento que envia los datos a las suscripciones 2hz =0,5 segundos

        SubscriptionManager manager = new SubscriptionManager() {			// Registra y elimina las suscripciones
 
            public boolean register(Subscription suscripcion) {
                suscripciones.add(suscripcion);
                return true;
            }
 
            public boolean deregister(Subscription suscripcion) {
                suscripciones.remove(suscripcion);
                return true;
            }
        };

        this.addBehaviour(new CrearSuscripcion(this, plantilla, manager));	// Comportamiento que maneja las respuestas de suscripcion
	}

	// FUNCIONES ============================================================

	private boolean compruebaMensaje(String propuesta){
		return (propuesta.length() > 2);
	}

	// COMPORTAMIENTOS ======================================================

	private class EnviarDatos extends TickerBehaviour {						// Envia los datos generados a cada suscriptor

		public EnviarDatos(Agent agente, long delay){						// Se pasa el agente y el delay entre un mensaje y otro
			super(agente, delay);
		}

		public void onTick() {												// Se llama este metodo cada 'delay' milisegundos
			ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
			double dato;
			dato = Math.random();											// Se genera un numero aleatorio entre 0 y 1
			mensaje.setContent(""+dato);

			System.out.println(myAgent.getLocalName()+": "+dato);

			for(Subscription suscripcion : Servidor.this.suscripciones){	// Se envia el mensaje a cada agente suscrito
				suscripcion.notify(mensaje);
			}
		}
	}


	private class CrearSuscripcion extends SubscriptionResponder {

		private Subscription suscripcion;

		public CrearSuscripcion(Agent agente, MessageTemplate plantilla, SubscriptionManager manager) {
			super(agente, plantilla, manager);
		}

		protected ACLMessage handleSubscription(ACLMessage propuesta) throws NotUnderstoodException {
			System.out.println(myAgent.getLocalName()+": SUSCRIBE recibido de: "+propuesta.getSender().getLocalName());
			//System.out.println(myAgent.getLocalName()+": La propuesta es");

			if(Servidor.this.compruebaMensaje(propuesta.getContent())){		// Comprueba los datos de la propuesta

				this.suscripcion = this.createSubscription(propuesta);		// Crea la suscripcion

				try{
					this.mySubscriptionManager.register(suscripcion);		// El manager registra la suscripcion
				}catch(Exception e){
					System.out.println(myAgent.getLocalName()+": Error en el registro de la suscripcion: "+e.getMessage());
				}

				ACLMessage agree = propuesta.createReply();					// Acepta la propuesta y envia AGREE
				agree.setPerformative(ACLMessage.AGREE);
				return agree;
			}else{
				ACLMessage refuse = propuesta.createReply();				// Rechaza la propuesta y envia REFUSE
				refuse.setPerformative(ACLMessage.REFUSE);
				return refuse;
			}
		}

		protected ACLMessage handleCancel(ACLMessage cancelacion) {
			System.out.println(myAgent.getLocalName()+": CANCEL recibido de:"+cancelacion.getSender().getLocalName());

			try{
				this.mySubscriptionManager.deregister(this.suscripcion);	// El manager elimina la suscripcion
			}catch(Exception e){
				System.out.println(myAgent.getLocalName()+": Error en la cancelacion de la suscripcion: "+e.getMessage());
			}

			ACLMessage cancelado = cancelacion.createReply();				// Acepta la cancelacion y envia INFORM
			cancelado.setPerformative(ACLMessage.INFORM);
			//cancelado.setContent("Suscripcion cancelada correctamente");
			return cancelado;
		}
	}
}
