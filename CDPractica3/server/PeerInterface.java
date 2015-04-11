import java.rmi.*;

public interface PeerInterface extends java.rmi.Remote {

   public void sendMessage(String msg) throws java.rmi.RemoteException;

   public void startChat(PeerInterface friend) throws java.rmi.RemoteException;

   public String getUser() throws java.rmi.RemoteException;
}
