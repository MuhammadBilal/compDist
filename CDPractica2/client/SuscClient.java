import java.io.*;
import java.rmi.*;


public class SuscClient {

   private static String portNum = "3456";
   private static String hostName = "localhost";

   public static void main(String args[]) {

      SuscInterface h = startClient();
      try{
         System.out.println(h.publish(0.4564));
      }catch(Exception e){
         //
      }
   }


   public static SuscInterface startClient(){

      SuscInterface h = null;

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
         String registryURL = "rmi://" + hostName+ ":" + portNum + "/susc";
  
         // find the remote object and cast it to an interface object
         h = (SuscInterface)Naming.lookup(registryURL);
         System.out.println("LOOKUP COMPLETED " );

         // invoke the remote method
         //
         //String message = h.publish(0.356);
         //System.out.println("Client: " + message);
         
      }  
      catch (Exception e) {
         System.out.println("ERROR: Exception in Client: " + e);
      } 
      
      return h;
   }
}
