import java.io.*;
import java.rmi.*;


public class Client {

   private static String hostName = "localhost";
   private static String portNum = "3456";

  public static void main(String args[]) {
    try {
      int RMIPort;         
      //String hostName;
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
     
      //System.out.println("Enter the RMIRegistry host namer:");
      //hostName = br.readLine();
      //System.out.println("Enter the RMIregistry port number:");
      //String portNum = br.readLine();
      RMIPort = Integer.parseInt(portNum); 
      System.out.println("Enter how many seconds to stay registered:");
      String timeDuration = br.readLine();
      int time = Integer.parseInt(timeDuration);
      String registryURL = "rmi://localhost:" + portNum + "/callback";  
      
      // find the remote object and cast it to an 
      //   interface object
      ServerInterface h =(ServerInterface)Naming.lookup(registryURL);
      System.out.println("Lookup completed " );

      System.out.println("Server said " + h.sayHello());
      ClientInterface callbackObj = new ClientImpl();
      // register for callback
      
      System.out.println("------------------------------");
      

      h.registerForCallback(callbackObj, time); //
      System.out.println("Registered for callback.");
      
      /*
      try {
        Thread.sleep(time * 1000);
      }
      catch (InterruptedException ex){ // sleep over
      }

      h.unregisterForCallback(callbackObj);
      System.out.println("Unregistered for callback.");
      */
    } 
    catch (Exception e) {
      System.out.println("Exception in CallbackClient: " + e);
    }
  } 

}
