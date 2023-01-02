CREATE TABLE elsprage_words.words
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    value varchar(200) NOT NULL,
    translation varchar(200) NOT NULL,
    value_language_id integer NOT NULL,
    translation_language_id integer NOT NULL,
    image varchar(400) NOT NULL,
    sound varchar(400) NOT NULL,
    example varchar(200) NOT NULL,
    image_data bytea,
    PRIMARY KEY (id)
);

CREATE TABLE elsprage_words.languages
(
    id serial NOT NULL,
    name varchar(50) NOT NULL,
    symbol varchar(10) NOT NULL,
    PRIMARY KEY (id)
);
