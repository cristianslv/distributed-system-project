package client;

import interfaces.RemoteObjectInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RemoteObjectUsecases {
    private RemoteObjectInterface remoteObjectInterface;

    public RemoteObjectUsecases() {}

    public void connectToServer() throws MalformedURLException, NotBoundException, RemoteException {
        remoteObjectInterface = (RemoteObjectInterface) Naming.lookup("//localhost:3000/master");
    }

    public void sendMessageAndPrintServerEcho(String message) throws MalformedURLException, NotBoundException, RemoteException {
        try {
            var echo = remoteObjectInterface.echo(message);

            Utils.echoMessage(echo);
        } catch (RemoteException ignored) {
            Utils.reconnectMessage();

            connectToServer();
        }
    }

    public void getAllMessagesAndPrint() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            var allMessages = remoteObjectInterface.getAllMessages();

            Utils.echoMessages(allMessages);
        } catch (RemoteException ignored) {
            Utils.reconnectMessage();
            connectToServer();
        }
    }
}
