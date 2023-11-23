package client;

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

    private void sendMessage() {
        Utils.askUserMessage();
        var userInput = Utils.getUserInput();

        remoteObjectUsecases.sendMessageAndPrintServerEcho(userInput);
    }

    private void getAllMessages() {
        remoteObjectUsecases.getAllMessagesAndPrint();
    }
}
