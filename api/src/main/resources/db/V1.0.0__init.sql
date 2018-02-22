---------------------------------------
---------- Table: message -----------
---------------------------------------

CREATE TABLE public.message
(
	id bigint NOT NULL,
	select_user varchar,
	role varchar,
    message varchar,
	date_time varchar,

	CONSTRAINT message_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.message_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: observation ---------
---------------------------------------

CREATE TABLE public.observation
(
	id bigint NOT NULL,
    name varchar,
	select_user varchar,
	role varchar,
    observation_type varchar,
	who varchar,
	what varchar,
	attachment varchar,
	date_time varchar,

	CONSTRAINT observation_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.observation_seq
    START 1
    INCREMENT 1
    MINVALUE 1;
