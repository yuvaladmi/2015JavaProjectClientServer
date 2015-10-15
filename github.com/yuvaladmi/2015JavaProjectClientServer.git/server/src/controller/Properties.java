package controller;

public class Properties {

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
