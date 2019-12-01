package pl.polsl.lukasz.latusik.lab.model;

import pl.polsl.lukasz.latusik.lab.model.exception.DataManipulationException;
import pl.polsl.lukasz.latusik.lab.model.exception.DataFilteringException;
import pl.polsl.lukasz.latusik.lab.model.exception.InputException;
import pl.polsl.lukasz.latusik.lab.model.util.FileManager;
import pl.polsl.lukasz.latusik.lab.model.util.StringUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 The type Shop.
 @author Łukasz Latusik
 @version 1.0
 */
public class Shop {
	private List<Client> clients;
	private List<Product> products;
	private List<ProductCategory> productCategories;
	
	private boolean shopInitialized;
	
	private String clientFile;
	private String productFile;
	private String productCategoryFile;
	
	/**
	 Instantiates a new Shop.
	 */
	public Shop(){
		shopInitialized = false;
		clients = new ArrayList<>();
		products = new ArrayList<>();
		clientFile = "";
		productFile = "";
		productCategoryFile = "";
		productCategories = new ArrayList<>();
	}
	
	/**
	 Instantiates a new Shop.
	 
	 @param clientFile the client file
	 @param productFile the product file
	 @param productCategoryFile the product category file
	 @throws InputException the input exception
	 */
	public Shop(String clientFile, String productFile, String productCategoryFile) throws InputException {
		this.clientFile = clientFile;
		this.productFile = productFile;
		this.productCategoryFile = productCategoryFile;
		shopInitialized =true;
		readShopData();
	}
	
	/**
	 Reads shop data from files.
	 @throws InputException
	 */
	private void readShopData() throws InputException{
		
		if(!clientFile.isBlank()){
			FileManager<Client> fmClient = new FileManager<>();
			clients=fmClient.readFromFile(clientFile);
		}
		if(!productFile.isBlank()){
			FileManager<Product> fmProduct = new FileManager<>();
			products=fmProduct.readFromFile(productFile);
		}
		if(!productCategoryFile.isBlank()){
			FileManager<ProductCategory> fmProductCategory = new FileManager<>();
			productCategories=fmProductCategory.readFromFile(productCategoryFile);
		}
	}
	
	/**
	 Save shop data.
	 
	 @throws IOException the io exception
	 */
	public void saveShopData() throws IOException {
		if(clientFile.isBlank() || productFile.isBlank() || productCategoryFile.isBlank()){
			throw new IOException("Wrong file name.");
		}
			FileManager<Client> fmClient = new FileManager<>();
			FileManager<Product> fmProduct = new FileManager<>();
			FileManager<ProductCategory> fmProductCategory = new FileManager<>();
		
			fmClient.putToFile(clientFile,clients);
			fmProduct.putToFile(productFile,products);
			fmProductCategory.putToFile(productCategoryFile,productCategories);
	}
	
	/**
	 Show clients string.
	 
	 @return the string
	 */
	public String showClients(){
		return showList(clients);
	}
	
	/**
	 Show products string.
	 
	 @return the string
	 */
	public String showProducts(){
		return showList(products);
	}
	
	/**
	 Show product categories string.
	 
	 @return the string
	 */
	public String showProductCategories(){
		return showList(productCategories);
	}
	
	/**
	 Return list of given type as String.
	 @param list list
	 @param <T> List type
	 @return String representation of list.
	 */
	private <T> String showList(List<T> list){
		String output="";
		for(T elem : list){
			output+=elem.toString();
			output+="\n";
		}
		if(output.equals("")) {
			output+="No items found!";
		}
		return output;
	}
	
	/**
	 Gets products from category.
	 
	 @param productCategoryStr the product category str
	 @return the products from category
	 @throws DataFilteringException the data filtering exception
	 */
	public String getProductsFromCategory(String productCategoryStr) throws DataFilteringException{
		AtomicReference<String> value = new AtomicReference<>();
		
		Stream<ProductCategory> productCategoryStream = productCategories.stream();
		List<ProductCategory> productCategoriesFiltered = productCategoryStream.filter(pC -> pC.getCategoryName().toLowerCase().equals(productCategoryStr.toLowerCase())).collect(Collectors.toList());
		
		ProductCategory productCategory;
		if(productCategoriesFiltered.size()!=1) {
			 throw new DataFilteringException("Category not found!");
		}
		else {
			productCategory = productCategoriesFiltered.get(0);
			
			value.set("");
			Stream<Product> productsStream = products.stream();
			productsStream.filter(p -> p.getCategoryName().equals(productCategory.getCategoryName())).forEach(p -> value.set(value.get() + p.toString()));
			if (value.get().equals("")) {
				throw new DataFilteringException("No items found!");
			}
		}
		return value.get();
	}
	
