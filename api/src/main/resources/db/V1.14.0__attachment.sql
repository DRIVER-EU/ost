---------------------------------------
---------- Table: attachment ----------
---------------------------------------

CREATE TABLE public.attachment
(
	id bigint NOT NULL,
    answer_id bigint NOT NULL,
	uri varchar NOT NULL,
	type varchar NOT NULL,

	CONSTRAINT attachment_pkey PRIMARY KEY (id),
    CONSTRAINT fk6nkewgbarvyv69wgy0nhu4i6c FOREIGN KEY (answer_id)
        REFERENCES public.answer (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.attachment_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

