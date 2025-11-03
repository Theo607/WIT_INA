    SELECT customer_id, COUNT(*) AS rentals_count
    FROM rental
    GROUP BY customer_id
    HAVING COUNT(*) > (
        SELECT COUNT(*)
        FROM rental r
        JOIN customer c ON r.customer_id = c.customer_id
        WHERE c.email = 'MARY.SMITH@sakilacustomer.org'
    )
