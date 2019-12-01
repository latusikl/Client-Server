package pl.polsl.lukasz.latusik.lab.server;

import pl.polsl.lukasz.latusik.lab.server.exceptions.InputException;

/**
 The type Input manager.
 @author Łukasz Latusik
 @version 1.0
 */
public class InputManager {
	/**
	 Parse port int.
	 
	 @param port the port
	 @return the int
	 @throws InputException the input exception
	 */
	public static int parsePort(String port) throws InputException {
		int portNumber;
		
		try {
			portNumber=Integer.parseInt(port);
		}
		catch(NumberFormatException e)
		{
			throw new InputException("Unable to read port number");
		}
		
		if(portNumber < 0 || portNumber > 65535)
			throw new InputException("Unable to read port number");
		
		return portNumber;
	}
	
	/**
	 Check if value of byte of ip address is good.
	 @param byteValue String containing byte of IP address
	 @return true or false depending on result
	 */
	private static boolean isIPByteGood(String byteValue) {
		
		try {
			int byteValueInt=Integer.parseInt(byteValue);
			if(byteValueInt < 0 || byteValueInt > 255)
				return false;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 Check ip string.
	 
	 @param ipAddress the ip address
	 @return the string
	 @throws InputException the input exception
	 */
	public static String checkIP(String ipAddress) throws InputException{
		String [] splitIPAddress = ipAddress.split("[.]");
		if(splitIPAddress.length!= 4)
			throw new InputException("Unable to process IP address");
		else {
			for(String elem : splitIPAddress){
				if(!isIPByteGood(elem)){
					throw new InputException("Unable to process IP address");
				}
			}
		}
		return ipAddress;
	}
}
