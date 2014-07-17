import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


//nazwę firmy, województwo, miejscowość, ulicę, kod pocztowy, osobę kontaktową, 
//telefon (jak się uda), adres www, zatrudnienie, nip, regon oraz pod branże

public class HTML_4 {
		
	public static void main(String args[]) throws IOException{
		
		String str="";
		String str2="nazwa firmy;województwo;miejscowość;ulica;kod pocztowy;osoba kontaktowa;telefon (jak się uda);adres www;zatrudnienie;nip;regon;\r\n";
		String adresStrony = "http://www.odi.pl/firmy/budownictwo/";
		Document budownictwoDoc = Jsoup.connect(adresStrony).get();
		String budownictwoString = budownictwoDoc.toString(); 
		
		Element content = budownictwoDoc.getElementById("content");
		Elements links = content.getElementsByTag("a");
		for (Element link : links) //POCZĄTEK:pobieranie nazw i linków do działów od Architektura do Inne 
		{
			String linkHref = link.attr("href");
			String linkText = link.text();
//-------------------------------------------------------------------------------
			budownictwoDoc = Jsoup.connect(linkHref).get();
			budownictwoString = budownictwoDoc.toString();
			budownictwoString = budownictwoString.substring(budownictwoString.indexOf("<div class=\"l_ikona\"></div>"));
			String pomString = "";
			int zliczInt = 1;
			//Po ostatniej stronie powtarza się pierwsz wpisując następne adresy (jęsli np 8 jest ostatnie to po wczytaniu 9 ładuje się 1)
			while(!(budownictwoString.equals(pomString)))//tak długo aż ostatnie będzie równe pierwsze
			{
					String zliczString = Integer.toString(zliczInt);
					pomString=linkHref+zliczString+"/";
					System.out.println("co sie dzieje"+pomString);
					budownictwoDoc = Jsoup.connect(pomString).get();
					Document pomDoc = Jsoup.connect(pomString).get();
				
					pomString = pomDoc.toString();
					pomString = pomString.substring(pomString.indexOf("<div class=\"l_ikona\"></div>"));

			//-------------------------------------------------------------------------------
				content = budownictwoDoc.getElementById("content");
				links = content.getElementsByTag("span");
				for (Element link2 : links)//POCZĄTEK:pobieranie nazw firm 
				{
					String linkText2 = link2.text();
												  
					budownictwoDoc = Jsoup.connect(linkHref).get();
						
					content = budownictwoDoc.getElementById("content");
					links = content.getElementsByTag("a");
					for (Element link4 : links)//POCZATEK:pobieranie linków do firm 
					{
						String linkHref4 = link4.attr("href");
						String linkText4 = link4.text();
						if(linkText4.equals(linkText2))
						{
							//System.out.println(linkText4+":\t\t"+linkHref4);
							budownictwoDoc = Jsoup.connect(linkHref4).get();
							
							str2=str2+linkText4+";";
								
							content = budownictwoDoc.getElementById("content");
							links = content.getElementsByTag("td");
							for (Element link5 : links) 
							{		
								String linkText5 = link5.text();
								str=str+linkText5;
							}
							 
						}
						  
					 }//KONIEC:pobieranie linków do firm
					 //System.out.println(str);
					 str=str.substring(str.indexOf("Województwo:  "),str.indexOf("Tel")+3);
					 str=str.replaceAll("Województwo:  ","");
					 str=str.replaceAll(" Miejscowość:  ",";");
					 str=str.replaceAll(" Ulica nr:  ",";");
					 str=str.replaceAll(" Kod pocztowy:  ",";");
					 str=str.replaceAll(" Osoba kontakt.:  ",";");
//					 String indeks=str.substring(str.indexOf("Tel. kom.:  "),str.indexOf("pokaż tel. kom. ")+10);
//					 String indeks2=str.substring(str.indexOf("Tel.:  "),str.indexOf("pokaż telefon ")+13);
					 str=str.replaceAll(" Tel.:  ",";");
					 str=str.replaceAll("[0-9]{3}... pokaż telefon ","");
					 str=str.replaceAll(" Adres WWW:  ",";");
					 str=str.replaceAll(" NIP:  ",";");
					 str=str.replaceAll(" Zatrudnionych:  ",";");
					 //( Tel.:  032... pokaż telefon 032 255 13 70)
					 //Tel. kom.:  663... pokaż tel. kom. 663800774
					  
					 str2=str2+str+"\r\n";
					 str="";

					 System.out.println(str2);
					 PrintWriter firmy = new PrintWriter("firmybudownictwo.csv");
					 firmy.println(str2);
					 firmy.close();
				}//KONIEC:pobieranie nazw firm 

				
			//-------------------------------------------------------------------------------
				zliczInt++;System.out.println("trolrorklor");
			}//KONIEC:while(!(budownictwoString.equals(pomString)))
//-------------------------------------------------------------------------------			
			
			if((linkText.equals("Inne ...")))break;
		}//KONIEC:pobieranie nazw i linków do działów od Architektura do Inne
		
		
		
		
		//Napis końcowy
		System.out.println("kk");
		
	}
}


