package server;

import interfaces.RemoteObjectInterface;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

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

    public void bindRemoteObject(RemoteObject remoteObject) throws MalformedURLException, RemoteException, AlreadyBoundException {
        var registeredNames = Naming.list("//localhost:3000");
        var namesWithoutMaster = Arrays.stream(registeredNames)
                .map(s -> s.replace("//localhost:3000/", ""))
                .filter(s -> !s.equals("master"));

        var higherRegisteredCloneName = namesWithoutMaster.mapToInt(Integer::parseInt).max().orElseGet(() -> 0);
        var newRemoteObjectName = higherRegisteredCloneName + 1;
        System.out.println("my new name " + newRemoteObjectName);
        remoteObjectRegistry.bind(String.valueOf(newRemoteObjectName), remoteObject);
    }
}
