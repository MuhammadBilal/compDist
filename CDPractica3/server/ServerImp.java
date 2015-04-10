import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;

import model.*;   // DAO

public class ServerImp extends UnicastRemoteObject implements ServerInterface {
   private DAOInt DAO;
   private Vector clientList;
   private HashMap<String, ArrayList<ClientInterface>> clients;   // (String clientName, ArrayList<ClientInterface> friends)
   private HashMap<String, PeerInterface> friendsOnline;
   private HashMap<String, ClientInterface> clientList2;

   public ServerImp() throws RemoteException{
      super();
      clients = new HashMap();
      friendsOnline = new HashMap();
      clientList = new Vector();
      clientList2 = new HashMap();
      DAO = (DAOInt) new DAOImpl();
   } 
   
   public synchronized void register(ClientInterface clientObj, PeerInterface peerObj ) throws java.rmi.RemoteException {

      Client client;
      ArrayList<Client> friends;
      ArrayList<ClientInterface> friendlist=null, aux=null;
      String user = clientObj.getUser();
      String pass = clientObj.getPass();
      String friendName;
      ClientInterface friend;
      PeerInterface peer;
      ArrayList<PeerInterface> friendlist2= null, aux2 = null;

      if(DAO.checkPass(user, pass)){ // Login OK
         clientObj.checkLogin(true);
         clientList.addElement(clientObj);
         clientList2.put(user, clientObj);

         System.out.println("*Iniciada sesion: "+user);

         friends = new ArrayList(DAO.getFriends(user));

         if(friends.size() > 0) { // Has friends

            friendlist = new ArrayList();
   
            if(!friendsOnline.isEmpty()){

               friendlist2 = new ArrayList();

               for(int i=0; i < friends.size(); i++){

                  friendName = friends.get(i).getName();
                  System.out.print("NOMBRE AMIGO #"+i+" "+friendName);
                  
                  if(friendsOnline.containsKey(friendName)){
                     System.out.print("\t [ONLINE]");
                     peer = (PeerInterface) friendsOnline.get(friendName);
                     friendlist2.add(peer);

                     friend = (ClientInterface) clientList2.get(friendName);
                     //friend.connectedUser(peerObj);                              // Se le pasa al clientObj del amigo la peerObj del cliente actual
                  }              
                  System.out.print("\n");
               }
            }

            friendsOnline.put(user, peerObj);


         }else{ 
            // Send a notification - "You can add friends going..."??
         }

         // Send friend list to the client
         if(friendlist2 != null)  {
            clientObj.receiveFriendlist(friendlist2);
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
      if(clientList.removeElement(clientObj)){  // este if es necesario???
         // eliminar en los amigos la conexion - cerrar ventanas abiertas?/poner led verde en negro?
         String user, friendName;
         ArrayList<ClientInterface> friends, aux;
         
         user = clientObj.getUser();
         friends = clients.get(user);
         
         if(friends != null){
            
            for(int i = 0; i < friends.size(); i++){
               friendName = friends.get(i).getUser();
               aux = new ArrayList(clients.get(friendName));
               
               aux.remove(clientObj);
               clients.remove(friendName);
               clients.put(friendName, aux);
               // **** OBTENER EL PEER OBJ DE LA LISTA DE CONECTADOS Y PASARLO A DISCONNECTED USER
               //friends.get(i).disconnectedUser(PeerObj); // Notificacion desconexion a los clientes en la lista de amigos
            }


         }else{ 
         }

         clients.remove(user);
         clientList2.remove(user);
         friendsOnline.remove(user);
         System.out.println("*Finalizada sesion: "+user);
      }else{
         System.out.println("ERROR: ServerImpl;unregister : client wasn't registered.");
      }
   }

   public void searchUser(ClientInterface clientObj, String username) throws java.rmi.RemoteException {
      Client c;

      c = DAO.getClient(username);

      if(c != null){
         
      }else{
      

      }
   }
}
