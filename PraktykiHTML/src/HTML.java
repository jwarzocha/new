import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HTML {
	
	
	public static void main(String args[]) throws IOException{
		//Początek:Liczenie czasu
		long start=System.currentTimeMillis();
		
		Firmy[] firmy = new Firmy[1045];
        for(int i=0;i<firmy.length;i++){
        	firmy[i]=new Firmy();
        	firmy[i].nazwa="";
			firmy[i].ulica="";
			firmy[i].kod_pocztowy="";
			firmy[i].miejscowosc="";
			firmy[i].wojewodztwo="";
			firmy[i].adres_www="";
			firmy[i].telefon="";
			firmy[i].fax="";
        }
		int zlicz=0;
		//Wczytanie strony 
		String adresStronyPodstawowy = "http://www.e-sieci.pl/";
		Document adresStronyDoc = Jsoup.connect(adresStronyPodstawowy).timeout(0).get();
		//String String = Doc.toString();
		//System.out.println(String);
		
//-----------------------------------------------------------------------------------------------------		
		
		String str="nazwa;ulica;kod_pocztowy;miejscowosc;wojewodztwo;adres_www;telefon;fax\r\n";
		
		Elements links1 = adresStronyDoc.getElementsByTag("a");
		for (Element link1 : links1 ) 
		{
			String linkHref1 = link1.attr("href");//adres działu
			String linkText1 = link1.text();//nazwa dzialu
			
			//wchodzimy tylko w te strony co mają numer
			Pattern pattern = Pattern.compile("([0-9]{1,})");
			Matcher matcherpattern = pattern.matcher(linkText1);
			matcherpattern.reset();
			boolean found = matcherpattern.find();
			if(found){
//				System.out.println("href= "+linkHref1);
//				System.out.println("text= "+linkText1);
	
				int iloscPodstron = Integer.parseInt(matcherpattern.group());
				System.out.println(iloscPodstron);
				
				Document stronyDzialowDoc = Jsoup.connect(linkHref1).timeout(0).get();
				
				Elements links2 = stronyDzialowDoc.getElementsByTag("a");
				for (Element link2 : links2 )
				{
					String linkHref2 = link2.attr("href");
					String linkText2 = link2.text();//ilosc podstron wojewodztwa
					
					if((linkText2.equals("Więcej informacji...")))
					{
//						System.out.println("\threfdzial= "+linkHref2);
//						System.out.println("\ttextdzial= "+linkText2);
						String adresPodstrony="http://www.lista.e-sieci.pl/"+linkHref2;
						
						Document stronyFirmyDoc = Jsoup.connect(adresPodstrony).timeout(0).get();
						System.out.println("\t\t "+adresPodstrony);
						
						Elements links3 = stronyFirmyDoc.getElementsByTag("h2");
						for (Element link3 : links3 )
						{
							String linkText3 = link3.text();
							System.out.println("\t\t\ttextdzial= "+linkText3);
							firmy[zlicz].nazwa=linkText3;							
						}//koniec: for (Element link3 : links3 )
						
						Elements links4 = stronyFirmyDoc.getElementsByTag("p");
						for (Element link4 : links4 )
						{
							String linkText4 = link4.text();
							System.out.println("\t\t\ttextfirma= "+linkText4);
							
							
							
							Pattern patternAdres = Pattern.compile("Ulica: ");
							Matcher matcherpatternAdres = patternAdres.matcher(linkText4);
							matcherpatternAdres.reset();
							boolean foundAdres = matcherpatternAdres.find();
							if(foundAdres){
								//String pom="";
								Pattern patternKod = Pattern.compile("[0-9]{2}-[0-9]{3}");
								Matcher matcherpatternKod = patternKod.matcher(linkText4);
								matcherpatternKod.reset();
								boolean foundKod = matcherpatternKod.find();
								if(foundKod){
									firmy[zlicz].kod_pocztowy=matcherpatternKod.group();
									firmy[zlicz].ulica= linkText4.substring(7,matcherpatternKod.start());
									firmy[zlicz].miejscowosc= linkText4.substring(matcherpatternKod.end()+2);
									System.out.println("\t\t\t\ttextkod_pocztowy= "+firmy[zlicz].kod_pocztowy);
									System.out.println("\t\t\t\ttextulica= "+firmy[zlicz].ulica);
									System.out.println("\t\t\t\ttextmiejscowosc= "+firmy[zlicz].miejscowosc);
								}//koniec: if(foundAdres)
							}//koniec: if(foundAdres)
							
							
							
							Pattern patternWojewodztwo = Pattern.compile("województwo ");
							Matcher matcherpatternWojewodztwo = patternWojewodztwo.matcher(linkText4);
							matcherpatternWojewodztwo.reset();
							boolean foundWojewodztwo = matcherpatternWojewodztwo.find();
							if(foundWojewodztwo){
								firmy[zlicz].wojewodztwo=linkText4.substring(12);
								System.out.println("\t\t\t\ttextwojewodztwo= "+firmy[zlicz].wojewodztwo);
							}//koniec: if(foundWojewodztwo)
							
							
							
							Pattern patternFax = Pattern.compile("Fax[\\.\\ \\/\\-\\(\\)0-9]{7,}");
							Matcher matcherpatternFax = patternFax.matcher(linkText4);
							matcherpatternFax.reset();
							boolean foundFax = matcherpatternFax.find();
							if(foundFax){
								firmy[zlicz].fax=(matcherpatternFax.group().substring(3)).replace(".", "");
								System.out.println("\t\t\t\ttextfax= "+firmy[zlicz].fax);
							}//koniec: if(foundFax)
											
							
							
							Pattern patternTel = Pattern.compile("Tel");
							Matcher matcherpatternTel = patternTel.matcher(linkText4);
							matcherpatternTel.reset();
							boolean foundTel = matcherpatternTel.find();
							if(foundTel){
								Pattern patternTelNumer = Pattern.compile("[\\ \\/\\-\\(\\)0-9]{7,}");
								Matcher matcherpatternTelNumer = patternTelNumer.matcher(linkText4);
								matcherpatternTelNumer.reset();
								boolean foundTelNumer = matcherpatternTelNumer.find();
								if(foundTelNumer){
									firmy[zlicz].telefon=matcherpatternTelNumer.group();
									System.out.println("\t\t\t\ttexttelefon= "+firmy[zlicz].telefon);
									break;
								}//koniec: if(foundTelNumer)
							}//koniec: if(foundTel)
							
							if((linkText4.equals("Opis sieci")))break;
						}//koniec: for (Element link4 : links4 )
						
						Elements links5 = stronyFirmyDoc.getElementsByTag("a");
						for (Element link5 : links5 )
						{
							String linkText5 = link5.text();
							
													
							Pattern patternLink = Pattern.compile("http://");
							Matcher matcherpatternLink = patternLink.matcher(linkText5);
							matcherpatternLink.reset();
							boolean foundLink = matcherpatternLink.find();
							if(foundLink){
								System.out.println("\t\t\t\ttextlink= "+linkText5);
								firmy[zlicz].adres_www=linkText5;
							}//koniec: if(foundLink)						
					
						}//koniec: for (Element link5 : links5 )
						
						
						zlicz++;
					}//koniec: if((linkText1.equals("Więcej informacji...")))
					
				}//koniec: for (Element link2 : links2 )
				
			}//koniec: if(found)

			
			
			if((linkText1.equals("- Sklepy Kosmetyczno - Chemiczne (29)")))break;
		}//koniec:	for (Element link1 : links1 ) 

//-----------------------------------------------------------------------------------------------------
		

		for(int i=0;i<firmy.length;i++){
			str = str + firmy[i].nazwa+";"
					  + firmy[i].ulica+";"
					  + firmy[i].kod_pocztowy+";"
					  + firmy[i].miejscowosc+";"
					  + firmy[i].wojewodztwo+";"
					  + firmy[i].adres_www+";"
					  + firmy[i].telefon+";"
					  + firmy[i].fax+";\r\n";
			System.out.println("2--"+i);
		}
		
		System.out.println(str);
		
		PrintWriter doPliku = new PrintWriter("firmy.csv");
		doPliku.println(str);
		doPliku.close();
		
		//Koniec:Liczenie czasu
		long stop=System.currentTimeMillis();
		System.out.println("\n\n\nCzas wykonania:"+(stop-start));
		
		//Napis końcowy
		System.out.println("kk");
	}
}
