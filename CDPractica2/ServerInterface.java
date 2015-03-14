import java.rmi.*;


public interface ServerInterface extends Remote {

  public String sayHello() throws java.rmi.RemoteException;

  
  public void registerForCallback(ClientInterface callbackClientObject, int time) throws java.rmi.RemoteException;

// This remote method allows an object client to 
// cancel its registration for callback

  public void unregisterForCallback(ClientInterface callbackClientObject) throws java.rmi.RemoteException;
}
