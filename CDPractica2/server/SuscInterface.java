
import java.rmi.*;

public interface SuscInterface extends Remote {

   public String publish(double number) throws java.rmi.RemoteException;

}

