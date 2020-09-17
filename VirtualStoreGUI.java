package store.gui;

import java.util.List;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import store.business.Book;
import store.business.Customer;
import store.business.DVD;
import store.business.EmptyStockException;
import store.business.Game;
import store.business.Product;
import store.business.Store;
import store.business.Transaction;

public class VirtualStoreGUI implements ActionListener {
	
	private String picturePath = "./files";//Path files directory
	private String nameProductCategory[] = {"DVD", "Book", "Game", "All"};//All product categories that will be use to fill a ComboBox
	

	private JFrame addressFrame;//Window for complete the sign up of the user
	
	/*Contents of the addressFrame*/
	private JPanel addressPanel = new JPanel();//main Panel
	private JTextField customerFirstName = new JTextField(20);
	private JTextField customerLastName = new JTextField(20);
	private JTextField customerAddress = new JTextField(20);
	
	
	private JFrame frame;//Name of the window
	
	/*Contents of the Store Area : frame (1,1)*/
	private JPanel storePanel = new JPanel(new GridLayout(1,2));//Main Panel
		/*Contents of storePanel(1,1)*/
	private JPanel storePanelLogo = new JPanel(new BorderLayout());//Panel which contains the store logo
			/*Contents of storePanelLogo*/
	private ImageIcon logo;//logo of the store
		/*Contents of storePanel(1,2)*/
	private JPanel storePanelProductCategory = new JPanel(new BorderLayout());//Panel which contains categories list
			/*Contents of storePanelProductCategory*/
	private JComboBox productCategory = new JComboBox(nameProductCategory);//ComboBox with all categories
	private JButton storeSearchButton = new JButton ("Search");//Button to search all products from a specific category
	
	
	/*Contents of the Product Area : frame (2,1)*/
	private JPanel productPanel = new JPanel(new GridLayout(1,3));//Main Panel
		/*Contents of productPanel(1,1)*/
	private JPanel productPanelAvailable = new JPanel(new BorderLayout());//Panel which contains the list of available product for a specific category
			/*Contents of productPanelAvailable*/
	private DefaultListModel<String> list;
	private JList<String> productList; //list of available product
		/*Contents of productPanel(1,2)*/
	private JPanel productPanelCurrent = new JPanel(new BorderLayout());//Panel which contains the information of the chosen product
			/*Contents of productPanelCurrent*/
	private JButton selectButton = new JButton ("Select");
		/*Contents of productPanel(1,3)*/
	private ImageIcon productImage;	//image of the product
	
	
	/*Contents of the Customer Area : frame(3,1)*/
	private JPanel customerPanel = new JPanel(new GridLayout(1,2));//Main Panel
		/*Contents of customerPanel(1,1)*/
	private JPanel customerPanelSearch = new JPanel(new GridLayout(3,1));//Panel which contains the space where to log in
			/*Contents of customerPanelSearch(1,1)*/
	private JPanel customerPanelSearchFirstName = new JPanel(new BorderLayout());//Panel which contains elements to indicate First Name
			/*Contents of customerPanelSearch(2,1)*/
	private JPanel customerPanelSearchLastName = new JPanel(new BorderLayout());//Panel which contains elements to indicate Last Name
			/*Contents of customerPanelSearch(3,1)*/
	private JButton customerSearchButton = new JButton ("Search");//Button to check if customer exists or not
		/*Contents of customerPanel(1,2)*/
	private JPanel customerPanelInfo = new JPanel(new BorderLayout());//Panel which contains information about the product
			/*Contents of customerPanelInfo*/
	private JLabel customerInfoLabel = new JLabel();//Description of the product
	
	
	/*Contents of the Transaction Area : frame(4,1)*/
	private JPanel transactionPanel = new JPanel(new GridLayout(1,2));//Main Panel
		/*Contents of transactionPanel(1,1)*/
	private JPanel transactionPanelCurrentCustomer = new JPanel(new BorderLayout());//Display information about the current customer who may buy something
			/*Contents of transactionPanelCurrentCustomer*/
	private JLabel transactionCustomerInfo = new JLabel();//Display full name of the current customer who may buy a product
		/*Contents of transactionPanel(1,2)*/
	private JPanel transactionPanelCurrentProduct = new JPanel(new BorderLayout());//Display Information about the chosen product to buy
			/*Contents of transactionPanelCurrentProduct*/
	private JLabel transactionProductInfo = new JLabel();//Display information about the current product to buy
	private JComboBox<Integer> numberProduct = new JComboBox<Integer>();//Display stock
	private JButton buyButton = new JButton ("Buy");//Button to make transaction occurs
	
	
	/*Classes and variables*/
	private Store store = new Store();
	private Product currentProduct; //Product choose by the user to buy
	private Customer currentCustomer; //Client who wants to buy something
	private String category; //Category of product chosen by the client
	private int number = 0; //Number of items the client wants to buy
	private double totalPrice = 0.0; // The total price the client need to pay 

	
	private VirtualStoreGUI() {//Graphical User Interface of the Store

		/** Graphical User Interface of the Store
		*/
		
		logo = new ImageIcon(picturePath +"/Leecota_logo.jpeg");//upload logo image
		
		/*Default configuration of the main window*/
		frame = new JFrame("Leecota");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//Get size of the screen
	    	//frame.setMinimumSize(screenSize);//full screen mode 
		frame.setMinimumSize(new Dimension(1800,900));
		frame.setIconImage(logo.getImage());//Put an icon
		frame.setLayout(new GridLayout(4,1));//Define type of Layout
		
		/*Store Area*/
		storePanel.setBorder(BorderFactory.createLineBorder(Color.black));//Put black border
			/*Logo area*/
		storePanelLogo.add(new JLabel(logo), BorderLayout.CENTER);//Add logo into Panel
		storePanel.add(storePanelLogo);
			/*Product Category area*/
		storePanelProductCategory.add(new JLabel("Product category"), BorderLayout.NORTH);//Add Text into Panel
		productCategory.setSelectedItem(null);//Define no selected categories when launch the program
		storePanelProductCategory.add(productCategory, BorderLayout.CENTER);//Add ComboBox into Panel
		storePanelProductCategory.add(storeSearchButton, BorderLayout.EAST);//Add Search Button into Panel
		storePanel.add(storePanelProductCategory);
		
		frame.add(storePanel);
		
		
		/*Product area*/
		productPanel.setBorder(BorderFactory.createLineBorder(Color.black));//Put black border
			/*Available product area*/
		productPanelAvailable.add(new JLabel("Available products"), BorderLayout.NORTH);//Add text to Panel
		productPanel.add(productPanelAvailable);
			/*Current product area*/
		productPanelCurrent.add(new JLabel("Current product"), BorderLayout.NORTH);//Add Text ot Panel
		productPanel.add(productPanelCurrent);
		
		frame.add(productPanel);
		
		
		/*Customer area*/
		customerPanel.setBorder(BorderFactory.createLineBorder(Color.black));//Put black border
			/*Search Customer area*/
		customerPanelSearchFirstName.add(new JLabel("First name"), BorderLayout.NORTH);//Add text to Panel
		customerPanelSearchFirstName.add(customerFirstName, BorderLayout.CENTER);//Add Text field to Panel
		customerPanelSearch.add(customerPanelSearchFirstName);
		customerPanelSearchLastName.add(new JLabel("Last name"), BorderLayout.NORTH);//Add text to Panel
		customerPanelSearchLastName.add(customerLastName, BorderLayout.CENTER);//Add Text Field to Panel
		customerPanelSearch.add(customerPanelSearchLastName);
		customerPanelSearch.add(customerSearchButton);//Add Search Button to Panel
		customerPanel.add(customerPanelSearch);
			/*Current Client information area*/
		customerPanelInfo.add(new JLabel("Chosen Client's information"), BorderLayout.NORTH);//Add text to Panel
		customerPanelInfo.add(customerInfoLabel, BorderLayout.CENTER);//Add Label to Panel
		customerPanel.add(customerPanelInfo);
		
		frame.add(customerPanel);
		
		
		/*Transaction area*/
		transactionPanel.setBorder(BorderFactory.createLineBorder(Color.black));//Put black border
			/*Display Customer's name area*/
		transactionPanelCurrentCustomer.add(new JLabel("Current customer's name"), BorderLayout.NORTH);//Add text to Panel
		transactionPanelCurrentCustomer.add(transactionCustomerInfo, BorderLayout.CENTER);//Add Label to Panel
		transactionPanel.add(transactionPanelCurrentCustomer);
			/*Display product to buy area*/
		transactionPanelCurrentProduct.add(new JLabel("Current product to buy"), BorderLayout.NORTH);//Add text to Panel
		transactionPanelCurrentProduct.add(transactionProductInfo, BorderLayout.CENTER);//Add Label to Panel
		numberProduct.setSelectedItem(null);//Define no selected number of items when launch the program
		transactionPanelCurrentProduct.add(numberProduct, BorderLayout.SOUTH);//Add ComboBox to Panel
		transactionPanelCurrentProduct.add(buyButton , BorderLayout.EAST);//Add button to Panel
		transactionPanel.add(transactionPanelCurrentProduct);
		
		frame.add(transactionPanel);
		
		frame.pack(); // resize the window considering the new elements
		frame.setVisible(true);//Show the window
		/*Make Panel visible or not when launch the program*/
		productPanel.setVisible(false);
		customerPanel.setVisible(false);
	    transactionPanel.setVisible(false);
		
		
		/*Button interactions*/
		storeSearchButton.setActionCommand("Search Category");
		storeSearchButton.addActionListener((java.awt.event.ActionListener) this);
		customerSearchButton.setActionCommand("Search Customer");
		customerSearchButton.addActionListener((java.awt.event.ActionListener) this);
		buyButton.setActionCommand("Buy Product");
		buyButton.addActionListener((java.awt.event.ActionListener) this);
		selectButton.setActionCommand("Select Product");
		selectButton.addActionListener((java.awt.event.ActionListener) this);
		numberProduct.setActionCommand("Total Price");
		numberProduct.addActionListener((java.awt.event.ActionListener) this);
	}
	
