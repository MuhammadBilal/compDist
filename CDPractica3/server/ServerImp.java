import java.rmi.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Vector;

import model.*;   // DAO

public class ServerImp extends UnicastRemoteObject implements ServerInterface {
   private DAOInt DAO;
   private Vector clientList;
   private HashMap<String, ArrayList<String>> clients;   // (String clientName, ArrayList<String> friends)

   public ServerImp() throws RemoteException{
      super();
      clients = new HashMap();
      clientList = new Vector();
      DAO = (DAOInt) new DAOImpl();
   } 
   // clientObj : (user, pass)
   public synchronized void register(ClientInterface clientObj) throws java.rmi.RemoteException {
      // Registro:
      //    - Enviar lista de amigos conectados
      //    - Enviar notificacion a todos los amigos 
      Client client;
      ArrayList<Client> friends;
      ArrayList<String> friendsCon, aux;
      // DAO = (DAOInt) new DAOImpl(); //*for PabloPunk* : en caso de que vuelva a dar problemas the same thing  
      String user = clientObj.getUser();
      String pass = clientObj.getPass();
      String friendName;

      if(DAO.checkPass(user, pass)){ // Login OK
         clientObj.checkLogin(true);
         clientList.addElement(clientObj);
         
         friends = new ArrayList(DAO.getFriends(user));
         if(friends!=null){
            for(int i = 0; i < friends.size(); i++){
               friendsCon = new ArrayList();
               friendName = friends.get(i).getName();

               if(clients.containsKey(friendName)){
                  friendsCon.add(friendName);
                  aux = new ArrayList(clients.get(friendName));
                  if(aux == null)   aux = new ArrayList();

                  aux.add(user);
                  clients.remove(friendName);
                  clients.put(friendName, aux);
               }

            }
         }else{ // This guy is a looser
            // Send a notification - "You can add friends going..."??
         }

         // Send friend list to the client
         clientObj.receiveFriendlist(ArrayList<String> friendlist);

         // Send notification to each friend
      }else{   // Login error
         clientObj.checkLogin(false);
      }
   }

   public synchronized void unregister(ClientInterface clientObj) throws java.rmi.RemoteException {
      if(clientList.removeElement(clientObj)){  // este if es necesario???
         // eliminar en los amigos la conexion - cerrar ventanas abiertas?/poner led verde en negro?
      }else{
         System.out.println("ERROR: ServerImpl;unregister : client wasn't registered.");
      }
   }

}
