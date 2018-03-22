------------------------------------------------------
---------- Table: trial_session_trial_users ----------
------------------------------------------------------

CREATE TABLE public.trial_session_trial_users
(
  trial_sessions_id bigint NOT NULL,
  trial_users_id bigint NOT NULL,

  CONSTRAINT fk2oxoomkaco1q71oxs3ntqk9vn FOREIGN KEY (trial_users_id)
      REFERENCES public.trial_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkstbhaddu99e3rssq39t4varv5 FOREIGN KEY (trial_sessions_id)
      REFERENCES public.trial_session (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE public.trial_session_trial_users
     START 1
     INCREMENT 1
     MINVALUE 1;
