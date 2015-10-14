package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;

import controller.Controller;

public abstract class AbView extends Observable implements View {

	protected Controller controller;
	
	public abstract void start();

	public abstract void stop();

	public abstract void display(String s);

	public abstract void generateProtocol(BufferedReader inFromClient, PrintWriter outToServer);

	public abstract void solveProtocol(BufferedReader inFromClient, PrintWriter outToServer);

	public abstract void getMazeProtocol(BufferedReader inFromClient, PrintWriter outToServer);
	
	public abstract void getSolveProtocol(BufferedReader inFromClient, PrintWriter outToServer);

}
