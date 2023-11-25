package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class UserUsecases {
    private RemoteObjectUsecases remoteObjectUsecases;

    public UserUsecases(RemoteObjectUsecases remoteObjectUsecases) {
        this.remoteObjectUsecases = remoteObjectUsecases;
    }

    public void execute() {
        var keepLoop = true;

        do {
            Utils.loopMessage();
            var userInput = Utils.getUserInput();

            if (serverIsNotAvailable()) continue;

            switch (userInput) {
                case "1":
                    sendMessage();
                    break;
                case "2":
                    getAllMessages();
                    break;
                case "3":
                    keepLoop = false;
                    break;
                default:
                    Utils.cleanTerminal();
            }
        } while (keepLoop);
    }

    private boolean serverIsNotAvailable() {
        try {
            remoteObjectUsecases.connectToServer();
        } catch (Exception ignored) {
            Utils.remoteExceptionMessage();
            return true;
        }
        return false;
    }

    private void sendMessage() {
        Utils.askUserMessage();
        var userInput = Utils.getUserInput();

        try {
            remoteObjectUsecases.sendMessageAndPrintServerEcho(userInput);
        } catch (Exception ignored) {
            Utils.remoteExceptionMessage();
        }
    }

    private void getAllMessages() {
        try {
            remoteObjectUsecases.getAllMessagesAndPrint();
        } catch (Exception ignored) {
            Utils.remoteExceptionMessage();
        }
    }
}
