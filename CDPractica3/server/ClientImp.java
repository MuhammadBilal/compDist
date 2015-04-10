import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class ClientImp extends UnicastRemoteObject implements ClientInterface, PeerInterface {

   private Application clientApp;
   private String user;
   private String pass;

   public ClientImp(Application clientApp, String user) throws RemoteException {
      super();
      this.clientApp = clientApp;
      this.user = user;
      this.pass = null;
   }

   public ClientImp(Application clientApp, String user, String pass) throws RemoteException {
      super();
      this.clientApp = clientApp;
      this.user = user;
      this.pass = pass;
   }

   public String getUser() throws java.rmi.RemoteException { return this.user; }
   public String getPass() throws java.rmi.RemoteException { return this.pass; }

   public void checkLogin(boolean b) throws java.rmi.RemoteException {

      if(b){ // Login correct
         clientApp.loged();
      }else{ // Error
         clientApp.errorLogin();
      }
   }

   public void receiveFriendlist(ArrayList<PeerInterface> friendlist) throws java.rmi.RemoteException {
      clientApp.updateFriendList(friendlist); // Upates friendlist in the interface
   }

   public void connectedUser(PeerInterface friend) throws java.rmi.RemoteException {
      if (friend != null) {
         clientApp.connectedUser(friend); // Upates friendlist in the interface
      } else {
         System.out.println("Null friend connected received from server");
      }
   }

   public void disconnectedUser(PeerInterface friend) throws java.rmi.RemoteException {
      if (friend != null) {
         clientApp.disconnectedUser(friend); // Upates friendlist in the interface
      } else {
         System.out.println("Null friend disconnected received from server");
      }
   }

   public void receiveNotification(String notification) throws java.rmi.RemoteException {
      clientApp.setNotification(notification);
   }

   public void receiveError(String error) throws java.rmi.RemoteException {
      clientApp.setError(error);
   }

   public void sendMessage(String msg) throws java.rmi.RemoteException {
      System.out.println("Implementar SEND MENSAJE");

   }
}
