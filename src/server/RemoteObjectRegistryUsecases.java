package server;

import interfaces.RemoteObjectInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.stream.IntStream;

public class RemoteObjectRegistryUsecases {

    public RemoteObjectRegistryUsecases() throws RemoteException {
        remoteObjectRegistry = LocateRegistry.getRegistry(3000);
    }

    private final Registry remoteObjectRegistry;
    private static final String MASTER = "master";

    public void bindRemoteObjectToMaster(RemoteObjectInterface remoteObject) throws AlreadyBoundException,
            RemoteException {
        remoteObjectRegistry.bind(MASTER, remoteObject);
    }

    public RemoteObjectInterface getRemoteObject(String name) {
        try {
            return (RemoteObjectInterface) remoteObjectRegistry.lookup(name);
        } catch (NotBoundException | RemoteException ignored) {
            return null;
        }
    }

    public boolean areThereNoClones() throws RemoteException {
        var registeredNames = remoteObjectRegistry.list();

        return registeredNames.length == 0;
    }

    public void bindRemoteObject(RemoteObject remoteObject) throws RemoteException, AlreadyBoundException {
        var cloneNamesAsInt = getCloneNamesAsInt();
        var higherRegisteredCloneName = cloneNamesAsInt.max().orElseGet(() -> 0);
        var newRemoteObjectName = higherRegisteredCloneName + 1;

        remoteObjectRegistry.bind(String.valueOf(newRemoteObjectName), remoteObject);

        remoteObject.setName(String.valueOf(newRemoteObjectName));
        remoteObject.addShutdownHook(this);

        System.out.println(
                "[INFO] Servidor registrado como objeto remoto '" + newRemoteObjectName + "'");
    }

    public IntStream getCloneNamesAsInt() throws RemoteException {
        var registeredNames = remoteObjectRegistry.list();
        var namesWithoutMaster = Arrays.stream(registeredNames)
                .filter(s -> !s.equals(MASTER));

        return namesWithoutMaster.mapToInt(Integer::parseInt);
    }

    public void unbindRemoteObject(String name) throws NotBoundException, RemoteException {
        remoteObjectRegistry.unbind(name);
    }

    public void electOlderClone() throws RemoteException, AlreadyBoundException, NotBoundException {
        var olderCloneName = getOlderCloneName();
        var cloneRemoteObject = getRemoteObject(olderCloneName);

        unbindRemoteObject(olderCloneName);
        bindRemoteObjectToMaster(cloneRemoteObject);

        System.out.println("[INFO] Servidor clone '" + olderCloneName + "' registrado como objeto remoto 'master'");
    }

    public String getOlderCloneName() throws RemoteException {
        var cloneNamesAsInt = getCloneNamesAsInt();
        var olderClone = cloneNamesAsInt.min().orElseThrow(() -> new RemoteException("[ERROR] Não há clones"));

        return String.valueOf(olderClone);
    }
}
