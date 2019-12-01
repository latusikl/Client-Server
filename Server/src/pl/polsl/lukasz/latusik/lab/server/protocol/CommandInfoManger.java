package pl.polsl.lukasz.latusik.lab.server.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 The type Command info manger.
 @author Łukasz Latusik
 @version 1.0
 */
public class CommandInfoManger {
	
	/**
	 The Command list.
	 */
	Set<String> commandList;
	/**
	 The Command info.
	 */
	Map<String,String> commandInfo;
	
	private final String commandListDirectoryPath;
	private final String commandListFileName;
	
	/**
	 Instantiates a new Command info manger.
	 
	 @param commandListDirectoryPath the command list directory path
	 @param commandListFileName the command list file name
	 */
	public CommandInfoManger(String commandListDirectoryPath, String commandListFileName) {
		this.commandListDirectoryPath = commandListDirectoryPath;
		this.commandListFileName = commandListFileName;
	}
	
	/**
	 Read command list.
	 
	 @throws IOException the io exception
	 */
	public void readCommandList() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader((commandListDirectoryPath+ File.separator+commandListFileName)));
		String input;
		commandList= new HashSet<>();
		while((input=br.readLine())!=null){
			commandList.add(input);
		}
	}
	
	private String readFileAsString(String fileName) throws IOException{
		String input="";
		input = new String(Files.readAllBytes(Paths.get(fileName)));
		return input;
	}
	
	private String getOkFileName(String commandName){
		return commandListDirectoryPath + File.separator + commandName + "OK";
	}
	
	private String getErrorFileName(String commandName){
		return commandListDirectoryPath + File.separator + commandName + "ERROR";
	}
	
	/**
	 Read command info.
	 
	 @throws IOException the io exception
	 */
	public void readCommandInfo() throws IOException {
		
		if(commandList!= null) {
			commandInfo = new HashMap<>();
			
			for(var elem : commandList){
				commandInfo.put(elem+"OK",readFileAsString(getOkFileName(elem)));
				commandInfo.put(elem+"ERROR",readFileAsString(getErrorFileName(elem)));
			}
		}
		if((commandList.size()*2) != commandInfo.size()){
			throw new IOException("No enough files with command info");
		}
	}
	
	/**
	 Gets command list.
	 
	 @return the command list
	 */
	public Set<String> getCommandList() {
		return commandList;
	}
	
	/**
	 Gets command info.
	 
	 @return the command info
	 */
	public Map<String, String> getCommandInfo() {
		return commandInfo;
	}
}
