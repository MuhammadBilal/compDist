package model;

import java.util.ArrayList;

public interface DAOInt {

   public void newRequest(String clientFrom, String clientTo);
   public ArrayList<Client> getRequests(String clientTo);
   public ArrayList<Client> getFriends(Client client);
   public ArrayList<Client> getFriends(String clientName);
   public boolean isFriend(String client1, String client2);
   public Client getClient(String clientName);
   public boolean newClient(Client client);
   public void removeClient(Client client);
   public void removeClient(String clientName);
   public void changeClientPass(Client client, String pass);
   public void changeClientPass(String clientName, String pass);
   public boolean checkPass(Client client, String pass);
   public boolean checkPass(String clientName, String pass);

}
