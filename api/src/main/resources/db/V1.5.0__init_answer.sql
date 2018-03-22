---------------------------------------
---------- Table: answer --------------
---------------------------------------

CREATE TABLE public.answer
(
	id bigint NOT NULL,
    trial_session_id bigint NOT NULL,
    trial_user_id bigint NOT NULL,
    sent_real_time int NOT NULL,
    simulation_time int NOT NULL,
    field_value varchar NOT NULL,

	CONSTRAINT answer_pkey PRIMARY KEY (id),
    CONSTRAINT fkgq4p5xvw0a7tueotl81gu4ti0 FOREIGN KEY (trial_session_id)
        REFERENCES public.trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT asdf4p5xvw0a7tueotl81gu4ti0 FOREIGN KEY (trial_user_id)
        REFERENCES public.trial_session (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.answer_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

