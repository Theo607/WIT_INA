ALTER TABLE language ADD COLUMN films_no INT;

UPDATE language l
SET films_no = (
    SELECT COUNT(*)
    FROM film f
    WHERE f.language_id = l.language_id
);
