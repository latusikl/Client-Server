package pl.polsl.lukasz.latusik.lab.server;

import java.io.*;
import java.net.Socket;

public class ServerThread
		extends Thread
		implements AutoCloseable
{
	
	private Socket socket;
	//Closed with socket
	private BufferedReader input;
	private PrintWriter output;

	ServerThread(Socket socket) throws IOException{
		super("Client server thread");
		this.socket=socket;
		this.output = new PrintWriter(socket.getOutputStream(), true);
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		sendMessageToClient("Welcome on Java Server!");
	}
	
	public String getInput() throws IOException {
		return this.input.readLine();
	}
	
	public void sendMessageToClient (String message){
		output.println(message);
	}
	
	@Override
	public void close() throws Exception {
		socket.close();
	}
	
	@Override
	public void run() {

		String clientInputLine;
		while(true){
			try {
				clientInputLine = getInput();
		} catch (IOException e) {
			sendMessageToClient(e.toString());
			break;
		}
			if(clientInputLine == null || clientInputLine.equals("exit")){
				System.out.println("One of clients disconnected.");
				sendMessageToClient("Connection with server has been closed.");
				break;
			}
			sendMessageToClient(clientInputLine);
		}
	}
}
