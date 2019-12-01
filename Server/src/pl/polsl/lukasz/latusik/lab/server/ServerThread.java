package pl.polsl.lukasz.latusik.lab.server;

import pl.polsl.lukasz.latusik.lab.model.Shop;
import pl.polsl.lukasz.latusik.lab.server.protocol.InputParser;

import java.io.*;
import java.net.Socket;

/**
 The type Server thread.
 */
public class ServerThread
		extends Thread
		implements AutoCloseable
{
	
	private Socket socket;
	//Closed with socket
	private BufferedReader input;
	private PrintWriter output;
	private Shop currentShop;
	private InputParser inputParser;
	
	/**
	 Instantiates a new Server thread.
	 
	 @param socket the socket
	 @param currentShop the current shop
	 @param initialMessage the initial message
	 @param inputParser the input parser
	 @throws IOException the io exception
	 */
	ServerThread(Socket socket, Shop currentShop, String initialMessage, InputParser inputParser) throws IOException{
		super("Client server thread");
		this.socket=socket;
		this.output = new PrintWriter(socket.getOutputStream(), true);
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.currentShop=currentShop;
		this.inputParser=inputParser;
		
		sendMessageToClient("Welcome on Java Server!\nType \"help\" to get available commands.");
		sendMessageToClient(initialMessage);
	}
	
	/**
	 Gets input.
	 
	 @return the input
	 @throws IOException the io exception
	 */
	public String getInput() throws IOException {
		return this.input.readLine();
	}
	
	/**
	 Send message to client.
	 
	 @param message the message
	 */
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
			sendMessageToClient(e.getStackTrace().toString());
			break;
		}
			if(clientInputLine == null || clientInputLine.equals("exit")){
				System.out.println("One of clients disconnected.");
				sendMessageToClient("Connection with server has been closed.");
				break;
			}
			sendMessageToClient(inputParser.analyzeClientInput(clientInputLine));
		}
	}
}
