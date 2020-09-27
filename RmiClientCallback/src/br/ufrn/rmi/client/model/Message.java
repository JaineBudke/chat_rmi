package br.ufrn.rmi.client.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
	
	private String message;
	private String date;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  

	public Message(String message) {
		this.message = message;
		this.date = formatter.format(new Date());
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

	@Override
	public String toString() {
		return "Message [message=" + message + ", date=" + date + "]";
	}
	

}
