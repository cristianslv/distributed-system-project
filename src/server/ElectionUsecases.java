package server;

import interfaces.ElectionInterface;

public class ElectionUsecases implements Runnable {
    RemoteObjectRegistryUsecases remoteObjectRegistryUsecases;
    private static final String MASTER = "master";
    RemoteObject remoteObject;

    public ElectionUsecases(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases, RemoteObject remoteObject) {
        this.remoteObjectRegistryUsecases = remoteObjectRegistryUsecases;
        this.remoteObject = remoteObject;
    }

    public void run() {
        try {
            var masterRemoteObject = remoteObjectRegistryUsecases.getRemoteObject(MASTER);

            if (masterRemoteObject == null) {
                System.out.println("[INFO] Servidor master indisponível, iniciando eleição");

                var olderCloneName = remoteObjectRegistryUsecases.getOlderCloneName();
                remoteObjectRegistryUsecases.electOlderClone();

                if (remoteObject.getName().equals(olderCloneName)) {
                    remoteObject.becomeMaster();
                } else {
                    warnMasterElection();
                    syncWithMasterRemoteObject();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void warnMasterElection() {
        try {
            var masterRemoteObject = (ElectionInterface) remoteObjectRegistryUsecases.getRemoteObject(MASTER);
            masterRemoteObject.becomeMaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncWithMasterRemoteObject() {
        try {
            var masterRemoteObject = remoteObjectRegistryUsecases.getRemoteObject(MASTER);
            var allMessages = masterRemoteObject.getAllMessages();

            if (remoteObject.getAllMessages().isEmpty()) {
                remoteObject.syncMessages(allMessages);
                remoteObject.setMqttSubscriberUsecases();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}