---------------------------------------
----- Table: answer_question_item -----
---------------------------------------

CREATE TABLE public.answer_question_item
(
	answer_id bigint NOT NULL,
	question_item_id bigint NOT NULL,
	field_value varchar NOT NULL,

	CONSTRAINT answer_question_item_pkey PRIMARY KEY (answer_id, question_item_id),
    CONSTRAINT fk5u83bhoj6e69p23anb6iocqdt FOREIGN KEY (answer_id)
        REFERENCES public.answer (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT a0dk3wosfg4354334h56ty8234iq FOREIGN KEY (question_item_id)
        REFERENCES public.question_item (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);
