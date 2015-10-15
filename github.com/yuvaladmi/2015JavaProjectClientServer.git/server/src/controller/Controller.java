package controller;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Maze3dClientHandler;
import model.Model;
import model.MyServer;
import view.ServerWindow;
import view.View;

public class Controller {

	Model m;
	View v;
	ServerWindow sw;
	MyServer server;
	Maze3dClientHandler maze;
	Properties properties;

	public Controller() {
		this.properties=new Properties();
		
	}

	public void start() {
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop(){
		try {
			server.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


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
		System.out.println("get Sol");
		return m.gethSol().get(m.gethMaze().get(name));
	}

	public Maze3dClientHandler getMaze() {
		return maze;
	}
	
	public void setMaze(Maze3dClientHandler maze) {
		this.maze = maze;
		server = new MyServer(properties.getPort(), this.maze,properties.getMaxClients(),this);
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