	/**
	 Look for client string.
	 
	 @param email the email
	 @return the string
	 @throws DataFilteringException the data filtering exception
	 */
	public String lookForClient(String email) throws DataFilteringException {
		AtomicReference<String> value = new AtomicReference<>();
		value.set("");
		Stream<Client> clientsStream = clients.stream();
		clientsStream.filter(c -> c.getEmail().equals(email))
				.forEach(p -> value.set(value.get() + p.toString()));
		if(value.get().equals(""))
		{
			throw new DataFilteringException("No items found!");
		}
		return value.get();
	}
	
	/**
	 Filter list.
	 @param filterCondition Predicate with filtering condition.
	 @param list The list.
	 @param <T> List type.
	 @return
	 */
	private <T> List<T> filterList(Predicate filterCondition, List<T> list){
		Stream<T> listStream = list.stream();
		return (List<T>)listStream.filter(filterCondition).collect(Collectors.toList());
	}
	
	/**
	 Calculate price including discount.
	 @param price The price.
	 @param discountLevel Discount level.
	 @return
	 */
	private double calculatePrice(double price, DiscountLevel discountLevel) {
		return price - price*discountLevel.getDiscountValue();
	}
	
	/**
	 Gets product price.
	 
	 @param email the email
	 @param productName the product name
	 @return the product price
	 @throws DataFilteringException the data filtering exception
	 */
	public String getProductPrice(String email,String productName) throws DataFilteringException{
		String output="";
		
		Predicate<Product> productPredicate = product -> product.getProductName().equals(productName);
		Predicate<Client> clientPredicate = client -> client.getEmail().equals(email);
		
		List<Product> filteredProducts =  filterList(productPredicate,products);
		List<Client> filteredClients = filterList(clientPredicate,clients);
		
		if(filteredClients.size() == 0){
			throw new DataFilteringException("Unable to find client.");
		}
		else if (filteredProducts.size() == 0) {
			throw new DataFilteringException("Unable to find product");
		}
		else
		{
			for(var client : filteredClients){
				output+=client.toString();
				output+="\n";
				for(var product :filteredProducts){
					output+="Price: " +  calculatePrice(product.getPrice(),client.getDiscountLevel());
				}
			}
		}
		return output;
	}
	
	/**
	 Is shop initialized boolean.
	 
	 @return the boolean
	 */
	public boolean isShopInitialized() {
		return shopInitialized;
	}
	
	/**
	 Sets shop initialized.
	 
	 @param shopInitialized the shop initialized
	 */
	public synchronized void setShopInitialized(boolean shopInitialized) {
		this.shopInitialized = shopInitialized;
	}
	
	/**
	 Add client.
	 
	 @param name the name
	 @param surname the surname
	 @param email the email
	 @param discountLevel the discount level
	 @throws DataManipulationException the data manipulation exception
	 */
	public synchronized void addClient(String name, String surname, String email, String discountLevel) throws DataManipulationException {
		clients.add(Client.getNewClient(name, surname, email, discountLevel));
	}
	
	/**
	 Add product.
	 
	 @param price the price
	 @param name the name
	 @param category the category
	 @param amount the amount
	 @throws DataManipulationException the data manipulation exception
	 */
	public synchronized void addProduct(String price, String name, String category, String amount) throws DataManipulationException {
		products.add(Product.getNewProduct(price,name,category,amount,this.productCategories));
	}
	
	/**
	 Add category.
	 
	 @param categoryName the category name
	 @throws DataManipulationException the data manipulation exception
	 */
	public synchronized void addCategory(String categoryName) throws DataManipulationException {
		productCategories.add(ProductCategory.getNewCategory(categoryName));
	}
	
