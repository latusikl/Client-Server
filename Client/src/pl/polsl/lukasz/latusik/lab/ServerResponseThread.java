package pl.polsl.lukasz.latusik.lab;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerResponseThread extends Thread {
	
	ServerConnector serverConnector;
	OutputManager clientOutput;
	
	ServerResponseThread(ServerConnector serverConnector, OutputManager clientOutput){
		super("Server response thread");
		this.serverConnector = serverConnector;
		this.clientOutput = clientOutput;
	}
	
	@Override
	public void run() {
		String serverMessage;
		while(true){
			try {
				serverMessage = serverConnector.readMessage();
			} catch (IOException e) {
				clientOutput.pushMessage(e.toString());
				break;
			}
			if(serverMessage == null ){
				clientOutput.pushMessage("Server disconnected.");
				break;
			}
			clientOutput.pushMessage("Server response: " + serverMessage);
		}
	}
}
