package pl.polsl.lukasz.latusik.lab.model;

import org.junit.Test;
import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;

import static org.junit.Assert.*;

/**
 The type Product category test.
 @author Łukasz Latuisk
 @version 1.0
 */
public class ProductCategoryTest {
	
	/**
	 Gets new category exception.
	 
	 @throws DataManipulationException the data manipulation exception
	 */
	@Test(expected = DataManipulationException.class)
	public void getNewCategoryException() throws DataManipulationException {
		//Given
		String categoryName=" ";
		//When
		ProductCategory productCategory = ProductCategory.getNewCategory(categoryName);
	}
	
	/**
	 Get new cateogry good.
	 */
	@Test
	public void getNewCateogryGood(){
		//Given
		String categoryName="category1";
		//When
		ProductCategory productCategory=null;
		try {
			productCategory = ProductCategory.getNewCategory(categoryName);
		} catch (DataManipulationException e) {
			fail(e.getMessage());
		}
		//Then
		assertEquals("Category name doesn't match",categoryName,productCategory.getCategoryName());
	}
}