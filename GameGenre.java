package store.business;

public enum GameGenre {
	
	ARCADE("Arcade"), ADVENTURE("Adventure"), ROLE("Role"), MULTIPLAYER("Multiplayer"), EDUCATIONAL("Educational"), STRATEGY("Strategy"), ACTION("Action"), SPORTS("Sports");
	
	private String gameGenre;
	
	GameGenre(String name){//Constructor

		/** Constructor used to instantiate an object of this enum
		*/
		gameGenre = name;
	}
	
	public String toString() {//returns the Game's genre information

		/** Returns the Game's genre information
		*/
		return gameGenre;
	}
	
	public String getGameGenre() {//returns the Game's genre as a String

		/** returns the Game's genre as a String
		*/
		return gameGenre;
	}
	
	public GameGenre GetGameGenreFromString(String s) {//returns the Game's genre according to a string

		/** returns the Game's genre according to a string
		@params String s which is the game's genre
		*/
		for (GameGenre g : GameGenre.values()) {//Get contents of Genre.values()
			if (s.toUpperCase().compareTo(g.getGameGenre().toUpperCase()) == 0) {//If the two genres are the same
				return g;
			}
		}
		return null;
	}
	
}
