---------------------------------------
---------- Table: question ------------
---------------------------------------

CREATE TABLE public.question
(
	id bigint NOT NULL,
    observation_type_id bigint NOT NULL,
	description varchar NOT NULL,
	name varchar(50) NOT NULL,
    answer_type varchar NOT NULL,

	CONSTRAINT question_pkey PRIMARY KEY (id),
    CONSTRAINT fk9s3t36iggg00ss7aasqh13cib FOREIGN KEY (observation_type_id)
        REFERENCES public.observation_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.question_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

