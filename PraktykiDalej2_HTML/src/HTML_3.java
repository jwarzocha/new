import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class HTML_3 {
		
	public static void main(String args[]) throws IOException {
		String str="";
		File input = new File("wynik.html");
		String str2="nazwa;ulica;kod pocztowy;osoba kontaktowa;\r\n";
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://www.odi.pl/");

		Element content = doc.getElementById("content");
		Elements links = content.getElementsByTag("a");
		for (Element link : links) 
		{
			String linkHref = link.attr("href");
			String linkText = link.text();
			//System.out.println(linkText+":\t\t"+linkHref);
			if(linkText.equals("AGD i RTV"))
			{
				System.out.println(linkText+":\t\t"+linkHref);
				doc = Jsoup.connect(linkHref).get();
			  
				PrintWriter zapisDoPliku = new PrintWriter("wynikLink.html");
				zapisDoPliku.println(doc);
				zapisDoPliku.close();
			  
				input = new File("wynikLink.html");
				doc = Jsoup.parse(input, "UTF-8", linkHref);

				content = doc.getElementById("content");
				links = content.getElementsByTag("a");
				for (Element link2 : links) 
				{
				  String linkHref2 = link2.attr("href");
				  String linkText2 = link2.text();
				  if(linkText2.equals("Dywany"))
				  {
					  System.out.println(linkText2+":\t\t"+linkHref2);
					  doc = Jsoup.connect(linkHref2).get();
					  
					  PrintWriter zapisDoPliku3 = new PrintWriter("wynikLink2.html");
					  zapisDoPliku3.println(doc);
					  zapisDoPliku3.close();
					  
					  input = new File("wynikLink2.html");
					  doc = Jsoup.parse(input, "UTF-8", linkHref2);

					  content = doc.getElementById("content");
					  links = content.getElementsByTag("span");
					  for (Element link3 : links) 
					  {
						  String linkHref3 = link3.attr("href");
						  String linkText3 = link3.text();
						  
						  //System.out.println(linkText3+":\t\t"+linkHref3+"costam");
						  
						  
						  input = new File("wynikLink2.html");
						  doc = Jsoup.parse(input, "UTF-8", linkHref2);

						  content = doc.getElementById("content");
						  links = content.getElementsByTag("a");
						  for (Element link4 : links) 
						  {
							  String linkHref4 = link4.attr("href");
							  String linkText4 = link4.text();
							  if(linkText4.equals(linkText3))
							  {
								  System.out.println(linkText4+":\t\t"+linkHref4);
								  doc = Jsoup.connect(linkHref4).get();
								  
								  PrintWriter zapisDoPliku4 = new PrintWriter("wynikLink3.html");
								  zapisDoPliku4.println(doc);
								  zapisDoPliku4.close();
								  str2=str2+linkText4+";";
								  input = new File("wynikLink3.html");
								  doc = Jsoup.parse(input, "UTF-8", linkHref4);

								  content = doc.getElementById("content");
								  links = content.getElementsByTag("td");
								  for (Element link5 : links) 
								  {		
									  String linkText5 = link5.text();
									  //System.out.println(linkText5+":\t\t");
									  str=str+linkText5;
								  }
								  //str2=str2+"\r\n";
							  }
							  
						  }
						  //System.out.println("UWAGA"+str);
						  str=str.substring(str.indexOf("Ulica nr:  "),str.indexOf("Tel")+3);
						  //str=str.replaceAll("Miejscowość:  ","");
						  str=str.replaceAll("Ulica nr:  ","");						  
						  str=str.replaceAll(" Kod pocztowy:  ",";");
						  str=str.replaceAll(" Osoba kontakt.:  ",";");
						  str=str.replaceAll(" Tel",";");
						  str2=str2+str+"\r\n";
						  str="";

						  System.out.println(str2);
						  PrintWriter firmy = new PrintWriter("firmy.csv");
						  firmy.println(str2);
						  firmy.close();
					  }
					  break;
				  }
				}  
			  break;
			}
		}	
		
		
		
		System.out.println("kk");
	}
	
}
