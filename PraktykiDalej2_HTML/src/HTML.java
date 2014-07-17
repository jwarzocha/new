import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class HTML {
		
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://www.odi.pl/").get();
		//System.out.println(doc);
		
		String str = doc.toString();
		
		PrintWriter zapisDoPliku = new PrintWriter("wynik.html");
		zapisDoPliku.println(str);
		zapisDoPliku.close();

		System.out.println("Zapisano kod do wynik.html\n");
		
		
		Pattern pattern = Pattern.compile("(((<div){1})((.){1,})(class=\"left\"))");
		Matcher matcher = pattern.matcher(str);
				
		String result = "Szukany wzorzec: \"" + pattern + "\"\n";
		boolean isMatching = matcher.matches();
	    result = result + "\nmatches(): Cały tekst" + (isMatching ? "" : " NIE") + " pasuje do wzorca.";
	    matcher.reset();
	    boolean found = matcher.find();
	    if (!found) result = result + "\nfind(): Nie znaleziono żadnego podłańcucha " + "pasującego do wzorca";
	    else{
	    	do{
	    		result = result + "\nfind(): Dopasowano podłańcuch \"" 
	    				+ matcher.group() +
	                   "\" od pozycji " + matcher.start() +
	                   " do pozycji " + matcher.end() + ".";
	    	}while(matcher.find());
	    }
	    System.out.println(result+"\n");
	    
	    
	    System.out.println("\n\n");
	    
	    
	  //="http://
	   	String pom="";result="";int a=0;
	   	Random losuj = new Random();
        int liczba = losuj.nextInt(50);
	   	
	    Pattern pattern2 = Pattern.compile("(href=\"http://)(.){1,}/\"");
		Matcher matcher2 = pattern2.matcher(str);
		
		result = "Szukany wzorzec: \"" + pattern2 + "\"\n";
		isMatching = matcher2.matches();
	    result = result + "\nmatches(): Cały tekst" + (isMatching ? "" : " NIE") + " pasuje do wzorca.";
	    matcher2.reset();
	    found = matcher2.find();
	    if (!found) result = result + "\nfind(): Nie znaleziono żadnego podłańcucha " + "pasującego do wzorca";
	    else{
	    	do{
	    		result = result + "\nfind(): Dopasowano podłańcuch \"" 
	    				+ matcher2.group() +
	                   "\" od pozycji " + matcher2.start() +
	                   " do pozycji " + matcher2.end() + ".";
	    		if(a==liczba){
	    			break;
	    		}
	    		a++;
	    		
	    	}while(matcher2.find());
	    }
	    System.out.println(result+"\n");
	    
	    
	    pom=matcher2.group();
	    a = pom.indexOf("\" ");
	    System.out.println("ota a\t"+a);
	    //if((a<1)||a>liczba)a = pom.indexOf("\"");
	   // pom=pom.substring(13,a);
	    System.out.println("\n\n\n"+pom+"\n");	
	    
//		doc = Jsoup.connect(pom).get();
//		str = doc.toString();
//	    
//	    PrintWriter zapisLinkuDoPliku = new PrintWriter("wynikLink.html");
//		zapisLinkuDoPliku.println(str);
//		zapisLinkuDoPliku.close();
//
//		System.out.println("Zapisano kod do wynikLink.html\n");
		
	    
	}
	
}
