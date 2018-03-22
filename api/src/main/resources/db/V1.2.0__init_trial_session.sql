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

