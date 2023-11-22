package server;

import interfaces.RemoteObjectInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryUsecases {

    public RegistryUsecases() throws RemoteException {
        getOrCreateRegistry();
    }

    private Registry registry;

    public void getOrCreateRegistry() throws RemoteException {
        registry = LocateRegistry.createRegistry(3000);
    }

    public void bindRemoteObjectRepresentationToMaster(RemoteObjectInterface remoteObjectRepresentation) throws AlreadyBoundException, RemoteException {
        registry.bind("master", remoteObjectRepresentation);
    }
}
