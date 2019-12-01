package pl.polsl.lukasz.latusik.lab.model.util;

/**
 The type String utility.
 @author Łukasz Latusik
 @version 1.0
 */
public class StringUtility {
	
	/**
	 Is string good boolean.
	 
	 @param vararg the vararg
	 @return the boolean
	 */
	public static boolean isStringGood(String ... vararg){
		for(var elem : vararg){
			if(elem.isBlank() || elem.contains("&")) {
				return false;
			}
		}
		return true;
	}
}
