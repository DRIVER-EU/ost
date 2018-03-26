---------------------------------------
---------- Table: trial ---------------
---------------------------------------

CREATE TABLE public.trial
(
	id bigint NOT NULL,
	description varchar NOT NULL,
    language_version varchar NOT NULL,
	name varchar(50) NOT NULL,
	is_defined boolean NOT NULL,

	CONSTRAINT trial_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.trial_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

