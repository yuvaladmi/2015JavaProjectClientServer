package controller;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Maze3dClientHandler;
import model.Model;
import model.MyServer;
import view.ServerWindow;
import view.View;
/**
 * 
 * @author Yuval Admi & Afek Ben Simon
 *
 *        This class should get an information what we want to do from the View,
 *        send it to the Model, wait for an answer and send to the View back.
 */
public class Controller {

	Model m;
	View v;
	ServerWindow sw;
	MyServer server;
	Maze3dClientHandler maze;
	Properties properties;
/**
 * CTOR
 * @param p
 */
	public Controller(Properties p) {
		this.properties= p;
		
	}
/**
 * Starting the server
 */
	public void start() {
		try {
		    XMLDecoder xmlD = new XMLDecoder(new BufferedInputStream(new FileInputStream("serverProperties.xml")));
		    properties = (Properties) xmlD.readObject();
		    xmlD.close();
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		try {
			server = new MyServer(properties.getPort(), this.maze,properties.getMaxClients(),this);
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Stopping the model and the server
	 */
	public void stop(){
		try {
			m.close();
			server.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

/**
 * Getting a command from the view and send it to the model
 * @param str
 * @return true when we done doing the command
 */
	public boolean update(String[] str) {
		switch (str[0]) {
		case "generate":
			m.generateMaze(str);
			System.out.println("update return");
			return true;
		case "solve":
			m.solve(str);
			System.out.println("update return solution");
			return true;
		}
		return false;
	}
	
	public void display(String str){
		sw.displayPopUp(str);
	}

	public ServerWindow getSw() {
		return sw;
	}

	public void setSw(ServerWindow sw) {
		this.sw = sw;
	}

	public Maze3d getMaze(String str) {
		Maze3d maze = m.sendGame(str);
		return maze;

	}
	

	public Solution<Position> getSolution(String name) {
		return m.gethSol().get(m.gethMaze().get(name));
	}

	public Maze3dClientHandler getMaze() {
		return maze;
	}
	
	public void setMaze(Maze3dClientHandler maze) {
		this.maze = maze;
		
	}
	
	public Model getM() {
		return m;
	}
	
	public void setM(Model m) {
		this.m = m;
	}
	
	public View getV() {
		return v;
	}
	
	public void setV(View v) {
		this.v = v;
	}
}
