package store.business;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException; 

public class Store {
	
	private List<Product> productList = new ArrayList<Product>();//List of products from our xml file
	private List<Customer> customerList = new ArrayList<Customer>();//List of customers from our xml file
	
	public Store() {

		/** /update contents of the store
		*/

		UpdateStore();//update contents of the store
	}
	
	public void UpdateStore() {//Update the Store

		/** Updates the Store's inforation
		*/

		/*Make sure the 2 lists are empty*/
		customerList.clear();
		productList.clear();
		
		/*Get information to put into the lists from xml files*/
		GetCustomerFile(); 
		GetProductFile();
	}
	
	public Customer SearchCustomerIntoList( String fullName) {//search if a customer exists in the List according to the full name

		/** Searches if a customer exists in the List according to the full name
		* @params fullname which takes the full name of the customer to be searched into the list
		* @returns the customer name
		*/
		Customer customer = null;
		
		for (int i=0; i<customerList.size(); i++) {//Go through the list
			if (fullName.equals(customerList.get(i).getFirstName() +" "+ customerList.get(i).getLastName()) == true ) {//If we find a match
				customer = customerList.get(i);//We get the customer from the list
				break;
			}
		}
		
		return customer;//returns the customer
	}
	
	public Customer SearchCustomerIntoList(String firstName, String lastName) {//search if a customer exists in the List according to the firstName and the surname

		/** Searches if a customer exists in the List with respect to to the firstName and the surname
		* @params firstname which is the firstname of the client in question
		* @params lastname which is the last of the client in question
		* @returns the customer name
		*/
		Customer customer = null;
		
		for (int i=0; i<customerList.size(); i++) {//Go through the list
			if (firstName.equals(customerList.get(i).getFirstName()) == true && lastName.equals(customerList.get(i).getLastName()) == true) {//If we find a match
				customer = customerList.get(i);//We get the customer from the list
				break;
			}
		}
		
		return customer;//returns the customer
	}
	
	public Product SearchProductIntoList(UUID id) {//returns a product from the list according to its ID

		/** Returns a product from the list according to its ID
		* @params id which is the identity of the product
		* @returns the product in question
		*/
		Product product = null;
		
		for (int i=0; i<productList.size(); i++) {//Go through the list
			if (id.compareTo(productList.get(i).getIdentifier()) == 0) {//If we find a match
				product = productList.get(i);//We get the product from the list
				break;
			}
		}
		
		return product;//returns the product
	}
	
	public void DisplayCustomerCharacteristics(UUID identifier) {//Display information of a specific customer from the list according to the ID

		/** Displays information of a specific customer from the list according to the ID
		* @params identifier which is the identification characters of the customer
		*/
		
		for (int i=0; i<customerList.size(); i++) {//Go through the list
			if (identifier.compareTo(customerList.get(i).getUniqueID()) != 0) {//If we find a match
				customerList.get(i).toString();//Display its characteristics on the screen
			}
		}
		
	}
	
	public List<Product> GetProductList(){//returns the list of product

		/** Returns the list of product
		*/
		return productList;
	}
	
	public List<Product> GetDVDFromProduct() {//returns the list of DVDs from the store

		/** Returns the list of DVDs from the store
		*/
		List<Product> DVDList = new ArrayList<Product>();
		
		for(int i=0; i<productList.size();i++) {//Go through the list of products of the store
			if (productList.get(i) instanceof DVD && productList.get(i).getStock() != 0) {//If we find a DVD
				DVDList.add(productList.get(i));//Add this product into our list of DVDs
			}
		}
		
		return DVDList;//returns the list of DVD
	}
	
	public List<Product> GetBookFromProduct(){//returns the list of Books from the store

		/** Returns the list of Books from the store
		*/
		List<Product> BookList =new ArrayList<Product>();;
		
		for(int i=0; i<productList.size();i++) {//Go through the list of products of the store
			if (productList.get(i) instanceof Book && productList.get(i).getStock() != 0) {//If we find a Book
				BookList.add(productList.get(i));//Add this product into our list of Books
			}
		}
		
		return BookList;//returns the list of Book
	}
	
	public List<Product> GetGameFromProduct(){//returns the list of Games from the store

		/** Returns the list of Games from the store
		*/
		List<Product> GameList = new ArrayList<Product>();
		
		for(int i=0; i<productList.size();i++) {//Go through the list of products of the store
			if (productList.get(i) instanceof Game && productList.get(i).getStock() != 0) {//If we find a Game
				GameList.add(productList.get(i));//Add this product into our list of Games
			}
		}
		
		return GameList;//returns the list of games
	}
	
