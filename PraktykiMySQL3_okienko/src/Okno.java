import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;

import com.csvreader.CsvReader;


public class Okno extends JFrame
{
		//JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		static final String DB_URL = "jdbc:mysql://localhost/druga_baza";

		//user and password
		static final String USER = "user";
		static final String PASS = "password";
		
		private static final int WIDTH = 400;
	    private static final int HEIGHT = 300;
	     
	    private JLabel labelTekst;
	    private JTextField textfieldPoleTekstowe;
	    private JButton importuj, wyjdz;
	     
	    //Button handlers:
	    private ImportujButtonHandler cbHandler;
	    private wyjdzButtonHandler ebHandler;
	    
	    public String nazwaPliku;
	     
	    public Okno()
	    {
	        labelTekst = new JLabel("Wpisz nazwe pliku: ", SwingConstants.RIGHT);
	        textfieldPoleTekstowe = new JTextField(10);
	      	         
	        //SPecify handlers for each button and add (register) ActionListeners to each button.
	        importuj = new JButton("Importuj");
	        cbHandler = new ImportujButtonHandler();
	        importuj.addActionListener(cbHandler);
	        wyjdz = new JButton("Wyjdz");
	        ebHandler = new wyjdzButtonHandler();
	        wyjdz.addActionListener(ebHandler);
	         
	        setTitle("Okienko");
	        Container pane = getContentPane();
	        pane.setLayout(new GridLayout(4, 2));
	         
	        //Add things to the pane in the order you want them to appear (left to right, top to bottom)
	        pane.add(labelTekst);
	        pane.add(textfieldPoleTekstowe);

	        pane.add(importuj);
	        pane.add(wyjdz);
	         
	        setSize(WIDTH, HEIGHT);
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	     
	    
	    //importuj button
	    private class ImportujButtonHandler implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	//pobieranie nazwy pliku
	            nazwaPliku = textfieldPoleTekstowe.getText();
	            
	            //begin pobieranie danych z pliku
	            String str="";String str2="";
	            String[] rekord = new String[1000000];
	            for(int i=0;i<rekord.length;i++) rekord[i]=new String();
	            int iloscKolumn=0,iloscWhile=0,iloscRekordow=0;
	            CSVReader reader;
				try {
					reader = new CSVReader(new FileReader(nazwaPliku));
		    	    String[] row;
		    	    try 
		    	    {
						while ((row = reader.readNext()) != null) 
						{
							for (int i = 0; i < row.length; i++) 
						    {
								if(iloscWhile==0) iloscKolumn++;
								rekord[iloscRekordow]=row[i];
								//System.out.print(iloscRekordow+" "+rekord[iloscRekordow]+"; ");
								iloscRekordow++;
						    }
							iloscWhile++;
							System.out.println();
						    //break;
						}
					}catch(IOException e1) {e1.printStackTrace();}
				}catch(FileNotFoundException e3) {e3.printStackTrace();}
				//end pobieranie danych z pliku
				

//---------------WRZUCANIE DANYCH DO BAZY DANYCH----------------------------------------------	
    	    
	    		Connection conn = null;
	    		Statement stmt = null;
	    		try
	    		{
	    			//Register JDBC driver
	    			Class.forName("com.mysql.jdbc.Driver");
	    			
	    			//Open a connection
	    			conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    			System.out.println("Connection to database succeed.");
	    			
	    			//Execute a query
	    			System.out.println("Creating statement:");
	    			stmt = conn.createStatement();
	    			String sql="";
	    			
	    			//nazwa pliku
	    			String nazwaTabeli= nazwaPliku.substring(0, nazwaPliku.length()-4);

	    			
	    			//begin create table
	    			System.out.println(iloscKolumn);
	    			sql = "CREATE TABLE "+nazwaTabeli+" (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ";
	    	        for(int i=0;i<iloscKolumn;i++){
	    	        	rekord[i]=rekord[i].replaceAll(" ", "_");
	    	        	if((i<iloscKolumn-1))sql=sql+rekord[i]+" VARCHAR(300), ";
	    	        	if(i==iloscKolumn-1)sql=sql+rekord[i]+" VARCHAR(300));";
	    	        }
	    	        System.out.println(sql);
	    			stmt.executeUpdate(sql);
	    			//end create table
	    			
	    			//begin insert
	    			sql = "INSERT INTO "+nazwaTabeli+" (";
	    			
	    			for(int i=0;i<iloscKolumn;i++){
		    	       	rekord[i]=rekord[i].replaceAll(" ", "_");
		    	       	if(i<iloscKolumn-1)sql=sql+rekord[i]+", ";
		    	       	if(i==iloscKolumn-1)sql=sql+rekord[i]+") VALUES ('";
		    	    }
	    			System.out.println(sql);
	    			int j;
	    			for(int i=iloscKolumn;i<iloscRekordow-1;i++)
	    			{
	    				for(j=0;j<iloscKolumn;j++){
		    				if(j!=iloscKolumn-1){
		    					str=str+rekord[i+j]+"','";
		    				}
		    				if(j==iloscKolumn-1){
			    	        	str=str+rekord[i+j]+"');";
			    	        	str2=sql+str;
			    	        	stmt.executeUpdate(str2);
			    	        	System.out.println(i+"\t"+str2);
			    	        	str2="";str="";
			    	        }
	    				}
	    				i=i+j;
	    			}
	    			//end insert	

	    			//Clean-up environment
	    			stmt.close();
	    			conn.close();
	    		}
	    		catch(SQLException se){	se.printStackTrace();}//Handle errors for JDBC
	    		catch(Exception ex){  ex.printStackTrace();}//Handle errors for Class.forName
	    		finally
	    		{//finally block used to close resources
	    			try{if(stmt!=null) stmt.close();}catch(SQLException se2){}// nothing we can do
	    			try{if(conn!=null) conn.close();}catch(SQLException se){se.printStackTrace();}//end try        
	    		}//end finally
	    	   
	    		//napis końcowy
	    		System.out.println("kk");     
	        }
	    }
	     
	    
	    
	    
	    //wyjscie button
	    public class wyjdzButtonHandler implements ActionListener{
	        public void actionPerformed(ActionEvent e){
	            System.exit(0);
	        }
	    }
	    
	    
	   
	    //main
	    public static void main(String[] args){
	        Okno rectObj = new Okno();
	    }
	    	
}
	
	
	
	
	
	