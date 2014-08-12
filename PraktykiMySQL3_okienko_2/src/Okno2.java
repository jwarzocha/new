import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class Okno2 extends JFrame{
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/druga_baza";

	//user and password
	static final String USER = "user";
	static final String PASS = "password";
	
	
	private JLabel labelKolumnaCSV, labelKolumnaSQL, labelPrzyporzadkowanie;
	private JTextField textfieldKolumnaCSV, textfieldKolumnaSQL, textfieldPrzyporzadkowanie;
	private JButton zapisz, wyjdz, wstaw;
	
	private zapiszButtonHandler zapiszHandler;
    private wyjdz2ButtonHandler wyjdzHandler;
    private wstawButtonHandler wstawHandler;
    
    public String kolumnaCSV;
    public String kolumnaSQL;
    public String rezultatPrzyporzadkowania;
    
 
    
	public  Okno2()
    {
		labelKolumnaCSV = new JLabel("Kolumna CSV do przyporządkowania: ", SwingConstants.RIGHT);
		textfieldKolumnaCSV = new JTextField(10);
	        
		labelKolumnaSQL = new JLabel("Kolumna SQL do przyporządkowania: ", SwingConstants.RIGHT);
		textfieldKolumnaSQL = new JTextField(10);
	        
		labelPrzyporzadkowanie = new JLabel("Informacje: ", SwingConstants.RIGHT);
		textfieldPrzyporzadkowanie = new JTextField(10);
        
		zapisz = new JButton("Zapisz zmiany");
		zapiszHandler = new zapiszButtonHandler();
		zapisz.addActionListener(zapiszHandler);
        
        wyjdz = new JButton("Wyjdz");
        wyjdzHandler = new wyjdz2ButtonHandler();
        wyjdz.addActionListener(wyjdzHandler);
        
        wstaw = new JButton("Wstaw do bazy");
        wstawHandler = new wstawButtonHandler();
        wstaw.addActionListener(wstawHandler);
        
        setTitle("Przyporządkowanie kolumn");
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(5, 2));
        
		
        pane.add(labelKolumnaCSV);
        pane.add(textfieldKolumnaCSV);
        
        pane.add(labelKolumnaSQL);
        pane.add(textfieldKolumnaSQL);
        
        pane.add(labelPrzyporzadkowanie);
        pane.add(textfieldPrzyporzadkowanie);
        
        pane.add(zapisz);
        pane.add(wyjdz);
        pane.add(wstaw);
        
        setSize(1200, 300);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
	   //wyjscie button
    public class wyjdz2ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	setVisible(false); 
        	dispose();
        }
    }
    Okno pom= new Okno();
    String rekordyCSV[]= new String[1000000];
    String[] tablicaKolCSV = new String[100];
    String[] tablicaKolSQL = new String[100];  
    int indeksCSV,indeksSQL;
    //zapisz button
    public class zapiszButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	kolumnaCSV = textfieldKolumnaCSV.getText();
        	kolumnaSQL = textfieldKolumnaSQL.getText();
        	
        	//System.out.println(pom.getiloscKolumn()); 
        	for(int i=0;i<pom.getrekord().length;i++){	rekordyCSV[i] = pom.getrekord()[i];}
        	for(int i=0;i<pom.gettabKolCSV().length;i++){	tablicaKolCSV[i] = pom.gettabKolCSV()[i];}
        	for(int i=0;i<pom.gettabKolSQL().length;i++){	tablicaKolSQL[i] = pom.gettabKolSQL()[i];}
        	
        	for(int i=0;i<tablicaKolSQL.length;i++)
        	{	
        		if(tablicaKolSQL[i].equals(kolumnaSQL)){indeksSQL=i;break;}
        	}
        	
        	for(int i=0;i<tablicaKolCSV.length;i++)
        	{	
        		if(tablicaKolCSV[i].equals(kolumnaCSV)){indeksCSV=i;break;}
        	}
        	
        	System.out.println("CSV-"+indeksCSV+"\nSQL-"+indeksSQL);
        	int j;
        	String pomoc;
    		for(int i=pom.getiloscKolumn();i<pom.getiloscRekordow()-1;i++)
    		{
    			System.out.println("i="+i);
    			for(j=0;j<pom.getiloscKolumn();j++){
	    			if(j==indeksCSV-1)
	    			{
	    				System.out.println("j="+indeksCSV);
	    				if(indeksCSV > indeksSQL)
	    	        	{
	    					System.out.println("CSV > SQL");
	    					for(int a=-1;a<indeksCSV-indeksSQL;a++)
	    					{
	    						System.out.println("a="+a);
	    						pomoc = rekordyCSV[i+j-a];
	    						rekordyCSV[i+j-a]=rekordyCSV[i+j-a-1];
	    						rekordyCSV[i+j-a-1] = pomoc;
	    						System.out.println("\tpomoc="+pomoc);
	    						System.out.println("\trekordyCSV["+(i+j-a)+"]="+rekordyCSV[i+j-a]);
	    						System.out.println("\trekordyCSV["+(i+j-a-1)+"]="+rekordyCSV[i+j-a-1]+"\n");
	    					}
	    				}
	    				if(indeksCSV < indeksSQL)
	    				{
	    					System.out.println("CSV < SQL");
	    					for(int a=1;a<indeksSQL-indeksCSV;a++)
	    					{
	    						System.out.println("a="+a);
	    						pomoc = rekordyCSV[i+j+a];
	    						rekordyCSV[i+j+a]=rekordyCSV[i+j+a+1];
	    						rekordyCSV[i+j+a+1] = pomoc;
	    						System.out.println("\tpomoc="+pomoc);
	    						System.out.println("\trekordyCSV["+(i+j+a)+"]="+rekordyCSV[i+j+a]);
	    						System.out.println("\trekordyCSV["+(i+j+a+1)+"]="+rekordyCSV[i+j+a+1]+"\n");
	    					}
	    				}
		            }
    			}
    			i=i+j;
    		}
    		for(int i=0;i<24;i++){
    			
    			System.out.println("-\t"+rekordyCSV[i]);}	

