package pl.polsl.lukasz.latusik.lab.server.protocol;

import pl.polsl.lukasz.latusik.lab.model.Shop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 The type Input parser.
 @author Łukasz Latusik
 @version 1.0
 */
public class InputParser {
	
	/**
	 The Shop.
	 */
	Shop shop;
	/**
	 The Command executor.
	 */
	CommandExecutor commandExecutor;
	/**
	 The Command info manger.
	 */
	CommandInfoManger commandInfoManger;
	
	/**
	 Instantiates a new Input parser.
	 
	 @param shop the shop
	 @param commandInfoManger the command info manger
	 */
	public InputParser(Shop shop, CommandInfoManger commandInfoManger){
		this.shop=shop;
		this.commandInfoManger=commandInfoManger;
		this.commandExecutor=new CommandExecutor(shop,commandInfoManger);
	}
	
	/**
	 Shop uninitialized string.
	 
	 @return the string
	 */
	public String shopUninitialized(){
		String output="No shop data found! \n"
				+ "Uses save-file for setting data files. \n"
				+"For more info use HELP command.";
		return output;
	}
	
	/**
	 No valid command string.
	 
	 @return the string
	 */
	public String noValidCommand(){
		return "Command not found.";
	}
	
	private String[] splitInput(String clientInput) {
		final String splitter = "[&]";
		String[] splitCommand = clientInput.split(splitter);
		if (splitCommand.length == 0 && !clientInput.isBlank()) {
			return new String[]{clientInput};
		}
		else
			return splitCommand;
	}
	private boolean isCommand(String command){
		if(commandInfoManger.getCommandList().contains(command)){
			return true;
		}
		else
			return false;
	}
	
	/**
	 Analyze client input string.
	 
	 @param clientInput the client input
	 @return the string
	 */
	public String analyzeClientInput(String clientInput) {
		
		if (!shop.isShopInitialized()) {
			return shopUninitialized();
		}
		else {
			String[] splitCommand = splitInput(clientInput);
				if (splitCommand.length == 0 || !isCommand(splitCommand[0])) {
					return noValidCommand();
				}
			else {
				String serverOutput="";
				try {
					//Method name, method argument type
					String methodName= splitCommand[0];
					Method commandMethod = CommandExecutor.class.getMethod(methodName,List.class);
					serverOutput= (String) commandMethod.invoke(this.commandExecutor, Arrays.asList(splitCommand));
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					serverOutput="Command method not found!\n" + e.toString();
				}
					return serverOutput;
			}
			}
		}
}

