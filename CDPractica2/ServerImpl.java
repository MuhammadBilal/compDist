import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.util.HashMap;


public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

   private Vector clientList;
   private HashMap<ClientInterface, Integer> clients;

   public ServerImpl() throws RemoteException {
      super();
      clientList = new Vector();
      clients = new HashMap();   
   }
 
   public String sayHello() throws java.rmi.RemoteException {
      return("hello");
   }

   public void generateNumber() throws java.rmi.RemoteException{
      double x = Math.random();
      System.out.println("random: "+x);
      doCallbacks(""+x);
   }

  public synchronized void registerForCallback(ClientInterface callbackClientObject, Integer time) throws java.rmi.RemoteException{
      // store the callback object into the vector
      if (!(clientList.contains(callbackClientObject))) {
         clientList.addElement(callbackClientObject);
         clients.put(callbackClientObject, time*2);   // 2Hz = 0.5 sec.
         System.out.println("Registered new client ");
         
    } 
  }  

  public synchronized void unregisterForCallback(ClientInterface callbackClientObject) throws java.rmi.RemoteException{
    if (clientList.removeElement(callbackClientObject)) {
      System.out.println("Unregistered client ");
      clients.remove(callbackClientObject);
      callbackClientObject.notifyEnd("Finalizada suscripcion");
    } else {
       System.out.println("unregister: client wasn't registered.");
    }
  } 

  private synchronized void doCallbacks(String number) throws java.rmi.RemoteException{

       for (int i = 0; i < clientList.size(); i++){
           
         ClientInterface nextClient = (ClientInterface)clientList.elementAt(i);
         int t = clients.get(nextClient);
         if(t > 0){
            System.out.println("doing callback to client #"+ i +"\n"); 
            t--;
            clients.remove(nextClient);
            clients.put(nextClient, t);
            nextClient.notifyMe(" "+number);
        
         }else{
            unregisterForCallback(nextClient);
            //nextClient.notifyEnd("Finalizada suscripcion");
         }
       }
      

  }

}
