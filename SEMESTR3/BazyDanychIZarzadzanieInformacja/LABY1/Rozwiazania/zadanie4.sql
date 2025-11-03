SELECT f.title, length
FROM film f
WHERE f.rating = 'PG-13'
ORDER BY length ASC
LIMIT 4;
