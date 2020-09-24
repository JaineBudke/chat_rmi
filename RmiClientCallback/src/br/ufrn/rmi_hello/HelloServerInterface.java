package br.ufrn.rmi_hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.ufrn.rmi.model.Message;

public interface HelloServerInterface extends Remote{

	public void registerClient(HelloClientInterface client) throws RemoteException;
	
	public void sendMessage(Message message) throws RemoteException;


}
