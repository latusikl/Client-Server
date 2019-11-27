package pl.polsl.lukasz.latusik.lab.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServicesManger {
	public static void main(String[] args) {
		
	
		try (Server server = new Server();){
			System.out.println("Threads active before first client:" + Thread.activeCount() + "\n\n");
			
			while(true){
				Socket socket=server.startServerForClient();
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();
				System.out.println("Threads active:" + Thread.activeCount());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
		
			
		}
}

