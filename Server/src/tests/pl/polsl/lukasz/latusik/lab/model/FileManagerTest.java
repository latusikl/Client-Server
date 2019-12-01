package pl.polsl.lukasz.latusik.lab.model;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.polsl.lukasz.latusik.lab.model.exception.InputException;
import pl.polsl.lukasz.latusik.lab.model.util.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 The type File manager test.
 @author Łukasz Latuisk
 @version 1.0
 */
public class FileManagerTest {
	
	/**
	 Setup.
	 */
	@BeforeClass
	public static void setup(){
		clientList.add(new Client("John","Smith","email@email2.com",DiscountLevel.NORMAL));
		
		productCategoryList.add(new ProductCategory("category1"));
		
		productList.add(new Product(10.0,"Product",productCategoryList.get(0),10));
	}
	
	/**
	 The constant clientList.
	 */
	public static List<Client> clientList=new ArrayList<>();
	/**
	 The constant productList.
	 */
	public static List<Product> productList = new ArrayList<>();
	/**
	 The constant productCategoryList.
	 */
	public static List<ProductCategory> productCategoryList = new ArrayList<>();
	
	/**
	 Put to file clients.
	 */
	@Test
	public void putToFileClients() {
		//Given
		File temporaryFile=null;
		try {
			temporaryFile = File.createTempFile("testFile", ".txt");
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		//When
		FileManager<Client> fileManagerClients=new FileManager<>();
		
		try {
			fileManagerClients.putToFile(temporaryFile.getPath(),clientList);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		//Then
		try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(temporaryFile.getPath()));)
			{
			List<Client>readClientList= (List<Client>) objectStream.readObject();
			
			Client readClient= readClientList.get(0);
			assertEquals("Client name not read correctly",clientList.get(0).getName(),readClient.getName());
			assertEquals("Client surname not read correctly",clientList.get(0).getSurname(),readClient.getSurname());
			assertEquals("Client email not read correctly",clientList.get(0).getEmail(),readClient.getEmail());
			assertEquals("Client discount not read correctly",clientList.get(0).getDiscountLevel(),readClient.getDiscountLevel());
			} catch (IOException | ClassNotFoundException e) {
			fail(e.getMessage());
		}
		temporaryFile.deleteOnExit();
	}
	
	/**
	 Put to file category.
	 */
	@Test
	public void putToFileCategory() {
		//Given
		File temporaryFile=null;
		try {
			temporaryFile = File.createTempFile("testFile", ".txt");
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		//When
		FileManager<ProductCategory> fileManagerProductCategory=new FileManager<>();
		
		try {
			fileManagerProductCategory.putToFile(temporaryFile.getPath(),productCategoryList);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		//Then
		try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(temporaryFile.getPath()));)
		{
			List<ProductCategory>readProductCategoryList= (List<ProductCategory>) objectStream.readObject();
			
			ProductCategory readProductCategory= readProductCategoryList.get(0);
			assertEquals("Category name not read correctly",productList.get(0).getCategoryName(),readProductCategory.getCategoryName());
		} catch (IOException | ClassNotFoundException e) {
			fail(e.getMessage());
		}
		temporaryFile.deleteOnExit();
	}
	
	/**
	 Put to file product.
	 */
	@Test
	public void putToFileProduct() {
		//Given
		File temporaryFile=null;
		try {
			temporaryFile = File.createTempFile("testFile", ".txt");
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		//When
		FileManager<Product> fileManagerProducts=new FileManager<>();
		
		try {
			fileManagerProducts.putToFile(temporaryFile.getPath(),productList);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		//Then
		try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(temporaryFile.getPath()));)
		{
			List<Product>readProductList= (List<Product>) objectStream.readObject();
			
			Product readProduct= readProductList.get(0);
			assertEquals("Product price not read correctly",productList.get(0).getPrice(),readProduct.getPrice(),0.001);
			assertEquals("Product name not read correctly",productList.get(0).getProductName(),readProduct.getProductName());
			assertEquals("Product category not read correctly",productList.get(0).getCategoryName(),readProduct.getCategoryName());
			assertEquals("Product amount not read correctly",productList.get(0).getArticleAmount(),readProduct.getArticleAmount());
		} catch (IOException | ClassNotFoundException e) {
			fail(e.getMessage());
		}
		temporaryFile.deleteOnExit();
	}
	
	/**
	 Read from file clients.
	 */
	@Test
	public void readFromFileClients() {
		//Given
		File temporaryFile=null;
		try {
			temporaryFile = File.createTempFile("testFile", ".txt");
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		try(ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(temporaryFile.getPath()));){
			objectStream.writeObject(clientList);
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		//When
		FileManager<Client> clientFileManager = new FileManager<>();
		List<Client> clientListRead=null;
		try {
			clientListRead = clientFileManager.readFromFile(temporaryFile.getPath());
		} catch (InputException e) {
			fail(e.getMessage());
		}
		//Then
		Client readClient= clientListRead.get(0);
		assertEquals("Client name not read correctly",clientList.get(0).getName(),readClient.getName());
		assertEquals("Client surname not read correctly",clientList.get(0).getSurname(),readClient.getSurname());
		assertEquals("Client email not read correctly",clientList.get(0).getEmail(),readClient.getEmail());
		assertEquals("Client discount not read correctly",clientList.get(0).getDiscountLevel(),readClient.getDiscountLevel());
	}
	
	/**
	 Read from file product category.
	 */
	@Test
	public void readFromFileProductCategory() {
		//Given
		File temporaryFile=null;
		try {
			temporaryFile = File.createTempFile("testFile", ".txt");
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		try(ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(temporaryFile.getPath()));){
			objectStream.writeObject(productCategoryList);
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		//When
		FileManager <ProductCategory> productCategoryFileManager = new FileManager<>();
		List<ProductCategory> productCategoryRead=null;
		try {
			productCategoryRead = productCategoryFileManager.readFromFile(temporaryFile.getPath());
		} catch (InputException e) {
			fail(e.getMessage());
		}
		//Then
		ProductCategory readProductCategory= productCategoryRead.get(0);
		assertEquals("Category name not read correctly",productList.get(0).getCategoryName(),readProductCategory.getCategoryName());
	}
	
	/**
	 Read from file product product.
	 */
	@Test
	public void readFromFileProductProduct() {
		//Given
		File temporaryFile=null;
		try {
			temporaryFile = File.createTempFile("testFile", ".txt");
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		try(ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(temporaryFile.getPath()));){
			objectStream.writeObject(productList);
		}
		catch (IOException e){
			fail(e.getMessage());
		}
		//When
		FileManager <Product> productFileManager = new FileManager<>();
		List<Product> productRead=null;
		try {
			productRead = productFileManager.readFromFile(temporaryFile.getPath());
		} catch (InputException e) {
			fail(e.getMessage());
		}
		//Then
		Product readProduct= productRead.get(0);
		assertEquals("Product price not read correctly",productList.get(0).getPrice(),readProduct.getPrice(),0.001);
		assertEquals("Product name not read correctly",productList.get(0).getProductName(),readProduct.getProductName());
		assertEquals("Product category not read correctly",productList.get(0).getCategoryName(),readProduct.getCategoryName());
		assertEquals("Product amount not read correctly",productList.get(0).getArticleAmount(),readProduct.getArticleAmount());
	}
}