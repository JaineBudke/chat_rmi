package br.ufrn.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.rmi.ConnectException;

import br.ufrn.rmi.client.model.Message;
import br.ufrn.rmi.client.ChatClientInterface;
import br.ufrn.rmi.client.ChatServerInterface;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {

	private volatile List<ChatClientInterface> clients = new ArrayList<ChatClientInterface>();
	
	private boolean newMessage = false;
	private String mensagem = "";
	
	protected ChatServer() throws RemoteException {
		super();	
		
		new Notify().start();
	}

	@Override
	public void registerClient(ChatClientInterface client, String nome) throws RemoteException {
			
		clients.add(client);
		sendMessageToClients(nome+ " entrou no chat!");
		
	}
	
	
	private void sendMessageToClients( String message ) {

		Iterator<ChatClientInterface> i = clients.iterator();
		while (i.hasNext()) {
			
			try {
				ChatClientInterface helloClientInterface = i.next();
				helloClientInterface.printMessage(new Message(message));

			} catch(ConnectException e){
				i.remove();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	private class Notify extends Thread{
		
		public void run() {
			
			for(;;) {
				
				// TODO: retirar o cliente quando ele desconecta (n sei se aq ou no client)
				// TODO: quando cliente desconectar, avisar para todos "fulano saiu"	
				
			}
			
		}
	}
	

	@Override
	public void sendMessage(Message message) throws RemoteException {
		
		// envia mensagem para os clientes
		sendMessageToClients(message.toString());
		
		// TODO: verificar quem enviou a mensagem e n�o mandar pra esse cliente
		
	}

	
}