	public void actionPerformed(ActionEvent e) {//Actions after the interaction with the buttons

		/** Actions performed after the interaction with the buttons
		*/		

		switch (e.getActionCommand()) {
			case "Search Category"://if press "Search" button in the Store area to select to display products from selected category
				category = (String) productCategory.getSelectedItem();//Get chosen category from ComboBox
				
				/*Make sure variable are "clear"*/
				currentProduct = null;
				currentCustomer = null;
				number = 0;
				
				/*Make Panel visible or not*/
				productPanel.setVisible(true);
				customerPanel.setVisible(false);
				transactionPanel.setVisible(false);
				
				DisplayProductArea ();//Call function to display Product Area
				
			break;
			
			case "Search Customer"://if press "Search" button in the Customer area to check if customer exist or not
				
				currentCustomer = store.SearchCustomerIntoList(customerFirstName.getText(), customerLastName.getText());//Returns the customer written by the user if it exists, otherwise returns null
				
				if(currentCustomer == null) {//It's the first time the client is login in
					/*Display message to indicate that this account doesn't exist yet*/
					final JPanel panel = new JPanel();
				    JOptionPane.showMessageDialog(panel, "Your account doesn't exist yet. Indicate your address to complete it please", "Sign up", JOptionPane.ERROR_MESSAGE);
				    
				    /*Steps to sign up*/
				    addressPanel.removeAll();//Remove all elements from addressPanel to be empty
				    
				    /*Contents of addressPanel*/
				    addressPanel.add(new JLabel("First name"));//Add text to Panel
					addressPanel.add(customerFirstName);//Add text field to Panel
					addressPanel.add(Box.createHorizontalStrut(15));//a spacer
					
					addressPanel.add(new JLabel("Last name"));//Add text to Panel
					addressPanel.add(customerLastName);//Add text field to Panel
					addressPanel.add(Box.createHorizontalStrut(15));//a spacer
					
					addressPanel.add(new JLabel("Address"));//Add text to Panel
					addressPanel.add(customerAddress);//Add text field to Panel
					addressPanel.add(Box.createHorizontalStrut(15));//a spacer
					
					/*Display window to sign up*/
					int result = JOptionPane.showConfirmDialog(null, addressPanel, "Please Enter your first name, last name and address please", JOptionPane.OK_CANCEL_OPTION);
				      if (result == JOptionPane.OK_OPTION) {//If click to ok
				    	  currentCustomer = new Customer(customerFirstName.getText(), customerLastName.getText(), store.CreateID(), customerAddress.getText());//Create customer
				    	  store.AddCustomerFile(currentCustomer);//Add the customer into the xml file
							
						  JOptionPane.showMessageDialog(panel, "Your account is created with success!");//Show a message to confirm the operation
				      }	      
				      DisplayCustomerArea();//Calls the function to update the contents of the Customer area		      
				}
				
				customerInfoLabel.setText(currentCustomer.toString());//Display the information about the chosen customer
				
			    addressPanel.repaint();//tell a component to repaint itself.
			    addressPanel.revalidate();// tell the layout manager to reset based on the new component list
				
				DisplayTransactionArea();
				
			break;
			
			case "Buy Product"://if press "Buy" button in the Transaction area, we have to make the transaction occurs
				
				number = (Integer) numberProduct.getSelectedItem();//Retrieve number of items to buy
				totalPrice = currentProduct.getPrice() * number;//Calculate total price
				System.out.println("number of product the user wants to buy is "+number);
						
				/*Display window to confirm the purchase*/
				final JPanel panel = new JPanel();
			    int result = JOptionPane.showConfirmDialog(panel, "Are you sure to buy this product for a total amount of "+totalPrice+" \u20ac", "Confirmation of your purchase", JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION) {//If the user want to carry on, we make the transaction occurs
					Transaction transaction = new Transaction(currentCustomer.getUniqueID(), currentProduct.getIdentifier(), number, new Timestamp(System.currentTimeMillis()));//Create transaction
					try {
						transaction.MakeTransaction(store);//Make the transaction
						JOptionPane.showMessageDialog(panel, "Thank you for your purchase!");//Display a message to indicate that the transaction succeed
					} catch (EmptyStockException e1) {
						e1.printStackTrace();
					}	
					store.UpdateStore();//Update the contents of the store	
				}
				
				if(result == JOptionPane.CANCEL_OPTION) {//If the user want to cancel the transaction
					JOptionPane.showMessageDialog(panel, "Your purchase was cancelled.");//Display a message to indicate that the transaction was cancelled
				}
				
				/*Make Panels visible or not*/
				productPanel.setVisible(true);
				customerPanel.setVisible(true);
				transactionPanel.setVisible(true);
			break;
			
			case "Select Product"://if press Select button in the Product Area, we must display the information of the product
				
				int index = productList.getSelectedIndex();//Get the index of the selected product from the List
				currentProduct = getSelectedProduct(index);//Get selected product according to its index

				DisplayProductArea();//Calls the function to update the display of the Product Area
				
				/*Make Panels visible or not*/
				productPanel.setVisible(true);
				customerPanel.setVisible(true);
				transactionPanel.setVisible(false);
				
			break;
			
			case "Total Price"://Display the total price when select a temporary number of items to buy
				System.out.println("We are in the function to change Total Price with number equals to "+numberProduct.getSelectedItem());
				totalPrice = currentProduct.getPrice() * (Integer) numberProduct.getSelectedItem();//Calculate total price
				DisplayTransactionArea();//Calls the function to update the display of the Transaction Area
				
			break;
			
		}		
	}
	
