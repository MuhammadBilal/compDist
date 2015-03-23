
package controller;

import java.util.ArrayList;
import java.sql.*;

import model.*;

public class DAOImpl implements DAOint {

   private DBController controller;

   public DAOImpl(DBController controller){
      this.controller = controller;
   }

   public ArrayList<Client> getFriends(Client client){
      return getFriends(client.getName());
   }

   public ArrayList<Client> getFriends(String name){
      // - -
   }

   public void newClient(Client client){
   
   }

   public void removeClient(Client client){
      removeClient(client.getName());
   }

   public void removeClient(String name){
   
   }

   public void changeClientPass(Client client, Strig pass){
      changeClientPass(client.getName());
   }

   public void changeClientPass(String name, String pass){
   
   }

   public boolean checkPass(Client client, String pass){
      return checkPass(client.getName(), pass);
   }

   public boolean checkPass(String name, String pass){

   }
}
