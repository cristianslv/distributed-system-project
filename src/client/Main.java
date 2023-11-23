package client;

public class Main {
    public static void main(String[] args) {
        var messageUsecases = new RemoteObjectUsecases();

        Utils.welcomeMessage();

        var loopUsecases = new UserUsecases(messageUsecases);

        loopUsecases.execute();
    }
}
