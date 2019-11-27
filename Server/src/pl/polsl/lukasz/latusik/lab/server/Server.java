package pl.polsl.lukasz.latusik.lab.server;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements AutoCloseable{
	private final int portNumber;
	private ServerSocket serverSocket;
	
	
	public Server(int portNumber) throws IOException {
		this.portNumber = portNumber;
		this.serverSocket=new ServerSocket(portNumber);
		System.out.println("Server started.");
		InetAddress localhost = InetAddress.getLocalHost();
	}
	public Server() throws IOException {
		this(8888);
	}
	
	public Socket startServerForClient() throws IOException{
		Socket socket=this.serverSocket.accept();
		System.out.println("New client connected!");
		return socket;
	}
	
	@Override
	public void close() throws Exception {
			if(serverSocket != null){
				serverSocket.close();
			}
	}
}
