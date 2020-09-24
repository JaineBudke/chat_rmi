package br.ufrn.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.rmi.client.model.Message;
import br.ufrn.rmi.client.ChatClientInterface;
import br.ufrn.rmi.client.ChatServerInterface;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {

	private volatile List<ChatClientInterface> clients = new ArrayList<ChatClientInterface>();
	
	protected ChatServer() throws RemoteException {
		super();	
		
		new Notify().start();
	}

	@Override
	public void registerClient(ChatClientInterface client) throws RemoteException {
			
		clients.add(client);
		System.out.println("Novo cliente registrado com sucesso! Total: "+clients.size());
	}
	
	
	private class Notify extends Thread{
		
		public void run() {
			
			for(;;) {
				
				if(clients.size() > 0) {
					
					System.out.println("Notificando clientes");
					
					int i = 0;
					// todo: retirar o cliente quando ele desconecta (n sei se aq ou no client)
					for (ChatClientInterface helloClientInterface : clients) {
						
						try {
							helloClientInterface.printMessage(new Message("Hello client " + (i++)));
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					
					// TODO: quando cliente entrar imprimir "fulaninho entrou"
					// TODO: quando cliente desconectar, avisar para todos "fulano saiu"
					
					// TODO: receber mensagem de cliente e distribuir com o nome dele (menos p quem mandou)
					
					
					try {
						Thread.sleep(15 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
				}
				
			}
			
		}
	}


	@Override
	public void sendMessage(Message message) throws RemoteException {
		System.out.println(message.toString());
		
	}

	
}
