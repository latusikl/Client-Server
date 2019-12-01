package pl.polsl.lukasz.latusik.lab.model;

import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;
import pl.polsl.lukasz.latusik.lab.model.util.StringUtility;

import java.io.Serializable;

/**
 The type Product category.
 @author Łukasz Latusik
 @version 1.0
 */
public class ProductCategory  implements Serializable {
	private String categoryName;
	
	/**
	 Instantiates a new Product category.
	 
	 @param categoryName the category name
	 */
	public ProductCategory(String categoryName) {
		this.categoryName = categoryName;
	}
	
	/**
	 Gets category name.
	 
	 @return the category name
	 */
	public String getCategoryName() {
		return categoryName;
	}
	
	/**
	 Gets new category.
	 
	 @param categoryName the category name
	 @return the new category
	 @throws DataManipulationException the data manipulation exception
	 */
	public static ProductCategory getNewCategory (String categoryName) throws DataManipulationException {
		if(!StringUtility.isStringGood(categoryName)){
			throw new DataManipulationException("Wrong category name.");
		}
		return new ProductCategory(categoryName);
	}
	
	@Override
	public String toString() {
		return "ProductCategory{" + "categoryName='" + categoryName + '\'' + '}';
	}
}
