import java.rmi.*;

public interface ServerInterface extends Remote {

   public void register(ClientInterface clientObj) throws java.rmi.RemoteException;

   public void unregister(ClientInterface clientObj) throws java.rmi.RemoteException;

   public void searchUser(String username); 
}
