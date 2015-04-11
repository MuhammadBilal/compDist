package model;

import java.util.ArrayList;

public class Client {

   private String name;
   private String email;
   private String password;
   private String date; // string - other type ?
   private ArrayList<Client> friends;

   // Constructors
   public Client(){
      this.friends = new ArrayList<Client>();
   }

   public Client(String name){
      this.name = name;
      this.friends = new ArrayList<Client>();
   }

   public Client(String name, String email, String password) {
      this.name = name;
      this.email = email;
      this.password = password;
      this.friends = new ArrayList<Client>();
   }

   public Client(String name, String email, String password, String date){
      this.name = name;
      this.email = email;
      this.password = password;
      this.date = date;
      this.friends = new ArrayList<Client>();
   }

   // Setters && Getters
   public void setName(String name){ this.name = name; }
   public void setEmail(String email){ this.email = email; }
   public void setPassword(String password){ this.password = password; }
   public void setDate(String date){ this.date = date; }
   public void setFriends(ArrayList<Client> friends){ this.friends = (ArrayList<Client>) friends.clone(); } 

   public String getName(){ return name; }
   public String getEmail(){ return email; }
   public String getPassword(){ return password; }
   public String getDate(){ return date; }
   public ArrayList<Client> getFriends(){ return friends; }

   // Methods
   public void addFriend(Client client){
      if(friends != null && !friends.isEmpty()){
         friends.add(client);
      }
   }

   public void removeFriend(Client client){
      removeFriend(client.getName());
   }

   public void removeFriend(String name){
      if(friends!=null && !friends.isEmpty()){
         for(Client f : friends){
            if(f.getName().equals(name)){
               friends.remove(f);
            }
         }
      }
   }

}
