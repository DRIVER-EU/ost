---------------------------------------
---------- Table: attachment ----------
---------------------------------------

CREATE TABLE public.attachment
(
	id bigint NOT NULL,
	name varchar NOT NULL,
	type varchar NOT NULL,

	CONSTRAINT attachment_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.attachment_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: event ---------------
---------------------------------------

CREATE TABLE public.event
(
    id bigint NOT NULL,
    description varchar NOT NULL,
	event_id int NOT NULL,
	"language" varchar NOT NULL,
	name varchar NOT NULL,
	"time" varchar NOT NULL,

	CONSTRAINT event_pkey PRIMARY KEY (id)
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
    sent_real_time int NOT NULL,
    "value" varchar NOT NULL,

	CONSTRAINT observation_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.observation_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: observation_type ----
---------------------------------------

CREATE TABLE public.observation_type
(
	id bigint NOT NULL,
	description varchar,
	name varchar NOT NULL,

	CONSTRAINT observation_type_pkey PRIMARY KEY (id)
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
	description varchar,
	name varchar NOT NULL,

	CONSTRAINT question_pkey PRIMARY KEY (id)
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
	answer_type varchar NOT NULL,
	"language" varchar NOT NULL,
	"value" varchar NOT NULL,

	CONSTRAINT question_item_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.question_item_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


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
---------- Table: trial_session -------
---------------------------------------

CREATE TABLE public.trial_session
(
	id bigint NOT NULL,
	start_time timestamp without time zone NOT NULL,
	status varchar NOT NULL,

	CONSTRAINT trial_session_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.trial_session_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: trial_stage ---------
---------------------------------------

CREATE TABLE public.trial_stage
(
	id bigint NOT NULL,
	name varchar NOT NULL,
	simulation_time timestamp without time zone NOT NULL,

	CONSTRAINT trial_stage_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.trial_stage_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

