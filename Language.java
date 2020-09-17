package store.business;

public enum Language {
	
	ENGLISH("English"), FRENCH("French"), GERMAN("German"), SPANISH("Spanish"), CHINESE("Chinese");
	
	private String language;
	
	Language(String l){//Constructor
		
		/** Constructor used for the instatiation of an object of this class
		*/
		language = l;
	}
	
	public String toString() {//returns the Language's information

		/** Returns the Language's information
		*/
		return language;
	}
	
	public String getLanguageName() {//returns the Language's name as a String

		/** Returns the Language's name as a String*/
		return language;
	}
	
	public Language GetLanguageFromString(String s) {//returns the Language according to a String

		/** Returns the Language according to a String
		*@params String s which is the name of the language
		*/
		for (Language l : Language.values()) {//Get contents of Genre.values()
			if (s.toUpperCase().compareTo(l.getLanguageName().toUpperCase()) == 0) {//If the two language are the same
				return l;
			}
		}
		return null;
	}

}