	private JComboBox<Integer> intializeComboBox() {//Initialize contents of ComboBox to display number of items possible to buy

		/** Initializes the  contents of ComboBox to display number of items possible to buy
		*/
		
		JComboBox<Integer> combo = new JComboBox<Integer>();
		
		for(int i=1; i<=currentProduct.getStock(); i++) {//Add all possible number of items to buy
			combo.addItem(i);
		}
		
		return combo;
		
	}	
	
	private Image getScaledImage(Image srcImg, int w, int h){

		/** Scales the image for it to suit our program
		* @srcImg which is the image to be scaled
		* @w which is the scaling length
		* @h which s the scaling width
		* @returns the resized image
		*/

	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	private void DisplayProductArea (){//Display the contents of the Product area

		/** Displays the contents of the Product area
		*/
		
		switch(category) {//In function of the chosen category
		case "DVD"://If choose the Book category
			list = GetListDVD();//Get the list of DVDs
			productList = new JList<String>(list);//Create a List according to the list of DVDs
		break;
		
		case "Game"://If choose the Game category
			list = GetListGame();////Get the list of Games
			productList = new JList<String>(list);//Create a List according to the list of Games
		break;
		
		case "Book"://If choose the Book category
			list = GetListBook();//Get the list of Books
			productList = new JList<String>(list);//Create a List according to the list of Books
		break;
		
		case "All"://If choose All category
			list = GetListProduct();//Create a List according to the list of products
			productList = new JList<String>(list);//Create a List according to the list of products
		break;
		}
		
		/*Update contents of the panel*/		
		productPanelAvailable.removeAll();//Remove all elements from productPanelAvailable to be empty
		
		productPanelAvailable.add(new JLabel("Available products"), BorderLayout.NORTH);//Add a text to the Panel
		productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollPane = new JScrollPane(productList);//Add a Scroll Bar
		productPanelAvailable.add(scrollPane, BorderLayout.CENTER);//Add a Scroll Bar to the Panel
		productPanelAvailable.add(selectButton, BorderLayout.SOUTH);//Add the Search Button to the Panel
		productPanel.add(productPanelAvailable);
		
		productPanelAvailable.repaint();//tell a component to repaint itself.
		productPanelAvailable.revalidate();// tell the layout manager to reset based on the new component list
		
		productPanelCurrent.removeAll();//Remove all elements from productPanelCurrent to be empty

		productPanelCurrent.add(new JLabel("Current product"), BorderLayout.NORTH);//Add text to Panel
		
		if(currentProduct != null) {//If we have selected a product
			JTextArea text = new JTextArea(currentProduct.toString());//Create a Text Area where to display information of the product
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			productPanelCurrent.add(text, BorderLayout.CENTER);//Add the text area to the Panel
			/*Load the image of the product from the computer depending on the category*/
			if(currentProduct instanceof Book) {//If it's a Book
				productImage = new ImageIcon(picturePath +"/Book/"+currentProduct.getImage());
			}
			
			if(currentProduct instanceof Game) {//If it's a Game
				productImage = new ImageIcon(picturePath +"/Game/"+currentProduct.getImage());
			}
			
			if(currentProduct instanceof DVD) {//If it's a DVD
				productImage = new ImageIcon(picturePath +"/DVD/"+currentProduct.getImage());
			}
			productImage = new ImageIcon(getScaledImage(productImage.getImage(), 250, 300));//Resize image
			productPanelCurrent.add(new JLabel(productImage), BorderLayout.EAST);//Add the image to the Panel
		}
		productPanel.add(productPanelCurrent);
		
		productPanelCurrent.repaint();//tell a component to repaint itself.
		productPanelCurrent.revalidate();// tell the layout manager to reset based on the new component list
			
		}
	
	private void DisplayTransactionArea() {//

		/** Displays the content of Transaction Area
		*/
		
		/*Set text*/
		transactionCustomerInfo.setText(currentCustomer.getFirstName()+"     "+currentCustomer.getLastName());//Display information about the customer
		
		/*Set text to display information about chosen product*/
		if(currentProduct instanceof Book) {//If it's a Book
			transactionProductInfo.setText("Category: Book    Name: "+currentProduct.getName()+"       Price: "+currentProduct.getPrice()+"  \u20ac");
		}
		
		if(currentProduct instanceof Game) {//It's a Game
			transactionProductInfo.setText("Category: Game    Name: "+currentProduct.getName()+"       Price: "+currentProduct.getPrice()+"  \u20ac");
		}
		
		if(currentProduct instanceof DVD) {//It's a DVD
			transactionProductInfo.setText("Category: DVD    Name: "+currentProduct.getName()+"       Price: "+currentProduct.getPrice()+"  \u20ac");
		}
		
		numberProduct = intializeComboBox();//Initialize contents of Combo Box to display number of available items
		
		transactionPanelCurrentCustomer.removeAll();//Remove all elements from transactionPanelCurrentCustomer to be empty
		
		transactionPanelCurrentCustomer.add(new JLabel("Current Customer's name"), BorderLayout.NORTH);//Add a text to Panel
		transactionPanelCurrentCustomer.add(transactionCustomerInfo, BorderLayout.CENTER);//Add a text field to Panel
		transactionPanel.add(transactionPanelCurrentCustomer);
		
		transactionPanelCurrentCustomer.repaint();//tell a component to repaint itself.
		transactionPanelCurrentCustomer.revalidate();// tell the layout manager to reset based on the new component list
		
		transactionPanelCurrentProduct.removeAll();//Remove all elements from transactionPanelCurrentProduct to be empty
		
		transactionPanelCurrentProduct.add(new JLabel("Current product to buy"), BorderLayout.NORTH);//Add a text to panel
		transactionPanelCurrentProduct.add(transactionProductInfo, BorderLayout.CENTER);//Add a text field to Panel
		transactionPanelCurrentProduct.add(numberProduct, BorderLayout.SOUTH);//Add combo box with number of items available
		transactionPanelCurrentProduct.add(buyButton , BorderLayout.EAST);//Add Buy Button 
		transactionPanel.add(transactionPanelCurrentProduct);
		
		transactionPanelCurrentProduct.repaint();//tell a component to repaint itself.
		transactionPanelCurrentProduct.revalidate();// tell the layout manager to reset based on the new component list
		
		/*Make Panels visible or not*/
		productPanel.setVisible(true);
		customerPanel.setVisible(true);
	    transactionPanel.setVisible(true);
	}
	
	private void DisplayCustomerArea() {//Display contents in Customer Area

		/** Displays the content in Customer Area
		*/
	      
	      customerPanelSearchFirstName.removeAll();//Remove all elements from cutomerPanelSearchFirstName to be empty
	      
	      customerPanelSearchFirstName.add(new JLabel("First name"), BorderLayout.NORTH);//Add text to Panel
		  customerPanelSearchFirstName.add(customerFirstName, BorderLayout.CENTER);//Add Text to fill to Panel
		  customerPanelSearch.add(customerPanelSearchFirstName);
		  
		  customerPanelSearchFirstName.repaint();//tell a component to repaint itself.
		  customerPanelSearchFirstName.revalidate();// tell the layout manager to reset based on the new component list
		  
		  customerPanelSearchLastName.removeAll();//Remove all elements from cutomerPanelSearchLastName to be empty
		  
		  customerPanelSearchLastName.add(new JLabel("Last name"), BorderLayout.NORTH);//Add text to Panel
		  customerPanelSearchLastName.add(customerLastName, BorderLayout.CENTER);//Add text to fill to Panel
		  
		  customerPanelSearch.add(customerPanelSearchLastName);
		  customerPanelSearch.add(customerSearchButton);
		  
		  customerPanelSearchLastName.repaint();//tell a component to repaint itself.
		  customerPanelSearchLastName.revalidate();// tell the layout manager to reset based on the new component list
		  
		  customerPanelInfo.removeAll();//Remove all elements from cutomerPanelInfo to be empty
		  
		  customerPanelInfo.add(new JLabel("Chosen Client's information"), BorderLayout.NORTH);//Add text to Panel
		  customerPanelInfo.add(customerInfoLabel, BorderLayout.CENTER);//Add text field to Panel
		  customerPanel.add(customerPanelSearch);
		  customerPanel.add(customerPanelInfo);
		  
		  customerPanelInfo.repaint();//tell a component to repaint itself.
		  customerPanelInfo.revalidate();// tell the layout manager to reset based on the new component list
		
	}
	
	private Product getSelectedProduct(int index) {//Get selected product according to the index
			
		/** Gets the selected product according to the index
		* @params index which is the index of the selected product from list
		* @returns p which is the selected product*/

		Product p = null;
		
		switch (category) {//Depending on the category
		/*Retrieve product*/
		case "DVD"://if it's DVD category
			p = store.GetDVDFromProduct().get(index);
		break;
		
		case "Game"://If it's Game category
			p = store.GetGameFromProduct().get(index);
		break;
		
		case "Book"://If it's  Book category
			p= store.GetBookFromProduct().get(index);
		break;
		
		case "All"://If it's all category
			p= store.GetProductList().get(index);
		break;
		}
		
		return p;//returns selected product
	}
	
	private DefaultListModel<String> GetListProduct(){//Get list of Product to display in a JList

		/** Gets the list of Products be to display in a JList
		* @returns l, the list of products 
		*/

		List <Product> listProduct = store.GetProductList();//Get the list of product from the store
		DefaultListModel<String> l = new DefaultListModel<String>();
		int index = 0;
		
		for (int i=0; i<listProduct.size(); i++) {//Go through the list of Products until the end
			if(listProduct.get(i).getStock() != 0) {//If the product still have available items to buy, add it to the list to return
				if(listProduct.get(i) instanceof Book) {//If it's a Book
					l.add(index, "Category: Book ------------------- Name: "+listProduct.get(i).getName()+" ------------------- Price: "+listProduct.get(i).getPrice()+"  \u20ac");
					index ++;
				}
				
				if(listProduct.get(i) instanceof DVD) {//If it's a DVD
					l.add(index, "Category: DVD ------------------- Name: "+listProduct.get(i).getName()+" ------------------- Price: "+listProduct.get(i).getPrice()+"  \u20ac");
					index ++;
				}
				
				if(listProduct.get(i) instanceof Game) {//If it's a Game
					l.add(index, "Category: Game ------------------- Name: "+listProduct.get(i).getName()+" ------------------- Price: "+listProduct.get(i).getPrice()+"  \u20ac");
					index ++;
				}
			}
		}
		
		return l;
	}
	
	private DefaultListModel<String>  GetListDVD(){//Get list of DVDs to display in a JList

		/** Gets the list of DVDs to display in a JList
		* @returns l, the list of DVDs to be displayed in a JList*/

		List <Product> listProduct = store.GetDVDFromProduct();//Get list of DVDs from the store
		DefaultListModel<String> l = new DefaultListModel<String>();
		int index = 0;
		
		for (int i=0; i<listProduct.size(); i++) {//Go through the list of DVDs until the end
			if(listProduct.get(i).getStock() != 0) {//If the DVD still have available items to buy, add it to the list to return
				l.add(index, "Category: DVD ------------------- Name: "+listProduct.get(i).getName()+" ------------------- Price: "+listProduct.get(i).getPrice()+"  \u20ac");
				index ++;
			}
		}
		
		return l;
	}
	
	private DefaultListModel<String>  GetListGame(){//Get list of Games to display in a JList

		/** Gets the list of Games to display in a JList
		* @returns l, the list of Games to display in a JList*/

		List <Product> listProduct = store.GetGameFromProduct();//Get list of Games from the store
		DefaultListModel<String> l = new DefaultListModel<String>();
		int index = 0;
		
		for (int i=0; i<listProduct.size(); i++) {//Go through the list of Games until the end
			if(listProduct.get(i).getStock() != 0) {//If the Games still have available items to buy, add it to the list to return
				l.add(index, "Category: Game ------------------- Name: "+listProduct.get(i).getName()+" ------------------- Price: "+listProduct.get(i).getPrice()+"  \u20ac");
				index ++;
			}
		}
		
		return l;
	}
	
	private DefaultListModel<String>  GetListBook(){//Get list of Books to display in a JList

		/** Gets the list of Books to display in a JList
		* @returns l, list of Books to display in a JList
		*/
		
		List <Product> listProduct = store.GetBookFromProduct();//Get list of Books from the store
		DefaultListModel<String> l = new DefaultListModel<String>();
		int index = 0;
		
		for (int i=0; i<listProduct.size(); i++) {//Go through the list of Books until the end
			if(listProduct.get(i).getStock() != 0) {//If the Games still have available items to buy, add it to the list to return
				l.add(index, "Category: Book ------------------- Name: "+listProduct.get(i).getName()+" ------------------- Price: "+listProduct.get(i).getPrice()+"  \u20ac");
				index ++;
			}
		}
		
		return l;
	}

	public static void main(String[] args) {

	/** Runs the Graphical User Interface*/
		new VirtualStoreGUI();//run the Graphical User Interface
	}
	
}
