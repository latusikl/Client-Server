package pl.polsl.lukasz.latusik.lab.server.protocol;

import pl.polsl.lukasz.latusik.lab.model.Shop;
import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;
import pl.polsl.lukasz.latusik.lab.model.exception.DataFilteringException;

import java.io.IOException;
import java.util.List;

/**
 The type Command executor.
 @author Łukasz Latusik
 @version 1.0
 */
public class CommandExecutor {
	
	/**
	 The Shop.
	 */
	Shop shop;
	/**
	 The Command info manger.
	 */
	CommandInfoManger commandInfoManger;
	
	/**
	 Instantiates a new Command executor.
	 
	 @param shop the shop
	 @param commandInfoManger the command info manger
	 */
	public CommandExecutor(Shop shop, CommandInfoManger commandInfoManger) {
		this.shop = shop;
		this.commandInfoManger=commandInfoManger;
	}
	
	/**
	 Help string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String help(List splitInput){
		if(commandInfoManger.getCommandList().size() <= 1 ) {
			return getErrorMessage("help");
		}
		else
			return getOKMessage("help");
	}
	
	/**
	 Showpr string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String showpr (List splitInput){
		return shop.showProducts();
	}
	
	/**
	 Showcli string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String showcli(List splitInput){
		return shop.showClients();
	}
	
	/**
	 Showcat string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String showcat(List splitInput){
		return shop.showProductCategories();
	}
	
	/**
	 Gets .
	 
	 @param splitInput the split input
	 @return the
	 */
	public String getpc(List splitInput) {
		
		if (splitInput.size() != 2) {
			return getErrorMessage("getpc")+ "\nWrong amount of arguments.";
		}
		try {
			return getOKMessage("getpc") + "\n" + shop.getProductsFromCategory((String)splitInput.get(1));
		}
		catch (DataFilteringException e) {
			return getErrorMessage("getpc") + e.getMessage();
		}
	}
	
	/**
	 Findcli string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String findcli(List splitInput){
		if (splitInput.size() != 2) {
			return getErrorMessage("findcli")+ "\nWrong amount of arguments.";
		}
		try {
			return getOKMessage("findcli") + "\n" + shop.lookForClient((String)splitInput.get(1));
		}
		catch (DataFilteringException e) {
			return getErrorMessage("findcli") + e.getMessage();
		}
	}
	
	/**
	 Getprcli string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String getprcli(List splitInput){
		if (splitInput.size() != 3) {
			return getErrorMessage("getprcli")+ "\nWrong amount of arguments.";
		}
		try {
			return getOKMessage("getprcli") + "\n" + shop.getProductPrice((String)splitInput.get(1),(String)splitInput.get(2));
		} catch (DataFilteringException e) {
			return getErrorMessage("getprcli") + e.getMessage();
		}
	}
	
	/**
	 Addcli string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String addcli(List splitInput){
		if (splitInput.size() != 5) {
			return getErrorMessage("addcli") + "\nWrong amount of arguments.";
		}
		else{
			
			try{
				shop.addClient((String)splitInput.get(1),(String)splitInput.get(2),(String)splitInput.get(3),(String)splitInput.get(4));
			}
			catch(DataManipulationException e){
				return getErrorMessage("addcli") + "\n" + e.getMessage();
			}
			return  getOKMessage("addcli");
		}
	}
	
	/**
	 Addprod string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String addprod(List splitInput){
		if (splitInput.size() != 5) {
			return getErrorMessage("addprod")+ "\nWrong amount of arguments.";
		}
		try{
			shop.addProduct((String)splitInput.get(1),(String)splitInput.get(2),(String)splitInput.get(3),(String)splitInput.get(4));
		}
		catch (DataManipulationException e){
			return getErrorMessage("addprod") + "\n" + e.getMessage();
		}
			return getOKMessage("addprod");
	}
	
	/**
	 Addpc string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String addpc(List splitInput){
		if (splitInput.size() != 2) {
			return commandInfoManger.getCommandInfo().get("addpcERROR");
		}
		try{
			shop.addCategory((String)splitInput.get(1));
		}
		catch (DataManipulationException e){
			return getErrorMessage("addpc" + e.getMessage());
		}
			return  getOKMessage("addpc");
	}
	
	/**
	 Savefiles string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String savefiles(List splitInput){
		if (splitInput.size() != 4) {
			return getErrorMessage("savefiles");
		}
		try {
			shop.addSaveFiles((String)splitInput.get(1),(String)splitInput.get(2),(String)splitInput.get(3));
			return getOKMessage("savefiles");
		} catch (DataManipulationException e) {
			return getErrorMessage("savefiles") + e.getMessage();
		}
	}
	
	/**
	 Del pro string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String delPro(List splitInput){
		if (splitInput.size() != 2) {
			return getErrorMessage("delPro");
		}
		try {
			shop.deleteProduct((String)splitInput.get(1));
		} catch (DataManipulationException e) {
			return getErrorMessage("delPro") + "\n" + e.getMessage();
		}
		return getOKMessage("delPro");
	}
	
	/**
	 Del pro cat string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String delProCat(List splitInput){
		if (splitInput.size() != 2) {
			return getErrorMessage("delProCat");
		}
		try {
			shop.deleteCategory((String)splitInput.get(1));
		} catch (DataManipulationException e) {
			return getErrorMessage("delProCat") + "\n" + e.getMessage();
		}
		return getOKMessage("delProCat");
	}
	
	/**
	 Del cli string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String delCli(List splitInput){
		if (splitInput.size() != 2) {
			return getErrorMessage("delCli");
		}
		try {
			shop.deleteClient((String)splitInput.get(1));
		} catch (DataManipulationException e) {
			return getErrorMessage("delCli") + "\n" + e.getMessage();
		}
		return getOKMessage("delCli");
	}
	
	/**
	 Save string.
	 
	 @param splitInput the split input
	 @return the string
	 */
	public String save(List splitInput){
		if (splitInput.size() != 1) {
			return getErrorMessage("save");
		}
		try {
			shop.saveShopData();
		} catch (IOException e) {
			return getErrorMessage("save") + "\n" + e.getStackTrace();
		}
		return getOKMessage("save");
	}
	private String checkOperationResult(String commandName, boolean operationResult){
		if(operationResult){
			return commandInfoManger.getCommandInfo().get(commandName + "OK");
		}
		else{
			return commandInfoManger.getCommandInfo().get(commandName + "ERROR");
		}
	}
	
	
	private String getErrorMessage(String commandName){
		return commandInfoManger.getCommandInfo().get(commandName + "ERROR");
	}
	private String getOKMessage(String commandName){
		return commandInfoManger.getCommandInfo().get(commandName + "OK");
	}
	
	
	
}
