package pl.polsl.lukasz.latusik.lab.model;

import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;
import pl.polsl.lukasz.latusik.lab.model.util.StringUtility;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 The type Product.
 @author Łukasz Latusik
 @version 1.0
 */
public class Product implements Serializable {
	
	private double price;
	private String productName;
	private ProductCategory productCategory;
	private int articleAmount;
	
	/**
	 Instantiates a new Product.
	 
	 @param price the price
	 @param productName the product name
	 @param productCategory the product category
	 @param articleAmount the article amount
	 */
	public Product(double price, String productName, ProductCategory productCategory, int articleAmount) {
		this.price = price;
		this.productName = productName;
		this.productCategory = productCategory;
		this.articleAmount = articleAmount;
	}
	
	/**
	 Gets price.
	 
	 @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 Sets price.
	 
	 @param price the price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 Gets product name.
	 
	 @return the product name
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 Sets product name.
	 
	 @param productName the product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/**
	 Get category name string.
	 
	 @return the string
	 */
	public String getCategoryName(){
		return productCategory.getCategoryName();
	}
	
	/**
	 Gets article amount.
	 
	 @return the article amount
	 */
	public int getArticleAmount() {
		return articleAmount;
	}
	
	/**
	 Gets new product.
	 
	 @param price the price
	 @param name the name
	 @param category the category
	 @param amount the amount
	 @param productCategoryList the product category list
	 @return the new product
	 @throws DataManipulationException the data manipulation exception
	 */
	public static Product getNewProduct(String price, String name, String category, String amount, List<ProductCategory> productCategoryList) throws DataManipulationException {
		double priceDouble;
		int amountInt;
		
		try{priceDouble = Double.parseDouble(price);
			amountInt = Integer.parseInt(amount);
		}
		catch (NumberFormatException e){
			throw new DataManipulationException("Wrong price or amount value.");
		}
		
		if(!StringUtility.isStringGood(name)){
			throw new DataManipulationException("Wrong product name");
		}
		if(productCategoryList==null){
			throw new DataManipulationException("Unable to find category list.");
		}
		
		Stream<ProductCategory> productCategoryStream = productCategoryList.stream();
		List<ProductCategory> productCategoriesFiltered = productCategoryStream.filter(pC -> pC.getCategoryName().toLowerCase().equals(category.toLowerCase())).collect(Collectors.toList());
		
		if(productCategoriesFiltered.size()!=1)
			throw new DataManipulationException("Unable to find corresponding product category");
		else
			return new Product(priceDouble,name,productCategoriesFiltered.get(0),amountInt);
	}
	
	@Override
	public String toString() {
		return "Product{" + "price=" + price + ", productName='" + productName + '\'' + ", productCategory=" + productCategory.getCategoryName() + ", articleAmount=" + articleAmount + '}';
	}
}
