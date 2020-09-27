package br.ufrn.rmi.chat_rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.rmi.ConnectException;
import java.rmi.ServerException;

import br.ufrn.rmi.model.Message;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {

	private volatile List<ChatClientInterface> clients = new ArrayList<ChatClientInterface>();
	private volatile List<String> nomes = new ArrayList<String>();
	
	private boolean newMessage = false;
	private String mensagem = "";
	
	protected ChatServer() throws RemoteException {
		super();	
		
		new Notify().start();
	}

	@Override
	public void registerClient(ChatClientInterface client, String nome) throws RemoteException {
			
		clients.add(client);
		nomes.add(nome);
		sendMessageToClients(nome+ " entrou no chat!");
		System.out.println(nome + " entrou no chat!");
		
	}

	private void sendMessageToClients( String message ) {

		Iterator<ChatClientInterface> client = clients.iterator();
		while ( client.hasNext()){
			try {
				ChatClientInterface chatClientInterface = client.next();
				chatClientInterface.printMessage( new Message(message));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}
	}

	private void checkClients(){
		Iterator<ChatClientInterface> it = clients.iterator();
		Iterator<String> it2 = nomes.iterator();
		String name = null;
		while (it.hasNext()) {

			try {
				// Testa conexão
				ChatClientInterface chatClientInterface = it.next();
				name = it2.next();
				try {
					chatClientInterface.testConnection();
				} catch (ServerException ignored){}
			} catch(ConnectException e){
				it.remove();

				// avisa que cliente saiu
				sendMessageToClients( name + " saiu do chat!");
				System.out.println(name + " saiu do chat!");
				it2.remove();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	private class Notify extends Thread{
		
		public void run() {
			
			for(;;) {

				checkClients();
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
