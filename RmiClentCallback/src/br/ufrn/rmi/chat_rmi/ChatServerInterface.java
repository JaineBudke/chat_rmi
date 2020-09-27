package br.ufrn.rmi.chat_rmi;

import br.ufrn.rmi.model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote{

	public void registerClient(ChatClientInterface client, String nome) throws RemoteException;

	public void sendMessage(Message message) throws RemoteException;


}
