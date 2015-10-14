package view;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface View {

	public void start();

	public void stop();

	public void display(String s);
	
	public void generateProtocol(BufferedReader inFromClient, PrintWriter outToServer);
	
	public void solveProtocol(BufferedReader inFromClient, PrintWriter outToServer);
	
	public void getMazeProtocol(BufferedReader inFromClient, PrintWriter outToServer);
	
	public void getSolveProtocol(BufferedReader inFromClient, PrintWriter outToServer);

}
