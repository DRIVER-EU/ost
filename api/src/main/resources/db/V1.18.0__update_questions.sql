DROP TABLE public.answer_question_item;
DROP TABLE public.question_item;

DELETE FROM public.question;

ALTER TABLE public.answer ADD COLUMN form_data text NOT NULL;
ALTER TABLE public.answer ADD COLUMN observation_type_id bigint NOT NULL;
ALTER TABLE public.answer ADD CONSTRAINT fkwot1ofibkvp27yof2zb168eip FOREIGN KEY (observation_type_id)
      REFERENCES public.observation_type (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE public.question ADD COLUMN json_schema text NOT NULL;

INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '1', 'Decide if there were any mistakes made', 'Mark good or poor awareness', 'RADIO_BUTTON',
    '{"title":"Mark good or poor awareness","description":"Decide if there were any mistakes made","type":"string","enum":["good awareness","poor awareness"]}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '2', 'Decide if there were any mistakes made', 'Mark good or poor communication', 'RADIO_BUTTON',
    '{"title":"Mark good or poor communication","description":"Decide if there were any mistakes made","type":"string","enum":["good communication","poor communication"]}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '1', 'Decide if there were any mistakes made', 'Because I observed...', 'TEXT_FIELD',
    '{"title":"Because I observed...","description":"Decide if there were any mistakes made","type":"string"}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '2', 'Decide if there were any mistakes made', 'Because I observed...', 'TEXT_FIELD',
    '{"title":"Because I observed...","description":"Decide if there were any mistakes made","type":"string"}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '1', 'Decide if there were any mistakes made', 'Because I observed...', 'CHECKBOX',
    '{"title":"Because I observed...","description":"Decide if there were any mistakes made","type":"boolean"}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '2', 'Decide if there were any mistakes made', 'Because I observed...', 'CHECKBOX',
    '{"title":"Because I observed...","description":"Decide if there were any mistakes made","type":"boolean"}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '3', 'Describe all problems you noticed', 'What problems they had while connecting with db?', 'TEXT_FIELD',
    '{"title":"What problems they had while connecting with db?","description":"Describe all problems you noticed","type":"string"}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '3', 'Decide how good or how weak it was', 'How do you feel about quality of connection?', 'SLIDER',
    '{"title":"How do you feel about quality of connection?","description":"Decide how good or how weak it was","type":"number","min":1,"max":10,"value":1,"step":1}');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type", "json_schema")
    VALUES (nextval('public.question_seq'), '3', 'Decide how long or how short it was', 'How long took connection with database?', 'SLIDER',
    '{"title":"How long took connection with database?","description":"Decide how long or how short it was","type":"number","min":1,"max":10,"value":1,"step":1}');
