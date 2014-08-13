//trzeba najpierw utworzyć baze danych o nazwie 'druga_baza' lub odpowiednio znaleźć w plikach 'Okno.java' i 'Okno2.java' 
i zamienić nazwe 'druga_baza' na inną baze danych:
		static final String DB_URL = "jdbc:mysql://localhost/druga_baza";

	
	1)	W plikach 'Okno.java' i 'Okno2.java' znaleźć i uzupełnić użytkownika i hasło do bazy danych:
			//user and password
			static final String USER = "user";
			static final String PASS = "password";


//Okno 'Okienko'
	2)	Aby wybrać plik trzeba wcisnąć guzik 'Wybierz Plik' i wybrać plik(opcjonalnie można wpisać
	nazwe pliku lub ścieżke do pliku) - wybrany plik musi być formatu .csv

	3) 	W polu tekstowym obok 'Wpisz nazwe tabeli' należy wpisać nazwe istniejącej już lub nowej tabeli

	4) Guzik 'Importuj Dane' pobiera dane do tabeli z podanego pliku w polu tekstowym obok 'Wybierz lub wpisz
	ścieżke pliku' i tworzy tabele o nazwie jaka jest w polu tekstowym obok 'Wpisz nazwe tabeli' jeśli 
	tabela jeszcze nie istnieje
	
	5) Guzik 'Wstaw do bazy' wstawia dane pobrane z pliku w polu tekstowym obok 'Wybierz lub wpisz ścieżke pliku' 
	do tabeli jaka jest w polu tekstowym obok 'Wpisz nazwe tabeli'
	
	6) Guzik 'Pokaż kolumny CSV' pokazuje w polu tekstowym obok 'CSV' jakie są kolumny w pliku w polu tekstowym 
	obok 'Wybierz lub wpisz ścieżke pliku' 

	7) Guzik 'Pokaż kolumny SQL' pokazuje w polu tekstowym obok 'SQL' jakie są kolumny w tabeli w polu tekstowym 
	obok 'Wpisz nazwe tabeli' 
	
	8) Pola tekstowe obok 'CSV' i 'SQL' pokazują również informacje jakie działania się wykonały poprawnie 
	a jakie błędnie
	
	9) Guzik 'Przyporządkuj kolumny' otwiera nowe okno w którym można odpowiednio zmienić kolejność kolumn pobranych
	po Guziku 'Importuj Dane'
	
	10) Guzik 'Wyjdz' sprawia tylko zniknięcie okna w celu zamknięcia programu należy tradycyjnie wcisnąć  'X' 

//Okno 'Przyporządkowanie kolumn'	
	11)	W polu tekstwoym obok 'Kolumna CSV do przyporządkowania' należy podać kolumne którą chcemy przesunąć
	
	12) W polu tekstwoym obok 'Kolumna SQL do przyporządkowania' należy podać kolumne na której miejsce 
	będzie przesunięta kolumna wybrana w pkt. 11) (dla bezpieczeństwa działania programu nie wybierać kolumn 
	id i data - kolumny tworzone automatycznie w pkt. 4))
	* jeśli nie pamiętasz nazw kolumn skorzystaj z pkt. 6) i 7)
		
	13)	Guzik 'Zapisz zmiany' zapisuje zmiany w tabeli 
	
	14) Guzik 'Wstaw do bazy' wstawia dane po zmianie do tabeli w bazie danych
	
	15) W polu tekstwoym obok 'Informacje' pojawiają się komunikaty z wykonywanych działań
	
	16) Wtym okienie zar ówno guzik 'Wyjdz' jak i  'X' sprawiają, że okno jest niewidzialne i nie powodują zakończenia 
	programu  
	
* przy każdym wstawianiu do bazy danych sprawdzane jest czy istnieje w bazie kolumna kod pocztowy według patternu
"[Kk]od.[Pp]ocztowy" jeśli istnieje dokonywana jest walidacja kodu pocztowego zgodnie z patternem "[0-9]{2}-[0-9]{3}"
