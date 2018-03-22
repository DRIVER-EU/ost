---------------------------------------
---------- Table: answer_question_item ---------
---------------------------------------

CREATE TABLE public.answer_question_item
(
	id bigint NOT NULL,
	answer_id bigint NOT NULL,
	field_value varchar NOT NULL,

	CONSTRAINT answer_question_item_pkey PRIMARY KEY (id),
    CONSTRAINT fk5u83bhoj6e69p23anb6iocqdt FOREIGN KEY (answer_id)
        REFERENCES public.answer (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.answer_question_item_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

