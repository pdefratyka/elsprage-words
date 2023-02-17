CREATE TABLE elsprage_words.words
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    value varchar(200) NOT NULL,
    translation varchar(200) NOT NULL,
    value_language_id integer NOT NULL,
    translation_language_id integer NOT NULL,
    image varchar(400),
    sound varchar(400),
    example varchar(200),
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

CREATE TABLE elsprage_words.packets
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    value_language_id integer NOT NULL,
    translation_language_id integer NOT NULL,
    name varchar(400) NOT NULL,
    description varchar(400),
    created_at timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE elsprage_words.packets_words
(
    packet_id integer NOT NULL,
    word_id integer NOT NULL,
    PRIMARY KEY (packet_id,word_id)
);
