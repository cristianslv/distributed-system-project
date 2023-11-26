package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ElectionInterface extends Remote {
    void becomeMaster() throws RemoteException;
}
