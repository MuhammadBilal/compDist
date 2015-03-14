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

   public void readNumber() throws java.rmi.RemoteException{
      double x = Math.random();
      System.out.println("number: "+x);
      doCallbacks(""+x);
   }

  public synchronized void registerForCallback(ClientInterface callbackClientObject, int time) throws java.rmi.RemoteException{
      // store the callback object into the vector
      if (!(clientList.contains(callbackClientObject))) {
         clientList.addElement(callbackClientObject);
         clients.put(callbackClientObject, time*2);
         System.out.println("Registered new client ");
         
         
         //doCallbacks();
    } // end if
  }  

// This remote method allows an object client to 
// cancel its registration for callback
// @param id is an ID for the client; to be used by
// the server to uniquely identify the registered client.
  public synchronized void unregisterForCallback(ClientInterface callbackClientObject) throws java.rmi.RemoteException{
    if (clientList.removeElement(callbackClientObject)) {
      System.out.println("Unregistered client ");
      clients.remove(callbackClientObject);
    } else {
       System.out.println("unregister: clientwasn't registered.");
    }
  } 

  private synchronized void doCallbacks(String number) throws java.rmi.RemoteException{
    // make callback to each registered client
    //System.out.println("**************************************\n"+ "Callbacks initiated ---");

       for (int i = 0; i < clientList.size(); i++){
           
         // convert the vector object to a callback object
         ClientInterface nextClient = (ClientInterface)clientList.elementAt(i);
         int t = clients.get(nextClient);
         if(t > 0){
            System.out.println("doing "+ i +"-th callback\n"); 
            t--;
            clients.remove(nextClient);
            clients.put(nextClient, t);
            nextClient.notifyMe("num: "+number);
            
        
         }else{
            unregisterForCallback(nextClient);
         }
         // invoke the callback method
         // - nextClient.notifyMe("Number of registered clients="+  clientList.size());
         //nextClient.notifyMe("num: "+number);
       }// end for
      

    //System.out.println("********************************\n"+"Server completed callbacks ---");
  } // doCallbacks

}
