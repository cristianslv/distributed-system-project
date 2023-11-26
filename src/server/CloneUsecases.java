package server;

import interfaces.RemoteObjectInterface;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class CloneUsecases {

    private CloneUsecases() {}

    public static void execute(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases,
                               RemoteObject remoteObject) throws RemoteException, AlreadyBoundException, MalformedURLException {
        remoteObjectRegistryUsecases.bindRemoteObject(remoteObject);

        syncWithMasterRemoteObject(remoteObjectRegistryUsecases, remoteObject);
    }

    private static void syncWithMasterRemoteObject(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases, RemoteObject remoteObject) throws RemoteException {
        var masterRemoteObject = getMasterRemoteObject(remoteObjectRegistryUsecases, remoteObject);

        if (masterRemoteObject != null) {
            setAllCloneMessages(remoteObject, masterRemoteObject);
            HealthCheckUsecases.execute(remoteObjectRegistryUsecases, remoteObject);
        }
    }

    private static RemoteObjectInterface getMasterRemoteObject(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases, RemoteObject remoteObject) {
        var masterRemoteObject = remoteObjectRegistryUsecases.getRemoteObject("master");

        if (masterRemoteObject == null) {
            HealthCheckUsecases.execute(remoteObjectRegistryUsecases, remoteObject);
        }

        return masterRemoteObject;
    }

    private static void setAllCloneMessages(RemoteObject remoteObject, RemoteObjectInterface masterRemoteObject) throws RemoteException {
        var allMessages = masterRemoteObject.getAllMessages();

        remoteObject.syncMessages(allMessages);

        System.out.println(
                "[INFO] As mensagens foram sincronizadas: "
                        .concat(remoteObject.getAllMessages().toString()));
    }
}
