-----------------------------------------
---------- Table: question_answer -------
-----------------------------------------

CREATE TABLE public.question_answer
(
  answer_id bigint NOT NULL,
  question_id bigint NOT NULL,

  CONSTRAINT question_answer_pkey PRIMARY KEY(question_id, answer_id),
  CONSTRAINT fkflwcda2rengsndju5f1deywok FOREIGN KEY (question_id)
      REFERENCES public.question (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkil3pbbv488omhx2gkakco46yl FOREIGN KEY (answer_id)
      REFERENCES public.answer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);