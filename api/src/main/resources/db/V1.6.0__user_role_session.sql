-------------------------------------------
-------- Table: user_role_session ---------
-------------------------------------------

CREATE TABLE public.user_role_session
(
    trial_user_id bigint NOT NULL,
    trial_role_id bigint NOT NULL,
    trial_session_id bigint NOT NULL,

    CONSTRAINT user_role_session_pkey PRIMARY KEY(trial_user_id, trial_role_id, trial_session_id),
    CONSTRAINT tm2234fgfg4354334h23iuh234iq FOREIGN KEY (trial_user_id)
        REFERENCES trial_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT a0dk3wosfg4354334h56ty8234iq FOREIGN KEY (trial_role_id)
        REFERENCES trial_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT vlo4e3osfg435ro4kw56ty8234iq FOREIGN KEY (trial_session_id)
        REFERENCES trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);