package client;

import interfaces.RemoteObjectInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MessageUsecases {
    private RemoteObjectInterface remoteObjectInterface;

    public MessageUsecases() {
        connectToServer();
    }

    private void connectToServer() {
        try {
            var registry = LocateRegistry.getRegistry(null, 3000);
            remoteObjectInterface = (RemoteObjectInterface) registry.lookup("master");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void sendMessageAndPrintServerEcho(String message) {
        try {
            var echo = remoteObjectInterface.echo(message);

            Utils.echoMessage(echo);
        } catch (RemoteException ignored) {
            ignored.printStackTrace();
            Utils.remoteExceptionMessage();
        }
    }

    public void getAllMessagesAndPrint() {
        try {
            var allMessages = remoteObjectInterface.getAllMessages();

            Utils.echoMessages(allMessages);
        } catch (RemoteException ignored) {
            ignored.printStackTrace();
            Utils.remoteExceptionMessage();
        }
    }
}
