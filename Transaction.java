package store.business;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Transaction {
	
	private UUID customerID;
	private UUID productID;
	private int number;
	private Timestamp time;

	public Transaction(UUID customerID, UUID productID, int number, Timestamp time){//Constructor

		/** Constructor used to instantiate an object of this class
		*/
		this.customerID = customerID;
		this.productID = productID;
		this.number = number;
		this.time = time;
	}
	
	public boolean MakeTransaction(Store store) throws EmptyStockException {//Method to make the transaction happens

		/** Method to make the transaction occur
		* @params store which is our store
		* @returns buy which indicates if the transaction has occurred or not
		*/
		boolean buy = false;
		
		Product product = store.SearchProductIntoList(productID);	//Get the product from the list according to its ID
		
		if(product.getStock() < number) {//If the number of product the customer wants to buy is bigger than the number of available products
			new EmptyStockException("You can't buy more than the number of products available");
		}else {//Otherwise
			product.updateStock(product.getStock() - number);//retrieve the number of product the client wanted
			buy = true;//the transaction happens with success
			UpdateTransactionFile();//Update contents of transaction.xml
			store.UpdateProductFile();//Need to update contents of product.xml
		}
		
		return buy;//Indicate if the transaction has occurred or not	
	}
	
	public void UpdateTransactionFile(){//Update contents of transaction.xml 
		
		/** Updates contents of transaction.xml 
		*/
		
		try {
			
			String filepath = "C:\\Users\\trico\\OneDrive\\Documents\\Scolarit�\\2019-2020\\Java\\Project\\Virtual_Store\\Virtual_Store\\src\\files\\transaction.xml";//Where the document is
			File xmlFile = new File(filepath);//Create a file
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFile);
			
			//Get the root element
			Node transactions = doc.getFirstChild();
			
			//append a new node to transaction
			Element purchase = doc.createElement("purchase");
			transactions.appendChild(purchase);
			
			//customerID elements
			Element customerID = doc.createElement("customerID");
			customerID.appendChild(doc.createTextNode(this.customerID.toString()));
			purchase.appendChild(customerID);
			
			//productID elements
			Element productID = doc.createElement("productID");
			productID.appendChild(doc.createTextNode(this.productID.toString()));
			purchase.appendChild(productID);
			
			//number elements
			Element number = doc.createElement("number");
			number.appendChild(doc.createTextNode(String.valueOf(this.number)));
			purchase.appendChild(number);
			
			//time elements
			Element time = doc.createElement("time");
			String s = new SimpleDateFormat("dd/MM/yyy HH:mm:ss").format(this.time);
			time.appendChild(doc.createTextNode(s));
			purchase.appendChild(time);
			
			//Write modifications into the xml file
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
	
	public UUID getCustomerID() {//returns customer's ID

		/** Returns customer's ID*/
		return customerID;
	}
	
	public UUID getProductID() {//returns product's ID

		/** Returns product's ID*/
		return productID;
	}
	
	public int getNumber() {//returns number of product the client wants to buy

		/** Returns number of product the client wants to buy
		*/
		return number;
	}
	
	public Timestamp getTime() {//returns when the client bought the product

		/** Returns when the client bought the product
		*/
		return time;
	}
	
	public String toString() {//Returns the transaction's information

		/** Returns the transaction's information
		*/
		return customerID +" "+ productID +" "+ number +" "+ time;
	}
}
