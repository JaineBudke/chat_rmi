package br.ufrn.rmi.chat_rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.ufrn.rmi.model.Message;


public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

	private String name;
	
	protected ChatClient(String name) throws RemoteException {
		super();
		this.name = name;
		// TODO Auto-generated constructor stub
		new Notify().start();
	}

	@Override
	public void printMessage(Message message) throws RemoteException {
		
		System.out.println(message);
		
	}

	@Override
	public void testConection() throws RemoteException {
		//stub
		System.out.println("teste");
	}

	private class Notify extends Thread{
		
		public void run() {
			
			for(;;) {
					
				try {

					ChatServerInterface server = (ChatServerInterface)
							Naming.lookup("rmi://127.0.0.1:1098/ChatServer");

					
					Scanner scanner = new Scanner(System.in);
					String msg = scanner.next();

					server.sendMessage(new Message(msg, name));
					
					
				} catch (RemoteException e) {
					System.out.println("N�o foi poss�vel enviar sua mensagem.");
				} catch (MalformedURLException e) {
					System.out.println("N�o foi poss�vel enviar sua mensagem.");
				} catch (NotBoundException e) {
					System.out.println("N�o foi poss�vel enviar sua mensagem.");
				}
				
				
				try {
					Thread.sleep(15 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
			}
			
		}
	}

}
