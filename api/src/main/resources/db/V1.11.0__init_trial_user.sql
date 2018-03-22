---------------------------------------
---------- Table: trial_user ----------
---------------------------------------

CREATE TABLE public.trial_user
(
    id bigint NOT NULL,
    auth_user_id bigint NOT NULL,
    user_language character varying(255) NOT NULL,

    CONSTRAINT trial_user_pkey PRIMARY KEY (id),
    CONSTRAINT fki9iitu6g5xyqb8gyexq3ef7q FOREIGN KEY (auth_user_id)
        REFERENCES public.auth_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.trial_user_seq
    START 1
    INCREMENT 1
    MINVALUE 1;

