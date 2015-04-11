import java.rmi.*;

public interface PeerInterface extends java.rmi.Remote {

   public void sendMessage(PeerInterface friend, String msg) throws java.rmi.RemoteException;

   public String getUser() throws java.rmi.RemoteException;
}
