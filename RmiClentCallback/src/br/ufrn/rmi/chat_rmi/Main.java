package br.ufrn.rmi.chat_rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws MalformedURLException, RemoteException,
			NotBoundException, IOException {
		
		ChatServerInterface server = (ChatServerInterface)
				Naming.lookup("rmi://127.0.0.1:1098/ChatServer");

		ChatClientInterface client = new ChatClient();

		System.out.println("Digite seu nome de usuário: ");
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		String nome = reader.readLine();
		
		server.registerClient(client, nome);
		
	}
}
