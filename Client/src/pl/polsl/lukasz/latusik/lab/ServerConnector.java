package pl.polsl.lukasz.latusik.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnector implements AutoCloseable {
	
	int portNumber;
	
	String ipAddress;
	Socket socket;
	PrintWriter output;
	BufferedReader input;
	
	ServerConnector(int portNumber, String ipAddress) throws IOException {
		this.socket=new Socket(ipAddress,portNumber);
		this.output = new PrintWriter(socket.getOutputStream(), true);
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public String readMessage() throws IOException{
		return input.readLine();
	}
	
	public void sendMessage(String message){
		output.println(message);
	}
	
	public BufferedReader getInput() {
		return input;
	}
	
	@Override
	public void close() throws Exception {
		socket.close();
	}
}
