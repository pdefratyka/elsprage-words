ALTER TABLE elsprage_words.words
ADD CONSTRAINT unique_value_with_value_language_id
UNIQUE (value, value_language_id);