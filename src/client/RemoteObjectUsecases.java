package client;

import interfaces.RemoteObjectInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class RemoteObjectUsecases {
    private RemoteObjectInterface remoteObjectInterface;

    public RemoteObjectUsecases() {
        connectToServer();
    }

    private void connectToServer() {
        try {
            remoteObjectInterface = (RemoteObjectInterface) Naming.lookup("//localhost:3000/master");
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
