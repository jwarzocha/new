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
		int index=0;
		int iloscPodstron=0;
		
		Firmy[] firmy = new Firmy[1045];
        for(int i=0;i<firmy.length;i++){
        	firmy[i]=new Firmy();
        	firmy[i].nazwa="";
			firmy[i].ulica="";
			firmy[i].kod_pocztowy="";
			firmy[i].miejscowosc="";
			firmy[i].email="";
			firmy[i].adres_www="";
			firmy[i].telefon="";
			firmy[i].branza="";
        }
        
		int zlicz=0;
		//Wczytanie strony 
		String adresStronyPodstawowy = "http://www.portal-firem.cz";
		Document adresStronyDoc = Jsoup.connect(adresStronyPodstawowy).timeout(0).get();
		//String String = Doc.toString();
		//System.out.println(String);
		
//-----------------------------------------------------------------------------------------------------		
		
		String str="nazwa;ulica;kod_pocztowy;miejscowosc;email;adres_www;telefon;branza\r\n";
		boolean poczatek = false;
		
		Elements links1 = adresStronyDoc.getElementsByTag("a");
		for (Element link1 : links1 ) 
		{
			String linkHref1 = link1.attr("href");//część adresu działu
			String linkText1 = link1.text();//nazwa dzialu
			
			if((linkText1.equals("Auto-moto")))poczatek = true;
			
			if(poczatek == true){
				
				if (zlicz==0 || zlicz%4==0){
					System.out.println("zlicz= "+zlicz);
					System.out.println("href= "+linkHref1);
					System.out.println("text= "+linkText1);
					
					String adresDzialu = adresStronyPodstawowy + linkHref1;
					
					int zlicz2 = 0;
					boolean poczatek2 = false;
					
					Document adresDzialuDoc = Jsoup.connect(adresDzialu).timeout(0).get();
					Elements links2 = adresDzialuDoc.getElementsByTag("a");
					for (Element link2 : links2 ) 
					{
						String linkHref2 = link2.attr("href");//część adresu poddziału
						String linkText2 = link2.text();//nazwa poddzialu
						
						if((linkText2.equals("Katalog firem")))poczatek2 = true;
						
						if(poczatek2 == true){
							if(linkHref2.indexOf("/firma/")==-1){
								if(zlicz2!=0){
									System.out.println("\t2href= "+linkHref2);
									System.out.println("\t2text= "+linkText2);
									
									String adresPoddzialu = adresStronyPodstawowy + linkHref2;
									boolean poczatek3 = false;
									boolean pomIloscStron = false;
									boolean pomIloscStron2 = false;
									int zlicz3 = 0;
									int zliczKtore = 1, pomZliczKtore=0;
									Document adresPoddzialuDoc = Jsoup.connect(adresPoddzialu).timeout(0).get();
									Elements links3 = adresPoddzialuDoc.getElementsByTag("a");
									for (Element link3 : links3 ) 
									{
										String linkHref3 = link3.attr("href");//część adresu firmy
										String linkText3 = link3.text();//attr("title");//nazwa firmy
										
										
										if((linkText3.equals("Katalog firem")))poczatek3 = true;
										
										if(poczatek3 == true){
											if(zlicz3>2){
												System.out.println("\t\t3href= "+linkHref3);
												System.out.println("\t\t3text= "+linkText3);

												//tutaj ma byćwchodzenie do poszczególnych firm
												

//												if((linkText3.equals("8"))&&(pomIloscStron==false)&&(pomIloscStron2==false)){
//													pomIloscStron = true;
//													pomIloscStron2 = true;
//												}//koniec: 
//												
//												if((pomIloscStron2==true)&&(pomIloscStron == true)){
//													iloscPodstron = Integer.parseInt(linkText3);
//													System.out.println(iloscPodstron+linkText3);
//													pomIloscStron = false;
//												}
												if(pomZliczKtore<=25*3){
													if(zliczKtore==1){
														/*adres podstrony o firmie linkHref3*/
														String adresFirmy = adresStronyPodstawowy + linkHref3;

														System.out.println("\t\t\t3href= "+linkHref3);
														Document adresFirmyDoc = Jsoup.connect(adresFirmy).timeout(0).get();
														Elements links4 = adresFirmyDoc.getElementsByTag("div");
														for (Element link4 : links4 ) 
														{
															String linkText4 = link4.text();//
															if((linkText4.indexOf("Adresa ")==0)&&(linkText4.indexOf("Kontakt ")!=-1)){
																System.out.println("\t\t\t4text= "+linkText4);//wszystkie dane o firmie
																
															}
//															
															
														}//koniec:for (Element link4 : links4 ) 	
														
														firmy[index].nazwa=linkText3;
													}//koniec:if(zliczKtore==1)
														
												}//koniec: if(pomZliczKtore<=25*3)
												if (zliczKtore == 3){ zliczKtore = 0; index++;}
												zliczKtore++;
												pomZliczKtore++;
												if((linkText3.equals(">>")))break;
											}//koniec: if(zlicz3>2)
											zlicz3++;
										}
									}//koniec: for (Element link3 : links3 ) 
									
								}//koniec: if(zlicz2!=0)
								zlicz2++;
							}else break;
							
						}//koniec: if(poczatek == true)
							
					}//koniec: for (Element link2 : links2 )	
					
					
				}//koniec: if (zlicz==0 || zlicz%4==0)
				
				zlicz++;
			}//koniec: if(poczatek == true)
			
			
			if((linkText1.equals("Soudy, úřady a organizace")))break;
		}//koniec:	for (Element link1 : links1 ) 

//-----------------------------------------------------------------------------------------------------
		

//		for(int i=0;i<firmy.length;i++){
//			str = str + firmy[i].nazwa+";"
//					  + firmy[i].ulica+";"
//					  + firmy[i].kod_pocztowy+";"
//					  + firmy[i].miejscowosc+";"
//					  + firmy[i].wojewodztwo+";"
//					  + firmy[i].adres_www+";"
//					  + firmy[i].telefon+";"
//					  + firmy[i].fax+";\r\n";
//			System.out.println("2--"+i);
//		}
//		
//		System.out.println(str);
//		
//		PrintWriter doPliku = new PrintWriter("firmy.csv");
//		doPliku.println(str);
//		doPliku.close();
		
		//Koniec:Liczenie czasu
		long stop=System.currentTimeMillis();
		System.out.println("\n\n\nCzas wykonania:"+(stop-start));
		
		//Napis końcowy
		System.out.println("kk");
	}
}
