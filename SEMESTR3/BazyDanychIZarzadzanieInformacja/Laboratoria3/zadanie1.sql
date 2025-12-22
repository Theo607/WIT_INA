-- Ustawienie daty referencyjnej na dzień 2025-12-08
SET @DATA_REFERENCYJNA = '2025-12-08';

-- PESEL jest unikalny dla każdej osoby, co jest pożądane dla klucza głównego.
-- Jednakże, jest to klucz "naturalny", który może zawierać dane osobowe i
-- zmieniać się w bardzo rzadkich, ale możliwych przypadkach (np. sprostowanie pomyłki).
-- Lepszą praktyką w bazach danych jest użycie sztucznego, prostego klucza numerycznego
-- jako klucza głównego, a PESEL pozostawić jako unikalny indeks.

-- 1. Utworzenie i wybór nowej bazy danych
CREATE DATABASE IF NOT EXISTS ZadanieBD;
USE ZadanieBD;

---
-- ZADANIE 1.1: Tworzenie tabel z ograniczeniami integralności
---

-- Tabela Ludzie (osoba_id jako PK, PESEL jako UNIQUE z CHECK)
CREATE TABLE Ludzie (
    osoba_id INT AUTO_INCREMENT PRIMARY KEY,
    PESEL CHAR(11) NOT NULL UNIQUE
        CHECK (LENGTH(PESEL) = 11 AND PESEL REGEXP '^[0-9]+$'),
    imie VARCHAR(30) NOT NULL,
    nazwisko VARCHAR(30) NOT NULL,
    data_urodzenia DATE NOT NULL,
    plec ENUM('K', 'M') NOT NULL
);

-- Tabela Zawody (pensja_min >= 0, pensja_max > pensja_min)
CREATE TABLE Zawody (
    zawod_id INT AUTO_INCREMENT PRIMARY KEY,
    nazwa VARCHAR(50) NOT NULL UNIQUE,
    pensja_min FLOAT NOT NULL CHECK (pensja_min >= 0),
    pensja_max FLOAT NOT NULL CHECK (pensja_max > pensja_min)
);

-- Tabela Pracownicy (pensja >= 0)
CREATE TABLE Pracownicy (
    PESEL CHAR(11) PRIMARY KEY,
    zawod_id INT NOT NULL,
    pensja FLOAT NOT NULL CHECK (pensja >= 0),
    FOREIGN KEY (PESEL) REFERENCES Ludzie(PESEL),
    FOREIGN KEY (zawod_id) REFERENCES Zawody(zawod_id)
);

-- 1.2: Wypełnianie tabeli Zawody
INSERT INTO Zawody (nazwa, pensja_min, pensja_max) VALUES
('polityk', 6000.00, 15000.00),
('nauczyciel', 3500.00, 7000.00),
('lekarz', 8000.00, 25000.00),
('informatyk', 7000.00, 20000.00);

---
-- 1.3: Procedury i Kursory do wstawiania danych (45 + 5 + 5 osób)
---
DELIMITER //

-- Procedura do wstawiania 45 osób pełnoletnich (18-59 lat)
CREATE PROCEDURE WypelnijLudzie()
BEGIN
    DECLARE i INT DEFAULT 6;
    DECLARE rok INT;
    DECLARE imie_val VARCHAR(30);
    DECLARE plec_val ENUM('K', 'M');
    DECLARE pesel_val CHAR(11);
    DECLARE data_val DATE;

    WHILE i <= 50 DO
        SET imie_val = CASE WHEN i % 2 = 0 THEN 'Katarzyna' ELSE 'Marek' END;
        SET plec_val = CASE WHEN i % 2 = 0 THEN 'K' ELSE 'M' END;
        SET pesel_val = CONCAT(LPAD(i, 2, '0'), '01010', LPAD(i, 4, '0'));
        -- Rok urodzenia między 1966 a 2007
        SET rok = 2007 - FLOOR(RAND() * (2007 - 1966 + 1));
        SET data_val = CONCAT(rok, '-01-01');

        INSERT INTO Ludzie (PESEL, imie, nazwisko, data_urodzenia, plec) VALUES
        (pesel_val, imie_val, CONCAT('Testowy', i), data_val, plec_val);
        SET i = i + 1;
    END WHILE;
