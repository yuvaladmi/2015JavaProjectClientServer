package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;
import model.MyServer;

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
	public void generateProtocol(BufferedReader inFromClient, PrintWriter outToServer) {

		try {
			String name, temp;
			int x, y, z;
			outToServer.println("What is the maze name?");
			outToServer.flush();
			name = (inFromClient.readLine().split(": ")[1]);
			outToServer.println("How many levels?");
			outToServer.flush();
			temp = (inFromClient.readLine().split(": ")[1]);
			x = Integer.parseInt(temp);
			outToServer.println("How many lines?");
			outToServer.flush();
			temp = (inFromClient.readLine().split(": ")[1]);
			y = Integer.parseInt(temp);
			outToServer.println("How many columns?");
			outToServer.flush();
			temp = (inFromClient.readLine().split(": ")[1]);
			z = Integer.parseInt(temp);
			controller.update(("generate" + name + " " + x + " " + y + " " + z).split(" "));
			Maze3d maze = controller.getMaze(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void getMazeProtocol(BufferedReader inFromClient, PrintWriter outToServer) {
		try {
			String name;
			outToServer.println("What is the maze name?");
			name = (inFromClient.readLine().split(": ")[1]);
			Maze3d maze = controller.getMaze(name);
			if (maze == null) {
				System.out.println("error");
			} else {
				byte[] buffer = maze.toByteArray();
				for (byte b : buffer) {
					outToServer.write((int) b);
					outToServer.flush();
				}
				outToServer.write(127);
				outToServer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void solveProtocol(BufferedReader inFromClient, PrintWriter outToServer) {
		try {
			String name, algo;
			outToServer.println("What is the maze name?");
			outToServer.flush();
			name = (inFromClient.readLine().split(": ")[1]);
			outToServer.println("What is the algorithm name?");
			outToServer.flush();
			algo = (inFromClient.readLine().split(": ")[1]);
			controller.update(("solve" + name + " " + algo).split(" "));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void getSolveProtocol(BufferedReader inFromClient, PrintWriter outToServer) {
		try {
			String name;
			outToServer.println("What is the maze name?");
			outToServer.flush();
			name = (inFromClient.readLine().split(": ")[1]);
			Solution<Position> solution = controller.getSolution(name);
			String solutionStr = solution.toString();
			if(solution != null){
				outToServer.write(solutionStr);
				outToServer.flush();
			}
			outToServer.write(-1);
			outToServer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
