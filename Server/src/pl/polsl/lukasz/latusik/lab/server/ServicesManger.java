package pl.polsl.lukasz.latusik.lab.server;

import pl.polsl.lukasz.latusik.lab.model.*;
import pl.polsl.lukasz.latusik.lab.model.exception.InputException;
import pl.polsl.lukasz.latusik.lab.server.protocol.CommandInfoManger;
import pl.polsl.lukasz.latusik.lab.server.protocol.InputParser;

import java.io.IOException;
import java.net.Socket;

/**
 The type Services manger.
 @author Łukasz Latusik
 @version 1.0
 */
public class ServicesManger {
	
	private static final String COMMAND_DIR_PATH= "Command_Files";
	private static final String COMMAND_FILE_NAME = "commands";
	
	/**
	 Program takes 3 arguments which are file names with serialized data. Sequence of names: clients_file_name
	 products_file_name product_categories_file_name
	 
	 @param args the args
	 */
	public static void main(String[] args){
		Shop currentShop;
		String initialMessage="";
		CommandInfoManger commandInfoManger = new CommandInfoManger(COMMAND_DIR_PATH,COMMAND_FILE_NAME);
		
		try {
			commandInfoManger.readCommandList();
			commandInfoManger.readCommandInfo();
		} catch (IOException e) {
			commandInfoManger= null;
			initialMessage+="Unable to initialize command info manager \n" + e.getStackTrace();
		}

		try {
			currentShop = getShop(args);
		}
		catch (InputException e){
			currentShop=new Shop();
			initialMessage += "Shop is uninitalized.\nPass file names by savefile command.";
		}
		
		InputParser inputParser = new InputParser(currentShop,commandInfoManger);
		
		
		try (Server server = new Server();){
			System.out.println("Threads active before first client:" + Thread.activeCount() + "\n\n");
			while(true){
				Socket socket=server.startServerForClient();
				ServerThread serverThread = new ServerThread(socket,currentShop,initialMessage,inputParser);
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
		
		private static Shop getShop(String[] args) throws InputException{
			if(args.length!=3){
				throw new InputException("Not enough file names!");
			}
			else
				return  new Shop(args[0],args[1],args[2]);
		}
}

