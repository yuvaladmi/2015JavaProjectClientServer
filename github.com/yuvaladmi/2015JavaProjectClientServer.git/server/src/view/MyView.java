package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void generateProtocol(InputStream inFromClient, OutputStream outToServer) {

		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToServer);
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
			controller.update(("generate" + name + " " + x + " " + y + " " + z).split(" "));
			Maze3d maze = controller.getMaze(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void getMazeProtocol(InputStream inFromClient, OutputStream outToServer) {
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToServer);
			String name;
			out.println("What is the maze name?");
			name = (in.readLine().split(": ")[1]);
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
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToServer);
			
			String name, algo;
			out.println("What is the maze name?");
			out.flush();
			name = (in.readLine().split(": ")[1]);
			out.println("What is the algorithm name?");
			out.flush();
			algo = (in.readLine().split(": ")[1]);
			controller.update(("solve" + name + " " + algo).split(" "));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void getSolveProtocol(InputStream inFromClient, OutputStream outToServer) {
		try {
			BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out=new PrintWriter(outToServer);
			
			String name;
			out.println("What is the maze name?");
			out.flush();
			name = (in.readLine().split(": ")[1]);
			Solution<Position> solution = controller.getSolution(name);
			String solutionStr = solution.toString();
			if(solution != null){
				out.write(solutionStr);
				out.flush();
			}
			out.write(-1);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
