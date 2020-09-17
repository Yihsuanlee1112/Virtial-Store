//import store.console;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import store.business.Customer;
import store.business.EmptyStockException;
import store.business.Product;
import store.business.Store;
import store.business.Transaction;

public class VirtualStoreConsole {
	
	private Scanner scan = new Scanner(System.in);
	
	public VirtualStoreConsole() {//Console for running the Virtual Store in the Terminal


		/** Console for running the Virtual Store on the Terminal
		*/
				
		int choice;
		String yORn;
		boolean carryOn = true;
		List<Product> tmpList = new ArrayList<Product>();	
		
		System.out.println("---------- Welcome to Leecota ----------");
		
		Store store = new Store();//Open the Store
		do {
			tmpList.clear();//Clear the content of the list
			do {//Choose the category to see or to quit the store
				System.out.println("Which category do you want to see?");
				System.out.println("1) DVD");
				System.out.println("2) Book");
				System.out.println("3) Game");
				System.out.println("4) All");
				System.out.println("5) Quit Leecota");
				choice = scan.nextInt();
			}while ( choice<1 || choice>5);//If the choice is incorrect
		
			switch (choice) {//Depending of chocie's value
			case 1://DVD
				System.out.println("You choose the category <DVD>");
				tmpList = store.GetDVDFromProduct();//Get the list of DVDs from the products
			
				if(tmpList.size() != 0) {//If the list is not empty
					System.out.println("size is "+tmpList.size());
					for(int i=0; i<tmpList.size();i++) {//Display all the DVDs
						System.out.println(tmpList.get(i).toString());
					}	
				}else {//Otherwise
					System.out.println("None products of the category <DVD>");
					break;
				}
			
				System.out.println("Is there a product you want to buy?[y/n]");
				do {
					yORn= scan.next();
				
					switch(yORn.charAt(0)) {
						case 'y':
							BuyProduct(store, tmpList);//Buy product if the client wants to buy one of the DVDs
							
							do {
								System.out.println("Do you want to go to the main menu?[y/n]");
								yORn = scan.next();
							
								switch(yORn.charAt(0)) {
									case 'y':
										carryOn = true;
										System.out.println("You decided to go back to the main menu!");
									break;
									
									case 'n':
										System.out.println("You decided to leave us. See you soon :)");
										carryOn = false;
									break;
									
									default:
										System.out.println("You're choice is incorrect. Retry please.");
									break;
								}
							}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
							
						break;
					
						case 'n':
							System.out.println("You are going back to the main menu");
						break;
					
						default:
							System.out.println("Your choice is incorrect. Please retry.");
						break;
					}
				
				}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
			
				break;
		
			case 2://Book
				System.out.println("You choose the category <Book>");
				tmpList = store.GetBookFromProduct();//Get the list of books from the products
			
				if(tmpList.size() != 0) {//If the list is not empty
					for(int i=0; i<tmpList.size();i++) {//Display all books
						System.out.println(tmpList.get(i).toString());
					}	
				}else {//Otherwise
					System.out.println("None products of the category <Book>");
					break;
				}
			
				System.out.println("Is there a product you want to buy?[y/n]");
				do {
					yORn= scan.next();
				
					switch(yORn.charAt(0)) {
						case 'y':
							BuyProduct(store, tmpList);//Buy the product if the clients wants to buy a book
							
							do {
								System.out.println("Do you want to go to the main menu?[y/n]");
								yORn = scan.next();
							
								switch(yORn.charAt(0)) {
									case 'y':
										carryOn = true;
										System.out.println("You decided to go back to the main menu!");
									break;
									
									case 'n':
										System.out.println("You decided to leave us. See you soon :)");
										carryOn = false;
									break;
									
									default:
										System.out.println("You're choice is incorrect. Retry please.");
									break;
								}
							}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
							
						break;
					
						case 'n':
							System.out.println("You are going back to the main menu");
						break;
					
						default:
							System.out.println("Your choice is incorrect. Please retry.");
						break;
					}
				
				}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
				
				break;
		
			case 3://Game
				System.out.println("You choose the category <Game>");
				
				tmpList = store.GetGameFromProduct();//Get the list of games from the products
			
				if(tmpList.size() != 0) {//If the list is not empty
					for(int i=0; i<tmpList.size();i++) {//Display all games
						System.out.println(tmpList.get(i).toString());
					}	
				}else {//Otherwise
					System.out.println("None products of the category <Game>");
					break;
				}
			
				System.out.println("Is there a product you want to buy?[y/n]");
				do {
					yORn= scan.next();
				
					switch(yORn.charAt(0)) {
						case 'y':
							BuyProduct(store, tmpList);//Buy the Game because the client wants
							
							do {
								System.out.println("Do you want to go to the main menu?[y/n]");
								yORn = scan.next();
							
								switch(yORn.charAt(0)) {
									case 'y':
										carryOn = true;
										System.out.println("You decided to go back to the main menu!");
									break;
									
									case 'n':
										System.out.println("You decided to leave us. See you soon :)");
										carryOn = false;
									break;
									
									default:
										System.out.println("You're choice is incorrect. Retry please.");
									break;
								}
							}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
							
						break;
					
						case 'n':
							System.out.println("You are going back to the main menu");
						break;
					
						default:
							System.out.println("Your choice is incorrect. Please retry.");
						break;
					}
				
				}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
			
				break;
		
			case 4://All
				
				System.out.println("You choose the category <All>");
				tmpList = store.GetProductList();//Get the list of products
			
				if(tmpList.size() != 0) {//If the list of products is not empty
					for(int i=0; i<tmpList.size();i++) {//Display the list of products
						System.out.println(tmpList.get(i).toString());
					}	
				}else {//Otherwise
					System.out.println("None products of the category <All>");
					break;
				}
			
				System.out.println("Is there a product you want to buy?[y/n]");
				do {
					yORn= scan.next();
				
					switch(yORn.charAt(0)) {
						case 'y':
							BuyProduct(store, tmpList);//Client wants to buy a product
							
							do {
								System.out.println("Do you want to go to the main menu?[y/n]");
								yORn = scan.next();
							
								switch(yORn.charAt(0)) {
									case 'y':
										carryOn = true;
										System.out.println("You decided to go back to the main menu!");
									break;
									
									case 'n':
										System.out.println("You decided to leave us. See you soon :)");
										carryOn = false;
									break;
									
									default:
										System.out.println("You're choice is incorrect. Retry please.");
									break;
								}
							}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
							
						break;
					
						case 'n':
							System.out.println("You are going back to the main menu");
						break;
					
						default:
							System.out.println("Your choice is incorrect. Please retry.");
						break;
					}
				
				}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
				
			break;
			
			case 5://Quit the store
				System.out.println("Thank you and see you soon!");
				carryOn = false;
			break;
		
			default://Other incorrect choice
				System.out.println("Your choice is incorrect.Please retry.");
			break;
			}
		}while(carryOn == true);
	}
	
