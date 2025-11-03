SELECT DISTINCT f.title
FROM film f
JOIN inventory i ON f.film_id = i.film_id
JOIN rental r ON i.inventory_id = r.inventory_id
WHERE r.rental_date BETWEEN '2005-05-31' AND '2005-06-30'
ORDER BY f.title DESC;
