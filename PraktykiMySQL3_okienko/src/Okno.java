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
	     
	    private class ImportujButtonHandler implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	            nazwaPliku = textfieldPoleTekstowe.getText();
	            String str="";String str2="";
	            String[] rekord = new String[100000];
	            for(int i=0;i<rekord.length;i++) rekord[i]=new String();
	            int iloscKolumn=0,iloscWhile=0,iloscRekordow=0;
	            CSVReader reader;
				try {
					reader = new CSVReader(new FileReader(nazwaPliku));
				
		    	    String[] row;
		    	   
		    	    try {
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
						//System.out.print(iloscKolumn);
						//firmybudownictwo.csv
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	            

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
	    			
	    			String nazwaTabeli= nazwaPliku.substring(0, nazwaPliku.length()-4);
// niedziała	drop    			
//	    			sql="DROP TABLE "+nazwaPliku;
//	    			stmt.executeUpdate(sql);
//	    			System.out.println("Table  deleted in given database...");
	    			

	    			
//	    			sql = "CREATE TABLE "+nazwaTabeli+" (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ";
//	    	        for(int i=0;i<iloscKolumn;i++){
//	    	        	rekord[i]=rekord[i].replaceAll(" ", "_");
//	    	        	if(i<iloscKolumn-1)sql=sql+rekord[i]+" VARCHAR(300), ";
//	    	        	if(i==iloscKolumn-1)sql=sql+rekord[i]+" VARCHAR(300));";
//	    	        }
//	    	        System.out.println(sql);
//	    			stmt.executeUpdate(sql);
	    			
	    			//begin insert
	    			sql = "INSERT INTO "+nazwaTabeli+" (";
	    			//firmy (nazwa_firmy, wojewodztwo, miejscowosc, ulica, kod_pocztowy, osoba_kontaktowa, telefon, tel_kom, adres_www, nip, regon, zatrudnienie) VALUES ";               
	    			
	    			 for(int i=0;i<iloscKolumn;i++){
		    	        	rekord[i]=rekord[i].replaceAll(" ", "_");
		    	        	if(i<iloscKolumn-1)sql=sql+rekord[i]+", ";
		    	        	if(i==iloscKolumn-1)sql=sql+rekord[i]+") VALUES ('";
		    	     }
	    			System.out.println(sql);

	    			for(int i=iloscKolumn;i<iloscRekordow;i++)
	    			{
	    				if((i%iloscKolumn!=0)){//&(i==iloscKolumn)){
	    					str=str+rekord[i]+"','";System.out.println("cos");
	    				}
	    				else if((i%iloscKolumn==0)){
		    	        	str=str+rekord[i]+"');";
		    	        	str2=sql+str;
		    	        	//stmt.executeUpdate(str2);
		    	        	System.out.println(i+"\t"+str2);
		    	        	str2="";str="";
		    	        }
	    				
	    			}
	    			
	    			//end insert
	    			
	    			
//	    			//begin select
//	    			sql = "SELECT * FROM firmy";
//	    			ResultSet rs = stmt.executeQuery(sql);
//	    
//	    			
//	    			//Extract data from result set
//	    			while(rs.next())
//	    			{
//	    				//Retrieve by column name
//	    				int id  = rs.getInt("id");
//	    				String nazwa_firmy = rs.getString("nazwa_firmy");
//	    				String wojewodztwo = rs.getString("wojewodztwo");
//	    				String miejscowosc = rs.getString("miejscowosc");
//	    				String ulica = rs.getString("ulica");
//	    				String kod_pocztowy = rs.getString("kod_pocztowy");
//	    				String osoba_kontaktowa = rs.getString("osoba_kontaktowa");
//	    				String telefon = rs.getString("telefon");
//	    				String tel_kom = rs.getString("tel_kom");
//	    				String adres_www = rs.getString("adres_www");
//	    				String nip = rs.getString("nip");
//	    				String regon = rs.getString("regon");
//	    				String zatrudnienie = rs.getString("zatrudnienie");
//	    
//	    				//Display values
//	    				
//	    				
//	    				System.out.print(id);
//	    				System.out.print("\t" + nazwa_firmy);
//	    				System.out.print("\t" + wojewodztwo);
//	    				System.out.print("\t" + miejscowosc);
//	    				System.out.print("\t" + ulica);
//	    				System.out.print("\t" + kod_pocztowy);
//	    				System.out.print("\t" + osoba_kontaktowa);
//	    				System.out.print("\t" + telefon);
//	    				System.out.print("\t" + tel_kom);
//	    				System.out.print("\t" + adres_www);
//	    				System.out.print("\t" + nip);
//	    				System.out.print("\t" + regon);
//	    				System.out.println("\t" + zatrudnienie);
//	    
//	    			
//	    			
//	    			
//	    			}//end while 
//	    			//end select
//	    			
//	    			//Clean-up environment
//	    			rs.close();
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
	    	   
	    		
	    	   
	    	   
	    	   System.out.println("kk");     
	        }
	    }
	     
	    
	    
	    
	    //wyjscie
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
	
	
	
	
	
	