-- ========================================================
-- Zadanie 1: Tworzenie bazy danych i użytkownika
-- ========================================================
CREATE DATABASE aparaty; -- Tworzymy bazę danych
USE aparaty;             -- Wybieramy bazę danych do pracy

-- Tworzenie użytkownika (nazwany numerem albumu) z hasłem (nazwisko + ostatnie 2 cyfry albumu)
CREATE USER '283958'@'localhost' IDENTIFIED BY 'Smuga58';
-- Nadanie uprawnień: SELECT, INSERT, UPDATE
GRANT SELECT, INSERT, UPDATE ON aparaty.* TO '283958'@'localhost';
FLUSH PRIVILEGES; -- Odświeżamy uprawnienia

-- ========================================================
-- Zadanie 2: Tworzenie tabel
-- ========================================================

-- Tabela Producent
CREATE TABLE Producent (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    nazwa VARCHAR(50) NOT NULL,
    kraj VARCHAR(20) NOT NULL DEFAULT 'nieznany',
    adresKorespondencyjny VARCHAR(100) NOT NULL DEFAULT 'nieznany'
);

-- Tabela Matryca
CREATE TABLE Matryca (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    przekatna DECIMAL(4,2) UNSIGNED,
    rozdzielczosc DECIMAL(3,1) UNSIGNED,
    typ VARCHAR(10)
) AUTO_INCREMENT=100; -- start ID od 100

-- Tabela Obiektyw
CREATE TABLE Obiektyw (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(30),
    minPrzeslona FLOAT UNSIGNED,
    maxPrzeslona FLOAT UNSIGNED,
    CHECK (minPrzeslona < maxPrzeslona)
);

-- Tabela Aparat
CREATE TABLE Aparat (
    model VARCHAR(30) PRIMARY KEY,
    producent INT,
    matryca INT,
    obiektyw INT,
    waga FLOAT UNSIGNED,
    typ ENUM('kompaktowy','lustrzanka','profesjonalny','inny'),
    FOREIGN KEY (producent) REFERENCES Producent(ID),
    FOREIGN KEY (matryca) REFERENCES Matryca(ID),
    FOREIGN KEY (obiektyw) REFERENCES Obiektyw(ID)
);

-- ========================================================
-- Zadanie 3: Dodawanie przykładowych danych
-- ========================================================

-- Dodanie 5 producentów z Chin
INSERT INTO Producent (nazwa, kraj, adresKorespondencyjny) VALUES
('Canon China','Chiny','Shanghai'),
('Nikon China','Chiny','Beijing'),
('Sony China','Chiny','Shenzhen'),
('Fujifilm China','Chiny','Shanghai'),
('Olympus China','Chiny','Beijing');

-- Dodanie 10 innych producentów
INSERT INTO Producent (nazwa) VALUES
('Canon'),('Nikon'),('Sony'),('Fujifilm'),('Olympus'),
('Panasonic'),('Leica'),('Pentax'),('Samsung'),('Kodak');

-- Dodanie przykładowych matryc
INSERT INTO Matryca (przekatna, rozdzielczosc, typ) VALUES
(1.0,12.3,'CMOS'),(1.2,16.1,'CMOS'),(1.5,24.2,'CCD'),
(2.0,20.1,'CMOS'),(2.3,18.0,'CMOS'),(1.0,10.0,'CCD'),
(1.8,14.2,'CMOS'),(1.6,12.0,'CMOS'),(1.4,15.0,'CMOS'),
(1.2,8.0,'CCD'),(1.5,24.0,'CMOS'),(2.0,21.0,'CMOS'),
(2.3,22.0,'CMOS'),(1.0,12.0,'CCD'),(1.8,16.0,'CMOS');

-- Dodanie przykładowych obiektywów
INSERT INTO Obiektyw (model, minPrzeslona, maxPrzeslona) VALUES
('Obiektyw A',2.8,5.6),('Obiektyw B',1.8,3.5),('Obiektyw C',3.5,6.3),
('Obiektyw D',2.0,4.0),('Obiektyw E',2.2,5.0),('Obiektyw F',1.4,2.8),
('Obiektyw G',2.8,6.3),('Obiektyw H',3.2,6.0),('Obiektyw I',1.8,4.0),
('Obiektyw J',2.0,5.6),('Obiektyw K',2.8,4.0),('Obiektyw L',1.8,3.5),
('Obiektyw M',3.5,5.6),('Obiektyw N',2.0,4.5),('Obiektyw O',2.8,5.6);

-- Dodanie przykładowych aparatów
INSERT INTO Aparat (model, producent, matryca, obiektyw, waga, typ) VALUES
('Model1',1,100,1,450,'lustrzanka'),
('Model2',2,101,2,380,'kompaktowy'),
('Model3',3,102,3,500,'profesjonalny'),
('Model4',4,103,4,420,'lustrzanka'),
('Model5',5,104,5,390,'inny'),
('Model6',6,105,6,430,'lustrzanka'),
('Model7',7,106,7,460,'profesjonalny'),
('Model8',8,107,8,370,'kompaktowy'),
('Model9',9,108,9,480,'lustrzanka'),
('Model10',10,109,10,400,'inny'),
('Model11',1,110,11,450,'profesjonalny'),
('Model12',2,111,12,420,'lustrzanka'),
('Model13',3,112,13,430,'kompaktowy'),
('Model14',4,113,14,460,'profesjonalny'),
('Model15',5,114,15,470,'lustrzanka');

