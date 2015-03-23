
import java.util.ArrayList;

public interface DAOInt {

   public ArrayList<Client> getFriends(String name);
   public void newClient();
   public void removeClient();
   public void changeClientPass(String name);

}
