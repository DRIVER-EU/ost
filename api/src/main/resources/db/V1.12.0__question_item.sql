---------------------------------------
---------- Table: question_item -------
---------------------------------------

CREATE TABLE public.question_item
(
	id bigint NOT NULL,
    question_id bigint NOT NULL,
	name varchar(50) NOT NULL,

	CONSTRAINT question_item_pkey PRIMARY KEY (id),
    CONSTRAINT fkihye3qj9w91whtr2hoqrthuxk FOREIGN KEY (question_id)
        REFERENCES public.question (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.question_item_seq
    START 1
    INCREMENT 1
    MINVALUE 1;
