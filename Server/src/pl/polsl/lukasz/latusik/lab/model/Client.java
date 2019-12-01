package pl.polsl.lukasz.latusik.lab.model;

import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;
import pl.polsl.lukasz.latusik.lab.model.util.StringUtility;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 The type Client.
 @author Łukasz Latusik
 @version 1.0
 */
public class Client implements Serializable {
	
	private String name;
	private String surname;
	private String email;
	private DiscountLevel discountLevel;
	
	/**
	 Instantiates a new Client.
	 
	 @param name the name
	 @param surname the surname
	 @param email the email
	 @param discountLevel the discount level
	 */
	public Client(String name, String surname, String email, DiscountLevel discountLevel) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.discountLevel = discountLevel;
	}
	
	/**
	 Gets new client.
	 
	 @param name the name
	 @param surname the surname
	 @param email the email
	 @param discountLevel the discount level
	 @return the new client
	 @throws DataManipulationException the data manipulation exception
	 */
	public static Client getNewClient(String name, String surname, String email, String discountLevel) throws DataManipulationException {
		DiscountLevel discount = parseDiscount(discountLevel);
		
		if(discount == null){
			throw new DataManipulationException("Unable to find discount type.");
		}
		if(!checkEmailFormat(email)){
			throw new DataManipulationException("Wrong email format.");
		}
		
		if(!StringUtility.isStringGood(surname,email,name)){
			throw new DataManipulationException("One of required values is incorrect.");
		}
			return new Client(name,surname,email,discount);
	}
	
	/**
	 Check if discount level exists.
	 @param discountLevel
	 @return DiscountLevel object
	 */
	private static DiscountLevel parseDiscount(String discountLevel){
		for(var elem : DiscountLevel.values()){
			if(elem.toString().equals(discountLevel.toUpperCase()))
				return elem;
		}
		return null;
	}
	
	/**
	 Checks email format.
	 @param email email
	 @return true/false depending on result.
	 */
	private static boolean checkEmailFormat(String email)  {
		String regexExpression = "\\p{Alnum}+@\\p{Alnum}+[.]\\p{Alnum}+";
		Pattern pattern = Pattern.compile(regexExpression);
		if (email == null || !pattern.matcher(email).matches())
			return false;
		else
			return true;
	}
	
	/**
	 Gets name.
	 
	 @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 Sets name.
	 
	 @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 Gets surname.
	 
	 @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 Sets surname.
	 
	 @param surname the surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 Gets email.
	 
	 @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 Sets email.
	 
	 @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 Gets discount level.
	 
	 @return the discount level
	 */
	public DiscountLevel getDiscountLevel() {
		return discountLevel;
	}
	
	/**
	 Sets discount level.
	 
	 @param discountLevel the discount level
	 */
	public void setDiscountLevel(DiscountLevel discountLevel) {
		this.discountLevel = discountLevel;
	}
	
	@Override
	public String toString() {
		return "Client{" + "name='" + name + '\'' + ", surname='" + surname + '\'' + ", email='" + email + '\'' + ", discountLevel=" + discountLevel + '}';
	}
}
