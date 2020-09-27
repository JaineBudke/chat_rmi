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
	
	
	protected ChatServer() throws RemoteException {
		super();	
		
		new Notify().start();
	}

	@Override
	public void registerClient(ChatClientInterface client, String nome) throws RemoteException {
			
		// se ainda não tem ninguém com esse nome no chat
		if( !nomes.contains(nome) ){
			
			clients.add(client);
			nomes.add(nome);

			System.out.println(nome + " entrou no chat!");
			
			// avisa ao cliente que ele entrou no chat
			client.printMessage(new Message("Você entrou no chat!", nome));		
			
			// avisa outros clientes que há um novo usuário no chat
			for( int i = 0; i<clients.size(); i++ ){
				ChatClientInterface chatClientInterface = clients.get(i);
				String nameClient = nomes.get(i);
				
				if(!nameClient.equals(nome)) {
					try {
						chatClientInterface.printMessage( new Message(nome +" entrou no chat!", nome));
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} 
				
			}
			
		} else {
			throw new RemoteException("Já existe um usuário com esse nome.");
		}
		
	}

	private void sendMessageToClients( String message ) {
		
		for( int i = 0; i<clients.size(); i++ ){
			ChatClientInterface chatClientInterface = clients.get(i);
			String nomeClient = nomes.get(i);
			try {
				chatClientInterface.printMessage( new Message(message, nomeClient));
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
		
		String remetente = message.getName();
		
		for( int i = 0; i<clients.size(); i++ ){
			ChatClientInterface chatClientInterface = clients.get(i);
			String nameClient = nomes.get(i);
			
			String[] data_hora = message.getDate().split(" ");
			
			// enviar mensagem pros outros clientes
			if(!nameClient.equals(remetente)) {
				
				String mensagem = remetente + " em " +data_hora[0]+ " às " + data_hora[1] +": " + message.getMessage();
				
				try {
					chatClientInterface.printMessage( new Message(mensagem, remetente));
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
			

		}
		
	}

	
}
