package br.ufrn.rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.rmi.client.model.Message;

public interface ChatClientInterface extends Remote {
	
	public void printMessage(Message message) throws RemoteException;
	
}
