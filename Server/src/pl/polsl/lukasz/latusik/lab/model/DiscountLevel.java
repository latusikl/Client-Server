package pl.polsl.lukasz.latusik.lab.model;

/**
 The enum Discount level.
 @author Łukasz Latusik
 @version 1.0
 */
public enum DiscountLevel {
	/**
	 Normal discount level.
	 */
	NORMAL(0),
	/**
	 Bronze discount level.
	 */
	BRONZE(0.05),
	/**
	 Silver discount level.
	 */
	SILVER(0.10),
	/**
	 Gold discount level.
	 */
	GOLD(0.15),
	/**
	 Platinum discount level.
	 */
	PLATINUM(0.3);
	
	private double discountValue;

	DiscountLevel(double discountValue){
		this.discountValue=discountValue;
	}
	
	/**
	 Gets discount value.
	 
	 @return the discount value
	 */
	public double getDiscountValue() {
		return discountValue;
	}
}
