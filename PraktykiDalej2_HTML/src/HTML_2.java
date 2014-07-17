import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class HTML_2 {
		
	public static void main(String args[]) throws IOException {
		String str="";
		File input = new File("wynik.html");
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
						  //print(str)
						  String linkHref3 = link3.attr("href");
						  String linkText3 = link3.text();
						  str=str+linkText3+"\r\n";
						  PrintWriter firmy = new PrintWriter("firmy.txt");
						  firmy.println(str);
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
