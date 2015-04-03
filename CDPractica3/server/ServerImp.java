import java.rmi.*;

public class ServerImp extends UnicastRemoteObject implements ServerInterface {

   // clientObj : (user, pass)
   public void register(ClientInterface clientObj) throws java.rmi.RemoteException {
      // Registro:
      //    - Comprobar login en la BD (si es correcto envia true al cliente y continua la ejecucion del metodo, si no, envia false)
      //    - Enviar lista de amigos conectados
      //    - Enviar notificacion a todos los amigos 
   
   }

   public void unregister(ClientInterface clientObj) throws java.rmi.RemoteException {

   }

}
