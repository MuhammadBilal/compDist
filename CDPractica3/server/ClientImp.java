import java.rmi.*;
import java.util.ArrayList;

public class ClientImp extends UnicastRemoteObject implements ClientInterface {

   private Application clientApp;
   private String user;
   private String pass;

   public ClientImp(Application clientApp){
      super();
      this.clientApp = clientApp;
   }

   public String getUser(){ return this.user; }
   public String getPass(){ return this.pass; }

   public void checkLogin(boolean b){

      if(b){ // Login correct


      }else{ // Error


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
