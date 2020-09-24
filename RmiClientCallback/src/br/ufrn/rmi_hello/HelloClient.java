package br.ufrn.rmi_hello;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.ufrn.rmi.model.Message;


public class HelloClient extends UnicastRemoteObject implements HelloClientInterface {

	protected HelloClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		new Notify().start();
	}

	@Override
	public void printMessage(Message message) throws RemoteException {
		
		System.out.println(message);
		
	}
	
	// TODO: criar metodo para sendMessage q espera o cliente digitar uma mensagem e envia pro servidor
	private class Notify extends Thread{
		
		public void run() {
			
			for(;;) {
					
				try {

					HelloServerInterface server = (HelloServerInterface) 
							Naming.lookup("rmi://127.0.0.1:1098/HelloServerCallbak");

					
					Scanner scanner = new Scanner(System.in);
					String msg = scanner.next();
					
					
					server.sendMessage(new Message(msg));
					
					
				} catch (RemoteException e) {
					System.out.println("Não foi possível enviar sua mensagem.");
				} catch (MalformedURLException e) {
					System.out.println("Não foi possível enviar sua mensagem.");
				} catch (NotBoundException e) {
					System.out.println("Não foi possível enviar sua mensagem.");
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
