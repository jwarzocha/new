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
	
	public static String BezPolskichZnakow(String slowo)
    {
        return slowo.replaceAll("ą", "a").replaceAll("Ą", "A").replaceAll("ę", "e").replaceAll("Ę", "E")
        		.replaceAll("ś", "s").replaceAll("Ś", "S").replaceAll("ż", "z").replaceAll("Ż", "Z")
        		.replaceAll("ć", "c").replaceAll("Ć", "C").replaceAll("ź", "z").replaceAll("Ź", "Z")
        		.replaceAll("ń", "n").replaceAll("Ń", "N").replaceAll("ó", "o").replaceAll("Ó", "O")
        		.replaceAll("ł", "l").replaceAll("Ł", "L");
    }
	
	public static void main(String args[]) throws IOException{
		//Początek:Liczenie czasu
		long start=System.currentTimeMillis();
		
		//Wczytanie strony 00-000.pl
		String adresStronyPodstawowy = "http://www.00-000.pl";
		Document kodPocztowyDoc = Jsoup.connect(adresStronyPodstawowy).timeout(0).get();
		//String kodPocztowyString = kodPocztowyDoc.toString();
		//System.out.println(kodPocztowyString);
		
//-----------------------------------------------------------------------------------------------------		
		int zliczWoj=0;
		int zliczIloscStron=0;
		int iloscStron=0;
		int petlawhile=1;
		int zliczMiasta=0;
		String str="kod pocztowy;miasto;\r\n";
		
		Elements links1 = kodPocztowyDoc.getElementsByTag("a");
		for (Element link1 : links1 ) //Początek:pobieranie nazw i linków do województw 
		{
			String linkHref1 = link1.attr("href");//rozszerzenia do adresu 00-000.pl
			String linkText1 = link1.text();//nazwy województw
			
			if(zliczWoj>1)//żeby tylko były województwa
			{
				//System.out.println(linkText1+"\t"+linkHref1);
				String adresStronyWojewodztwa = adresStronyPodstawowy+linkHref1;//adres pierwszej strony wojewodztwa
				//Wczytanie strony danego wojewodztwa
				Document kodPocztowyWojwodztwo_iloscpodstronDoc = Jsoup.connect(adresStronyWojewodztwa).timeout(0).get();
				
				Elements links2 = kodPocztowyWojwodztwo_iloscpodstronDoc.getElementsByTag("b");
				for (Element link2 : links2 )//Początek:pobieranie ilosci podstron wojewodztwa
				{
					String linkText2 = link2.text();//ilosc podstron wojewodztwa
					if(zliczIloscStron==2){iloscStron=Integer.parseInt(linkText2);break;}
					//iloscStron zawiera ilosc podstron 
					zliczIloscStron++;//zliczIloscStron odpowiada zeby pobierało 3 <b> do iloscStron
				}zliczIloscStron=0;//Koniec:pobieranie ilosci podstron wojewodztwa	
				
				while(petlawhile<=iloscStron)//Początek:pobierania kolejnych podstron wojwodztw
				{
					String adresPoszczegolnychPodstron = adresStronyWojewodztwa + Integer.toString(petlawhile);
					System.out.println("TERAZ W:\t"+adresPoszczegolnychPodstron);
					
					//Zawiera poszczegolne podstrony wojwodztw
					Document poszczegolnePodstronyWojwodztwDoc = Jsoup.connect(adresPoszczegolnychPodstron).timeout(0).get();
					
					Elements links3 = poszczegolnePodstronyWojwodztwDoc.getElementsByTag("a");
					for (Element link3 : links3 ) //Początek:pobierania kodow pocztowych i miast
					{
						String linkHref3 = link3.attr("href");//
						String linkText3 = link3.text();//

							adresStronyWojewodztwa=adresStronyWojewodztwa.replaceAll("www.", "");
							
							if(linkText3.length()>2)
							{
								//wyrazenie regularne do kodu pocztowego adresStronyWojewodztwa+"[0-9]{2}-[0-9]{3}"
								Pattern patternKodPocztowy = Pattern.compile("[0-9]{2}-[0-9]{3}");
								Matcher matcherpatternKodPocztowy = patternKodPocztowy.matcher(linkText3);
								matcherpatternKodPocztowy.reset();
								boolean found2 = matcherpatternKodPocztowy.find();
								if(found2){
									if(!linkText3.equals("00-000.pl")){
										System.out.println("RAZ\ttext "+linkText3+"\thref "+linkHref3);
										str=str+linkText3+";";
									}
								}
							
							
								//wyrazenie regularne do miasta
								String pom=adresStronyWojewodztwa+BezPolskichZnakow(linkText3).toLowerCase();
								pom=pom.replaceAll(" ", "_");
								Pattern patternMiasto = Pattern.compile(pom);
								Matcher matcherpatternMiasto = patternMiasto.matcher(linkHref3);
								matcherpatternMiasto.reset();
								boolean found = matcherpatternMiasto.find();
								if(found) 
								{	zliczMiasta++;//tutaj musi być bo tu są znalezione
									System.out.println("RAZ\ttext "+linkText3+"\thref "+linkHref3);
									str=str+linkText3+";\r\n";
								}
							}
							

						
							if(zliczMiasta==50)break;
					}//Koniec:pobierania kodow pocztowych i miast	
					zliczMiasta=0;//zerowniepo kazdej stronie
					petlawhile++;//zlicza ilosc obrotow petli
				}//Koniec(while(petlawhile<=iloscStron)):pobierania kolejnych podstron wojwodztw
				petlawhile=1;//wracamy żeby while mogl wejsc w podstrony wojewodztw
			}//Koniec:if(zliczWoj>1)
			
			zliczWoj++;
			if((linkText1.equals("Zachodniopomorskie")))break;
		}//Koniec:pobieranie nazw i linków do województw 

//-----------------------------------------------------------------------------------------------------
		

//		Pattern patternSmiec = Pattern.compile("[0-9]{1};");
//		Matcher matcherpatternSmiec = patternSmiec.matcher(str);
//		matcherpatternSmiec.reset();
//		boolean foundSmiec = matcherpatternSmiec.find();
//		if(foundSmiec)str=str.replaceAll(matcherpatternSmiec.group(),"");
		System.out.println(str);
		
		PrintWriter firmy = new PrintWriter("kody_pocztowe_i_miasta.csv");
		firmy.println(str);
		firmy.close();
		
		//Koniec:Liczenie czasu
		long stop=System.currentTimeMillis();
		System.out.println("\n\n\nCzas wykonania:"+(stop-start));
		
		//Napis końcowy
		System.out.println("kk");
	}
}
