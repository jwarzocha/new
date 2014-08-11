import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;

import java.io.File;
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
		
		private static final int WIDTH = 1200;
	    private static final int HEIGHT = 400;
	     
	    private JLabel labelTekst, labelNazwa, labelCSV, labelSQL;
	    private JTextField textfieldPoleTekstowe, textfieldNazwaPliku;
	    private JTextField textfieldCSV, textfieldSQL;
	    private JButton importuj, wyjdz, wybierz, pokazCSV, pokazSQL, przyporzadkuj;
	     
	    //Button handlers:
	    private ImportujButtonHandler cbHandler;
	    private wyjdzButtonHandler ebHandler;
	    private wybierzButtonHandler chHandler;
	    private csvButtonHandler csvHandler;
	    private sqlButtonHandler sqlHandler;
	    private przyButtonHandler przyHandler;
	    
	    public static String nazwaPliku;
	    public static String sciezkaPliku;
	     
	    public Okno()
	    {
	        labelTekst = new JLabel("Wybierz lub wpisz \r\nściezke pliku: ", SwingConstants.RIGHT);
	        textfieldPoleTekstowe = new JTextField(10);
	        
	        labelNazwa = new JLabel("Wpisz nazwe tabeli: ", SwingConstants.RIGHT);
	        textfieldNazwaPliku = new JTextField(10);
	        
	        labelCSV = new JLabel("CSV: ", SwingConstants.RIGHT);
	        textfieldCSV = new JTextField(10);
	        
	        labelSQL = new JLabel("SQL: ", SwingConstants.RIGHT);
	        textfieldSQL = new JTextField(10);
	      	         
	        //SPecify handlers for each button and add (register) ActionListeners to each button.
	        importuj = new JButton("Importuj Dane");
	        cbHandler = new ImportujButtonHandler();
	        importuj.addActionListener(cbHandler);
	        
	        wyjdz = new JButton("Wyjdz");
	        ebHandler = new wyjdzButtonHandler();
	        wyjdz.addActionListener(ebHandler);
	        
	        wybierz = new JButton("Wybierz Plik");
	        chHandler = new wybierzButtonHandler();
	        wybierz.addActionListener(chHandler);
	        
	        pokazCSV = new JButton("Pokaż kolumny csv");
	        csvHandler = new csvButtonHandler();
	        pokazCSV.addActionListener(csvHandler);
	        
	        pokazSQL = new JButton("Pokaż kolumny sql");
	        sqlHandler = new sqlButtonHandler();
	        pokazSQL.addActionListener(sqlHandler);

	        przyporzadkuj = new JButton("Przyporządkuj kolumny");
	        przyHandler = new przyButtonHandler();
	        przyporzadkuj.addActionListener(przyHandler);
	        
	        setTitle("Okienko");
	        Container pane = getContentPane();
	        pane.setLayout(new GridLayout(7, 2));
	         
	        //Add things to the pane in the order you want them to appear (left to right, top to bottom)
	        pane.add(labelTekst);
	        pane.add(textfieldPoleTekstowe);
	        
	        pane.add(labelNazwa);
	        pane.add(textfieldNazwaPliku);
	        
	        pane.add(labelCSV);
	        pane.add(textfieldCSV);
	        
	        pane.add(labelSQL);
	        pane.add(textfieldSQL);

	        pane.add(importuj);
	        pane.add(wyjdz);
	        pane.add(wybierz);
	        pane.add(pokazCSV);
	        pane.add(pokazSQL);
	        pane.add(przyporzadkuj);
	        
	        setSize(WIDTH, HEIGHT);
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	    
	    
	    public static String[] rekord = new String[1000000];
        public static int iloscKolumn=0,iloscWhile=0,iloscRekordow=0;
	    
	    

	    //importuj button
	    private class ImportujButtonHandler implements ActionListener
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	//pobieranie nazwy pliku
	            sciezkaPliku = textfieldPoleTekstowe.getText();
	            nazwaPliku = textfieldNazwaPliku.getText();

	            for(int i=0;i<rekord.length;i++) rekord[i]=new String();
	            
	            //begin pobieranie danych z pliku
	            String str="";String str2="";
	            iloscKolumn = 0;
	            iloscWhile = 0;
	            iloscRekordow = 0;
	            CSVReader reader;
				try {
					reader = new CSVReader(new FileReader(sciezkaPliku));
		    	    String[] row;
		    	    try 
		    	    {
						while ((row = reader.readNext()) != null) 
						{
							for (int i = 0; i < row.length; i++) 
						    {
								if(iloscWhile==0) iloscKolumn++;
								rekord[iloscRekordow]=row[i];
								System.out.print(iloscRekordow+" "+rekord[iloscRekordow]+"; ");
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
	    	    			
	    			
	    			//begin create table
	    			try{
		    			System.out.println(iloscKolumn);
		    			sql = "CREATE TABLE "+nazwaPliku+" (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ";
		    	        for(int i=0;i<iloscKolumn;i++){
		    	        	rekord[i]=rekord[i].replaceAll(" ", "_");
		    	        	if((i<iloscKolumn-1))sql=sql+rekord[i]+" VARCHAR(300), ";
		    	        	if(i==iloscKolumn-1)sql=sql+rekord[i]+" VARCHAR(300));";
		    	        }
		    	        System.out.println(sql);
		    			stmt.executeUpdate(sql);
		    			textfieldSQL.setText("Tabela "+nazwaPliku+" została utworzona.");
	    			}catch(SQLException e4) {textfieldSQL.setText("Taka "+nazwaPliku+" tabela już istnieje w bazie!!!");}
	    			//end create table
			
	    			
	    			
	    			sql="SHOW COLUMNS FROM "+nazwaPliku+";";
	    			ResultSet rs = stmt.executeQuery(sql);
	    			int pom=0;
	    			while(rs.next()){
	    				
	    				String kolumna = rs.getString(1);
	    				Pattern pattern = Pattern.compile("[Kk]od.[Pp]ocztowy");
						Matcher matcherpattern = pattern.matcher(kolumna);
						matcherpattern.reset();
						boolean found = matcherpattern.find();
						if(found){
							System.out.println(pom+"\t"+kolumna);
							break;
						}
	    				pom++;
	    			}
	    		    rs.close();
	    		    
	    		    int a;
	    			for(int i=iloscKolumn;i<iloscRekordow-1;i++)
	    			{
	    				for(a=0;a<iloscKolumn;a++){
		    				
		    				if(a==pom-1){
			    	        	Pattern patternKod = Pattern.compile("[0-9]{2}-[0-9]{3}");
								Matcher matcherpatternKod = patternKod.matcher(rekord[i+a]);
								matcherpatternKod.reset();
								boolean foundKod = matcherpatternKod.find();
								if(!foundKod){
									System.out.println(rekord[i+a]);
									rekord[i+a]="";
								}
		    				
		    				}
	    				}
	    				i=i+a;
	    			}
	    		    
	    		    
	    			
	    			//begin insert
	    			sql = "INSERT INTO "+nazwaPliku+" (";
	    			
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
	    			textfieldSQL.setText("Wstawianie do "+nazwaPliku+" zakończone pomyślnie.");
	    			stmt.close();
	    			conn.close();
	    		}
	    		catch(SQLException se){textfieldSQL.setText("Sprawdź połączenie z bazą danych.");}//Handle errors for JDBC
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
	    
	    //wybierz button
	    public class wybierzButtonHandler implements ActionListener{
	        public void actionPerformed(ActionEvent e){
	        	JFileChooser fileopen = new JFileChooser();
	            FileFilter filter = new FileNameExtensionFilter("c files", "c");
	            fileopen.addChoosableFileFilter(filter);

	            int ret = fileopen.showDialog(null, "Open file");

	            if (ret == JFileChooser.APPROVE_OPTION) {
	              File file = fileopen.getSelectedFile();
	              System.out.println(file);
	              textfieldPoleTekstowe.setText("" + file);
	            }
	        }
	    }
	   
	    public static String[] tabKolCSV = new String[100];
	    //csv button
	    public class csvButtonHandler implements ActionListener{
	        public void actionPerformed(ActionEvent e){
	        	
	        	for(int i=0;i<tabKolCSV.length;i++) tabKolCSV[i]=new String();
	        	iloscKolumn = 0;
	        	
	        	sciezkaPliku = textfieldPoleTekstowe.getText();
	        	String str = "";
	        	CSVReader reader;
				try {
					reader = new CSVReader(new FileReader(sciezkaPliku));
		    	    String[] row;
		    	    try 
		    	    {
						while ((row = reader.readNext()) != null) 
						{
							for (int i = 0; i < row.length; i++) 
						    {
								tabKolCSV[i]=row[i];
								str=str + row[i] + ", ";
								iloscKolumn=i;
						    }
							iloscKolumn = iloscKolumn + 1;
							break;
						}
					}catch(IOException e1) {textfieldCSV.setText("Problem z plikem!!!");}
				}catch(FileNotFoundException e3) {textfieldCSV.setText("Plik nie istnieje!!!");}
				
				textfieldCSV.setText("Kolumny: " + str);
				
	        }
	    }
	    public static String[] tabKolSQL = new String[100];
	    //sql button
	    public class sqlButtonHandler implements ActionListener{
	        public void actionPerformed(ActionEvent e){
	        	
	        	for(int i=0;i<tabKolSQL.length;i++) tabKolSQL[i]=new String();
	        	
	        	nazwaPliku = textfieldNazwaPliku.getText();
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
	    			String sql="SHOW COLUMNS FROM "+nazwaPliku+";";
	    			String pom = "Kolumny: ";
	    			ResultSet rs = stmt.executeQuery(sql);
	    			int i=0;
	    			//STEP 5: Extract data from result set
	    			while(rs.next()){
	    				
	    				String kolumna = rs.getString(1);
	    				tabKolSQL[i]=kolumna;i++;
	    				pom = pom + kolumna +", ";
		    			textfieldSQL.setText(pom);
	    				System.out.print(kolumna);
	    			}
	    		    rs.close();
	    			stmt.close();
	    			conn.close();
	    		}catch(Exception ex){  ex.printStackTrace();}
	        }
	    }

	   
	    public int getiloscKolumn() {return iloscKolumn;}
	    public void setiloscKolumn(int iloscKolumn) {this.iloscKolumn =iloscKolumn;}

	    public int getiloscWhile() {return iloscWhile;}
	    public void setiloscWhile(int iloscWhile) {this.iloscWhile =iloscWhile;}
    		
	    public int getiloscRekordow() {return iloscRekordow;}
	    public void setiloscRekordow(int iloscRekordow) {this.iloscRekordow =iloscRekordow;}
    		
	    public String[] getrekord() {return rekord;}
	    public void setrekord(String[] rekord) {this.rekord =rekord;}
    		
	    public String[] gettabKolCSV() {return tabKolCSV;}
	    public void settabKolCSV(String[] tabKolCSV) {this.tabKolCSV =tabKolCSV;}
    		
	    public String[] gettabKolSQL() {return tabKolSQL;}
	    public void settabKolSQL(String[] tabKolSQL) {this.tabKolSQL =tabKolSQL;}
	    
//	    public String[] rekord = new String[1000000];
	  //przyporzadkuj button
	    public class przyButtonHandler implements ActionListener{
	        public void actionPerformed(ActionEvent e){
	        	Okno2 rectsObj = new Okno2();
	        }
	    }
	    //main
	    public static void main(String[] args){
	        Okno rectObj = new Okno();
	    }

	    	
}
	
	
	
	
	
	