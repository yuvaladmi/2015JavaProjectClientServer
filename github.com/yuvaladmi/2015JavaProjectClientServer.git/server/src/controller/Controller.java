package controller;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Maze3dClientHandler;
import model.Model;
import model.MyServer;
import view.View;

public class Controller {

	Model m;
	View v;
	MyServer server;
	Maze3dClientHandler maze;

	public Controller() {
		server = new MyServer(5400, maze, 10,this);
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


	public void update(String[] str) {
		switch (str[0]) {
		case "generate":
			m.generateMaze(str);
			break;
		case "solve":
			m.solve(str);
			break;
		default:
			break;
		}
	}
	
	public void display(String str){
		v.display(str);
	}

	public Maze3d getMaze(String str) {
		Maze3d maze = m.sendGame(str);
		return maze;

	}

	public Solution<Position> getSolution(String name) {
		Solution<Position> solution = m.bringSolution(name);
		return solution;
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