-- ========================================================
-- Zadanie 4: Procedura generująca 100 nowych modeli
-- ========================================================
DELIMITER //
CREATE PROCEDURE generujAparaty()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE nowyModel VARCHAR(30);
    WHILE i <= 100 DO
        SET nowyModel = CONCAT('Model_auto_', i);
        INSERT IGNORE INTO Aparat (model, producent, matryca, obiektyw, waga, typ)
        SELECT nowyModel, FLOOR(1 + RAND()*15), 100 + FLOOR(RAND()*15), 1 + FLOOR(RAND()*15), 400 + FLOOR(RAND()*100), 'inny';
        SET i = i + 1;
    END WHILE;
END;
//
DELIMITER ;

-- ========================================================
-- Zadanie 5: Funkcja/procedura – aparat producenta z najmniejszą matrycą
-- ========================================================
DELIMITER //
CREATE FUNCTION aparatNajmniejszaMatryca(pID INT) RETURNS VARCHAR(30)
BEGIN
    DECLARE modelA VARCHAR(30);
    SELECT a.model INTO modelA
    FROM Aparat a
    JOIN Matryca m ON a.matryca = m.ID
    WHERE a.producent = pID
    ORDER BY m.przekatna ASC
    LIMIT 1;
    RETURN modelA;
END;
//
DELIMITER ;

-- ========================================================
-- Zadanie 6: Trigger – dodanie producenta jeśli nie istnieje
-- ========================================================
DELIMITER //
CREATE TRIGGER trigger_dodaj_producent
BEFORE INSERT ON Aparat
FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Producent WHERE ID = NEW.producent) THEN
        INSERT INTO Producent (nazwa) VALUES ('Nowy producent');
    END IF;
END;
//
DELIMITER ;

-- ========================================================
-- Zadanie 7: Funkcja – liczba aparatów dla matrycy
-- ========================================================
DELIMITER //
CREATE FUNCTION liczbaAparatowDlaMatrycy(pID INT) RETURNS INT
BEGIN
    DECLARE liczba INT;
    SELECT COUNT(*) INTO liczba FROM Aparat WHERE matryca = pID;
    RETURN liczba;
END;
//
DELIMITER ;

-- ========================================================
-- Zadanie 8: Trigger – usuwanie matrycy jeśli brak aparatów
-- ========================================================
DELIMITER //
CREATE TRIGGER trigger_usun_matryce
AFTER DELETE ON Aparat
FOR EACH ROW
BEGIN
    DELETE FROM Matryca WHERE ID = OLD.matryca
    AND NOT EXISTS (SELECT 1 FROM Aparat WHERE matryca = OLD.matryca);
END;
//
DELIMITER ;

-- ========================================================
-- Zadanie 9: Widok – lustrzanki poza Chinami
-- ========================================================
CREATE VIEW WidokLustrzanki AS
SELECT a.model, a.waga, p.nazwa AS producent, m.przekatna, m.rozdzielczosc,
       o.minPrzeslona, o.maxPrzeslona
FROM Aparat a
JOIN Producent p ON a.producent = p.ID
JOIN Matryca m ON a.matryca = m.ID
JOIN Obiektyw o ON a.obiektyw = o.ID
WHERE a.typ='lustrzanka' AND p.kraj<>'Chiny';

-- ========================================================
-- Zadanie 10: Widok – nazwa producenta, kraj, model
-- ========================================================
CREATE VIEW WidokAparaty AS
SELECT p.nazwa, p.kraj, a.model
FROM Aparat a
JOIN Producent p ON a.producent = p.ID;

-- Usunięcie aparatów producentów z Chin
DELETE FROM Aparat WHERE producent IN (SELECT ID FROM Producent WHERE kraj='Chiny');
-- Widok automatycznie odzwierciedla zmiany – modele z Chin znikną z WidokAparaty

-- ========================================================
-- Zadanie 11: Dodanie kolumny liczbaModeli i trigger aktualizujący
-- ========================================================
ALTER TABLE Producent ADD COLUMN liczbaModeli INT NOT NULL DEFAULT 0;

-- Inicjalizacja liczby modeli
UPDATE Producent p
SET liczbaModeli = (SELECT COUNT(*) FROM Aparat a WHERE a.producent = p.ID);

-- Trigger po dodaniu nowego aparatu
DELIMITER //
CREATE TRIGGER trigger_update_liczba_modeli_insert
AFTER INSERT ON Aparat
FOR EACH ROW
BEGIN
    UPDATE Producent SET liczbaModeli = liczbaModeli + 1 WHERE ID = NEW.producent;
END;
//

-- Trigger po usunięciu aparatu
CREATE TRIGGER trigger_update_liczba_modeli_delete
AFTER DELETE ON Aparat
FOR EACH ROW
BEGIN
    UPDATE Producent SET liczbaModeli = liczbaModeli - 1 WHERE ID = OLD.producent;
END;
//

-- Trigger po zmianie producenta w Aparat
CREATE TRIGGER trigger_update_liczba_modeli_update
AFTER UPDATE ON Aparat
FOR EACH ROW
BEGIN
    IF OLD.producent <> NEW.producent THEN
        UPDATE Producent SET liczbaModeli = liczbaModeli - 1 WHERE ID = OLD.producent;
        UPDATE Producent SET liczbaModeli = liczbaModeli + 1 WHERE ID = NEW.producent;
    END IF;
END;
//
DELIMITER ;
