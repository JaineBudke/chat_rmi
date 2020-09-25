package br.ufrn.rmi.server;

import br.ufrn.rmi.server.model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.rmi.server.model.Message;
public interface ChatServerInterface extends Remote{

	public void registerClient(ChatClientInterface client, String nome) throws RemoteException;
	
	public void sendMessage(String message) throws RemoteException;

}
