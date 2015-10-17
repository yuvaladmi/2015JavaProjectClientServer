package view;

import java.io.InputStream;
import java.io.OutputStream;

public interface View {
/**
 * Starting the server side
 */
	public void start();
/**
 * Stopping the server
 */
	public void stop();
/**
 * Display a messege in CLI mode
 * @param s
 */
	public void display(String s);
	/**
	 * This method gets 2 parameters and starting a communication protocol with the client
	 * @param inFromClient
	 * @param outToServer
	 */
	public void generateProtocol(InputStream inFromClient, OutputStream outToServer);
	/**
	 * This method gets 2 parameters and starting a communication protocol with the client
	 * @param inFromClient
	 * @param outToServer
	 */
	public void solveProtocol(InputStream inFromClient, OutputStream outToServer);
	/**
	 * This method gets 2 parameters and starting a communication protocol with the client
	 * @param inFromClient
	 * @param outToServer
	 */
	public void getMazeProtocol(InputStream inFromClient, OutputStream outToServer);
	/**
	 * This method gets 2 parameters and starting a communication protocol with the client
	 * @param inFromClient
	 * @param outToServer
	 */
	public void getSolveProtocol(InputStream inFromClient, OutputStream outToServer);

}
