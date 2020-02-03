---------------------------------------
---------- Table: question_option -------
---------------------------------------

CREATE TABLE public.question_option
(
	id bigint NOT NULL,
    question_id bigint NOT NULL,
	name varchar(200) NOT NULL,
    position integer NOT NULL DEFAULT 1,

	CONSTRAINT question_option_pkey PRIMARY KEY (id),
    CONSTRAINT question_option_question_fkey FOREIGN KEY (question_id)
        REFERENCES public.question (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.question_option_seq
    START 1
    INCREMENT 1
    MINVALUE 1;
