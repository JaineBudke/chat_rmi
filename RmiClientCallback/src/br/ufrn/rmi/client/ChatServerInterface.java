package br.ufrn.rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.rmi.client.model.Message;

public interface ChatServerInterface extends Remote{

	public void registerClient(ChatClientInterface client, String nome) throws RemoteException;

	// usar classe de msg do server p n ter problema de cast
	public void sendMessage(String message) throws RemoteException;


}
