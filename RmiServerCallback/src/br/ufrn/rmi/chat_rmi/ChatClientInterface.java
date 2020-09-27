package br.ufrn.rmi.chat_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.rmi.model.Message;

public interface ChatClientInterface extends Remote {
	
	public void printMessage(Message message) throws RemoteException;

	public void testConnection() throws RemoteException;

}
