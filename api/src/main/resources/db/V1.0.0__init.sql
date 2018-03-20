---------------------------------------
---------- Table: trial ---------------
---------------------------------------

CREATE TABLE public.trial
(
	id bigint NOT NULL,
	description varchar NOT NULL,
    "language" varchar NOT NULL,
	name varchar(50) NOT NULL,

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
	name varchar(50) NOT NULL,
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
	name varchar(50) NOT NULL,
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
---------- Table: answer --------------
---------------------------------------

CREATE TABLE public.answer
(
	id bigint NOT NULL,
    trial_session_id bigint NOT NULL,
    sent_real_time int NOT NULL,
    simulation_time int NOT NULL,
    "value" varchar NOT NULL,

	CONSTRAINT answer_pkey PRIMARY KEY (id),
    CONSTRAINT fkgq4p5xvw0a7tueotl81gu4ti0 FOREIGN KEY (trial_session_id)
        REFERENCES public.trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.answer_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


---------------------------------------
---------- Table: attachment ----------
---------------------------------------

CREATE TABLE public.attachment
(
	id bigint NOT NULL,
    answer_id bigint NOT NULL,
	name varchar(50) NOT NULL,
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


---------------------------------------
---------- Table: observation_type ----
---------------------------------------

CREATE TABLE public.observation_type
(
	id bigint NOT NULL,
	trial_id bigint NOT NULL,
    trial_stage_id bigint NOT NULL,
	description varchar NOT NULL,
	name varchar(50) NOT NULL,
    multiplicity boolean NOT NULL,

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


---------------------------------------
---------- Table: answer_item ---------
---------------------------------------

CREATE TABLE public.answer_item
(
	id bigint NOT NULL,
	answer_id bigint NOT NULL,
	"value" varchar NOT NULL,

	CONSTRAINT answer_item_pkey PRIMARY KEY (id),
    CONSTRAINT fk5u83bhoj6e69p23anb6iocqdt FOREIGN KEY (answer_id)
        REFERENCES public.answer (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.answer_item_seq
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
    answer_item_id bigint NOT NULL,
	"language" varchar NOT NULL,
	name varchar(50) NOT NULL,

	CONSTRAINT question_item_pkey PRIMARY KEY (id),
    CONSTRAINT fkihye3qj9w91whtr2hoqrthuxk FOREIGN KEY (question_id)
        REFERENCES public.question (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fkfph8skkk9223b3cavve118r1m FOREIGN KEY (answer_item_id)
        REFERENCES public.answer_item (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.question_item_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

