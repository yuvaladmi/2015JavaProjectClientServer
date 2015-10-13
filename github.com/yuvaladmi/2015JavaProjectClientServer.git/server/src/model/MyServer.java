package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyServer extends Observable {

	int port;
	ServerSocket server;
	
	ClinetHandler clinetHandler;
	int numOfClients;
	ExecutorService threadpool;
	
	volatile boolean stop;
	
	Thread mainServerThread;
	
	int clientsHandled=0;
	
	public MyServer(int port,ClinetHandler clinetHandler,int numOfClients) {
		this.port=port;
		this.clinetHandler=clinetHandler;
		this.numOfClients=numOfClients;
	}
	
	
	public void start() throws Exception{
		server=new ServerSocket(port);
		server.setSoTimeout(10*1000);
		threadpool=Executors.newFixedThreadPool(numOfClients);
		
		mainServerThread=new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!stop){
					try {
						final Socket someClient=server.accept();
						if(someClient!=null){
							threadpool.execute(new Runnable() {									
								@Override
								public void run() {
									try{										
										clientsHandled++;
										setChanged();
										notifyObservers("\thandling client "+clientsHandled);
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										someClient.close();
										setChanged();
										notifyObservers("\tdone handling client "+clientsHandled);										
									}catch(IOException e){
										e.printStackTrace();
									}									
								}
							});								
						}
					}
					catch (SocketTimeoutException e){
						setChanged();
						notifyObservers("no clinet connected...");
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				setChanged();
				notifyObservers("done accepting new clients.");
			} // end of the mainServerThread task
		});
		
		mainServerThread.start();
		
	}
	
	public void close() throws Exception{
		stop=true;	
		// do not execute jobs in queue, continue to execute running threads
		setChanged();
		notifyObservers("shutting down");
		threadpool.shutdown();
		// wait 10 seconds over and over again until all running jobs have finished
		boolean allTasksCompleted=false;
		while(!(allTasksCompleted=threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		
		setChanged();
		notifyObservers("all the tasks have finished");

		mainServerThread.join();		
		setChanged();
		notifyObservers("main server thread is done");
		
		server.close();
		setChanged();
		notifyObservers("server is safely closed");
	}
	
	
	public static void main(String[] args) throws Exception{
		System.out.println("Server Side");
		System.out.println("type \"close the server\" to stop it");
//		MyServer server=new MyServer(5400,new ASCIIArtClientHandler(),10);
//		server.start();
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		
		while(!(in.readLine()).equals("close the server"));
		
//		server.close();		
		
	}
}
