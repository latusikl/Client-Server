package pl.polsl.lukasz.latusik.lab;

public class OutputManager {
	
	public static synchronized void pushMessage(String message){
		System.out.println(message);
	}
}
