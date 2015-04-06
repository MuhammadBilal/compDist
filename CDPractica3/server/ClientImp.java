import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class ClientImp extends UnicastRemoteObject implements ClientInterface {

   private Application clientApp;
   private String user;
   private String pass;

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

   public void receiveFriendlist(ArrayList<ClientInterface> friendlist) throws java.rmi.RemoteException {

   }

   public void receiveNotification(ClientInterface friend) throws java.rmi.RemoteException {

   }

   public void disconnectedUser(ClientInterface friend) throws java.rmi.RemoteException {

   }

   public void close() throws java.rmi.RemoteException {
      clientApp.logout();
   }

}
