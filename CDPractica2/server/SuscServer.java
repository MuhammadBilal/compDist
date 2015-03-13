import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
import java.util.HashMap;


public class SuscServer  {
  
   private static String portNum = "3456";
   private HashMap<String, Subscriber> subs;

   public static void main(String args[]) {
  
      startServer();
      randomNumbers();
   }

   // This method creates a random number after 0.5 seconds
   private static void randomNumbers(){
      try{
         while(true){
            Thread.sleep(500);
            Thread newRandom = new Thread(new Runnable(){
               @Override
               public void run(){ 
                  System.out.println("num: "+Math.random());
               }
            });

            newRandom.start();
         }
      }catch(Exception e){
         System.out.println("ERROR: exception random number: "+ e.getMessage());
      }
   }

   private static void startServer() {

      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      //String portNum;
      String registryURL;

      try{     
         System.out.println("Enter the RMIregistry port number:");
         //portNum = (br.readLine()).trim();
         int RMIPortNum = Integer.parseInt(portNum);

         startRegistry(RMIPortNum);
         SuscImpl exportedObj = new SuscImpl();

         registryURL = "rmi://localhost:" + portNum + "/susc";
         Naming.rebind(registryURL, exportedObj);

         System.out.println("Server registered.  Registry currently contains:");
         // list names currently in the registry
         listRegistry(registryURL); 
         System.out.println("SERVER READY.");

      }
      catch (Exception re) {
         System.out.println("ERROR: Exception in SuscServer: " + re);
      }


   }

   // This method starts a RMI registry on the local host, if it
   // does not already exists at the specified port number.
   private static void startRegistry(int RMIPortNum) throws RemoteException{
      try {
         Registry registry = LocateRegistry.getRegistry(RMIPortNum);
         registry.list();  // This call will throw an exception
                            // if the registry does not already exist
      }
      catch (RemoteException e) { 
         // No valid registry at that port.
         System.out.println ("RMI registry cannot be located at port " + RMIPortNum);
         Registry registry = LocateRegistry.createRegistry(RMIPortNum);
         System.out.println("RMI registry created at port " + RMIPortNum);
      }
   }

  // This method lists the names registered with a Registry object
  private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
       
       System.out.println("Registry " + registryURL + " contains: ");
       String [ ] names = Naming.list(registryURL);

       for (int i=0; i < names.length; i++)
          System.out.println(names[i]);
  } 
     
} 
