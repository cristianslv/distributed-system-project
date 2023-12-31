package client;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private Utils() {}

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void cleanTerminal() {
        printMessage(System.lineSeparator().repeat(50));
    }

    public static void welcomeMessage() {
        cleanTerminal();

        var welcomeMessage = "*------------------------------------------------*"
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Bem vindo, Cliente!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Nesse projeto você poderá enviar mensagens")
                .concat(System.lineSeparator())
                .concat("à um servidor, que ecoará a mensagem novamente.")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Além disso, você poderá recuperar todas as")
                .concat(System.lineSeparator())
                .concat("mensagens que os clientes enviaram ao servidor.")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("*------------------------------------------------*")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Pressione ENTER para continuar...");

        printMessage(welcomeMessage);

        waitEnter();
    }

    public static void loopMessage() {
        cleanTerminal();

        var loopMessage = "*------------------------------------------------*"
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Este é o loop de opções!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Digite o número indicado e pressione a tecla")
                .concat(System.lineSeparator())
                .concat("ENTER para selecionar a opção desejada!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("1 - Enviar mensagem")
                .concat(System.lineSeparator())
                .concat("2 - Receber todas as mensagens enviadas")
                .concat(System.lineSeparator())
                .concat("3 - Finalizar execução")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("*------------------------------------------------*");

        printMessage(loopMessage);
    }

    public static void echoMessage(String message) {
        cleanTerminal();

        var echoMessage = "*------------------------------------------------*"
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("O servidor retornou sua mensagem!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Mensagem:")
                .concat(System.lineSeparator())
                .concat(message)
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("*------------------------------------------------*")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Pressione ENTER para continuar...");

        printMessage(echoMessage);

        waitEnter();
    }

    public static void remoteExceptionMessage() {
        cleanTerminal();

        var remoteExceptionMessage = "*------------------------------------------------*"
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("O servidor retornou uma excessão remota!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Aguarde alguns instantes e tente novamente!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("*------------------------------------------------*")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Pressione ENTER para continuar...");

        printMessage(remoteExceptionMessage);

        waitEnter();
    }

    public static void reconnectMessage() {
        cleanTerminal();

        var reconnectMessage = "*------------------------------------------------*"
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Ocorreu um erro e estamos tentando reconectá-lo!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Aguarde alguns instantes e tente novamente!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("*------------------------------------------------*")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Pressione ENTER para continuar...");

        printMessage(reconnectMessage);

        waitEnter();
    }

    public static void askUserMessage() {
        cleanTerminal();

        var askUserMessage = "*------------------------------------------------*"
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Digite a sua mensagem abaixo e pressione")
                .concat(System.lineSeparator())
                .concat("ENTER para enviar ao servidor!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("*------------------------------------------------*");

        printMessage(askUserMessage);
    }

    public static void echoMessages(List<String> allMessages) {
        cleanTerminal();

        var echoMessages = "*------------------------------------------------*"
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("O servidor retornou todas as mensagens")
                .concat(System.lineSeparator())
                .concat("que foram enviadas à ele!")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Mensagens:")
                .concat(System.lineSeparator())
                .concat(allMessages.toString().replace(",", "\n"))
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("*------------------------------------------------*")
                .concat(System.lineSeparator())
                .concat(System.lineSeparator())
                .concat("Pressione ENTER para continuar...");

        printMessage(echoMessages);

        waitEnter();
    }

    public static void waitEnter() {
        var scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static String getUserInput() {
        var scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
