import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class Okno2 extends JFrame{
	
	private JLabel labelKolumnaCSV, labelKolumnaSQL, labelPrzyporzadkowanie;
	private JTextField textfieldKolumnaCSV, textfieldKolumnaSQL, textfieldPrzyporzadkowanie;
	private JButton zapisz, wyjdz;
	
	private zapiszButtonHandler zapiszHandler;
    private wyjdz2ButtonHandler wyjdzHandler;
    
    public String kolumnaCSV;
    public String kolumnaSQL;
    public String rezultatPrzyporzadkowania;
    
 
    
	public  Okno2()
    {
		labelKolumnaCSV = new JLabel("Kolumna CSV do pryzporządkowania: ", SwingConstants.RIGHT);
		textfieldKolumnaCSV = new JTextField(10);
	        
		labelKolumnaSQL = new JLabel("Kolumna SQL do pryzporządkowania: ", SwingConstants.RIGHT);
		textfieldKolumnaSQL = new JTextField(10);
	        
		labelPrzyporzadkowanie = new JLabel("Przyporządkowanie: ", SwingConstants.RIGHT);
		textfieldPrzyporzadkowanie = new JTextField(10);
        
		zapisz = new JButton("Zapisz dane");
		zapiszHandler = new zapiszButtonHandler();
		zapisz.addActionListener(zapiszHandler);
        
        wyjdz = new JButton("Wyjdz");
        wyjdzHandler = new wyjdz2ButtonHandler();
        wyjdz.addActionListener(wyjdzHandler);
        
        setTitle("Przyporządkowanie kolumn");
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, 2));
        
		
        pane.add(labelKolumnaCSV);
        pane.add(textfieldKolumnaCSV);
        
        pane.add(labelKolumnaSQL);
        pane.add(textfieldKolumnaSQL);
        
        pane.add(labelPrzyporzadkowanie);
        pane.add(textfieldPrzyporzadkowanie);
        
        pane.add(zapisz);
        pane.add(wyjdz);
        
        setSize(1200, 300);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
	   //wyjscie button
    public class wyjdz2ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	setVisible(false); 
        	dispose();
        }
    }
    Okno pom= new Okno();
    Okno tablicaKolCSV[]= new Okno[1000000];
//    public String[] tablicaKolCSV = new String[100];
//    public String[] tablicaKolSQL = new String[100];  
    
    //zapisz button
    public class zapiszButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	kolumnaCSV = textfieldKolumnaCSV.getText();
        	kolumnaSQL = textfieldKolumnaSQL.getText();
        	System.out.println(pom.getiloscKolumn()); 
        	for(int i=0;i<pom.getrekord().length;i++){
        	System.out.println(pom.getrekord()); 
        	}
        	if(!(kolumnaCSV.equals(null))&!(kolumnaSQL.equals(null)))
        	{
        		
        	}
        	if(!(kolumnaCSV.equals(null))&(kolumnaSQL.equals(null)))
        	{
        		
        	}
        	       	
        	/*utwożyć tablice pomocnicza na kazdy wiersz, i pokolei zamieniać kolumny w kazdym wierszu osobno*/
            textfieldPrzyporzadkowanie.setText(rezultatPrzyporzadkowania);
        }
    }
    
}
