package pl.polsl.lukasz.latusik.lab.server;

import pl.polsl.lukasz.latusik.lab.model.Product;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 The type Server.
 @author Łukasz Latusik
 @version 1.0
 */
public class Server implements AutoCloseable{
	private final int portNumber;
	private ServerSocket serverSocket;
	
	/**
	 Instantiates a new Server.
	 
	 @throws IOException the io exception
	 */
	public Server() throws IOException {
		this.portNumber = this.portNumberProperty();
		this.serverSocket=new ServerSocket(portNumber);
		System.out.println("Server started.");
		InetAddress localhost = InetAddress.getLocalHost();
	}
	
	/**
	 Start server for client socket.
	 
	 @return the socket
	 @throws IOException the io exception
	 */
	public Socket startServerForClient() throws IOException{
		Socket socket=this.serverSocket.accept();
		System.out.println("New client connected!");
		return socket;
	}
	
	/**
	 Reads port number property.
	 @return Integer port value.
	 */
	private static int portNumberProperty(){
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("config.properties"));
			return Integer.parseInt(properties.getProperty("port"));
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
			return 8888;
		}
	}
	@Override
	public void close() throws Exception {
			if(serverSocket != null){
				serverSocket.close();
			}
	}
}
