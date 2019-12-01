package pl.polsl.lukasz.latusik.lab.model;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;

import static org.junit.Assert.*;

/**
 The type Client test.
 @author Łukasz Latuisk
 @version 1.0
 */
public class ClientTest {
	
	/**
	 Set up.
	 */
	@BeforeClass
			public static void setUp(){
		nameGood="John";
		surnameGood="Smith";
		emailGood="email@email.com";
		discountGood="silver";
	}
	
	/**
	 The constant nameGood.
	 */
	//Given
	static String nameGood;
	/**
	 The Surname good.
	 */
	static String surnameGood;
	/**
	 The Email good.
	 */
	static String emailGood;
	/**
	 The Discount good.
	 */
	static String discountGood;
	
	/**
	 Gets new client wrong discount.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewClientWrongDiscount() throws DataManipulationException {
		//When
		Client newClient = Client.getNewClient(nameGood,surnameGood,emailGood,"discount");
	}
	
	/**
	 Gets new client wrong string.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewClientWrongString() throws DataManipulationException {
		//When
		Client newClient = Client.getNewClient("",surnameGood,emailGood,discountGood);
	}
	
	/**
	 Gets new client wrong string 2.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewClientWrongString2() throws DataManipulationException {
		//When
		Client newClient = Client.getNewClient(nameGood,surnameGood,"email",discountGood);
	}
	
	/**
	 Gets new client wrong strin 3.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewClientWrongStrin3() throws DataManipulationException {
		//When
		Client newClient = Client.getNewClient(nameGood," ",emailGood,discountGood);
	}
	
	/**
	 Get new client good.
	 */
	@Test
	public void getNewClientGood(){
		//Given
		Client newClient=null;
		//When
		try {
			newClient = Client.getNewClient(nameGood, surnameGood, emailGood, discountGood);
		}
		catch (DataManipulationException e){
			fail(e.getMessage());
		}
		//Then
		assertEquals("Wrong names", nameGood,newClient.getName());
		assertEquals("Wrong surnames",surnameGood,newClient.getSurname());
		assertEquals("Wrong emails",emailGood,newClient.getEmail());
	}
}
