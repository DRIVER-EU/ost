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