	public void BuyProduct(Store store, List<Product> list) {//Buy a specific product
		int choice=0, number=0;
		boolean flag = false;
		String firstName, lastName, address, yORn;
		UUID customerID;
		Product wantedProduct;
		
		System.out.println("Select the index of the product you want to buy, please");
		do {
			for(int i=0; i<list.size(); i++) {//Display the list of product of a specific category
				System.out.println(i+" --> "+list.get(i).toString());
			}
			
			choice = scan.nextInt();//Choice a product to buy
			
		}while(choice<0 || choice>=list.size());
		
		wantedProduct = list.get(choice);
		
		System.out.println("Here is the product you want to buy :"+ wantedProduct.toString());
		/*The client needs to login*/
		System.out.println("Now, you have to login");
		System.out.println("Give me your firstname and surname please");
		System.out.println("FirstName: ");
		firstName = scan.next();
		System.out.println("LastName: ");
		lastName = scan.next();
		
		Customer newCustomer = store.SearchCustomerIntoList(firstName,lastName);//Search the customer into the list of customers
		if(newCustomer == null) {//If the customer doesn't exist
			/*The client needs to complete its profil*/
			System.out.println("Your account doesn't exist yet. You have to sign up. Give me you adress to complete your account, please.");
			System.out.println("Address: ");
			address = scan.next();
			
			customerID = store.CreateID();//Get a random unique ID
			
			newCustomer = new Customer(firstName, lastName, customerID, address);//Create the customer
			store.AddCustomerFile(newCustomer);//Add the customer into the xml file
			System.out.println("Your account is added with sucess!");
		}
		
		flag = true;
		do {
			/*The client needs to indicate the number of product to buy*/
			System.out.println("Tell me the amount you want to buy, please");
			number = scan.nextInt();
			
			if(number<1 || number>wantedProduct.getStock()) {//If the number wanted is too small or too big
				System.out.println("You can't buy less than 1 article and more than "+wantedProduct.getStock()+" articles");
				
				do {
					System.out.println("Do you want still buy this product?[y/n]");
					yORn = scan.next();
				
					switch(yORn.charAt(0)) {
						case 'y':
							flag = false;
							System.out.println("You decided to carry on the transaction, thank you!");
						break;
						
						case 'n':
							System.out.println("You decided to stop the transaction");
							flag = true;
						break;
						
						default:
							System.out.println("You're choice is incorrect. Retry please.");
						break;
					}
				}while(yORn.charAt(0) != 'y' && yORn.charAt(0) != 'n');
				
			}
		}while(flag == false);//If the choice is correct	
		
		if(number>0 && number<wantedProduct.getStock()) {//If the number of product ot buy is correct
			System.out.println("We are going to do the transaction!");
			Transaction transaction = new Transaction(newCustomer.getUniqueID(), wantedProduct.getIdentifier(), number, new Timestamp(System.currentTimeMillis()));//Create transaction
			try {
				transaction.MakeTransaction(store);//Make the transaction
				System.out.println("Congratulations! You bought your articles. Thank you very much!");
			} catch (EmptyStockException e) {
				e.printStackTrace();
			}
			store.UpdateStore();//Update the contents of the store
		}
		
	}
	
	
	public static void main(String[] args) {

		/** Main in charge of making the program run*/
		new VirtualStoreConsole();
	}

}
