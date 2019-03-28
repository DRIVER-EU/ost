---------------------------------------
---------- Table: trial_role ----------
---------------------------------------

CREATE TABLE public.trial_role
(
    id bigint NOT NULL,
    trial_id bigint NOT NULL,
    name varchar(50) NOT NULL,
    role_type varchar NOT NULL,

    CONSTRAINT trial_role_pkey PRIMARY KEY (id),
    CONSTRAINT asde50ecuo2xskui5mkb033fx1n FOREIGN KEY (trial_id)
        REFERENCES public.trial (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.trial_role_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


-------------------------------------------
---------- Table: trial_role_m2m ----------
-------------------------------------------

CREATE TABLE public.trial_role_m2m
(
    trial_observer_id bigint NOT NULL,
    trial_participant_id bigint NOT NULL,

    CONSTRAINT trial_role_m2m_pkey PRIMARY KEY(trial_observer_id, trial_participant_id),
    CONSTRAINT tm2234fgfg4354334h23iuh234iq FOREIGN KEY (trial_observer_id)
        REFERENCES trial_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT a0dk3wosfg4354334h56ty8234iq FOREIGN KEY (trial_participant_id)
        REFERENCES trial_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

