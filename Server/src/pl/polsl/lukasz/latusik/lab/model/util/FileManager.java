package pl.polsl.lukasz.latusik.lab.model.util;

import pl.polsl.lukasz.latusik.lab.model.exception.InputException;

import java.io.*;
import java.util.List;

/**
 The type File manager.
 @author Łukasz Latusik
 @version 1.0
 @param <T> the type parameter */
public class FileManager<T>  {
	
	/**
	 Put to file.
	 
	 @param fileName the file name
	 @param objectList the object list
	 @throws IOException the io exception
	 */
	public void putToFile(String fileName, List<T> objectList) throws IOException{
		ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(fileName));
		objectStream.writeObject(objectList);
	
	}
	
	/**
	 Read from file list.
	 
	 @param fileName the file name
	 @return the list
	 @throws InputException the input exception
	 */
	public List<T> readFromFile(String fileName) throws InputException {
		List<T> objectList;
		try {
			ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(fileName));
			 objectList= (List<T>) objectStream.readObject();
		}
		catch(IOException | ClassNotFoundException e){
			throw new InputException("Unable to read data!" + e.getStackTrace());
		}
		return objectList;
	}
}
