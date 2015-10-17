package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import view.View;
/**
 * 
 * @author Yuval Admi & Afek Ben Simon
 * @since 10.10.2015
 * This class implements ClientHandler
 *
 */
public class Maze3dClientHandler implements ClinetHandler {

	View view;
/**
 * CTOR
 * @param v
 */
	public Maze3dClientHandler(View v) {
		view = v;
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToClient);
			String line;
			while (!(line = in.readLine()).endsWith("exit")) {
				//getting commands from the client until he sends 'exit'
				switch (line) {
				case "generate new maze":
					out.println("ok");
					out.flush();
					view.generateProtocol(inFromClient, outToClient);
					out.println("done");
					out.flush();
					break;
				case "get maze":
					out.println("ok");
					out.flush();
					view.getMazeProtocol(inFromClient, outToClient);
					out.println("done");
					out.flush();
					break;
				case "solve maze":
					out.println("ok");
					out.flush();
					view.solveProtocol(inFromClient, outToClient);
					System.out.println("done solve client");
					out.println("done");
					out.flush();
					break;
				case "send solution":
					out.println("ok");
					out.flush();
					view.getSolveProtocol(inFromClient, outToClient);
					out.println("done");
					out.flush();
					break;

				}
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
