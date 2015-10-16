package controller;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

public class Properties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	protected int port;
	protected int maxClients;
	
	public Properties() {
		this.port=5400;
		this.maxClients=10;
	
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getMaxClients() {
		return maxClients;
	}
	public void setMaxClients(int maxClients) {
		this.maxClients = maxClients;
	}
	
	
}
