CREATE TABLE elsprage_words.words
(
    id bigserial NOT NULL,
    value varchar(200) NOT NULL,
    translation varchar(200) NOT NULL,
    value_language_id integer NOT NULL,
    translation_language_id integer NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE elsprage_words.language
(
    id serial NOT NULL,
    name varchar(50) NOT NULL,
    symbol varchar(10) NOT NULL,
    PRIMARY KEY (id)
);