//    		pom.setrekord(rekordyCSV);     
    		
    		
    	   
    		//napis końcowy
    		System.out.println("kk"); 
    		
    		
    		
    		textfieldPrzyporzadkowanie.setText("Zamiana miejsca kolumny zaończona.");
        }
    }
    
    //wstaw
    public class wstawButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
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
        			System.out.println(pom.getiloscKolumn());
        			sql = "CREATE TABLE "+pom.getnazwaPliku()+" (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ";
        			for(int i=0;i<pom.getiloscKolumn();i++){
        			    rekordyCSV[i]=rekordyCSV[i].replaceAll(" ", "_");
        			    if((i<pom.getiloscKolumn()-1))sql=sql+rekordyCSV[i]+" VARCHAR(300), ";
        			    if(i==pom.getiloscKolumn()-1)sql=sql+rekordyCSV[i]+" VARCHAR(300), data TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
        			}
        			System.out.println(sql);
        			stmt.executeUpdate(sql);
        			textfieldPrzyporzadkowanie.setText("Tabela "+pom.getnazwaPliku()+" została utworzona.");
        		}catch(SQLException e4) {textfieldPrzyporzadkowanie.setText("Taka "+pom.getnazwaPliku()+" tabela już istnieje w bazie!!!");}
        		//end create table
    			
    			sql="SHOW COLUMNS FROM "+pom.getnazwaPliku()+";";
    			ResultSet rs = stmt.executeQuery(sql);
    			int pomoc2=0;
    			while(rs.next()){
    				
    				String kolumna = rs.getString(1);
    				Pattern pattern = Pattern.compile("[Kk]od.[Pp]ocztowy");
					Matcher matcherpattern = pattern.matcher(kolumna);
					matcherpattern.reset();
					boolean found = matcherpattern.find();
					if(found){
						System.out.println(pomoc2+"\t"+kolumna);
						break;
					}
    				pomoc2++;
    			}
    		    rs.close();
    		   
    		    int a;
    			for(int i=pom.getiloscKolumn();i<pom.getiloscRekordow()-1;i++)
    			{
    				for(a=0;a<pom.getiloscKolumn();a++){
	    				
	    				if(a==pomoc2-1){
		    	        	Pattern patternKod = Pattern.compile("[0-9]{2}-[0-9]{3}");
							Matcher matcherpatternKod = patternKod.matcher(rekordyCSV[i+a]);
							matcherpatternKod.reset();
							boolean foundKod = matcherpatternKod.find();
							if(!foundKod){
								System.out.println(rekordyCSV[i+a]);
								rekordyCSV[i+a]="";
							}
	    				
	    				}
    				}
    				i=i+a;
    			}
    			System.out.println(pom.getnazwaPliku());
    		    String str="",str2="";
    			
    			//begin insert
    			sql = "INSERT INTO "+pom.getnazwaPliku()+" (";
    			
    			for(int i=0;i<pom.getiloscKolumn();i++){
    				rekordyCSV[i]=rekordyCSV[i].replaceAll(" ", "_");
	    	       	if(i<pom.getiloscKolumn()-1)sql=sql+rekordyCSV[i]+", ";
	    	       	if(i==pom.getiloscKolumn()-1)sql=sql+rekordyCSV[i]+") VALUES ('";
	    	    }
    			System.out.println(sql);
    			int z;
    			for(int i=pom.getiloscKolumn();i<pom.getiloscRekordow()-1;i++)
    			{
    				for(z=0;z<pom.getiloscKolumn();z++){
	    				if(z!=pom.getiloscKolumn()-1){
	    					str=str+rekordyCSV[i+z]+"','";
	    				}
	    				if(z==pom.getiloscKolumn()-1){
		    	        	str=str+rekordyCSV[i+z]+"');";
		    	        	str2=sql+str;
		    	        	stmt.executeUpdate(str2);
		    	        	System.out.println(i+"\t"+str2);
		    	        	str2="";str="";
		    	        }
    				}
    				i=i+z;
    			}
    			textfieldPrzyporzadkowanie.setText("Wstawianie zakończone pomyślnie.");
    			//end insert	
    			stmt.close();
    			conn.close();
    		}
    		catch(SQLException se){}//Handle errors for JDBC
    		catch(Exception ex){  ex.printStackTrace();}//Handle errors for Class.forName
    		finally
    		{//finally block used to close resources
    			try{if(stmt!=null) stmt.close();}catch(SQLException se2){}// nothing we can do
    			try{if(conn!=null) conn.close();}catch(SQLException se){se.printStackTrace();}//end try        
    		}//end finally
        }
    }
}
