import java.rmi.*;
import java.util.ArrayList;

public class ClientImp extends UnicastRemoteObject implements ClientInterface {

   private Application clientApp;
   private String user;
   private String pass;

   public ClientImp(Application clientApp, String user, String pass){
      super();
      this.clientApp = clientApp;
      this.user = user;
      this.pass = pass;
   }

   public String getUser(){ return this.user; }
   public String getPass(){ return this.pass; }

   public void checkLogin(boolean b){

      if(b){ // Login correct
         clientApp.loged();
      }else{ // Error
         clientApp.errorLogin();
      }
   }

   public void receiveFriendlist(ArrayList<ClientInterface> friendlist){

   }

   public void receiveNotification(ClientInterface friend){

   }

   public void disconnectedUser(ClientInterface friend){

   }

   public void close(){
      clientApp.logout();
   }

}
