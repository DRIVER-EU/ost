-------------------------------------------
---- Table: observation_type_trial_role ---
-------------------------------------------

CREATE TABLE public.observation_type_trial_role
(
    observation_type_id bigint NOT NULL,
    trial_role_id bigint NOT NULL,

    CONSTRAINT observation_type_trial_role_pkey PRIMARY KEY(observation_type_id, trial_role_id),
    CONSTRAINT tm2234fgfg4354334h23iuh234iq FOREIGN KEY (observation_type_id)
        REFERENCES public.observation_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT a0dk3wosfg4354334h56ty8234iq FOREIGN KEY (trial_role_id)
        REFERENCES public.trial_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);
