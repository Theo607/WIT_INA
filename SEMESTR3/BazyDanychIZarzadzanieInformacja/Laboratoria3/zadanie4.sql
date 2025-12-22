USE ZadanieBD;

---
-- ZADANIE 4: PREPARE statement
---

-- Przygotowanie zapytania zwracającego liczbę kobiet w danym zawodzie
PREPARE stmt_liczba_kobiet FROM
    'SELECT COUNT(*)
     FROM Ludzie L
     JOIN Pracownicy P ON L.PESEL = P.PESEL
     JOIN Zawody Z ON P.zawod_id = Z.zawod_id
     WHERE L.plec = "K" AND Z.nazwa = ?';

-- Przykładowe wywołanie:
SET @zawod_nazwa = 'lekarz';
EXECUTE stmt_liczba_kobiet USING @zawod_nazwa;

-- Zwolnienie przygotowanego statementu
DEALLOCATE PREPARE stmt_liczba_kobiet;
