package pl.polsl.lukasz.latusik.lab.model;

import org.junit.Before;
import org.junit.Test;
import pl.polsl.lukasz.latusik.lab.model.exception.DataFilteringException;
import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 The type Shop test.
 @author Łukasz Latuisk
 @version 1.0
 */
public class ShopTest {
	
	/**
	 Setup.
	 */
	@Before
	public  void setup(){
		shop=new Shop();
		clientList=new ArrayList<>();
		productList = new ArrayList<>();
		productCategoryList = new ArrayList<>();
		
		clientList.add(new Client("John","Smith","email@email2.com",DiscountLevel.SILVER));
		
		productCategoryList.add(new ProductCategory("category1"));
		productCategoryList.add(new ProductCategory("category2"));
		
		productList.add(new Product(10.0,"Product",productCategoryList.get(0),10));
		
		shop.setShopInitialized(true);
		shop.setClients(clientList);
		shop.setProducts(productList);
		shop.setProductCategories(productCategoryList);
	}
	
	/**
	 The Shop.
	 */
	public  Shop shop=new Shop();
	/**
	 The Client list.
	 */
	public  List<Client> clientList=new ArrayList<>();
	/**
	 The Product list.
	 */
	public  List<Product> productList = new ArrayList<>();
	/**
	 The Product category list.
	 */
	public  List<ProductCategory> productCategoryList = new ArrayList<>();
	
	/**
	 Save shop data error.
	 
	 @throws IOException the io exception
	 */
	@Test(expected = IOException.class)
	public void saveShopDataError() throws IOException {
		shop.saveShopData();
	}
	
