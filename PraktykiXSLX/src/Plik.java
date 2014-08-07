
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;



public class Plik {

	public static void main(String[] args) throws IOException 
	{
		
//--------------WCZYTYWANIE STRINGA Z PLIKU DO TABLICY OBIEKTÓW-------------------------------		
		Kolumny[] firmy = new Kolumny[45628];
        for(int i=0;i<firmy.length;i++) firmy[i]=new Kolumny();

		
		CSVReader reader = new CSVReader(new FileReader("przybylski roboczy.csv"));
	    String[] row;
	    String str = "";
	    int zlicz=0;
	    while ((row = reader.readNext()) != null) 
	    {
	        System.out.println("zlicz:	"+zlicz);
	    	for (int i = 0; i < row.length; i++) 
	        {
	    		//System.out.print(row[i]);
	            if(i==0){firmy[zlicz].lp=row[i];}//System.out.println(firmy[zlicz].lp);}
	            if(i==1){firmy[zlicz].nazwa_firmy=row[i];}//System.out.println(firmy[zlicz].nazwa_firmy);}
	            if(i==2){firmy[zlicz].email=row[i];}//System.out.println(firmy[zlicz].email);}
	            if(i==3){firmy[zlicz].branza=row[i];}//System.out.println(firmy[zlicz].branza);}
	            if(i==4){firmy[zlicz].wyszukaj_branze=row[i];}//System.out.println(firmy[zlicz].wyszukaj_branze);}
	        }
	    	zlicz++;
	    }
        
//----------------Poprawianie branz--------------------------------------------------------	    
        System.out.println("----------------SPRAWDZANIE nazw--------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	
	    	Pattern pattern = Pattern.compile("(([a-záäčćďéěíłĺľńňóôŕřšśťúůýžź]+)[A-ZÁÄČĆĎÉĚÍŁĹĽŃŇÓÔŔŘŠŚŤÚŮÝŽŹ])");
	    	String result ;//= i+"Wzorzec: \"" + pattern + "\"\n" + "Tekst: \"" + firmy[i].branza + "\"";
	    	Matcher matcherpattern = pattern.matcher(firmy[i].branza);
	    	boolean isMatching = matcherpattern.matches();
	    	//result = result + "\nmatches(): Cały tekst" + (isMatching ? "" : " NIE") + " pasuje do wzorca.";
	    	matcherpattern.reset();
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (found){
		    	do{
		    		result =i+"Wzorzec: \"" + pattern + "\"\n" + "Tekst: \"" + firmy[i].branza + "\"" + "\nfind(): Dopasowano podłańcuch \"" 
		    				+ matcherpattern.group() +
		                   "\" od pozycji " + matcherpattern.start() +
		                   " do pozycji " + matcherpattern.end() + ".";
		    		System.out.println(result+"\n");
		    		int kon=matcherpattern.end()-1;
		    		try{firmy[i].branza=firmy[i].branza.substring(0, kon);}
		    		catch(IOError e){System.out.println(i+" blad "+ e);}
		    		System.out.println(firmy[i].branza+"\n");
		    		break;
		    	}while(matcherpattern.find());
		    }
	    }
	 

	    
	    
//----------------Poprawianie nazw firm--------------------------------------------------------	    
        System.out.println("----------------SPRAWDZANIE nazw--------------");
	    for(int i=0;i<zlicz;i++)
	    {
	    	
	    	Pattern pattern = Pattern.compile("([a-záäčćďéěíłĺľńňóôŕřšśťúůýžź]\\.\\ [A-ZÁÄČĆĎÉĚÍŁĹĽŃŇÓÔŔŘŠŚŤÚŮÝŽŹ]([a-záäčćďéěíłĺľńňóôŕřšśťúůýžź]+)\\ [0-9])");
	    	String result ;//= i+"Wzorzec: \"" + pattern + "\"\n" + "Tekst: \"" + firmy[i].branza + "\"";
	    	Matcher matcherpattern = pattern.matcher(firmy[i].nazwa_firmy);
	    	boolean isMatching = matcherpattern.matches();
	    	//result = result + "\nmatches(): Cały tekst" + (isMatching ? "" : " NIE") + " pasuje do wzorca.";
	    	matcherpattern.reset();
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (found){
		    	do{
		    		result =i+"Wzorzec: \"" + pattern + "\"\n" + "Tekst: \"" + firmy[i].nazwa_firmy + "\"" + "\nfind(): Dopasowano podłańcuch \"" 
		    				+ matcherpattern.group() +
		                   "\" od pozycji " + matcherpattern.start() +
		                   " do pozycji " + matcherpattern.end() + ".";
		    		System.out.println(result+"\n");
		    		int pocz=matcherpattern.start()+1;
		    		try{firmy[i].nazwa_firmy=firmy[i].nazwa_firmy.substring(0, pocz);}
		    		catch(IOError e){System.out.println(i+" blad "+ e);}
		    		System.out.println(firmy[i].nazwa_firmy+"\n");
		    		break;
		    	}while(matcherpattern.find());
		    }
	    }	   
		
	   
	    
	    
	    
	    
	    for(int i=0;i<zlicz;i++)
	    {
	        str = str + firmy[i].lp +","+ firmy[i].nazwa_firmy +","+ firmy[i].email +","+ 
	        			firmy[i].branza +","+ firmy[i].wyszukaj_branze +"\r\n";
	        System.out.println(i);
        }	
	    
	    FileWriter f= new FileWriter("przybylski roboczy-poprawione.csv");
		f.write((str));
		f.close(); 
	   
	   System.out.println("kk");
	}//end main
}








