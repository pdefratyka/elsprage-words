ALTER TABLE elsprage_words.words
DROP CONSTRAINT unique_value_with_value_language_id;

ALTER TABLE elsprage_words.words
ADD CONSTRAINT unique_values
UNIQUE (value, translation, value_language_id, translation_language_id, example);