package br.ufrn.rmi.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{

	private String message;
	private String date;
	private String name;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public Message(String message, String name) {
		this.message = message;
		this.date = formatter.format(new Date());
		this.name = name;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = formatter.format(date);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	// Fulano em 27/09/2020 13:49: Olá
	@Override
	public String toString() {
		return this.message;
	}

}
