---------------------------------------
---------- Table: event ---------------
---------------------------------------

CREATE TABLE public.event
(
    id bigint NOT NULL,
    trial_session_id bigint NOT NULL,
    description varchar NOT NULL,
	event_id int NOT NULL,
	language_version varchar NOT NULL,
	name varchar(50) NOT NULL,
	event_time timestamp without time zone NOT NULL,

	CONSTRAINT event_pkey PRIMARY KEY (id),
    CONSTRAINT fktikaqpo3y5yxf0h6jecltd0v3 FOREIGN KEY (trial_session_id)
        REFERENCES public.trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.event_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

