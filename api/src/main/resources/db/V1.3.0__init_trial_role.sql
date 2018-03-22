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

