package br.ufrn.rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.rmi.client.model.Message;

public interface ChatServerInterface extends Remote{

	public void registerClient(ChatClientInterface client) throws RemoteException;
	
	public void sendMessage(Message message) throws RemoteException;


}
