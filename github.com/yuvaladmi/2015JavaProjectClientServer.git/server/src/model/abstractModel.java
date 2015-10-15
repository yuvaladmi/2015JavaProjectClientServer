package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

public abstract class abstractModel extends Observable implements Model {

	public StringBuilder sb;
	public ExecutorService threadpool;
	int numOfThread;
	public HashMap<String, Maze3d> hMaze;
	public HashMap<String, Position> hPosition;
	public HashMap<Maze3d, Solution<Position>> hSol;
	public Controller controller;

	public HashMap<Maze3d, Solution<Position>> gethSol() {
		return hSol;
	}

	public void sethSol(HashMap<Maze3d, Solution<Position>> hSol) {
		this.hSol = hSol;
	}

	public HashMap<String, Maze3d> gethMaze(){
		return hMaze;
	}

	public abstract boolean generateMaze(String[] arr);

	public abstract Maze3d sendGame(String str);

	public abstract boolean solve(String[] arr);

	public abstract Solution<Position> bringSolution(String name);

	public abstract void close();

	public abstract void saveZip();

	public abstract void loadZip();
}