END//

-- Procedura do przypisywania zawodów (użycie kursora)
CREATE PROCEDURE PrzypiszZawody()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE p_PESEL CHAR(11);
    DECLARE p_plec ENUM('K', 'M');
    DECLARE p_data_urodzenia DATE;
    DECLARE v_zawod_id INT;
    DECLARE v_pensja_min FLOAT;
    DECLARE v_pensja_max FLOAT;
    DECLARE v_pensja FLOAT;
    DECLARE v_wiek INT;

    -- Kursor dla osób pełnoletnich
    DECLARE cur_ludzie CURSOR FOR
        SELECT PESEL, plec, data_urodzenia
        FROM Ludzie
        -- Pełnoletni: data_urodzenia <= 2025-12-08 - 18 lat
        WHERE data_urodzenia <= @DATA_REFERENCYJNA - INTERVAL 18 YEAR;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur_ludzie;

    read_loop: LOOP
        FETCH cur_ludzie INTO p_PESEL, p_plec, p_data_urodzenia;
        IF done THEN
            LEAVE read_loop;
        END IF;

        SET v_wiek = TIMESTAMPDIFF(YEAR, p_data_urodzenia, @DATA_REFERENCYJNA);
        SET v_zawod_id = FLOOR(1 + (RAND() * 4));

        -- Logika ograniczeń dla Lekarzy (zawod_id = 3)
        IF v_zawod_id = 3 THEN
            IF (p_plec = 'M' AND v_wiek > 65) OR (p_plec = 'K' AND v_wiek > 60) THEN
                SET v_zawod_id = 4; -- Zmiana na informatyka
            END IF;
        END IF;

        SELECT pensja_min, pensja_max INTO v_pensja_min, v_pensja_max
        FROM Zawody WHERE zawod_id = v_zawod_id;

        SET v_pensja = v_pensja_min + (RAND() * (v_pensja_max - v_pensja_min));

        INSERT INTO Pracownicy (PESEL, zawod_id, pensja) VALUES
        (p_PESEL, v_zawod_id, v_pensja);

    END LOOP;

    CLOSE cur_ludzie;
END//

DELIMITER ; -- Przywrócenie ogranicznika

-- Wstawianie 5 osób niepełnoletnich
INSERT INTO Ludzie (PESEL, imie, nazwisko, data_urodzenia, plec) VALUES
('08210100001', 'Anna', 'Kowalska', '2008-01-01', 'K'),
('09210100002', 'Piotr', 'Nowak', '2009-05-15', 'M'),
('10210100003', 'Zofia', 'Lis', '2010-10-20', 'K'),
('11210100004', 'Kacper', 'Wójcik', '2011-03-03', 'M'),
('12210100005', 'Maja', 'Zając', '2012-07-25', 'K');

-- Wstawianie 5 osób >= 60 lat
INSERT INTO Ludzie (PESEL, imie, nazwisko, data_urodzenia, plec) VALUES
('55010100001', 'Stanisław', 'Dziadek', '1955-01-01', 'M'),
('60020200002', 'Janina', 'Babcia', '1960-02-02', 'K'),
('65030300003', 'Wiesław', 'Senior', '1965-03-03', 'M'),
('64040400004', 'Alicja', 'Starsza', '1964-04-04', 'K'),
('63050500005', 'Roman', 'Emeryt', '1963-05-05', 'M');

-- Wywołanie procedur
CALL WypelnijLudzie();
CALL PrzypiszZawody();

-- Czyszczenie
DROP PROCEDURE WypelnijLudzie;
DROP PROCEDURE PrzypiszZawody;