	public UUID CreateID() {//Create a unique random ID for the customer we want to add in the store

		/** Creates a unique random ID for the customer we want to add in the store
		* @returns a unique random customer ID
		*/
		boolean flag = false;
		UUID uuid_customer;
		
		if (customerList.size() == 0) {//If there is none customer added into the 'database'
			return 	uuid_customer = UUID.randomUUID();//returns a random ID
		}
			
		do {
			uuid_customer = UUID.randomUUID();//Create a random ID
			for (int i =0 ;i<customerList.size(); i++) {//Go through the list of customers from the xml file
				if (uuid_customer.compareTo(customerList.get(i).getUniqueID()) != 0) {//If this ID doesn't exist
					flag = true;
					break;
				}
			}
		}while(flag == false);//While we didn't find a unique ID different from ones already use by customers in the List
	    return uuid_customer;//returns a unique random customer ID
	}
	
	public void AddCustomerFile(Customer newCustomer) {//Add a customer into the xml file

		/** Adds a customer into the xml file
		*/
		String filepath = "./files/customer.xml";//path of the xml file
		File xmlFile = new File(filepath);//Create a file at the path indicate before
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = (Document) docBuilder.parse(xmlFile);
			
			//Get the root element
			Node customers = doc.getFirstChild();
			
			//append a new node to customers
			Element client = doc.createElement("client");
			customers.appendChild(client);
			
			//firstName elements
			Element firstName = doc.createElement("firstName");
			firstName.appendChild(doc.createTextNode(newCustomer.getFirstName()));
			client.appendChild(firstName);
			
			//lastName elements
			Element lastName = doc.createElement("lastName");
			lastName.appendChild(doc.createTextNode(newCustomer.getLastName()));
			client.appendChild(lastName);
			
			//uniqueID elements
			Element uniqueID = doc.createElement("uniqueID");
			uniqueID.appendChild(doc.createTextNode(newCustomer.getUniqueID().toString()));
			client.appendChild(uniqueID);
			
			//address elements
			Element address = doc.createElement("address");
			address.appendChild(doc.createTextNode(newCustomer.getAdress()));
			client.appendChild(address);
			
			/*To write modifications in the xml file*/
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
			
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void GetCustomerFile() {//Get the list of customer from the xml file

		/** Gets the list of customer from the xml file
		*/
		
		String filepath = "./files/customer.xml";//Where is the xml file
		File fXmlFile = new File(filepath);//Create a file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			NodeList nList = doc.getElementsByTagName("client");
			if(customerList.isEmpty() != true) {//If the list of customer is not empty
				customerList.clear();//We remove all the element inside
			}

			for (int temp = 0; temp < nList.getLength(); temp++) {//Go through all the document
				Node nNode = nList.item(temp);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Customer newCustomer = new Customer(eElement.getElementsByTagName("firstName").item(0).getTextContent(),	eElement.getElementsByTagName("lastName").item(0).getTextContent(), UUID.fromString(eElement.getElementsByTagName("uniqueID").item(0).getTextContent()), eElement.getElementsByTagName("address").item(0).getTextContent());//Create a new customer according to elements from the xml file
					customerList.add(newCustomer);//Add the customer to the list of customer
				}
			}
		 } catch (Exception e) {
			 e.printStackTrace();
		 }		
	}
	
	public void GetProductFile() {//Get the list of product from the xml file
		
		/** Gets the list of product from the xml file
		*/
		
		int numberGame=0, numberDVD=0, numberBook=0;
		
		String filepath = "./files/product.xml";//Where is the file
		File fXmlFile = new File(filepath);//Create a file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			NodeList nList = doc.getElementsByTagName("product");
			
			if(productList.isEmpty() != true) {//If the list of product is not empty
				productList.clear();//remove all elements from the list
			}

			for (int temp = 0; temp < nList.getLength(); temp++) {//Go through all the document
				Node nNode = nList.item(temp);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					String name, category, length;
					double price;
					int stock, number;
					UUID identifier;
					String image;
					NodeList n;
					Node node;
					Element e;
					
					/*Retrieve all common characteristics of a product*/
					name = eElement.getElementsByTagName("name").item(0).getTextContent();
					price = Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()); ;
					stock = Integer.parseInt(eElement.getElementsByTagName("stock").item(0).getTextContent());
					identifier = UUID.fromString(eElement.getElementsByTagName("identifier").item(0).getTextContent());
					image = eElement.getElementsByTagName("image").item(0).getTextContent();
					
					category = eElement.getAttribute("category");//Get the category of the product

					if(category.compareTo("DVD")==0) {//If the product is a DVD
						Vector <String> actors = new Vector<String>(10,2);
						int duration;
						Vector <DVDGenre> genre = new Vector<DVDGenre>(10,2);
						DVDGenre dvdGenre = DVDGenre.ACTION;
						
						n = doc.getElementsByTagName("actors");
						node = n.item(numberDVD);
						
						e = (Element) node;
						number = Integer.parseInt(e.getElementsByTagName("length").item(0).getTextContent());//Retrieve the number of actors
						
						for(int j=0; j<number; j++) {//Go through the list of actors in this file
							e = (Element) node;
							actors.add(e.getElementsByTagName("actor").item(j).getTextContent());//add the retrieve actor into the list of actors
						}
						
						duration = Integer.parseInt(eElement.getElementsByTagName("duration").item(0).getTextContent());//Retrieve the duration of the DVD
						
						n = doc.getElementsByTagName("genres");
						node = n.item(numberDVD);
						
						e = (Element) node;
						number = Integer.parseInt(e.getElementsByTagName("length").item(0).getTextContent());//Retrieve the number of genres
						
						for(int j=0; j<number; j++) {//Go through the list of genres
							e = (Element) node;
							dvdGenre = dvdGenre.GetDVDGenreFromString(e.getElementsByTagName("genre").item(j).getTextContent());//retrieve a genre
							genre.add(dvdGenre);//add this genre into the list of genre
						}
						
						numberDVD++;//Increment the number of DVD in this file
						DVD newDVD = new DVD(name, price, identifier,stock, image, actors, duration, genre);//Create a DVD from the retrieve variables
						productList.add(newDVD);//Add this DVD into the list of product
					}
					
					if(category.compareTo("Game")==0) {//If the product is a Game
						Vector <GameGenre> gamegenre = new Vector<GameGenre>(10,2);
						Platform platform = Platform.PC;
						GameGenre gameGenre = GameGenre.ARCADE;
						
						n = doc.getElementsByTagName("gameGenres");
						node = n.item(numberGame);
						
						e = (Element) node;
						number = Integer.parseInt(e.getElementsByTagName("length").item(0).getTextContent());//get the number of genre for this Game
					
						
						for(int j=0; j<number; j++) {//Go through the list of genre for this Game
							e = (Element) node;
							gameGenre = gameGenre.GetGameGenreFromString(e.getElementsByTagName("gameGenre").item(j).getTextContent());//retrieve the genre
							gamegenre.add(gameGenre);//add this genre into the list of genre
						}
						
						platform = platform.GetPlatformFromString(eElement.getElementsByTagName("platform").item(0).getTextContent());//Retrieve Game's platform
						
						numberGame++;//Increment the number of games into the document
						Game newGame = new Game(name, price, identifier,stock, image, gamegenre, platform);//Create a Game variable according to retrieve data
						productList.add(newGame);//Add this game into the list of product
						
					}
					
					if(category.compareTo("Book")==0) {//If the product is a book				
						Vector <String> author = new Vector<String>(10,2);//declares a Vector of size 10 and its space will increase by 2 when more then 10 elements are added.
						Language language= Language.ENGLISH;
						int page;
						
						n = doc.getElementsByTagName("authors");
						node = n.item(numberBook);
						
						e = (Element) node;
						number = Integer.parseInt(e.getElementsByTagName("length").item(0).getTextContent());//Get the number of authors for this book
						
						for(int j=0; j<number; j++) {//Go through all authors
							e = (Element) node;
							author.add(e.getElementsByTagName("author").item(j).getTextContent());//Get a author
						}
						
						language = language.GetLanguageFromString(eElement.getElementsByTagName("language").item(0).getTextContent());//get the language of this book
						page = Integer.parseInt(eElement.getElementsByTagName("page").item(0).getTextContent());//Get the number of page of this book
						
						numberBook++;//Increase the number of book in this document
						
						Book newBook = new Book(name, price, identifier,stock, image, author, language, page);//Create a book variable according to retrieve data
						productList.add(newBook);//Add the book into the list of product
						
					}
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }	
		
	}
	
	public void UpdateProductFile() {//Update contents of the xml file for products
		
		/** Updates contents of the xml file for products
		*/
			
		try{
    		File file = new File("./files/product.xml");//Where is the file to edit	
    		file.delete();//Delete the file
    		
    		String filepath = "./files/product.xml";//Where we want to create the file
    		File fic = new File(filepath);//Create a file
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = (Document) docBuilder.newDocument();
			
			// root elements
			Element rootElement = doc.createElement("products");
			doc.appendChild(rootElement);
			
			for(int i=0 ; i<productList.size(); i++) {//Go through the list of product
				
				// product elements
				Element product = doc.createElement("product");
				rootElement.appendChild(product);

				// set attribute to product element
				Attr attr = doc.createAttribute("category");
				
				/*Define the category of the product into the file*/
				if(productList.get(i) instanceof DVD) {//If the product is a DVD
					attr.setValue("DVD");
					product.setAttributeNode(attr);
				}
				
				if(productList.get(i) instanceof Book) {//If the product is a book
					attr.setValue("Book");
					product.setAttributeNode(attr);
				}
				
				if(productList.get(i) instanceof Game) {//If the product is a Game
					attr.setValue("Game");
					product.setAttributeNode(attr);
				}
				
				// name elements
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(productList.get(i).getName()));
				product.appendChild(name);

				// price elements
				Element price = doc.createElement("price");
				price.appendChild(doc.createTextNode(String.valueOf(productList.get(i).getPrice())));
				product.appendChild(price);

				// stock elements
				Element stock = doc.createElement("stock");
				stock.appendChild(doc.createTextNode(String.valueOf(productList.get(i).getStock())));
				product.appendChild(stock);

				// identifier elements
				Element identifier = doc.createElement("identifier");
				identifier.appendChild(doc.createTextNode(productList.get(i).getIdentifier().toString()));
				product.appendChild(identifier);

				// image elements
				Element image = doc.createElement("image");
				image.appendChild(doc.createTextNode(productList.get(i).getImage()));
				product.appendChild(image);
				
				if(productList.get(i) instanceof DVD) {//If the product is a DVD
					
					DVD dvd = (DVD) productList.get(i);//Get the product
					
					//actors elements
					Element actors = doc.createElement("actors");
					product.appendChild(actors);
					
					//Number of actors
					Element length = doc.createElement("length");
					length.appendChild(doc.createTextNode(String.valueOf(dvd.getActors().size())));
					actors.appendChild(length);
					
					//actor elements
					for(int j=0; j< dvd.getActors().size(); j++) {//Go through the list of actors
						Element a = doc.createElement("actor");
						a.appendChild(doc.createTextNode((String) dvd.getActors().get(j)));
						actors.appendChild(a);
					}
					
					//duration elements
					Element duration = doc.createElement("duration");
					duration.appendChild(doc.createTextNode(String.valueOf(dvd.getDuration())));
					product.appendChild(duration);
					
					//genre elements
					Element genres = doc.createElement("genres");
					product.appendChild(genres);
					
					//Number of genres
					length = doc.createElement("length");
					length.appendChild(doc.createTextNode(String.valueOf(dvd.getGenre().size())));
					genres.appendChild(length);
					
					//Genre elements
					for(int j=0; j< dvd.getGenre().size(); j++) {//Go through the list of genres
						Element g = doc.createElement("genre");
						g.appendChild(doc.createTextNode(dvd.getGenre().get(j).toString()));
						genres.appendChild(g);
					}	
				}
				
				if(productList.get(i) instanceof Book) {//the product is a book
					
					Book book = (Book) productList.get(i);//Get the product
					
					//author elements
					Element authors = doc.createElement("authors");
					product.appendChild(authors);
					
					//number of author
					Element length = doc.createElement("length");
					length.appendChild(doc.createTextNode(String.valueOf(book.getAuthor().size())));
					authors.appendChild(length);
					
					//Author elements
					for(int j=0; j< book.getAuthor().size(); j++) {//Go through the list of authors
						Element au = doc.createElement("author");
						au.appendChild(doc.createTextNode((String) book.getAuthor().get(j)));
						authors.appendChild(au);
					}
					
					//language elements
					Element language = doc.createElement("language");
					language.appendChild(doc.createTextNode(book.getLanguage().getLanguageName()));
					product.appendChild(language);
					
					//page elements
					Element page= doc.createElement("page");
					page.appendChild(doc.createTextNode(String.valueOf(book.getPage())));
					product.appendChild(page);
					
				}
				
				if(productList.get(i) instanceof Game) {//The product is a game

					Game game = (Game) productList.get(i);//Get the product
					
					//genres elements
					Element genres = doc.createElement("gameGenres");
					product.appendChild(genres);
					
					//Number of genres
					Element length = doc.createElement("length");
					length.appendChild(doc.createTextNode(String.valueOf(game.getGameGenre().size())));
					genres.appendChild(length);
					
					//genre elements
					for(int j=0; j< game.getGameGenre().size(); j++) {//Go through the list of genres
						Element gg = doc.createElement("gameGenre");
						gg.appendChild(doc.createTextNode(game.getGameGenre().get(j).toString()));
						genres.appendChild(gg);
					}
					
					//platform elements
					Element platform = doc.createElement("platform");
					platform.appendChild(doc.createTextNode(String.valueOf(game.getPlatform())));
					product.appendChild(platform);
				}
					
			}


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
	}
	
	
}
