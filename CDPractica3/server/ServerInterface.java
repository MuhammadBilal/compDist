import java.rmi.*;

public interface ServerInterface extends Remote {

   public void register(ClientInterface clientObj, PeerInterface peerObj) throws java.rmi.RemoteException;

   public void unregister(ClientInterface clientObj) throws java.rmi.RemoteException;

   public void searchUser(ClientInterface clientObj, String username) throws java.rmi.RemoteException; 
}
