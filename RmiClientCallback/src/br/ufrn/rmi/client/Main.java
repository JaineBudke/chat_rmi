package br.ufrn.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		ChatServerInterface server = (ChatServerInterface)
				Naming.lookup("rmi://127.0.0.1:1098/ChatServer");

		ChatClientInterface client = new ChatClient();
		
		server.registerClient(client, "Jaine");
		
	}
}
