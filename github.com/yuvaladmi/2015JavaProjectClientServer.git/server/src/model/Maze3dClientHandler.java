package model;

import java.io.InputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.Maze3d;

public class Maze3dClientHandler implements ClinetHandler {
	protected Maze3d myMaze;
	
	public Maze3dClientHandler() {
		myMaze=new Maze3d(3,3,3);
	}
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {

	}

}
