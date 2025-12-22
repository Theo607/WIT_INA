USE ZadanieBD;

---
-- ZADANIE 2: Indeksy i zapytania SQL
---

-- Tworzenie indeksów
CREATE INDEX idx_ludzie_plec_imie ON Ludzie (plec, imie);
CREATE INDEX idx_pracownicy_pensja ON Pracownicy (pensja);

-- Polecenia inspekcyjne dla raportu:
SHOW INDEXES FROM Ludzie;
SHOW INDEXES FROM Pracownicy;
EXPLAIN SELECT * FROM Ludzie WHERE plec = 'K' AND imie LIKE 'A%';

-- 2.1: Kobiety, których imię zaczyna się na 'A'
SELECT * FROM Ludzie WHERE plec = 'K' AND imie LIKE 'A%';

-- 2.2: Wszystkie kobiety
SELECT * FROM Ludzie WHERE plec = 'K';

-- 2.3: Wszystkie osoby, których imię zaczyna się na 'K'
SELECT * FROM Ludzie WHERE imie LIKE 'K%';

-- 2.4: Wszystkie osoby zarabiające poniżej 2000
SELECT L.imie, L.nazwisko, P.pensja
FROM Ludzie L
JOIN Pracownicy P ON L.PESEL = P.PESEL
WHERE P.pensja < 2000;

-- 2.5: Wszyscy informatycy płci męskiej, zarabiający powyżej 10000
SELECT L.imie, L.nazwisko, Z.nazwa, P.pensja
FROM Ludzie L
JOIN Pracownicy P ON L.PESEL = P.PESEL
JOIN Zawody Z ON P.zawod_id = Z.zawod_id
WHERE L.plec = 'M'
  AND Z.nazwa = 'informatyk'
  AND P.pensja > 10000;

/*
--------------------------------------------------------------------------------
1. Jakie mamy obecnie indeksy założone dla obu tabel?
--------------------------------------------------------------------------------

a) Tabela Ludzie:
   - PRIMARY (osoba_id)
   - UNIQUE (PESEL)
   - idx_ludzie_plec_imie (plec, imie)

b) Tabela Pracownicy:
   - PRIMARY (PESEL)
   - FOREIGN KEY (zawod_id)
   - idx_pracownicy_pensja (pensja)


--------------------------------------------------------------------------------
2. W przypadku których zapytań optymalizator użyje indeksów?
--------------------------------------------------------------------------------

- Kobiety, imię na 'A' (plec = 'K' AND imie LIKE 'A%'):
  -> Użyje: idx_ludzie_plec_imie. Indeks złożony jest efektywny, gdy użyta jest pierwsza kolumna i prefiks drugiej.

- Wszystkie kobiety (plec = 'K'):
  -> Użyje: idx_ludzie_plec_imie. Może użyć indeksu złożonego, filtrując tylko po pierwszej kolumnie (plec).

- Wszystkie osoby, imię na 'K' (imie LIKE 'K%'):
  -> NIE użyje optymalnie: idx_ludzie_plec_imie. Indeks pomija pierwszą kolumnę (plec), co zmusza do pełnego skanowania.

- Zarabiający poniżej 2000:
  -> Użyje: idx_pracownicy_pensja. Jest to bezpośrednie użycie indeksu do filtrowania zakresu płac.

- Informatycy M, pensja > 10000:
  -> Użyje: idx_pracownicy_pensja (dla warunku pensja > 10000) oraz indeksów kluczy (PESEL, zawod_id) do efektywnego łączenia tabel.
*/
