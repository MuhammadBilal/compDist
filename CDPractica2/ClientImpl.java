import java.rmi.*;
import java.rmi.server.*;


public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
  
   private Client c;

   public ClientImpl(Client c) throws RemoteException {
      super();
      this.c = c;
   }

   public void notifyEnd(String txt){
      c.appendText(txt);
   }

   public String notifyMe(String message){
      String returnMessage = "Call back received: " + message;
      System.out.println(returnMessage);
      c.appendText(message);
      return returnMessage;
   }      

}
