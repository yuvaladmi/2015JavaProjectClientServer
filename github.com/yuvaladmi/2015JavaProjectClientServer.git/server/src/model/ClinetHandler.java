package model;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClinetHandler {
	/**
	 * This method gets an input and output stream and handling the client
	 * according to what he asks
	 * 
	 * @param inFromClient
	 * @param outToClient
	 */
	void handleClient(InputStream inFromClient, OutputStream outToClient);
}
