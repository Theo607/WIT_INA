WITH italian_languages (
    language_id,
    name
) AS (
    SELECT language_id, name FROM language WHERE name = 'Italian'
)

UPDATE film f
JOIN language l ON f.language_id = l.language_id
SET f.language_id = italian_languages.language_id;
WHERE f.title = 'YOUNG LANGUAGE';
