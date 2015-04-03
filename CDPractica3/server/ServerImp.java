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
      ArrayList<ClientInterface> friendlist, aux;
      // DAO = (DAOInt) new DAOImpl(); //*for PabloPunk* : en caso de que vuelva a dar problemas the same thing  
      String user = clientObj.getUser();
      String pass = clientObj.getPass();
      String friendName;
      ClientInterface friend;

      if(DAO.checkPass(user, pass)){ // Login OK
         clientObj.checkLogin(true);
         clientList.addElement(clientObj);
         
         friends = new ArrayList(DAO.getFriends(user));

         if(friends!=null){

            for(int i = 0; i < friends.size(); i++){

               friendlist = new ArrayList();

               if(clients.containsKey(friendName)){
                  Iterator it = clientList.vector();
                  while(it.hasNext()){
                     if(it.next().getUser().equals(friendName)){
                        friend = it.next();
                        if(friend != null)   friendlist.add(friend);
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
         clientObj.receiveFriendlist(friendlist);

         // Send notification to each friend
         for(int i = 0; i < friendlist.size(); i++){
            friend = friendlist.get(i);
            if(friend != null) friend.receiveNotification(clientObj);
         }

      }else{   // Login error
         clientObj.checkLogin(false);
      }
   }

   public synchronized void unregister(ClientInterface clientObj) throws java.rmi.RemoteException {
      if(clientList.removeElement(clientObj)){  // este if es necesario???
         // eliminar en los amigos la conexion - cerrar ventanas abiertas?/poner led verde en negro?
         String user, friendName;
         ArrayList<ClientInterface> friends, aux;
         
         user = clientObj.getName();
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
         clientObj.close();
      }else{
         System.out.println("ERROR: ServerImpl;unregister : client wasn't registered.");
      }
   }

}
