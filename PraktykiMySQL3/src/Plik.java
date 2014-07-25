
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

import com.csvreader.CsvReader;


public class Plik {
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/druga_baza";

	//user and password
	static final String USER = "password";
	static final String PASS = "password";

	
	public static void main(String[] args) throws IOException 
	{
		
//--------------WCZYTYWANIE STRINGA Z PLIKU DO TABLICY OBIEKTÓW-------------------------------		
		Firmy[] firmy = new Firmy[562];
        for(int i=0;i<firmy.length;i++) firmy[i]=new Firmy();
		
        String str = "";
	    String str2 = "";
	    String bledyPrzyWalidacji = "";
	    int zlicz=0;
        
        CsvReader csvReader = new CsvReader("firmybudownictwo.csv");
		
		csvReader.readHeaders();

		while (csvReader.readRecord())
		{			
			firmy[zlicz].nazwa_firmy=csvReader.get("nazwa firmy");				System.out.println(firmy[zlicz].nazwa_firmy);
	    	firmy[zlicz].wojewodztwo=csvReader.get("wojewodztwo");				System.out.println(firmy[zlicz].wojewodztwo);
	    	firmy[zlicz].miejscowosc=csvReader.get("miejscowosc");				System.out.println(firmy[zlicz].miejscowosc);
	    	firmy[zlicz].ulica=csvReader.get("ulica");							System.out.println(firmy[zlicz].ulica);
	    	firmy[zlicz].kod_pocztowy=csvReader.get("kod pocztowy");			System.out.println(firmy[zlicz].kod_pocztowy);
	    	firmy[zlicz].osoba_kontaktowa=csvReader.get("osoba kontaktowa");	System.out.println(firmy[zlicz].osoba_kontaktowa);       
	    	firmy[zlicz].telefon=csvReader.get("telefon");						System.out.println(firmy[zlicz].telefon);
	    	firmy[zlicz].tel_kom=csvReader.get("tel. kom.");					System.out.println(firmy[zlicz].tel_kom);
	    	firmy[zlicz].adres_www=csvReader.get("adres www");					System.out.println(firmy[zlicz].adres_www);
	    	firmy[zlicz].nip=csvReader.get("nip");								System.out.println(firmy[zlicz].nip);
	    	firmy[zlicz].regon=csvReader.get("regon");							System.out.println(firmy[zlicz].regon);
	    	firmy[zlicz].zatrudnienie=csvReader.get("zatrudnienie");			System.out.println(firmy[zlicz].zatrudnienie);
	    	    	
			//System.out.println("\n"+zlicz+"\n");
			zlicz++;
		}

		csvReader.close();
		
		
        bledyPrzyWalidacji = "powód błędu,nazwa_firmy,wojewodztwo,"+
							"miejscowosc,ulica,"+
							"kod_pocztowy,osoba_kontaktowa,"+
							"telefon,tel_kom,"+
							"adres_www,nip,"+
							"regon,zatrudnienie,\r\n";
        
        
//----------------SPRAWDZANIE KODÓW POCZTOWYCH--------------------------------------------------------	    
        System.out.println("----------------SPRAWDZANIE KODÓW POCZTOWYCH--------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	//if(firmy[i].kod_pocztowy.equals(null))break;
	    	Pattern pattern = Pattern.compile("(([0-9]{2}-[0-9]{3})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].kod_pocztowy);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zły kod pocztowy,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].kod_pocztowy);
		    		firmy[i].kod_pocztowy="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)
	 
	    
	    
	    
//----------------SPRAWDZANIE STRON WWW--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE STRON WWW---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("((.{1,}\\.[a-z]{2,5})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].adres_www);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zły adres www,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].adres_www);
		    		firmy[i].adres_www="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)	    

	    
	    
	    
//----------------SPRAWDZANIE WOJEWODZTWA--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE WOJEWODZTWA---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("(([\\-a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].wojewodztwo);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "złe województwo,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].wojewodztwo);
		    		firmy[i].wojewodztwo="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)		    
	    
	    
	    
	    
	    
//----------------SPRAWDZANIE MIASTA--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE MIASTA---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("(([\\-a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].miejscowosc);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "złe miasto,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].miejscowosc);
		    		firmy[i].miejscowosc="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)		    
	    

	    
	    
	    
	    
	    
//----------------SPRAWDZANIE ULICY--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE ULICY---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("((([\\ \\.\\'\\-a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,}))|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].ulica);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zła ulica,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].ulica);
		    		firmy[i].ulica="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)	
	    
	    
	    
	    
	    
	    
//----------------SPRAWDZANIE OSOBY KONTAKTOWEJ--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE OSOBY KONTAKTOWEJ---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("((([\\ \\.\\\\-a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{1,}))|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].osoba_kontaktowa);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zła osoba kontaktowa,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].osoba_kontaktowa);
		    		firmy[i].osoba_kontaktowa="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)	
	    
	    
	    
	    
	    
	    
//----------------SPRAWDZANIE TELEFON--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE TELEFON---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("(([\\(\\)\\ \\+\\-0-9]{7,})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].telefon);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zły telefon,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].telefon);
		    		firmy[i].telefon="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)		
	    
	    
	    
	    
	    
	    
//----------------SPRAWDZANIE TELEFON KOMÓRKOWEGO--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE TELEFON KOMÓRKOWEGO---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("(([\\(\\)\\ \\+\\-0-9]{9,})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].tel_kom);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zły tel kom,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].tel_kom);
		    		firmy[i].tel_kom="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)		
	    
	    
	    
	    
	    
	    
	    
	    
//----------------SPRAWDZANIE NIP--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE NIP---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("(([0-9]{10})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].nip);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zły nip,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].nip);
		    		firmy[i].nip="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)	
	    
	    
	    

	    
	    
	    
//----------------SPRAWDZANIE REGON--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE REGON---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("(([0-9]{9})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].regon);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "zły regon,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].regon);
		    		firmy[i].regon="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)	
	    
	    
	    
	    
	    
	    
//----------------SPRAWDZANIE ZATRUDNIONYCH--------------------------------------------------------	    
	    System.out.println("----------------SPRAWDZANIE ZATRUDNIONYCH---------------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	Pattern pattern = Pattern.compile("(([0-9]{1,}-[0-9]{1,})|(\\A\\z))");
	    	Matcher matcherpattern = pattern.matcher(firmy[i].zatrudnienie);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (!found){
		    	do{
		    		bledyPrzyWalidacji = bledyPrzyWalidacji + "złe zatrudnienie,"+
		    				firmy[i].nazwa_firmy+","+firmy[i].wojewodztwo+","+
							firmy[i].miejscowosc+","+firmy[i].ulica+","+
							firmy[i].kod_pocztowy+","+firmy[i].osoba_kontaktowa+","+
							firmy[i].telefon+","+firmy[i].tel_kom+","+
							firmy[i].adres_www+","+firmy[i].nip+","+
							firmy[i].regon+","+firmy[i].zatrudnienie+",\r\n";
		    		System.out.println(i+"\t"+firmy[i].zatrudnienie);
		    		firmy[i].zatrudnienie="";
		    		
		    	}while(matcherpattern.find());
		    }
		    
	    }//end:for(int i=0;i<firmy.length;i++)	
	    
	    
	    
	    
	    
	    
	    
//--------------ZAPISYWANIE BŁĘDNYCH WIERSZY DO PLIKU------------------------------------------
	    
	    FileWriter f= new FileWriter("niewczytane.csv");
		f.write((bledyPrzyWalidacji));
		f.close(); 
	    
	    
		//System.out.println(bledyPrzyWalidacji);
	    
	    

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
			
			//begin insert
			sql = "INSERT INTO firmy (nazwa_firmy, wojewodztwo, miejscowosc, ulica, kod_pocztowy, osoba_kontaktowa, telefon, tel_kom, adres_www, nip, regon, zatrudnienie) VALUES ";               
			
			for(int i=1;i<zlicz;i++)
			{
				str="('"+firmy[i].nazwa_firmy+"','"+firmy[i].wojewodztwo+"',"+
						"'"+firmy[i].miejscowosc+"','"+firmy[i].ulica+"',"+
						"'"+firmy[i].kod_pocztowy+"','"+firmy[i].osoba_kontaktowa+"',"+
						"'"+firmy[i].telefon+"','"+firmy[i].tel_kom+"',"+
						"'"+firmy[i].adres_www+"','"+firmy[i].nip+"',"+
						"'"+firmy[i].regon+"','"+firmy[i].zatrudnienie+"');";
				str2=sql+str;
				stmt.executeUpdate(str2);
				System.out.println("insert:\t"+i);	
			}
			
			//end insert
			
			
			//begin select
			sql = "SELECT * FROM firmy";
			ResultSet rs = stmt.executeQuery(sql);

			
			//Extract data from result set
			while(rs.next())
			{
				//Retrieve by column name
				int id  = rs.getInt("id");
				String nazwa_firmy = rs.getString("nazwa_firmy");
				String wojewodztwo = rs.getString("wojewodztwo");
				String miejscowosc = rs.getString("miejscowosc");
				String ulica = rs.getString("ulica");
				String kod_pocztowy = rs.getString("kod_pocztowy");
				String osoba_kontaktowa = rs.getString("osoba_kontaktowa");
				String telefon = rs.getString("telefon");
				String tel_kom = rs.getString("tel_kom");
				String adres_www = rs.getString("adres_www");
				String nip = rs.getString("nip");
				String regon = rs.getString("regon");
				String zatrudnienie = rs.getString("zatrudnienie");

				//Display values
				
				
				System.out.print(id);
				System.out.print("\t" + nazwa_firmy);
				System.out.print("\t" + wojewodztwo);
				System.out.print("\t" + miejscowosc);
				System.out.print("\t" + ulica);
				System.out.print("\t" + kod_pocztowy);
				System.out.print("\t" + osoba_kontaktowa);
				System.out.print("\t" + telefon);
				System.out.print("\t" + tel_kom);
				System.out.print("\t" + adres_www);
				System.out.print("\t" + nip);
				System.out.print("\t" + regon);
				System.out.println("\t" + zatrudnienie);

			
			
			
			}//end while 
			//end select
			
			//Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}
		catch(SQLException se){	se.printStackTrace();}//Handle errors for JDBC
		catch(Exception e){  e.printStackTrace();}//Handle errors for Class.forName
		finally
		{//finally block used to close resources
			try{if(stmt!=null) stmt.close();}catch(SQLException se2){}// nothing we can do
			try{if(conn!=null) conn.close();}catch(SQLException se){se.printStackTrace();}//end try        
		}//end finally
	   
		
	   
	   
	   System.out.println("kk");
	}//end main
}








