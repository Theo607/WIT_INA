-- Zadanie 3:

DELIMITER $$
CREATE PROCEDURE Podwyzka(IN nazwa_zawodu VARCHAR(50))
BEGIN
    DECLARE przekroczenie INT;
    START TRANSACTION;
    SELECT COUNT(*) INTO przekroczenie
    FROM Pracownicy p
    JOIN Zawody z ON p.zawod_id = z.zawod_id
    WHERE z.nazwa = nazwa_zawodu 
    AND p.pensja * 1.05 > z.pensja_max;
    IF przekroczenie = 0 THEN
        UPDATE Pracownicy p
        JOIN Zawody z ON p.zawod_id = z.zawod_id
        SET p.pensja = p.pensja * 1.05
        WHERE z.nazwa = nazwa_zawodu;
        COMMIT;
    ELSE
        ROLLBACK;
    END IF;
END$$
DELIMITER ;
