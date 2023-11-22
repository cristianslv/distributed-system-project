package server;

import java.rmi.RemoteException;

public class Main {
    public static void main(String args[]) {
        try {
            var registryUsecases = new RegistryUsecases();
            var registryRemoteObjectUsecases = new RegistryRemoteObjectUsecases(registryUsecases);

            registryRemoteObjectUsecases.createRemoteObjectRepresentation();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
