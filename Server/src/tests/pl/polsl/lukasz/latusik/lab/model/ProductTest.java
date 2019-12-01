package pl.polsl.lukasz.latusik.lab.model;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 The type Product test.
 @author Łukasz Latuisk
 @version 1.0
 */
public class ProductTest {
	
	/**
	 Setup.
	 */
	@BeforeClass
	public static void setup(){
		priceGood="100.10";
		productNameGood="Product123";
		productCategoryGood="Category1";
		articleAmountGood ="10";
		productCategoriesList.add(new ProductCategory("Category1"));
		productCategoriesList.add(new ProductCategory("Category22"));
	}
	
	/**
	 The Price good.
	 */
	static String priceGood;
	/**
	 The Product name good.
	 */
	static String productNameGood;
	/**
	 The Product category good.
	 */
	static String productCategoryGood;
	/**
	 The Article amount good.
	 */
	static String articleAmountGood;
	/**
	 The Product categories list.
	 */
	static List<ProductCategory> productCategoriesList=new ArrayList<>();
	
	/**
	 Gets new product wrong price.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewProductWrongPrice() throws DataManipulationException {
		//Given
		String wrongPrice="price";
		//When
		Product newProduct = Product.getNewProduct(wrongPrice,productNameGood,productCategoryGood, articleAmountGood,productCategoriesList);
	}
	
	/**
	 Gets new product wrong article amount.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewProductWrongArticleAmount() throws DataManipulationException {
		//Given
		String wrongAmount="amount";
		//When
		Product newProduct = Product.getNewProduct(priceGood,productNameGood,productCategoryGood,wrongAmount,productCategoriesList);
	}
	
	/**
	 Gets new product wrong name.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewProductWrongName() throws DataManipulationException {
		//Given
		String wrongName=" ";
		//When
		Product newProduct = Product.getNewProduct(priceGood,wrongName,productCategoryGood,articleAmountGood,productCategoriesList);
	}
	
	/**
	 Gets new product wrong category.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewProductWrongCategory() throws DataManipulationException {
		//Given
		String wrongCategory="Category11";
		//When
		Product newProduct = Product.getNewProduct(priceGood,productNameGood,wrongCategory,articleAmountGood,productCategoriesList);
	}
	
	/**
	 Gets new product empty category list.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewProductEmptyCategoryList() throws DataManipulationException {
		//Given
		List<ProductCategory> wrongCategoryList=null;
		//When
		Product newProduct = Product.getNewProduct(priceGood,productNameGood,productCategoryGood,articleAmountGood,wrongCategoryList);
	}
	
	/**
	 Get new product good.
	 */
	@Test
	public void getNewProductGood(){
		//Given
		Product newProduct=null;
		//When
		try {
			newProduct=Product.getNewProduct(priceGood,productNameGood,productCategoryGood,articleAmountGood,productCategoriesList);
		} catch (DataManipulationException e) {
			fail(e.getMessage());
		}
		//Then
		assertEquals("Product name price doesn't match",Double.parseDouble(priceGood),newProduct.getPrice(),0.001);
		assertEquals("Product name doesn't match",productNameGood,newProduct.getProductName());
		assertEquals("Product category doesn't match",productCategoryGood,newProduct.getCategoryName());
		assertEquals("Product article amount doesn't match",Integer.parseInt(articleAmountGood),newProduct.getArticleAmount());
	}
}