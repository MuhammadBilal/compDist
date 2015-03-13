
import java.rmi.*;
import java.rmi.server.*;

public class SuscImpl extends UnicastRemoteObject implements SuscInterface {

   public SuscImpl() throws RemoteException{
      super();
   } 

   public String publish(double number) throws java.rmi.RemoteException{
      return ""+number+"";
   }

}
