import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;


public class Server  {

  private static String portNum = "3456";
  
  public static void main(String args[]) {
  
      startServer();   
  }
  
  public static void startServer(){

    InputStreamReader is = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(is);
    String registryURL;
    try{     
      int RMIPortNum = Integer.parseInt(portNum);
      startRegistry(RMIPortNum);
      ServerImp exportedObj = new ServerImp();
      registryURL = "rmi://localhost:" + portNum + "/callback";

      Naming.rebind(registryURL, exportedObj);
      System.out.println("SERVER READY.");
     
      while(true){}

    } catch (Exception re) {
      System.out.println("ERROR: Exception in Server: " + re);
    }

  }

  //This method starts a RMI registry on the local host, if
  //it does not already exists at the specified port number.
  private static void startRegistry(int RMIPortNum) throws RemoteException{
    try {
      Registry registry =  LocateRegistry.getRegistry(RMIPortNum);
      registry.list();  
        // This call will throw an exception
        // if the registry does not already exist
    }
    catch (RemoteException e) { 
      // No valid registry at that port.
      Registry registry = LocateRegistry.createRegistry(RMIPortNum);
    }
  }

}
