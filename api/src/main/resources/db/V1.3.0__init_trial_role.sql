---------------------------------------
---------- Table: trial_role ----------
---------------------------------------

CREATE TABLE public.trial_role
(
    id bigint NOT NULL,
    trial_id bigint NOT NULL,
    trial_user_id bigint NOT NULL,
    answer_id bigint,
    name varchar(50) NOT NULL,
    role_type varchar NOT NULL,

    CONSTRAINT trial_role_pkey PRIMARY KEY (id),
    CONSTRAINT asde50ecuo2xskui5mkb033fx1n FOREIGN KEY (trial_id)
        REFERENCES public.trial (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT asdf4p5asd0a7tueotl81gu4ti0 FOREIGN KEY (trial_user_id)
        REFERENCES public.trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT qwerty7vkx9asdfiga0x0o7wast FOREIGN KEY (answer_id)
        REFERENCES public.trial_stage (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.trial_role_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


-------------------------------------------
---------- Table: trial_role_m2m ----------
-------------------------------------------


CREATE TABLE public.trial_role_m2m_trial_role
(
    trial_role_id_a bigint NOT NULL,
    trial_role_id_b bigint NOT NULL,

    CONSTRAINT trial_role_m2m_pkey PRIMARY KEY(trial_role_id_a, trial_role_id_b),
    CONSTRAINT tm2234fgfg4354334h23iuh234iq FOREIGN KEY (trial_role_id_a)
        REFERENCES trial_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT a0dk3wosfg4354334h56ty8234iq FOREIGN KEY (trial_role_id_b)
        REFERENCES trial_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

