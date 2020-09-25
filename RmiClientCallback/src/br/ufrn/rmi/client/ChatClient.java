package br.ufrn.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.ufrn.rmi.client.model.Message;


public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

	protected ChatClient() throws RemoteException {
		super();
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
	}

	// TODO: criar metodo para sendMessage q espera o cliente digitar uma mensagem e envia pro servidor
	private class Notify extends Thread{
		
		public void run() {
			
			for(;;) {
					
				try {

					ChatServerInterface server = (ChatServerInterface)
							Naming.lookup("rmi://127.0.0.1:1098/ChatServer");

					
					Scanner scanner = new Scanner(System.in);
					String msg = scanner.next();
					
					
					server.sendMessage(msg);
					
					
				} catch (RemoteException e) {
					System.out.println("a");
					System.out.println("N�o foi poss�vel enviar sua mensagem.");
				} catch (MalformedURLException e) {
					System.out.println("b");
					System.out.println("N�o foi poss�vel enviar sua mensagem.");
				} catch (NotBoundException e) {
					System.out.println("c");
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
