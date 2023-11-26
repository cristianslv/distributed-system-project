package server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class Main {
    public static void main(String args[]) {
        try {
            var remoteObject = new RemoteObject();
            var remoteObjectRegistryUsecases = new RemoteObjectRegistryUsecases();
            var masterRemoteObject = remoteObjectRegistryUsecases.getRemoteObject("master");

            if (masterRemoteObject == null && remoteObjectRegistryUsecases.areThereNoClones()) {
                MasterUsecases.execute(remoteObjectRegistryUsecases, remoteObject);
            } else {
                CloneUsecases.execute(remoteObjectRegistryUsecases, remoteObject);
            }
        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
