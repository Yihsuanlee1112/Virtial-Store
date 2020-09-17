package store.business;

import java.util.UUID;
import java.util.Vector;
import java.util.Vector;

public class Game extends Product {
	
	private Vector <GameGenre> gamegenre = new Vector<GameGenre>(10,2);
	private Platform platform;
	
	public Game(String name, double price, UUID identifier, int stock, String image, Vector<GameGenre> gamegenre, Platform platform){//Construtor

		/** Constructor used during the instantiation of an object of this class
		*/
		this.name = name;
		this.price = price;
		this.identifier = identifier;
		this.stock = stock;
		this.image = image;
		this.platform = platform;
		
		for (int i=0 ;i<gamegenre.size(); i++) {
			this.gamegenre.add(i, gamegenre.get(i));
		}
	}

	public Platform getPlatform() {//returns the Game's platform

		/** Returns the Game's platform
		*/
		return platform;
	}
		
	public String GameGenreToString() {//returns the Game's genre 

		/** Returns the Game's genre 
		*/
		StringBuilder sb = new StringBuilder();//create our string
		
		for (int i=0; i<(gamegenre.size()-1); i++) {//add each element of the list into our string
			sb.append(gamegenre.get(i) + ",");
		}
		sb.append(gamegenre.get(gamegenre.size()-1));//add the last element of the list
			
		return sb.toString();//returns the string we build
	}
	
	public Vector<GameGenre> getGameGenre() {//returns the Game's genre as a Vector

		/** Returns the Game's genre as a Vector
		*/
		return gamegenre;
	}

	public String toString() {//returns the Game's information

		/** Returns the Game's information
		*/
		return "ID: "+identifier+ "\n Game's name: "+name +"\n Genre: "+ GameGenreToString()+"\n Price: "+ price +"\u20ac\n Platform: "+ platform+"\n Stock available: "+stock;
	}
	
	
}
