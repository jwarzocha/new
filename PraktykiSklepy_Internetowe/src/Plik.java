import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.csvreader.CsvReader;


public class Plik {
	public static void main(String[] args) throws IOException 
	{
		
			Sklepy[] sklepy = new Sklepy[562];
	        for(int i=0;i<sklepy.length;i++) sklepy[i]=new Sklepy();
			
		    int zlicz=0;
	        String bledneStrony="id,www,nip,blad,\r\n";
	        String czySklep="id,www,nip,czy sklep internetowy,\r\n";
	        String pom ="INTERIA.PL - Polska i świat: informacje, sport, gwiazdy";
	        
	        CsvReader csvReader = new CsvReader("www_dosprawdzenia.csv");
			csvReader.readHeaders();

			while (csvReader.readRecord())
			{			
				sklepy[zlicz].id=csvReader.get("id");		System.out.print(sklepy[zlicz].id+"\t");
		    	sklepy[zlicz].www=csvReader.get("www");		System.out.print(sklepy[zlicz].www+"\t\t\t\t");
		    	sklepy[zlicz].nip=csvReader.get("nip");		System.out.println(sklepy[zlicz].nip);
	
		    	String adres="http://"+sklepy[zlicz].www;
		    	try {
			    	Document Doc = Jsoup.connect(adres).timeout(0).get();
					String StringKoduStrony = Doc.toString();
//					Elements links = Doc.getElementsByTag("a");
//					for (Element link : links ) 
//					{
//						String linkText = link.text();
//						if(linkText.equals("Koszyk")){
//							czySklep =  czySklep+sklepy[zlicz].id+","+sklepy[zlicz].www+","+
//	                				sklepy[zlicz].nip+",tak,\r\n";break;
//						}
//						else{
//							czySklep =  czySklep+sklepy[zlicz].id+","+sklepy[zlicz].www+","+
//	                				sklepy[zlicz].nip+",nie,\r\n";break;
//						}
//					}
					
					int index = StringKoduStrony.indexOf("Koszyk");
					int indexPom = StringKoduStrony.indexOf(pom);
					
					if(index==-1){
						czySklep =  czySklep+sklepy[zlicz].id+","+sklepy[zlicz].www+","+
                				sklepy[zlicz].nip+",nie,\r\n";
					}
					else if((indexPom!=-1)){
						czySklep =  czySklep+sklepy[zlicz].id+","+sklepy[zlicz].www+","+
                				sklepy[zlicz].nip+",nie,\r\n";
					}
					else{		
						
						czySklep =  czySklep+sklepy[zlicz].id+","+sklepy[zlicz].www+","+
                				sklepy[zlicz].nip+",tak,\r\n";
					}
					//System.out.println("\n"+StringKoduStrony+"\n");
		    	} catch (IOException e) {
	                System.out.println("io - "+e);
	                bledneStrony =  bledneStrony+sklepy[zlicz].id+","+sklepy[zlicz].www+","+
	                				sklepy[zlicz].nip+","+e+",\r\n";
	            }
				System.out.println("\n"+zlicz+"\n");
				zlicz++;
			}
			csvReader.close();
			
			PrintWriter poprawne = new PrintWriter("poprawneStrony.csv");
			poprawne.println(czySklep);
			poprawne.close();
			
			PrintWriter bledne = new PrintWriter("bledneStrony.csv");
			bledne.println(bledneStrony);
			bledne.close();
			
			
			
			
			
			System.out.println("kk");
	}
}
