import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTML_4part2 {
		
	public static void main(String args[]) throws IOException{
			
		String nazwa_firmy[]= new String[1150000];
		String wojewodztwo[]= new String[1150000];
		String miejscowosc[]= new String[1150000]; 
		String ulica[]= new String[1150000];
		String kod_pocztowy[]= new String[1150000];
		String osoba_kontaktowa[]= new String[1150000];
		String telefon[]= new String[1150000];
		String tel_kom[]= new String[1150000];
		String adres_www[]= new String[1150000];
		String zatrudnienie[]= new String[1150000];
		String nip[]= new String[1150000];
		String regon[]= new String[1150000];
		int j=0;
		int a1=0,a2=0;
		String str="";
		String str2="nazwa firmy;województwo;miejscowość;ulica;kod pocztowy;osoba kontaktowa;telefon;tel. kom.;adres www;zatrudnienie;nip;regon;\r\n";
		
		String ostatni="";
		String adresStrony = "http://www.odi.pl/firmy/budownictwo/";
		Document budownictwoDoc = Jsoup.connect(adresStrony).timeout(0).get();
		String budownictwoString = budownictwoDoc.toString(); 
		
		int tab[]={57,62,12,69,29,72,40,96,96,50,13,9,12,27,29,8,60,81,44,25,34,16,30,34,84,5,19,111,8,30,33,31,147,28,29,124};
		int i=0;
		Element content1 = budownictwoDoc.getElementById("content");
		Elements links1 = content1.getElementsByTag("a");
		for (Element link : links1) //POCZĄTEK:pobieranie nazw i linków do działów od Architektura do Inne 
		{
			String linkHref = link.attr("href");
			String linkText = link.text();
//-------------------------------------------------------------------------------
			budownictwoDoc = Jsoup.connect(linkHref).timeout(0).get();
			budownictwoString = budownictwoDoc.toString();
			
			String pomString = "";
			int zliczInt = 1;
			while(zliczInt<=tab[i])
			{					
				String zliczString = Integer.toString(zliczInt);
				pomString=linkHref+zliczString+"/";
				System.out.println("\nKTORA STRONA\t"+pomString);
				budownictwoDoc = Jsoup.connect(pomString).timeout(0).get();
					
			//-------------------------------------------------------------------------------
				Element content2 = budownictwoDoc.getElementById("content");
				Elements links2 = content2.getElementsByTag("span");
				for (Element link2 : links2)//POCZĄTEK:pobieranie nazw firm 
				{
					String linkText2 = link2.text();
												  
					budownictwoDoc = Jsoup.connect(pomString).timeout(0).get();
						
					Element content3 = budownictwoDoc.getElementById("content");
					Elements links3 = content3.getElementsByTag("a");
					for (Element link4 : links3)//POCZATEK:pobieranie linków do firm 
					{
						String linkHref4 = link4.attr("href");
						String linkText4 = link4.text();
						if(linkText4.equals(linkText2))
						{
							System.out.println("\n\t"+linkText4+":\t\t"+linkHref4+"\n");
							budownictwoDoc = Jsoup.connect(linkHref4).timeout(0).get();
							//Jsoup.connect(url).timeout(0).get();
							//str2=str2+linkText4+";";
							//System.out.println("\n\tSTRING:\t"+str2+"\n");
							nazwa_firmy[j]=linkText4;
							//System.out.println(nazwa_firmy[j]+" ");
							//str2="";
							
							Element content4 = budownictwoDoc.getElementById("content");
							Elements links4= content4.getElementsByTag("td");
							for (Element link5 : links4) 
							{		
								String linkText5 = link5.text();
								//System.out.println("\n\tSTRING:\t"+linkText5+"\n");
								if(ostatni.equals("Województwo: "))
								{
									wojewodztwo[j]=linkText5;
									//System.out.print(wojewodztwo[j]+" ");
								}
//								else if(wojewodztwo[j]==null);
//									else wojewodztwo[j]="";
								
								if(ostatni.equals("Miejscowość: "))
								{
									miejscowosc[j]=linkText5;
									//System.out.print(miejscowosc[j]+" ");
								}
//								else if(miejscowosc[j]==null);
//								else miejscowosc[j]="";
								
								if(ostatni.equals("Ulica nr: "))
								{
									ulica[j]=linkText5;
									//System.out.print(ulica[j]+" ");
								}
//								else if(ulica[j]==null);
//								else ulica[j]="";
								
								if(ostatni.equals("Kod pocztowy: "))
								{
									kod_pocztowy[j]=linkText5;
									//System.out.print(kod_pocztowy[j]+" ");
								}
//								else if(kod_pocztowy[j]==null);
//								else kod_pocztowy[j]="";
								
								if(ostatni.equals("Osoba kontakt.: "))
								{
									osoba_kontaktowa[j]=linkText5;
									//System.out.print(osoba_kontaktowa[j]+" ");
								}
//								else if(osoba_kontaktowa[j]==null);
//								else osoba_kontaktowa[j]="";
								
								if(ostatni.equals("Tel.: "))
								{
									telefon[j]=linkText5;
									a1 =linkText5.indexOf("telefon ");
									a2 =a1+8;
									if((a1!=-1)||(a2!=-1))
									{
										 String indeks=linkText5.substring(a2);
										 telefon[j]=indeks;
									}//032... pokaż telefon 032 255 13 70 
									//System.out.print(telefon[j]+" ");
								}
//								else if(telefon[j]==null);
//								else telefon[j]="";
								
								if(ostatni.equals("Tel. kom.: "))
								{
									tel_kom[j]=linkText5;
									a1 =linkText5.indexOf("kom. ");
									a2 =a1+5;
									if((a1!=-1)||(a2!=-1))
									{
										 String indeks=linkText5.substring(a2);
										 tel_kom[j]=indeks;
									}//601... pokaż tel. kom. 601 289 709 
									//System.out.print(tel_kom[j]+" ");
								}
//								else if(tel_kom[j]==null);
//								else tel_kom[j]="";
								
								if(ostatni.equals("Adres WWW: "))
								{
									adres_www[j]=linkText5;
									//System.out.print(adres_www[j]+" ");
								}
//								else if(adres_www[j]==null);
//								else adres_www[j]="";
								
								if(ostatni.equals("NIP: "))
								{
									nip[j]=linkText5;
									//System.out.print(nip[j]+" ");
								}
//								else if(nip[j]==null);
//								else nip[j]="";
								
								if(ostatni.equals("Regon: "))
								{
									regon[j]=linkText5;
									//System.out.print(regon[j]+" ");
								}
//								else if(regon[j]==null);
//								else regon[j]="";
								
								if(ostatni.equals("Zatrudnionych: "))
								{
									zatrudnienie[j]=linkText5;
									//System.out.print(zatrudnienie[j]+" ");
								}
//								else if(zatrudnienie[j]==null);
//								else zatrudnienie[j]="";
								
								ostatni=linkText5;
							}
						}
					try{
						str=nazwa_firmy[j]+";"+wojewodztwo[j]+";"+miejscowosc[j]+";"+ulica[j]+";"+kod_pocztowy[j]+";"+osoba_kontaktowa[j]+";"+telefon[j]+";"+tel_kom[j]+";"+adres_www[j]+";"+nip[j]+";"+regon[j]+";"+zatrudnienie[j]+";";
						str=str.replaceAll("null;null;null;null;null;null;null;null;null;null;null;null;","");
						System.out.print("\t"+str+"");
						str2=str2+str;
					}catch(IndexOutOfBoundsException e){System.out.println(e);}	
						j++;
						 
					}//KONIEC:pobieranie linków do firm
					
					str2=str2+"\r\n";
					//System.out.println("\n\tSTRING:\t"+str2+"\n");
					
//					System.out.println(str2);
//					PrintWriter firmy = new PrintWriter("firmybudownictwo.csv");
//					firmy.println(str2);
//					firmy.close();
				}//KONIEC:pobieranie nazw firm 

				
			//-------------------------------------------------------------------------------
				zliczInt++;
								
			}//KONIEC:while
//-------------------------------------------------------------------------------			
			i++;
			System.out.println("\n NASTEPNY DZIAL \n");
			if((linkText.equals("Inne ...")))break;
		}//KONIEC:pobieranie nazw i linków do działów od Architektura do Inne
		
		
		
		
		//Napis końcowy
		System.out.println("kk");
		
	}
}


