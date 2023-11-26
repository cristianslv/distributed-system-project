package server;

import interfaces.ElectionInterface;

public class ElectionUsecases implements Runnable {
    RemoteObjectRegistryUsecases remoteObjectRegistryUsecases;
    RemoteObject remoteObject;

    public ElectionUsecases(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases, RemoteObject remoteObject) {
        this.remoteObjectRegistryUsecases = remoteObjectRegistryUsecases;
        this.remoteObject = remoteObject;
    }

    public void run() {
        try {
            var masterRemoteObject = remoteObjectRegistryUsecases.getRemoteObject("master");

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
            var masterRemoteObject = (ElectionInterface) remoteObjectRegistryUsecases.getRemoteObject("master");
            masterRemoteObject.becomeMaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncWithMasterRemoteObject() {
        try {
            var masterRemoteObject = remoteObjectRegistryUsecases.getRemoteObject("master");
            var allMessages = masterRemoteObject.getAllMessages();

            remoteObject.syncMessages(allMessages);
            System.out.println(
                    "[INFO] As mensagens foram sincronizadas: "
                            .concat(remoteObject.getAllMessages().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}