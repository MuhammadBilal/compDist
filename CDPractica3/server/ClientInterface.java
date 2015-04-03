import java.rmi.*;

public interface ClientInterface extends java.rmi.Remote {


   public String getUser();

   public Strig getPass();

   public void checkLogin(boolean b);

   public void receiveFriendlist(ArrayList<ClientInterface> friendlist);

   public void receiveNotification(ClientInterface friend);

   public void disconnectedUser(ClientInterface friend);

   public void close();
}
