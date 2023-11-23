package server;

import interfaces.RemoteObjectInterface;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteObjectRegistryUsecases {

    public RemoteObjectRegistryUsecases()  {}

    private Registry remoteObjectRegistry;

    public void createRemoteObjectRegistry() throws RemoteException {
        remoteObjectRegistry = LocateRegistry.createRegistry(3000);
    }

    public void setRemoteObjectRegistry() throws RemoteException {
        remoteObjectRegistry = LocateRegistry.getRegistry(3000);
    }

    public void bindRemoteObjectToMaster(RemoteObject remoteObject) throws AlreadyBoundException,
            RemoteException {
        remoteObjectRegistry.bind("master", remoteObject);
    }

    public RemoteObjectInterface getMasterRemoteObject() {
        try {
            return (RemoteObjectInterface) Naming.lookup("//localhost:3000/master");
        } catch (MalformedURLException | NotBoundException | RemoteException ignored) {
            return null;
        }
    }
}
