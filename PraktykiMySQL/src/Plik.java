
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.com.bytecode.opencsv.CSVReader;

public class Plik {
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/druga_baza";

	//user and password
	static final String USER = "nazwa_user";
	static final String PASS = "haslo_usera";
	   
	public static void main(String[] args) throws IOException 
	{
		CSVReader reader = new CSVReader(new FileReader("firmybudownictwo.csv"));
	    String[] row;
	    String str = "";
	    while ((row = reader.readNext()) != null) 
	    {
	    	for (int i = 0; i < row.length; i++) 
	        {
	            if(i==0)str=str+row[i];
	            else str=str+""+row[i];
	        }
	    	str=str +"\r\n";
	    }
	    str = str.replaceAll(";\r\n","'),\r\n('");
	    int dlugosc = str.length() - 7;
	    str= str.substring(0, dlugosc);
	    str = str.replaceAll(";" , "','");
	    str = str + ";"; 
	    int pom=str.indexOf("regon'),");
	    str=str.substring(pom+10);
		System.out.println(str);
		
		
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
			"VALUES"+str;               
			
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








