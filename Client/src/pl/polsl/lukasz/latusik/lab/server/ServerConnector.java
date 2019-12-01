package pl.polsl.lukasz.latusik.lab.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 The type Server connector.
 @author Łukasz Latusik
 @version 1.0
 */
public class ServerConnector implements AutoCloseable {
	
	private int portNumber;
	private String ipAddress;
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	
	/**
	 Instantiates a new Server connector.
	 
	 @param portNumber the port number
	 @param ipAddress the ip address
	 @throws IOException the io exception
	 */
	public ServerConnector(int portNumber, String ipAddress) throws IOException {
		this.socket=new Socket(ipAddress,portNumber);
		this.output = new PrintWriter(socket.getOutputStream(), true);
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	/**
	 Read message string.
	 
	 @return the string
	 @throws IOException the io exception
	 */
	public String readMessage() throws IOException{
		return input.readLine();
	}
	
	/**
	 Send message.
	 
	 @param message the message
	 */
	public void sendMessage(String message){
		output.println(message);
	}
	
	/**
	 Gets input.
	 
	 @return the input
	 */
	public BufferedReader getInput() {
		return input;
	}
	
	@Override
	public void close() throws Exception {
		socket.close();
	}
}
