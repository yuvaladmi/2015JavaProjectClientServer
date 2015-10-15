package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

public class MyView extends AbView {

	public MyView(Controller c) {
		this.controller = c;
	}

	@Override
	public void start() {
		controller.start();

	}

	@Override
	public void stop() {
		controller.stop();

	}

	@Override
	public void display(String s) {
		System.out.println(s);

	}

	@Override
	public void generateProtocol(InputStream inFromClient, OutputStream outToServer) {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToServer);
			String name, temp;
			int x, y, z;
			out.println("What is the maze name?");
			out.flush();
			name = (in.readLine().split(": ")[1]);
			out.println("How many levels?");
			out.flush();
			temp = (in.readLine().split(": ")[1]);
			x = Integer.parseInt(temp);
			out.println("How many lines?");
			out.flush();
			temp = (in.readLine().split(": ")[1]);
			y = Integer.parseInt(temp);
			out.println("How many columns?");
			out.flush();
			temp = (in.readLine().split(": ")[1]);
			z = Integer.parseInt(temp);
			controller.update(("generate " + name + " " + x + " " + y + " " + z).split(" "));
			// out.println("done");
			// out.flush();
			// Maze3d maze = controller.getMaze(name);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getMazeProtocol(InputStream inFromClient, OutputStream outToServer) {
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToServer);
			String name;
			out.println("What is the maze name?");
			out.flush();
			name = in.readLine();
			System.out.println(name);
			Maze3d maze = controller.getMaze(name);

			if (maze == null) {
				System.out.println("error");
			} else {
				byte[] buffer = maze.toByteArray();
				for (byte b : buffer) {
					out.write((int) b);
					out.flush();
				}
				out.write(127);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void solveProtocol(InputStream inFromClient, OutputStream outToServer) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToServer);

			String name, algo;
			out.println("What is the maze name?");
			out.flush();
			name = (in.readLine().split(": ")[1]);
			out.println("What is the algorithm name?");
			out.flush();
			algo = (in.readLine().split(": ")[1]);
			System.out.println(algo);
			controller.update(("solve " + name + " " + algo).split(" "));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getSolveProtocol(InputStream inFromClient, OutputStream outToServer) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToServer);

			String name,buffer;
			out.println("What is the maze name?");
			out.flush();
			name = (in.readLine().split(": ")[1]);
			System.out.println(name);
			Solution<Position> solution = controller.getSolution(name);
			Stack<Position> sol = solution.getSolution();
			Iterator<Position> itr = sol.iterator();
			Position p = new Position(0,0,0);
			while(itr.hasNext()){
				p = itr.next();
				buffer = p.toString();
				out.println(buffer);
				out.flush();
			}
//			while (!sol.isEmpty()) {
//				Position p = sol.pop();
//				buffer = p.toString();
//				out.println(buffer);
//				out.flush();
//			}
			out.println("yuval");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
