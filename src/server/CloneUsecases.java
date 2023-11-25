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

        var masterRemoteObject = getMasterRemoteObject(remoteObjectRegistryUsecases);

        setAllCloneMessages(remoteObject, masterRemoteObject);
    }

    private static void setAllCloneMessages(RemoteObject remoteObject, RemoteObjectInterface masterRemoteObject) throws RemoteException {
        var allMessages = masterRemoteObject.getAllMessages();

        remoteObject.setAllMessages(allMessages);

        System.out.println(remoteObject.getAllMessages());
    }

    private static RemoteObjectInterface getMasterRemoteObject(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases) throws RemoteException {
        var masterRemoteObject = remoteObjectRegistryUsecases.getMasterRemoteObject();

        if (masterRemoteObject == null) {
            throw new RemoteException("Não foi possível achar referência remota do servidor mestre!");
        }

        return masterRemoteObject;
    }
}