	/**
	 Save shop data good.
	 */
	@Test
	public void saveShopDataGood() {
		//Given
		File tmpFile1=null;
		File tmpFile2=null;
		File tmpFile3=null;
		try {
			tmpFile1 = File.createTempFile("tmpFile1","");
			tmpFile2 = File.createTempFile("tmpFile2","");
			tmpFile3 = File.createTempFile("tmpFile3","");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		Shop shop=new Shop();
		shop.setClientFile(tmpFile1.getPath());
		shop.setProductFile(tmpFile2.getPath());
		shop.setProductCategoryFile(tmpFile3.getPath());
		shop.setShopInitialized(true);
		//When
		try {
			shop.saveShopData();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		//Then
		assertTrue("Data not saved properly",tmpFile1.length()!=0);
		assertTrue("Data not saved properly",tmpFile2.length()!=0);
		assertTrue("Data not saved properly",tmpFile3.length()!=0);
		
		tmpFile1.deleteOnExit();
		tmpFile2.deleteOnExit();
		tmpFile3.deleteOnExit();
	}
	
	/**
	 Show clients.
	 */
	@Test
	public void showClients() {
		//When
		String showClientsResult = shop.showClients();
		//Then
		assertEquals("Client not shown properly",clientList.get(0).toString()+"\n",showClientsResult);
	}
	
	/**
	 Show products.
	 */
	@Test
	public void showProducts() {
		//When
		String showProductsResult = shop.showProducts();
		//Then
		assertEquals("Products not shown properly",productList.get(0).toString() + "\n",showProductsResult);
	}
	
	/**
	 Show product categories.
	 */
	@Test
	public void showProductCategories() {
		//When
		String showProductsCategoriesResult = shop.showProductCategories();
		//Then
		assertEquals("Products categories not shown properly",productCategoryList.get(0).toString() + "\n" + productCategoryList.get(1).toString() + "\n" ,showProductsCategoriesResult);
	}
	
	/**
	 Gets products from category exception category.
	 
	 @throws DataFilteringException the data filtering exception
	 */
	@Test(expected = DataFilteringException.class)
	public void getProductsFromCategoryExceptionCategory()  throws DataFilteringException{
		//Given
		String wrongCategory="cat";
		//When
		shop.getProductsFromCategory(wrongCategory);
	}
	
	/**
	 Gets products from category exception no items.
	 
	 @throws DataFilteringException the data filtering exception
	 */
	@Test(expected = DataFilteringException.class)
	public void getProductsFromCategoryExceptionNoItems()  throws DataFilteringException{
		//Given
		String wrongCategory="category2";
		//When
		shop.getProductsFromCategory(wrongCategory);
	}
	
	/**
	 Gets products from category.
	 */
	@Test
	public void getProductsFromCategory()  {
		//Given
		String categoryName=productList.get(0).getCategoryName();
		//When
		String productsFromCategory = null;
		try {
			productsFromCategory = shop.getProductsFromCategory(categoryName);
		} catch (DataFilteringException e) {
			fail(e.getMessage());
		}
		//Then
		assertEquals("Products from category not shown properly",  productList.get(0).toString(),productsFromCategory);
	}
	
	/**
	 Look for client error.
	 
	 @throws DataFilteringException the data filtering exception
	 */
	@Test(expected = DataFilteringException.class)
	public void lookForClientError() throws DataFilteringException{
		//Given
		String wrongEmail="email";
		//When
		shop.lookForClient(wrongEmail);
	}
	
	/**
	 Look for client good.
	 */
	@Test
	public void lookForClientGood() {
		//Given
		String goodEmail=clientList.get(0).getEmail();
		//When
		String clientFromCategory= null;
		try {
			clientFromCategory = shop.lookForClient(goodEmail);
		} catch (DataFilteringException e) {
			fail(e.getMessage());
		}
		//Then
		assertEquals("Client searching error",  clientList.get(0).toString(),clientFromCategory);
	}
	
	/**
	 Gets product price.
	 */
	@Test
	public void getProductPrice() {
		//Given
		double productPrice= productList.get(0).getPrice();
		double discountValue=clientList.get(0).getDiscountLevel().getDiscountValue()*productPrice;
		double finalPrice = productPrice - discountValue;
		String clientEmail = clientList.get(0).getEmail();
		String productName = productList.get(0).getProductName();
		//When
		String price = null;
		try {
			price = shop.getProductPrice(clientEmail,productName);
		} catch (DataFilteringException e) {
			fail(e.getMessage());
		}
		//Then
		int startIndex=price.indexOf(":");
		price=price.substring(startIndex+1,price.length());
		Double priceValueReturned =  Double.parseDouble(price);
		assertEquals("Wrong price", finalPrice,priceValueReturned,0.001);
	}
	
	/**
	 Gets product price exception client.
	 
	 @throws DataFilteringException the data filtering exception
	 */
	@Test(expected = DataFilteringException.class)
	public void getProductPriceExceptionClient() throws DataFilteringException {
		//Given
		double productPrice= productList.get(0).getPrice();
		double discountValue=clientList.get(0).getDiscountLevel().getDiscountValue()*productPrice;
		double finalPrice = productPrice - discountValue;
		String clientEmail = "email";
		String productName = productList.get(0).getProductName();
		//When
		String price = null;
			price = shop.getProductPrice(clientEmail,productName);
	}
	
	/**
	 Gets product price exception product.
	 
	 @throws DataFilteringException the data filtering exception
	 */
	@Test(expected = DataFilteringException.class)
	public void getProductPriceExceptionProduct() throws DataFilteringException {
		//Given
		double productPrice= productList.get(0).getPrice();
		double discountValue=clientList.get(0).getDiscountLevel().getDiscountValue()*productPrice;
		double finalPrice = productPrice - discountValue;
		String clientEmail = clientList.get(0).getEmail();
		String productName = "UnknownProduct";
		//When
		String price = null;
		price = shop.getProductPrice(clientEmail,productName);
	}
	
	//addClient,addCategory,addProduct doesn't required testing they just invoke creation method and pass exception further.
	//Creation method testes in adequate classes.
	
	/**
	 Add save files exception 1.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void addSaveFilesException1() throws DataManipulationException {
		shop.addSaveFiles("","file1","file2");
	}
	
	/**
	 Add save files exception 2.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void addSaveFilesException2() throws DataManipulationException {
		shop.addSaveFiles("file1","","file2");
	}
	
	/**
	 Add save files exception 3.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void addSaveFilesException3() throws DataManipulationException {
		shop.addSaveFiles("file1","file2"," ");
	}
	
	/**
	 Add save file good.
	 */
	@Test
	public void addSaveFileGood()
	{
		//Given
		String clientFile="filename1";
		String productFile="filename2";
		String categoryFile="filename3";
		//When
		try {
			shop.addSaveFiles(clientFile,productFile,categoryFile);
		} catch (DataManipulationException e) {
			fail(e.getMessage());
		}
		//Then
		assertEquals("Wrong file name",shop.getProductFile(),productFile);
		assertEquals("Wrong file name",shop.getClientFile(),clientFile);
		assertEquals("Wrong file name",shop.getProductCategoryFile(),categoryFile);
	}
	
	/**
	 Delete client exception email.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void deleteClientExceptionEmail() throws DataManipulationException{
		//Given
		String wrongEmail="";
		//When
		shop.deleteClient(wrongEmail);
	}
	
	/**
	 Delete client exception email 2.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void deleteClientExceptionEmail2() throws DataManipulationException{
		//Given
		String wrongEmail="email";
		//When
		shop.deleteClient(wrongEmail);
	}
	
	/**
	 Delete client good.
	 */
	@Test
	public void deleteClientGood(){
		//Given
		String goodEmail=clientList.get(0).getEmail();
		//When
		try {
			shop.deleteClient(goodEmail);
		} catch (DataManipulationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 Delete product exception 1.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void deleteProductException1() throws DataManipulationException{
		//Given
		String wrongName="";
		//When
		shop.deleteProduct(wrongName);
	}
	
	/**
	 Delete product exception 2.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void deleteProductException2() throws DataManipulationException{
		//Given
		String wrongName="unknown product";
		//When
		shop.deleteProduct(wrongName);
	}
	
	/**
	 Delete product.
	 */
	@Test
	public void deleteProduct(){
		//Given
		String goodName=productList.get(0).getProductName();
		//When
		try {
			shop.deleteProduct(goodName);
		} catch (DataManipulationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 Delete category exception 1.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void deleteCategoryException1() throws DataManipulationException{
		//Given
		String wrongName="";
		//When
		shop.deleteCategory(wrongName);
	}
	
	/**
	 Delete category exception 2.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void deleteCategoryException2() throws DataManipulationException{
		//Given
		String wrongName="unknown category";
		//When
		shop.deleteCategory(wrongName);
	}
	
	/**
	 Delete category.
	 */
	@Test
	public void deleteCategory() {
		//Given
		String goodName=productCategoryList.get(0).getCategoryName();
		//When
		try {
			shop.deleteCategory(goodName);
		} catch (DataManipulationException e) {
			fail(e.getMessage());
		}
	}
}