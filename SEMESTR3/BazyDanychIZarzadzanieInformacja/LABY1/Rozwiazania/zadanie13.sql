-- Pary aktorów (ID)
SELECT fa1.actor_id AS actor1, fa2.actor_id AS actor2, COUNT(*) AS films_together
FROM film_actor fa1
JOIN film_actor fa2 ON fa1.film_id = fa2.film_id AND fa1.actor_id < fa2.actor_id
GROUP BY fa1.actor_id, fa2.actor_id
HAVING COUNT(*) > 1;

-- Pary aktorów z imionami i nazwiskami
SELECT a1.first_name, a1.last_name, a2.first_name, a2.last_name, COUNT(*) AS films_together
FROM film_actor fa1
JOIN film_actor fa2 ON fa1.film_id = fa2.film_id AND fa1.actor_id < fa2.actor_id
JOIN actor a1 ON fa1.actor_id = a1.actor_id
JOIN actor a2 ON fa2.actor_id = a2.actor_id
GROUP BY a1.actor_id, a2.actor_id;
