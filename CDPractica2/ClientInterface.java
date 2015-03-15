import java.rmi.*;


public interface ClientInterface  extends java.rmi.Remote{

    public String notifyMe(String message) throws java.rmi.RemoteException;

    public void notifyEnd(String txt) throws java.rmi.RemoteException;
}
