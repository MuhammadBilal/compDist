
package model;

import java.util.ArrayList;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

import controller.*;

public class DAOImpl implements DAOInt {

   private DBController controller;

   // Constructors
   public DAOImpl(){
      controller = new DBController();
   }
   
   public DAOImpl(DBController controller){
      this.controller = controller;
   }

   // Methods
   public ArrayList<Client> getFriends(Client client){ // polymorph
      return getFriends(client.getName());
   }

   public void newRequest(String clientFrom, String clientTo) {Connection con = null;
      Statement stm = null;

      try{
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);

         stm = con.createStatement();

         stm.execute("INSERT INTO requests values ('"+clientFrom+"','"+clientTo+"');");

         con.commit();
      }catch(SQLException e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
         }
      }
   }

   public ArrayList<Client> getRequests(String clientTo){ // obtains friends requesting this client name
      ArrayList<Client> requests = new ArrayList<Client>();
      Connection con = null;
      Statement stm = null;

      String clientFrom;
      Client req;

      try{
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);
         stm = con.createStatement();

         ResultSet rs = stm.executeQuery("SELECT clientFrom FROM requests WHERE clientTo ='"+clientTo+"';");

         while(rs.next()){
            clientFrom = rs.getString("clientFrom");
            req = new Client(clientFrom);
            requests.add(req);
         }

         con.commit();
      }catch(SQLException e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
         }
      }

      if (requests.isEmpty()) return null;

      return requests;
   }

   public ArrayList<Client> getFriends(String clientName){
      
      ArrayList<Client> friends = new ArrayList<Client>();
      Connection con = null;
      Statement stm = null;

      String friendName;
      Client friend;

      try{
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);
         stm = con.createStatement();

         ResultSet rs = stm.executeQuery("SELECT client2 FROM friends WHERE client1 ='"+clientName+"';");

         while(rs.next()){
            friendName = rs.getString("client2");
            friend = new Client(friendName);
            friends.add(friend);
         }

         con.commit();
      }catch(SQLException e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
         }
      }

      return friends;
   }

   public boolean isFriend(String client1, String client2) {
      Connection con = null;
      Statement stm = null;

      boolean ret = false;

      try{
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);
         stm = con.createStatement();

         ResultSet rs = stm.executeQuery("SELECT client2 FROM friends WHERE client1 ='"+client1+"';");

         while(rs.next()){
            String friendName = rs.getString("client2");

            if (friendName.equals(client2)) {
               System.out.println(friendName + " es amigo de " + client1);
               ret = true; // Is a friend
               break;
            }
         }

         con.commit();
      }catch(SQLException e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
         }
      }

      return ret;
   }

   public Client getClient(String clientName){
      
      Client resultClient = null;
      Connection con = null;
      Statement stm = null;

      try{
         resultClient = new Client();
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);
         stm = con.createStatement();

         ResultSet rs = stm.executeQuery("SELECT * FROM clients WHERE name ='"+clientName+"';");

         if (rs.next()) {
            resultClient.setName(clientName);
            resultClient.setEmail(rs.getString("email"));
            resultClient.setPassword(rs.getString("pass"));
            resultClient.setDate(rs.getString("date"));
         } else {
            stm.close();
            con.close();
            return null;
         }

         con.commit();
      }catch(SQLException e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
         }
      }

      return resultClient;
   }

   // returns false if it fails (or the name already exists)
   public boolean newClient(Client client){
      Connection con = null;
      Statement stm = null;

      String name = client.getName();
      String email = client.getEmail();
      String pass = getHash(client.getPassword());
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String date = sdf.format(dt);

      try{
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);

         stm = con.createStatement();

         ResultSet rs = stm.executeQuery("SELECT name FROM clients WHERE name='"+name+"';");

         if(!rs.first()){
            boolean result;
            result = stm.execute("INSERT INTO clients (name, email, pass, date) VALUES('"
               +name+"','"+email+"','"+pass+"','"+date+"');");
            //if(result){}

         }else{
            System.out.println("ERROR: El usuario a introducir ya existe en la BD.");
            stm.close();
            con.close();
            return false;
         }

         con.commit();
      }catch(SQLException e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
            return false;
         }
      }

      return true;
   }

   public void removeClient(Client client){ // polymorph
      removeClient(client.getName());
   }

   public void removeClient(String clientName){
      Connection con = null;
      Statement stm = null;

      try{
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);

         stm = con.createStatement();

         boolean result = stm.execute("DELETE FROM friends WHERE client1 ='"+clientName+"' OR client2='"+clientName+"';");

         result = stm.execute("DELETE FROM clients WHERE name='"+clientName+"';");

         con.commit();
      }catch(SQLException e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
         }
      }
   }

   public void changeClientPass(Client client, String pass){ // polymorph
      changeClientPass(client.getName(), pass);
   }

   public void changeClientPass(String clientName, String pass){
      Connection con = null;
      Statement stm = null;

      pass = getHash(pass); // sha512 of the string

      try{
         controller = new DBController();
         con = controller.getConnection();
         con.setAutoCommit(false);

         stm = con.createStatement();

         int result = stm.executeUpdate("UPDATE clients SET pass='"+pass+"' WHERE name='"+clientName+"';");

         //if(!result){}
         con.commit();

      }catch(Exception e){
         System.out.println("ERROR: error realizando la transaccion:\n"+e.getMessage());
      }finally{
         try{
            stm.close();
            con.close();
         }catch(SQLException e){
            System.out.println("ERROR: No se pudo cerrar la conexion con la BD:\n"+e.getMessage());
         }
      }
   }

   public boolean checkPass(Client client, String pass){ // polymorph
      return checkPass(client.getName(), pass);
   }

   public boolean checkPass(String clientName, String pass){
      Client client = null;
      client = getClient(clientName);

      if (client == null || clientName == null) return false;

      pass = getHash(pass); // sha512 digest

      if (client.getPassword().equals(pass)) return true;

      return false;
   }

   private String getHash(String str) {

      String hash = new String();

      try {

         hash = DatatypeConverter.printHexBinary( MessageDigest.getInstance("SHA-512").digest(str.getBytes("UTF-8")));

      } catch (Exception ex) {
         System.out.println(ex);

         return null;
      }
        
      return hash;
   }
}
