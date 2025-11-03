SELECT DISTINCT a.first_name, a.last_name
FROM actor a
JOIN film_actor fa ON a.actor_id = fa.actor_id
JOIN film f ON fa.film_id = f.film_id
JOIN film_feature ff ON f.film_id = ff.film_id
WHERE ff.special_features LIKE '%Deleted Scenes%';