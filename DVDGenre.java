package store.business;

public enum DVDGenre {
	COMEDY("Comedy"), ACTION("Action"), DRAMA("Drama"), HOROR("Horor"), SCI_FI("Science-Fiction"), FAMILY("Family"), ANIMATION("Animation"), ADVENTURE("Adventure"), THRILLER("Thriller"), ROMANCE("Romance"), HISTORY("History"), FANTASTIC("Fantastic"), WAR("War");
	
	private String nameGenre;
	
	DVDGenre(String name) {//Constructor

		/** Constructor used for the instatiation of an object of this enum
		*/
		nameGenre = name;
	}
	
	public String getNameDVDGenre() {//returns the name of the DVD's genre
		/** Returns the name of the DVD's genre
		*/
		return nameGenre;
	}
	
	public DVDGenre GetDVDGenreFromString(String s) {//returns the DVD's genre from a string

		/**Returns the DVD's genre from a string
		*/
		for (DVDGenre g : DVDGenre.values()) {//Get contents of Genre.values()
			if (s.toUpperCase().compareTo(g.getNameDVDGenre().toUpperCase()) == 0) {
				return g;
			}
		}
		return null;
	}
	
	public String toString() {////returns the information of the DVD's genre

		/** Returns the information of the DVD's genre
		*/
		return nameGenre;
	}

}