	/**
	 Add save files.
	 
	 @param clientFile the client file
	 @param productFile the product file
	 @param productCategoryFile the product category file
	 @throws DataManipulationException the data manipulation exception
	 */
	public synchronized void addSaveFiles(String clientFile,String productFile, String productCategoryFile) throws DataManipulationException {
		if(!StringUtility.isStringGood(clientFile,productCategoryFile,productFile)){
			throw new DataManipulationException("Wrong file names.");
		}
		
		this.clientFile=clientFile;
		this.productFile=productFile;
		this.productCategoryFile=productCategoryFile;
	}
	
	/**
	 Delete client.
	 
	 @param clientEmail the client email
	 @throws DataManipulationException the data manipulation exception
	 */
	public synchronized void deleteClient(String clientEmail) throws DataManipulationException {
		if(!StringUtility.isStringGood(clientEmail)){
			throw new DataManipulationException("Wrong client email.");
		}
		
		Predicate<Client> clientPredicate = client -> client.getEmail().equals(clientEmail);
		List<Client> clientListFiltered = filterList(clientPredicate,this.clients);
		
		if(clientListFiltered.size()==0){
			throw new DataManipulationException("Client not found");
		}
			for(var elem : clientListFiltered) {
				this.clients.remove(elem);
			}
	}
	
	/**
	 Delete product.
	 
	 @param productName the product name
	 @throws DataManipulationException the data manipulation exception
	 */
	public synchronized void deleteProduct(String productName) throws DataManipulationException{
		if(!StringUtility.isStringGood(productName)){
			throw new DataManipulationException("Wrong product name.");
		}
		
		Predicate<Product> productPredicate = client -> client.getProductName().equals(productName);
		List<Product> productListFiltered = filterList(productPredicate,this.products);
		if(productListFiltered.size()==0){
			throw new DataManipulationException("Product not found.");
		}
		for(var elem : productListFiltered) {
			this.products.remove(elem);
		}
	}
	
	/**
	 Delete category.
	 
	 @param categoryName the category name
	 @throws DataManipulationException the data manipulation exception
	 */
	public synchronized void deleteCategory(String categoryName) throws DataManipulationException{
		
		if(!StringUtility.isStringGood(categoryName)){
			throw new DataManipulationException("Wrong category name.");
		}
		
		Predicate <ProductCategory> productCategoryPredicate = productCategory -> productCategory.getCategoryName().equals(categoryName);
		List<ProductCategory> productCategoryListFiltered = filterList(productCategoryPredicate,this.productCategories);
		
		if(productCategoryListFiltered.size()==0){
			throw new DataManipulationException("Category not found.");
		}
		
		for(var elem : productCategoryListFiltered){
			Predicate <Product> productPredicate = product -> product.getCategoryName().equals(categoryName);
			List<Product> productListFiltered = filterList(productPredicate,this.products);
			for(var elem2 : productListFiltered){
				this.products.remove(elem2);
			}
			this.productCategories.remove(elem);
		}
	}
	
	/**
	 Gets clients.
	 
	 @return the clients
	 */
	public List<Client> getClients() {
		return clients;
	}
	
	/**
	 Gets products.
	 
	 @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}
	
	/**
	 Gets product categories.
	 
	 @return the product categories
	 */
	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}
	
	/**
	 Gets client file.
	 
	 @return the client file
	 */
	public String getClientFile() {
		return clientFile;
	}
	
	/**
	 Gets product file.
	 
	 @return the product file
	 */
	public String getProductFile() {
		return productFile;
	}
	
	/**
	 Gets product category file.
	 
	 @return the product category file
	 */
	public String getProductCategoryFile() {
		return productCategoryFile;
	}
	
	/**
	 Sets clients.
	 
	 @param clients the clients
	 */
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	/**
	 Sets products.
	 
	 @param products the products
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	/**
	 Sets product categories.
	 
	 @param productCategories the product categories
	 */
	public void setProductCategories(List<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}
	
	/**
	 Sets client file.
	 
	 @param clientFile the client file
	 */
	public void setClientFile(String clientFile) {
		this.clientFile = clientFile;
	}
	
	/**
	 Sets product file.
	 
	 @param productFile the product file
	 */
	public void setProductFile(String productFile) {
		this.productFile = productFile;
	}
	
	/**
	 Sets product category file.
	 
	 @param productCategoryFile the product category file
	 */
	public void setProductCategoryFile(String productCategoryFile) {
		this.productCategoryFile = productCategoryFile;
	}
}
