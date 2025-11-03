-- Dodanie hiszpańskiego
INSERT INTO language (name) VALUES ('Spanish');

-- Zmiana języka dla filmów z ED CHASE
UPDATE film f
JOIN film_actor fa ON f.film_id = fa.film_id
JOIN actor a ON fa.actor_id = a.actor_id
JOIN language l ON f.language_id = l.language_id
SET f.language_id = (SELECT language_id FROM language WHERE name = 'Spanish')
WHERE a.first_name = 'ED' AND a.last_name = 'CHASE';
