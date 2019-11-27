package pl.polsl.lukasz.latusik.lab;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//Set on loopback
		try (ServerConnector serverConnector = new ServerConnector(8888,"127.0.0.1"))
		{
			OutputManager outputManager = new OutputManager();
			ServerResponseThread serverResponseThread = new ServerResponseThread(serverConnector,outputManager);
			serverResponseThread.start();
			
			Scanner scanner = new Scanner(System.in);
			while(true){
				if(scanner.hasNext())
				serverConnector.sendMessage(scanner.nextLine());
			}
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
