package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import controller.Controller;


/**
 * @author Yuval Admi & Afek Ben Simon
 * @since 08.10.2015 This class extends the abstract class - CommonModel. It
 *        should get a command from the presenter, do it and send a notification
 *        when it ends.
 * 
 */
public class Maze3dModel extends abstractModel{

	/**
	 * CTOR
	 */
	public Maze3dModel(Controller controller) {
		this.controller = controller;
		hMaze = new HashMap<String, Maze3d>();
		hSol = new HashMap<Maze3d, Solution<Position>>();
		hPosition = new HashMap<String, Position>();
		sb = new StringBuilder();
		numOfThread = 10;
		threadpool = Executors.newFixedThreadPool(numOfThread);
		loadZip();
	}

	
	@Override
	public boolean generateMaze(String[] arr) {
		int x = Integer.parseInt(arr[2]);
		int y = Integer.parseInt(arr[3]);
		int z = Integer.parseInt(arr[4]);
		String name = arr[1];

		Future<Maze3d> fCallMaze = threadpool.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				MyMaze3dGenerator mg = new MyMaze3dGenerator(x, y, z);
				Maze3d m = mg.generate(x, y, z);
				return m;
			}
		});
		try {
			hMaze.put(name, fCallMaze.get());
			hPosition.put(name, fCallMaze.get().getStart());
			System.out.println("generate done");
			return true;
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
//		String[] messege = ("maze is ready" + name).split(" ");
//		controller.update(messege);
		
	}

	
	@Override
	public Maze3d sendGame(String str) {
		String name = str;
		Maze3d temp = hMaze.get(name);
		return temp;
	}



	
	@Override
	public boolean solve(String[] arr) {
		String nameAlg =arr[arr.length-1];
		String name = arr[arr.length-2];
		System.out.println(name);
		Maze3d m = hMaze.get(name);
//		if ((hSol.get(tempMaze)) != null) {
//			return true;
//		}
		System.out.println("swsws");
		Future<Solution<Position>> fCallSolution = threadpool.submit(new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call() throws Exception {
				System.out.println("call solution");
				SearchableMaze sMaze = new SearchableMaze(m);
				CommonSearcher<Position> cs;
				Solution<Position> s = new Solution<Position>();
				switch (nameAlg) {
				case "Astar":
					cs = new AStar<Position>(new MazeManhattenDistance());
					s = cs.search(sMaze);
					break;
				case "A*":
					cs = new AStar<Position>(new MazeManhattenDistance());
					s = cs.search(sMaze);
					break;
				case "BFS":
					cs = new BFS<Position>();
					s = cs.search(sMaze);
					Stack<Position> temp = s.getSolution();
					System.out.println(temp);
					break;
					
				}
				return s;
			}
		});
		try {
			hSol.put(m, fCallSolution.get());
			System.out.println("solve done");
			return true;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public Solution<Position> bringSolution(String name) {
		System.out.println("solution " + name);
		Maze3d maze = hMaze.get(name);
		Solution<Position> sol;
		if (maze != null) {
			sol = hSol.get(maze);
			return sol;
		}
		return null;
	}
	
	@Override
	public void saveZip() {
		try {
			FileOutputStream fileMaze = new FileOutputStream("mazes.zip");
			ObjectOutputStream objMaze = new ObjectOutputStream(new GZIPOutputStream(fileMaze));
			objMaze.writeObject(hMaze);
			objMaze.writeObject(hSol);
			objMaze.flush();
			objMaze.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadZip() {
		try {
			FileInputStream fileMaze = new FileInputStream("mazes.zip");
			ObjectInputStream objMaze = new ObjectInputStream(new GZIPInputStream(fileMaze));
			hMaze = (HashMap<String, Maze3d>) objMaze.readObject();
			hSol = (HashMap<Maze3d, Solution<Position>>) objMaze.readObject();
			objMaze.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		saveZip();
		setChanged();
		notifyObservers("shutting down");
		threadpool.shutdown();
		// wait 10 seconds over and over again until all running jobs have
		// finished
		try {
			@SuppressWarnings("unused")
			boolean allTasksCompleted = false;
			while (!(allTasksCompleted = threadpool.awaitTermination(10, TimeUnit.SECONDS)))
				;
			setChanged();
			notifyObservers("Server is safely closed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
