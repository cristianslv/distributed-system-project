package server;

import interfaces.ElectionInterface;
import interfaces.RemoteObjectInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteObject extends UnicastRemoteObject implements RemoteObjectInterface, ElectionInterface {
    private String name;
    private Thread shutdownHookThread;
    private final List<String> messages = new ArrayList<>();
    private RemoteObjectRegistryUsecases remoteObjectRegistryUsecases;

    public RemoteObject() throws RemoteException {}

    @Override
    public String echo(String message) throws RemoteException {
        messages.add(message);
        System.out.println(
                "[INFO] Mensagem '" + message + "' enviada pelo cliente");

        return message;
    }

    @Override
    public List<String> getAllMessages() throws RemoteException {
        return messages;
    }

    @Override
    public void becomeMaster() throws RemoteException {
        setName("master");
        System.out.println("\n[INFO] Servidor registrado como objeto remoto 'master'");

        Runtime.getRuntime().removeShutdownHook(shutdownHookThread);

        addShutdownHook(this.remoteObjectRegistryUsecases);
        HealthCheckUsecases.stop();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void syncMessages(List<String> masterMessages) {
        masterMessages.removeAll(messages);

        messages.addAll(masterMessages);
    }

    public void addShutdownHook(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases) {
        this.remoteObjectRegistryUsecases = remoteObjectRegistryUsecases;

        shutdownHookThread = new Thread(() -> {
            try {
                this.remoteObjectRegistryUsecases.unbindRemoteObject(name);
                System.out.println(
                        "[INFO] Objeto remoto '" + name + "' removido do serviço de nomes");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
    }
}
