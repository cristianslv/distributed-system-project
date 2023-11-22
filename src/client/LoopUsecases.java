package client;

import jdk.jshell.execution.Util;

import java.util.Scanner;

public class LoopUsecases {
    private MessageUsecases messageUsecases;

    public LoopUsecases(MessageUsecases messageUsecases) {
        this.messageUsecases = messageUsecases;
    }

    public void startLoop() {
        var scanner = new Scanner(System.in);
        var keepLoop = true;

        do {
            Utils.loopMessage();
            var userInput = scanner.next();

            switch (userInput) {
                case "1":
                    sendMessage(userInput);
                    break;
                case "2":
                    getAllMessages();
                    break;
                case "3":
                    keepLoop = false;
                    scanner.close();
                    break;
                default:
                    Utils.cleanTerminal();
            }
        } while (keepLoop);
    }

    private void sendMessage(String userInput) {
        messageUsecases.sendMessageAndPrintServerEcho(userInput);
    }

    private void getAllMessages() {}
}
