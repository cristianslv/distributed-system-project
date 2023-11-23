package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class RegistryRemoteObjectUsecases {
    private final RegistryUsecases registryUsecases;

    public RegistryRemoteObjectUsecases(RegistryUsecases registryUsecases) {
        this.registryUsecases = registryUsecases;
    }

    public void createRemoteObjectRepresentation() throws RemoteException {
        var remoteObjectImpl = new RemoteObjectImpl();

        try {
            registryUsecases.bindRemoteObjectRepresentationToMaster(remoteObjectImpl);
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
            // TODO bind to another name
        }
    }
}
