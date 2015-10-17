package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import controller.Controller;

public class MyServer extends Observable {

	int port;
	ServerSocket server;

	ClinetHandler clinetHandler;
	int numOfClients;
	ExecutorService threadpool;

	volatile boolean stop;

	Thread mainServerThread;
	Controller controller;
	int clientsHandled = 0;

	public MyServer(int port, ClinetHandler clinetHandler, int numOfClients, Controller c) {
		this.port = port;
		this.clinetHandler = clinetHandler;
		this.numOfClients = numOfClients;
		this.controller = c;
	}

	public void start() throws Exception {
		controller.display("server connected");
		server = new ServerSocket(port);
		server.setSoTimeout(10 * 1000);
		threadpool = Executors.newFixedThreadPool(numOfClients);

		mainServerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stop) {
					try {
						final Socket someClient = server.accept();
						if (someClient != null) {
							threadpool.execute(new Runnable() {
								@Override
								public void run() {
									try {
										clientsHandled++;

										controller.display("handling client " + clientsHandled);
										clinetHandler.handleClient(someClient.getInputStream(),
												someClient.getOutputStream());
										someClient.close();
										controller.display("done handling client " + clientsHandled);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
						}
					} catch (SocketTimeoutException e) {
						// controller.display("no clinet connected...");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
//				controller.display("done accepting new clients.");
			} // end of the mainServerThread task
		});

		mainServerThread.start();

	}

	public void close() throws Exception {
		stop = true;
		// do not execute jobs in queue, continue to execute running threads
		threadpool.shutdown();
		// wait 10 seconds over and over again until all running jobs have
		// finished
		try {
			@SuppressWarnings("unused")
			boolean allTasksCompleted = false;
			while (!(allTasksCompleted = threadpool.awaitTermination(10, TimeUnit.SECONDS))){
				threadpool.shutdownNow();
			}
			mainServerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			server.close();
	}

}
