package client;

public class Main {
    public static void main(String[] args) {
        var messageUsecases = new MessageUsecases();

        Utils.welcomeMessage();

        var loopUsecases = new LoopUsecases(messageUsecases);

        loopUsecases.startLoop();
    }
}
