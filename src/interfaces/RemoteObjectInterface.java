package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteObjectInterface extends Remote {
    String echo(String message) throws RemoteException;
    List<String> getAllMessages() throws  RemoteException;
}
