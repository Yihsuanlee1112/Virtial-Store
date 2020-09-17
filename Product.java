package store.business;

import java.util.UUID;

public abstract class Product {
	
	protected String name;
	protected double price;
	protected int stock;
	protected UUID identifier;
	protected String image;
	
	public String getName() {//returns product's name

		/** Returns product's name
		*/
		return name;
	}
	
	public double getPrice() {//returns product's price

		/** Returns product's price
		*/
		return price;
	}
	
	public int getStock() {//returns product's stock

		/** Returns product's stock
		*/
		return stock;
	}
	
	public String getImage() {//returns product's image link

		/** Returns product's image link
		*/
		return image;
	}
	
	public UUID getIdentifier() {//returns product's ID
		
		/** Returns product's ID
		*/
		return identifier;
	}
	
	public String toString() {//returns product's information

		/** Returns product's information*/
		return "ID: "+identifier +"\n Product's name: "+ name +"\n Price: "+ price +"\n Available stock: "+ stock;
	}
	
	public void updateStock(int newStock) {//update the stock of the product

		/** Updates the stock of the product
		*@ newStock which is the number of product in stock of the product
		*/
		this.stock = newStock;
	}

}
