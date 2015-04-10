import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;   // DAO

public class ServerImp extends UnicastRemoteObject implements ServerInterface {
   private DAOInt DAO;
   private HashMap<String, PeerInterface> friendsOnline;
   private HashMap<String, ClientInterface> clientList;

   public ServerImp() throws RemoteException{
      super();
      friendsOnline = new HashMap();
      clientList = new HashMap();
      DAO = (DAOInt) new DAOImpl();
   } 
   
   public synchronized void register(ClientInterface clientObj, PeerInterface peerObj ) throws java.rmi.RemoteException {

      Client client;
      ArrayList<Client> friends;
      String user = clientObj.getUser();
      String pass = clientObj.getPass();
      String friendName;
      ClientInterface friend;
      PeerInterface peer;
      ArrayList<PeerInterface> friendlist= null;

      if(DAO.checkPass(user, pass)){ // Login OK
         clientObj.checkLogin(true);
         clientList.put(user, clientObj);

         System.out.println("*Iniciada sesion: "+user);

         friends = new ArrayList(DAO.getFriends(user));

         if(friends.size() > 0) { // Has friends   
   
            if(!friendsOnline.isEmpty()){

               friendlist = new ArrayList();

               for(int i=0; i < friends.size(); i++){

                  friendName = friends.get(i).getName();
                  System.out.print("NOMBRE AMIGO #"+i+" "+friendName);
                  
                  if(friendsOnline.containsKey(friendName)){
                     System.out.print("\t [ONLINE]");
                     peer = (PeerInterface) friendsOnline.get(friendName);
                     friendlist.add(peer);

                     friend = (ClientInterface) clientList.get(friendName);
                     friend.connectedUser(peerObj);                              // Se le pasa al clientObj del amigo la peerObj del cliente actual
                  }              
                  System.out.print("\n");
               }
            }

            friendsOnline.put(user, peerObj);


         }else{ 
            // Send a notification - "You can add friends going..."??
         }

         // Send friend list to the client
         if(friendlist != null)  {
            clientObj.receiveFriendlist(friendlist);
         } else {
            clientObj.receiveFriendlist(new ArrayList<PeerInterface>());
         }

         /*
         // Send notification to each friend
         for(int i = 0; i < friendlist.size(); i++){
            friend = friendlist.get(i);
            if(friend != null) friend.connectedUser(peerObj);
         }
          */
      } else {   // Login error
         clientObj.checkLogin(false);
      }
   }

   public synchronized void unregister(ClientInterface clientObj) throws java.rmi.RemoteException {
      //if(clientList.removeElement(clientObj)){  // este if es necesario???
         // eliminar en los amigos la conexion - cerrar ventanas abiertas?/poner led verde en negro?
         
         String user, friendName;
         ArrayList<Client> friends;
         ClientInterface friend;
         PeerInterface pInt;

         user = clientObj.getUser();
   
         friends = new ArrayList(DAO.getFriends(user));

         if(friends.size() > 0){
            
            for(int i = 0; i < friends.size(); i++){
               friendName = friends.get(i).getName();
               friend = (ClientInterface) clientList.get(friendName);
               pInt = (PeerInterface) friendsOnline.get(user);
               friend.disconnectedUser(pInt);
            }

         }

         friendsOnline.remove(user);
         clientList.remove(user);
         System.out.println("*Finalizada sesion: "+user);
      //}else{
        // System.out.println("ERROR: ServerImpl;unregister : client wasn't registered.");
      //}
   }

   public void searchUser(ClientInterface clientObj, String username) throws java.rmi.RemoteException {
      Client c;

      c = DAO.getClient(username);

      if(c != null){
         
      }else{
      

      }
   }
}
