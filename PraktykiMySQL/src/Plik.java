
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;

public class Plik {
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/druga_baza";

	//user and password
	static final String USER = "user";
	static final String PASS = "password";

	
	public static void main(String[] args) throws IOException 
	{
		CSVReader reader = new CSVReader(new FileReader("firmybudownictwo.csv"));
	    String[] row;
	    String str = "";
	    String str2 = "('";
	    int zlicz=0,zlicz2=0;
	    while ((row = reader.readNext()) != null) 
	    {
	    	for (int i = 0; i < row.length; i++) 
	        {
	            if(i==0)str=str+row[i];
	            else str=str+""+row[i];
	        }
	    	str=str +"\r\n";zlicz++;
	    }
	    String sprawdz = str;
	    //                          nazwafirmy;   wojewodztwo;                           miasto;                                adres;                                       kod pocztowy;            osoba kontaktow;                       telefon;                       tel kom;           adres www;                                    nip;       regon;    zatrudnionych;
		Pattern pattern = Pattern.compile(".{1,};[\\-\\s\\.a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{0,};[\\-\\s\\.a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{0,};[\\-\\s\\.\\/a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ0-9]{0,};([0-9]{2}-[0-9]{3}){0,};[\\-\\s\\.a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{0,};[\\/\\(\\)\\.\\+\\s\\-0-9]{0,};[\\/\\(\\)\\.\\+\\s\\-0-9]{0,};([\\-\\s\\.a-zA-Z0-9]{1,}\\.[a-z]{2,3}){0,1};[0-9]{0,};[0-9]{0,};([0-9]{1,}-[0-9]{1,}){0,};\r\n");
		Matcher matcherpattern = pattern.matcher(sprawdz);
	    matcherpattern.reset();
	    boolean found = matcherpattern.find();
	    if (found){
	    	do{
	    		str2 = str2 + matcherpattern.group();
	    		str = str.replace(matcherpattern.group(),"");//pasujące usuwamy tak aby w str zostały tylko nie pasujące 
	    		zlicz2++;
	    	}while(matcherpattern.find());
	    }

	    System.out.println(zlicz+"\t"+zlicz2);
	    str2 = str2.replaceAll(";\r\n","'),\r\n('");
	    int dlugosc = str2.length() - 5;
	    str2= str2.substring(0, dlugosc);
	    str2 = str2.replaceAll(";" , "','");
	    str2 = str2 + ";";
	  //  System.out.println(str);
		
	    FileWriter f= new FileWriter("niewczytane.csv");
		f.write((str));
		f.close();
		
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
			sql = "INSERT INTO firmy (nazwa_firmy, wojewodztwo, miejscowosc, ulica, kod_pocztowy, osoba_kontaktowa, telefon, tel_kom, adres_www, nip, regon, zatrudnienie)"+
			"VALUES"+str2;               
			
			stmt.executeUpdate(sql);
			System.out.println("insert");	
			//end insert
			
			
			//begin select
			sql = "SELECT * FROM firmy";
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.print("id\t"+"nazwa_firmy\t"+"wojewodztwo\t"+"miejscowosc\t"+"ulica\t");
			System.out.print("\tkod_pocztowy\t"+"osoba_kontaktowa\t"+"telefon\t"+"\ttel_kom\t");
			System.out.println("\tadres_www\t"+"nip\t"+"regon\t"+"zatrudnienie\t");
			
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








