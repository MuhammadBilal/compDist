
import java.util.ArrayList;

public interface DAOInt {

   public ArrayList<Client> getFriends(Client client);
   public ArrayList<Client> getFriends(String name);
   public void newClient(Client client);
   public void removeClient(Client client);
   public void removeClient(String name);
   public void changeClientPass(String name, String pass);

}
