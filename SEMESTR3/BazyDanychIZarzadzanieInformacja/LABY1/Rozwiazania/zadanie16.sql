SELECT c.customer_id, c.first_name, c.last_name
FROM customer c
JOIN payment p ON c.customer_id = p.customer_id
GROUP BY c.customer_id
HAVING AVG(p.amount) < (
    SELECT AVG(amount)
    FROM payment
    WHERE payment_date = '2005-07-30'
);
