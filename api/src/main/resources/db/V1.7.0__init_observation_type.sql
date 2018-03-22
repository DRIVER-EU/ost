---------------------------------------
---------- Table: observation_type ----
---------------------------------------

CREATE TABLE public.observation_type
(
	id bigint NOT NULL,
	trial_id bigint NOT NULL,
    trial_stage_id bigint NOT NULL,
    trial_role_id bigint,
	description varchar NOT NULL,
	name varchar(50) NOT NULL,
    multiplicity boolean NOT NULL,

	CONSTRAINT observation_type_pkey PRIMARY KEY (id),
    CONSTRAINT fkfc50ecuo2xskui5mkb033fx1n FOREIGN KEY (trial_id)
          REFERENCES public.trial (id) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fkskbe7vkx9aqpbiga0x0o7wast FOREIGN KEY (trial_stage_id)
          REFERENCES public.trial_stage (id) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT qwerty7vkx9aqpbiga0x0o7wast FOREIGN KEY (trial_role_id)
              REFERENCES public.trial_stage (id) MATCH SIMPLE
              ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.observation_type_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

