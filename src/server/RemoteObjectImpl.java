package server;

import interfaces.RemoteObjectInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteObjectImpl extends UnicastRemoteObject implements RemoteObjectInterface {
    private final List<String> messages = new ArrayList<>();

    public RemoteObjectImpl() throws RemoteException {}

    @Override
    public String echo(String message) throws RemoteException {
        messages.add(message);

        return message;
    }

    @Override
    public List<String> getAllMessages() throws RemoteException {
        return messages;
    }
}
