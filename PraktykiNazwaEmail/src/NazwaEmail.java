import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;


public class NazwaEmail {
	public static class Mail 
	{	 
        String nazwa;
        String email;
    }
	
	public static void main(String args[]) throws IOException{
		Mail[] mail = new Mail[3];
        for(int i=0;i<mail.length;i++) mail[i]=new Mail();
		
		CSVReader reader = new CSVReader(new FileReader("dane.csv"));
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
	    	Pattern pattern = Pattern.compile(";(.){1,}@(.){1,}\\.(com|pl)");
			Matcher matcherpattern = pattern.matcher(str);
		    matcherpattern.reset();
		    boolean found = matcherpattern.find();
		    if (found){
		    	int zlicz=0;
		    	do{
		    		mail[zlicz].email=matcherpattern.group().replace(";", "");
		    		zlicz++;
		    	}while(matcherpattern.find());
		    }
	    }
	    for(int i=0;i<mail.length;i++)
	    	System.out.println(i+".\t"+mail[i].email);
		
       
        System.out.println("kk");
	}
}
