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
	
	private boolean newMessage = false;
	private String mensagem = "";
	
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
					
					//System.out.println("Notificando clientes");
					
					int i = 0;
					
					// TODO: verificar quem enviou a mensagem e não mandar pra esse cliente
					// verifica se tem uma nova mensagem 
					if(newMessage == true) {
						
						// envia mensagem para os clientes
						for (ChatClientInterface helloClientInterface : clients) {
							
							try {
								helloClientInterface.printMessage(new Message(mensagem));
							} catch (RemoteException e) {
								e.printStackTrace();
							}
							
						}
						
						newMessage = false;
						
					}
					
					// TODO: retirar o cliente quando ele desconecta (n sei se aq ou no client)

					// TODO: quando cliente entrar imprimir "fulaninho entrou"
					// TODO: quando cliente desconectar, avisar para todos "fulano saiu"				
					
				}
				
			}
			
		}
	}


	@Override
	public void sendMessage(Message message) throws RemoteException {
		newMessage = true;
		mensagem = message.toString();
	}

	
}
