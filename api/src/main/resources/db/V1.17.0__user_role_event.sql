-------------------------------------------
-------- Table: user_role_event -----------
-------------------------------------------

CREATE TABLE public.user_role_event
(
    trial_user_id bigint NOT NULL,
    trial_role_id bigint NOT NULL,
    event_id bigint NOT NULL,

    CONSTRAINT user_role_event_pkey PRIMARY KEY(trial_user_id, trial_role_id, event_id),
    CONSTRAINT tm2234fgfg4354334h23iuh432iq FOREIGN KEY (trial_user_id)
        REFERENCES trial_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT a0dk3wosfg4354334h56ty4328iq FOREIGN KEY (trial_role_id)
        REFERENCES trial_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT vlo4e3osfg435ro4kw56ty4328iq FOREIGN KEY (event_id)
        REFERENCES event (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);