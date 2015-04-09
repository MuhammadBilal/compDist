import java.rmi.*;
import java.util.ArrayList;

public interface ClientInterface extends java.rmi.Remote {


   public String getUser() throws java.rmi.RemoteException;

   public String getPass() throws java.rmi.RemoteException;

   public void checkLogin(boolean b) throws java.rmi.RemoteException;

   public void receiveFriendlist(ArrayList<ClientInterface> friendlist) throws java.rmi.RemoteException;

   public void connectedUser(ClientInterface friend) throws java.rmi.RemoteException;

   public void disconnectedUser(ClientInterface friend) throws java.rmi.RemoteException;

   public void receiveNotification(String notification) throws java.rmi.RemoteException;

   public void receiveError(String error) throws java.rmi.RemoteException;
}
