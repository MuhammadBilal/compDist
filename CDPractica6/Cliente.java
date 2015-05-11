import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;
 
public class Cliente extends Agent {

    private ClienteGUI gui;
    public boolean puedeSuscribir = true;
 
    protected void setup() {
        gui = new ClienteGUI(this);
        gui.setVisible(true);
    }

    protected void takeDown(){
        System.out.println("Finalizado el agente: "+this.getLocalName());
    }

    // FUNCIONES ============================================================
 
    public void nuevaSuscripcion(Integer tiempo, String servidor){
        ACLMessage mensaje = new ACLMessage(ACLMessage.SUBSCRIBE);          // Se crea un mensaje tipo SUSCRIBE para enviar al servidor
        mensaje.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
        mensaje.setContent("Hola, deseo recibir suscripcion");
 
        AID id = new AID();                                                 // Se crea el destinatario del mensaje y se añade a este
        id.setLocalName(servidor);                            
        mensaje.addReceiver(id);
 
        this.addBehaviour(new SuscribirCatalogo(this, mensaje, tiempo));    // Comportamiento que maneja los mensajes del servidor
    }

    public void finalizar(){
        this.takeDown();
    }

    // COMPORTAMIENTOS======================================================

    private class SuscribirCatalogo extends SubscriptionInitiator {
        private int suscripciones = 0;
        private Integer maxSuscripciones;
 
        public SuscribirCatalogo(Agent agente, ACLMessage mensaje, Integer maxSuscripciones) {
            super(agente, mensaje);
            this.maxSuscripciones = maxSuscripciones;
        }
 
        protected void handleAgree(ACLMessage inform) {                     // Maneja la respuesta AGREE del servidor
            String mensaje = "Solicitud aceptada.";
            //System.out.println(myAgent.getLocalName() + ": "+mensaje);
            Cliente.this.gui.addMensaje(mensaje);
            Cliente.this.puedeSuscribir = false;                            // Indica que hay una suscripcion en curso
        }
 
        protected void handleRefuse(ACLMessage inform) {                    // Maneja la respuesta REFUSE del servidor
            String mensaje = "Solicitud rechazada.";
            //System.out.println(myAgent.getLocalName() + ": "+mensaje);
            Cliente.this.gui.addMensaje(mensaje);
        }
 
        protected void handleInform(ACLMessage inform) {                    // Maneja la respuesta INFORM del servidor
            String mensaje = "Mensaje recibido (#"+this.suscripciones+"): "+inform.getContent();
            //System.out.println(myAgent.getLocalName()+": "+mensaje);
            Cliente.this.gui.addMensaje(mensaje);
 
            this.suscripciones++;                                           // Se incrementa el numero de suscripciones que desea recibir
            if (this.suscripciones == maxSuscripciones) {                   // En caso de alcanzar las suscripciones deseadas
                                                                            // se envia la cancelacion al servidor
                this.cancel(inform.getSender(), false);

                this.cancellationCompleted(inform.getSender());             // Se comprueba que la cancelacion se ha realizado correctamente
            }
        }
 
        protected void handleFailure(ACLMessage failure) {                  // Maneja los errores (respuesta FAILURE)
            String mensaje;
            
            if (failure.getSender().equals(myAgent.getAMS())) {             //Se comprueba si el fallo viene del AMS o de otro agente.
                mensaje = "El destinatario no existe.";
            } else {
                mensaje = "Se ha producido un fallo (recibido FAILURE)";
            }
            //System.out.println(myAgent.getLocalName()+": "+mensaje);
            Cliente.this.gui.addMensaje(mensaje);
        }
 
        public void cancellationCompleted(AID agente) {                     // Comprueba que la cancelacion se ha realizado correctamente
            //Creamos una plantilla para solo recibir los mensajes del agente que va a cancelar la suscripción
            MessageTemplate template = MessageTemplate.MatchSender(agente);
            ACLMessage mensaje = blockingReceive(template);
            String msg;
 
            if (mensaje.getPerformative() == ACLMessage.INFORM){            // Cancelacion correcta
                msg = "Suscripcion cancelada correctamente";
                Cliente.this.puedeSuscribir = true;                         // Indica al cliente que la suscripcion ha finalizado
            }else{                                                          // Hubo un fallo en la cancelacion
                msg = "Error realizando la cancelacion de la suscripcion!";
            }
            //System.out.println(myAgent.getLocalName()+": "+msg);
            Cliente.this.gui.addMensaje(msg);
        }
    }
}