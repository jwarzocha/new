DROP TABLE firmy;

CREATE TABLE firmy (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
nazwa_firmy VARCHAR(100),
wojewodztwo VARCHAR(30),
miejscowosc VARCHAR(30),
ulica VARCHAR(40),
kod_pocztowy VARCHAR(7),
osoba_kontaktowa VARCHAR(30),
telefon VARCHAR(20),
tel_kom VARCHAR(30),
adres_www VARCHAR(30),
nip VARCHAR(20),
regon VARCHAR(20),
zatrudnienie VARCHAR(20));
