---------------------------------------
---------- Table: trial_user ----------
---------------------------------------

CREATE TABLE public.trial_user
(
    id bigint NOT NULL,
    auth_user_id bigint NOT NULL,
    user_language character varying(255) NOT NULL,
    is_trial_creator boolean NOT NULL,

    CONSTRAINT trial_user_pkey PRIMARY KEY (id),
    CONSTRAINT fki9iitu6g5xyqb8gyexq3ef7q FOREIGN KEY (auth_user_id)
        REFERENCES public.auth_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.trial_user_seq
    START 1
    INCREMENT 1
    MINVALUE 1;


------------------------------------------------------
------------ Table: trial_creator ------------
------------------------------------------------------

CREATE TABLE public.trial_creator
(
  trial_user_id bigint NOT NULL,
  trial_id bigint NOT NULL,

  CONSTRAINT trial_creator_pkey PRIMARY KEY(trial_user_id, trial_id),
  CONSTRAINT fk2oxoomkaco1q71oxs3ntqk9vn FOREIGN KEY (trial_user_id)
      REFERENCES public.trial_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkstbhaddu99e3rssq39t4varv5 FOREIGN KEY (trial_id)
      REFERENCES public.trial (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


------------------------------------------------------
------------ Table: trial_session_manager ------------
------------------------------------------------------

CREATE TABLE public.trial_session_manager
(
  trial_user_id bigint NOT NULL,
  trial_session_id bigint NOT NULL,

  CONSTRAINT trial_session_manager_pkey PRIMARY KEY(trial_user_id, trial_session_id),
  CONSTRAINT fk2oxoomkaco1q71oxs3ntqk9vn FOREIGN KEY (trial_user_id)
      REFERENCES public.trial_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkstbhaddu99e3rssq39t4varv5 FOREIGN KEY (trial_session_id)
      REFERENCES public.trial_session (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);