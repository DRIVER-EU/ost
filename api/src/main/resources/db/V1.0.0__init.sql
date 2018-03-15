---------------------------------------
---------- Table: trial ---------------
---------------------------------------

CREATE TABLE public.trial
(
	id bigint NOT NULL,
	description varchar,
    "language" varchar NOT NULL,
	name varchar NOT NULL,

	CONSTRAINT trial_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.trial_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: trial_stage ---------
---------------------------------------

CREATE TABLE public.trial_stage
(
	id bigint NOT NULL,
	trial_id bigint NOT NULL,
	name varchar NOT NULL,
	simulation_time timestamp without time zone NOT NULL,

	CONSTRAINT trial_stage_pkey PRIMARY KEY (id),
	CONSTRAINT fk8hdr6y2t8ociyirupjmvp9mvn FOREIGN KEY (trial_id)
          REFERENCES public.trial (id) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.trial_stage_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: trial_session -------
---------------------------------------

CREATE TABLE public.trial_session
(
	id bigint NOT NULL,
    trial_id bigint NOT NULL,
	start_time timestamp without time zone NOT NULL,
	status varchar NOT NULL,

	CONSTRAINT trial_session_pkey PRIMARY KEY (id),
    CONSTRAINT fkakpayd7n2tpvsnmpkqb01o015 FOREIGN KEY (trial_id)
        REFERENCES public.trial (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.trial_session_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: event ---------------
---------------------------------------

CREATE TABLE public.event
(
    id bigint NOT NULL,
    trial_session_id bigint NOT NULL,
    description varchar NOT NULL,
	event_id int NOT NULL,
	"language" varchar NOT NULL,
	name varchar NOT NULL,
	"time" varchar NOT NULL,

	CONSTRAINT event_pkey PRIMARY KEY (id),
    CONSTRAINT fktikaqpo3y5yxf0h6jecltd0v3 FOREIGN KEY (trial_session_id)
        REFERENCES public.trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.event_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: message -------------
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
    trial_session_id bigint NOT NULL,
    sent_real_time int NOT NULL,
    "value" varchar NOT NULL,

	CONSTRAINT observation_pkey PRIMARY KEY (id),
    CONSTRAINT fkgq4p5xvw0a7tueotl81gu4ti0 FOREIGN KEY (trial_session_id)
        REFERENCES public.trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.observation_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: attachment ----------
---------------------------------------

CREATE TABLE public.attachment
(
	id bigint NOT NULL,
    observation_id bigint NOT NULL,
	name varchar NOT NULL,
	type varchar NOT NULL,

	CONSTRAINT attachment_pkey PRIMARY KEY (id),
    CONSTRAINT fk6nkewgbarvyv69wgy0nhu4i6c FOREIGN KEY (observation_id)
        REFERENCES public.observation (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.attachment_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: observation_type ----
---------------------------------------

CREATE TABLE public.observation_type
(
	id bigint NOT NULL,
	trial_id bigint NOT NULL,
    trial_stage_id bigint NOT NULL,
	description varchar,
	name varchar NOT NULL,

	CONSTRAINT observation_type_pkey PRIMARY KEY (id),
    CONSTRAINT fkfc50ecuo2xskui5mkb033fx1n FOREIGN KEY (trial_id)
          REFERENCES public.trial (id) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fkskbe7vkx9aqpbiga0x0o7wast FOREIGN KEY (trial_stage_id)
          REFERENCES public.trial_stage (id) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.observation_type_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: question ------------
---------------------------------------

CREATE TABLE public.question
(
	id bigint NOT NULL,
observation_type_id bigint NOT NULL,
	description varchar,
	name varchar NOT NULL,

	CONSTRAINT question_pkey PRIMARY KEY (id),
    CONSTRAINT fk9s3t36iggg00ss7aasqh13cib FOREIGN KEY (observation_type_id)
        REFERENCES public.observation_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.question_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: question_item -------
---------------------------------------

CREATE TABLE public.question_item
(
	id bigint NOT NULL,
    question_id bigint NOT NULL,
    observation_id bigint NOT NULL,
	answer_type varchar NOT NULL,
	"language" varchar NOT NULL,
	"value" varchar NOT NULL,

	CONSTRAINT question_item_pkey PRIMARY KEY (id),
    CONSTRAINT fk5u83bhoj6e69p23anb6iocqdt FOREIGN KEY (observation_id)
        REFERENCES public.observation (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fkihye3qj9w91whtr2hoqrthuxk FOREIGN KEY (question_id)
        REFERENCES public.question (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.question_item_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

