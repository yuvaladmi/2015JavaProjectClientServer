package view;

import java.io.InputStream;
import java.io.OutputStream;

public interface View {

	public void start();

	public void stop();

	public void display(String s);
	
	public void generateProtocol(InputStream inFromClient, OutputStream outToServer);
	
	public void solveProtocol(InputStream inFromClient, OutputStream outToServer);
	
	public void getMazeProtocol(InputStream inFromClient, OutputStream outToServer);
	
	public void getSolveProtocol(InputStream inFromClient, OutputStream outToServer);

}
