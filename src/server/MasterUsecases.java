package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class MasterUsecases {

    private MasterUsecases() {}

    public static void execute(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases,
                               RemoteObject remoteObject) throws RemoteException, AlreadyBoundException {
        remoteObjectRegistryUsecases.bindRemoteObjectToMaster(remoteObject);
    }
}
