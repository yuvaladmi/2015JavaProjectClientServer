package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;
import presenter.Properties;

public abstract class abstractModel extends Observable implements Model {

	public StringBuilder sb;
	public ExecutorService threadpool;
	int numOfThread;
	public HashMap<String, Maze3d> hMaze;
	public HashMap<String, Position> hPosition;
	public HashMap<Maze3d, Solution<Position>> hSol;
	public Controller controller;

	public abstract void generateMaze(String[] arr);

	public abstract Maze3d sendGame(String str);

	public abstract void solve(String[] arr);

	public abstract Solution<Position> bringSolution(String name);

	public abstract void close();

	public abstract void saveZip();

	public abstract void loadZip();
}
