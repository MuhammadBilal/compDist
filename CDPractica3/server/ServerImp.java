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

   public ServerImp() throws RemoteException{
      super();
      clients = new HashMap();
      clientList = new Vector();
      DAO = (DAOInt) new DAOImpl();
   } 
   
   public synchronized void register(ClientInterface clientObj) throws java.rmi.RemoteException {
      
      Client client;
      ArrayList<Client> friends;
      ArrayList<ClientInterface> friendlist=null, aux=null;
      String user = clientObj.getUser();
      String pass = clientObj.getPass();
      String friendName;
      ClientInterface friend;

      if(DAO.checkPass(user, pass)){ // Login OK
         clientObj.checkLogin(true);
         clientList.addElement(clientObj);
         
         friends = new ArrayList(DAO.getFriends(user));

         if(friends.size() > 0) { // Has friends

            friendlist = new ArrayList();

            for(int i = 0; i < friends.size(); i++){
               
               friendName = friends.get(i).getName();

               if(clients.containsKey(friendName)){
                  Iterator it = clientList.iterator();
                  while(it.hasNext()){
                     friend = (ClientInterface) it.next();
                     if(friend.getUser().equals(friendName)){
                        friendlist.add(friend);
                     }
                  }
                  aux = new ArrayList(clients.get(friendName));
                  if(aux == null)   aux = new ArrayList();

                  aux.add(clientObj);
                  clients.remove(friendName);
                  clients.put(friendName, aux);
               }
            }

         }else{ // This guy is a looser
            // Send a notification - "You can add friends going..."??
         }

         // Send friend list to the client
         if(friendlist != null)  {
            clientObj.receiveFriendlist(friendlist);
         } else {
            //**************************************
            // aqui se produce el ERROR: register for callback exception: null
            clientObj.receiveFriendlist( new ArrayList<ClientInterface>() );
         }

         // Send notification to each friend
         for(int i = 0; i < friendlist.size(); i++){
            friend = friendlist.get(i);
            if(friend != null) friend.connectedUser(clientObj);
         }

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

               friends.get(i).disconnectedUser(clientObj); // Notificacion desconexion a los clientes en la lista de amigos
            }


         }else{ // Such a looser..
         }

         clients.remove(user);
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
