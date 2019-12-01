package pl.polsl.lukasz.latusik.lab.server;

import javafx.application.Platform;
import java.io.IOException;

/**
 The type Server response thread.
 @author Łukasz Latusik
 @version 1.0
 */
public class ServerResponseThread extends Thread {
	
	private ServerConnector serverConnector;
	private OutputSender outputSender;
	private volatile boolean end = false;
	
	/**
	 Instantiates a new Server response thread.
	 
	 @param serverConnector the server connector
	 @param outputSender the output sender
	 */
	public ServerResponseThread(ServerConnector serverConnector,OutputSender outputSender ){
		super("Server response thread");
		this.serverConnector = serverConnector;
		this.outputSender = outputSender;
	}
	
	/**
	 Changes end boolean to terminate thread
	 */
	public void end(){
		this.end=true;
	}
	
	/**
	 Sends disconnect message to GUI.
	 */
	private void disconnectedMessage(){
		Platform.runLater(() -> outputSender.manageOutput("Server disconnected."));
	}
	
	@Override
	public void run() {
		
		Boolean internalExit = false;
		while(!end && !internalExit){
			
			if(serverConnector == null){
				disconnectedMessage();
				internalExit = true;
			}
			else {
				final String serverMessage;
				try {
					serverMessage = serverConnector.readMessage();
					Platform.runLater(() -> outputSender.manageOutput(serverMessage));
					if (serverMessage == null) {
						Platform.runLater(() -> outputSender.manageOutput("Server disconnected."));
						
						internalExit = true;
					}
				} catch (IOException e) {
					Platform.runLater(() -> outputSender.manageOutput("Server disconnected."));
					internalExit = true;
				}
			}
		}
	}
}
