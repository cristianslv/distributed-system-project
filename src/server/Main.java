package server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class Main {
    public static void main(String args[]) {
        try {
            var remoteObject = new RemoteObject();
            var remoteObjectRegistryUsecases = new RemoteObjectRegistryUsecases();
            var masterRemoteObject = remoteObjectRegistryUsecases.getMasterRemoteObject();

            if (masterRemoteObject == null) {
                remoteObjectRegistryUsecases.createRemoteObjectRegistry();

                MasterUsecases.execute(remoteObjectRegistryUsecases, remoteObject);
            } else {
                remoteObjectRegistryUsecases.setRemoteObjectRegistry();
                CloneUsecases.execute(remoteObjectRegistryUsecases, remoteObject);
            }
        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
