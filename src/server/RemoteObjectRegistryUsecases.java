package server;

import interfaces.RemoteObjectInterface;

import java.net.MalformedURLException;
import java.rmi.*;
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
            return (RemoteObjectInterface) Naming.lookup("//localhost:3000/" + name);
        } catch (MalformedURLException | NotBoundException | RemoteException ignored) {
            return null;
        }
    }

    public boolean areThereNoClones() throws MalformedURLException, RemoteException {
        var registeredNames = Naming.list("//localhost:3000");

        return registeredNames.length == 0;
    }

    public void bindRemoteObject(RemoteObject remoteObject) throws MalformedURLException, RemoteException, AlreadyBoundException {
        var cloneNamesAsInt = getCloneNamesAsInt();
        var higherRegisteredCloneName = cloneNamesAsInt.max().orElseGet(() -> 0);
        var newRemoteObjectName = higherRegisteredCloneName + 1;

        remoteObjectRegistry.bind(String.valueOf(newRemoteObjectName), remoteObject);

        remoteObject.setName(String.valueOf(newRemoteObjectName));
        remoteObject.addShutdownHook(this);

        System.out.println(
                "[INFO] Servidor registrado como objeto remoto '" + newRemoteObjectName + "'");
    }

    public static IntStream getCloneNamesAsInt() throws RemoteException, MalformedURLException {
        var registeredNames = Naming.list("//localhost:3000");
        var namesWithoutMaster = Arrays.stream(registeredNames)
                .map(s -> s.replace("//localhost:3000/", ""))
                .filter(s -> !s.equals(MASTER));

        return namesWithoutMaster.mapToInt(Integer::parseInt);
    }

    public void unbindRemoteObject(String name) throws NotBoundException, RemoteException, MalformedURLException {
        var asd = getCloneNamesAsInt();

        remoteObjectRegistry.unbind(name);
    }

    public void electOlderClone() throws MalformedURLException, RemoteException, AlreadyBoundException, NotBoundException {
        var olderCloneName = getOlderCloneName();
        var cloneRemoteObject = getRemoteObject(olderCloneName);

        unbindRemoteObject(olderCloneName);
        bindRemoteObjectToMaster(cloneRemoteObject);

        System.out.println("[INFO] Servidor clone '" + olderCloneName + "' registrado como objeto remoto 'master'");
    }

    public String getOlderCloneName() throws RemoteException, MalformedURLException {
        var cloneNamesAsInt = getCloneNamesAsInt();
        var olderClone = cloneNamesAsInt.min().orElseThrow(() -> new RemoteException("[ERROR] Não há clones"));

        return String.valueOf(olderClone);
    }
}
