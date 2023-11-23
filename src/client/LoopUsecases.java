package client;

public class LoopUsecases {
    private MessageUsecases messageUsecases;

    public LoopUsecases(MessageUsecases messageUsecases) {
        this.messageUsecases = messageUsecases;
    }

    public void startLoop() {
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

        messageUsecases.sendMessageAndPrintServerEcho(userInput);
    }

    private void getAllMessages() {
        messageUsecases.getAllMessagesAndPrint();
    }
}
