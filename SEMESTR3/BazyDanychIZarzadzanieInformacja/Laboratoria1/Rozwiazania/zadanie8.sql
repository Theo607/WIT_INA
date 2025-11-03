SELECT rating, COUNT(*) AS film_count
FROM film
WHERE rating IN ("PG-13", "R")
GROUP BY rating
HAVING film_count > 200;
