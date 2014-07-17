
public class Firmy {
	public Firmy(String nazwa_firmy, String wojewodztwo, String miejscowosc, String ulica, String kod_pocztowy, String osoba_kontaktowa, String telefon,String tel_kom, String adres_www, String zatrudnienie, String nip, String regon) 
	{
		this.nazwa_firmy = nazwa_firmy;
		this.wojewodztwo = wojewodztwo;
		this.miejscowosc = miejscowosc; 
		this.ulica =ulica;
		this.kod_pocztowy = kod_pocztowy;
		this.osoba_kontaktowa = osoba_kontaktowa;
		this.telefon = telefon;
		this.tel_kom = tel_kom;
		this.adres_www = adres_www;
		this.zatrudnienie = zatrudnienie;
		this.nip = nip;
		this.regon = regon;
	}
	
	private String nazwa_firmy;
	private String wojewodztwo;
	private String miejscowosc;
	private String ulica;
	private String kod_pocztowy;
	private String osoba_kontaktowa;
	private String telefon;
	private String tel_kom;
	private String adres_www;
	private String zatrudnienie;
	private String nip;
	private String regon;
	
	public String getnazwa_firmy() {
		return nazwa_firmy;
	}
	public void setnazwa_firmy(String nazwa_firmy) {
		this.nazwa_firmy = nazwa_firmy;
	}

	public String getwojewodztwo() {
		return wojewodztwo;
	}
	public void setwojewodztwo(String wojewodztwo) {
		this.wojewodztwo = wojewodztwo;
	}

	public String getmiejscowosc() {
		return miejscowosc;
	}
	public void setmiejscowosc(String miejscowosc) {
		this.miejscowosc = miejscowosc;
	}
	
	public String getulica() {
		return ulica;
	}
	public void setulica(String ulica) {
		this.ulica = ulica;
	}

	public String getkod_pocztowy() {
		return kod_pocztowy;
	}
	public void setkod_pocztowy(String kod_pocztowy) {
		this.kod_pocztowy = kod_pocztowy;
	}

	public String getosoba_kontaktowa() {
		return osoba_kontaktowa;
	}
	public void setosoba_kontaktowa(String osoba_kontaktowa) {
		this.osoba_kontaktowa = osoba_kontaktowa;
	}

	public String gettelefon() {
		return telefon;
	}
	public void settelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public String gettel_kom() {
		return tel_kom;
	}
	public void settel_kom(String tel_kom) {
		this.tel_kom = tel_kom;
	}
	
	public String getadres_www() {
		return adres_www;
	}
	public void setadres_www(String adres_www) {
		this.adres_www = adres_www;
	}
	
	public String getzatrudnienie() {
		return zatrudnienie;
	}
	public void setzatrudnienie(String zatrudnienie) {
		this.zatrudnienie = zatrudnienie;
	}
	
	public String getnip() {
		return nip;
	}
	public void setnip(String nip) {
		this.nip = nip;
	}
	
	public String getregon() {
		return regon;
	}
	public void setregon(String regon) {
		this.regon = regon;
	}
	
}
