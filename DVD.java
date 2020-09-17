package store.business;

import java.util.UUID;
import java.util.Vector;

public class DVD extends Product{
	
	private Vector <String> actors = new Vector<String>(10,2);;
	private int duration;
	private Vector <DVDGenre> genre = new Vector<DVDGenre>(10,2);;
	
	public DVD(String name, double price, UUID identifier, int stock, String image, Vector <String> actors, int duration, Vector<DVDGenre> genre){//Constructor

		/** Constructor used to instantiate an object of this class
		*/
		this.name = name;
		this.price = price;
		this.identifier = identifier;
		this.stock = stock;
		this.image = image;
		
		for (int i=0 ;i<actors.size(); i++) {
			this.actors.add(actors.get(i));
		}
		
		this.duration = duration;
		
		for (int i=0 ;i<genre.size(); i++) {
			this.genre.add(i, genre.get(i));
		}
	}
	
	public String ActorToString() {//returns the DVD's actors list

		/** Returns the DVD's actors list
		*/
		StringBuilder sb = new StringBuilder();//create our string
		
		for (int i=0; i<(actors.size()-1); i++) {//add each element of the list into our string
			sb.append(actors.get(i) + ",");
		}
		if(actors.size() != 0) {
			sb.append(actors.get(actors.size()-1));//add the last element of the list
		}
			
		return sb.toString();//returns the string we build
	}
	
	public String GenreToString() {//returns the DVD's genre list

		/** Returns the DVD's genre list
		*/
		StringBuilder sb = new StringBuilder();//create our string
		
		for (int i=0; i<(genre.size()-1); i++) {//add each element of the list into our string
			sb.append(genre.get(i) + ",");
		}
		
		if(genre.size() != 0) {
			sb.append(genre.get(genre.size()-1));//add the last element of the list
		}	
		return sb.toString();//returns the string we build
	}
	
	public int getDuration() {//returns the DVD's duration

		/** Returns the DVD's duration
		*/
		return duration;
	}
	
	public Vector<String> getActors() {//returns the DVD's actors list as a vector

		/** Returns the DVD's actors list as a vector
		*/
		return actors;
	}
	
	public Vector<DVDGenre> getGenre() {//returns the DVD's genre list as a vector
		/** Returns the DVD's genre list as a vector
		*/
		return genre;
	}
	
	
	public String toString() {//returns the DVD's information
		/** Returns the DVD's information*/
		return "ID: "+identifier+"\n DVD's name: "+ name +"\n Genre: "+ GenreToString()+"\n Price: "+ price +"\u20ac\n Actor(s): "+ ActorToString()+ "\n Stock available: "+ stock;
	}
	

}
