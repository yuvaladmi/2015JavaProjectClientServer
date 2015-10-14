package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

import controller.Controller;

public abstract class AbView extends Observable implements View {

	protected Controller controller;
	
	public abstract void start();

	public abstract void stop();

	public abstract void display(String s);

	public abstract void generateProtocol(InputStream inFromClient, OutputStream outToServer);

	public abstract void solveProtocol(InputStream inFromClient, OutputStream outToServerr);

	public abstract void getMazeProtocol(InputStream inFromClient, OutputStream outToServer);
	
	public abstract void getSolveProtocol(InputStream inFromClient, OutputStream outToServer);

}
