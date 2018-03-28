INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Alice1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Alice', 'Zandecki', 'alice.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Mayer1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Mayer', 'Zandecki', 'mayer.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'John1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'John', 'Zandecki', 'john.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Doe1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Doe', 'Zandecki', 'doe.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'User1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'User', 'Zandecki', 'user.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Observer1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Observer', 'Zandecki', 'observer.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
UPDATE public.auth_user SET "position_id" = '2', "unit_id" = '1';
UPDATE public.auth_user SET "position_id" = '1', "unit_id" = '1' WHERE "login" = 'admin';


INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'Alice1'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_USER')
    );
INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'Mayer1'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_USER')
    );
INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'John1'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_USER')
    );
INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'Doe1'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_USER')
    );
INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'User1'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_USER')
    );
INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'Observer1'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_USER')
    );


INSERT INTO public.trial ("id", "description", "language_version", "name", "is_defined")
    VALUES (nextval('public.trial_seq'), 'The Vessel X has already left from port of Gdynia with 1600 passengers '
    'on –board. Captain receives an emergency about  severe weather  conditions. Because of that one of its cooling '
    'system has failed 5 hours after departure.  The fire appears in the engine room. The crew have to call for '
    'external help. ', 'ENGLISH', 'Sea Disaster', TRUE);
INSERT INTO public.trial ("id", "description", "language_version", "name", "is_defined")
    VALUES (nextval('public.trial_seq'), 'Old building with 120 inhabitants started to burn because of failure of '
    'electrical installations. The fire is spreading very fast. One of inhabitants called for an external help. ',
    'ENGLISH', 'Building Fire', TRUE);


INSERT INTO public.trial_stage ("id", "trial_id", "name", "simulation_time")
    VALUES (nextval('public.trial_stage_seq'), '1', 'xxx', '2018-02-02 00:00');
INSERT INTO public.trial_stage ("id", "trial_id", "name", "simulation_time")
    VALUES (nextval('public.trial_stage_seq'), '2', 'yyy', '2018-03-03 03:33');


INSERT INTO public.trial_session ("id", "trial_id", "start_time", "status", "paused_time", "last_trial_stage_id")
    VALUES (nextval('public.trial_session_seq'), '1', '2018-01-01 12:23', 'STARTED', '2018-01-01 13:45', '1');
INSERT INTO public.trial_session ("id", "trial_id", "start_time", "status", "paused_time", "last_trial_stage_id")
    VALUES (nextval('public.trial_session_seq'), '1', '2018-01-01 14:22', 'STARTED', '2018-01-01 15:00', '1');
INSERT INTO public.trial_session ("id", "trial_id", "start_time", "status", "paused_time", "last_trial_stage_id")
    VALUES (nextval('public.trial_session_seq'), '2', '2018-01-01 11:08', 'STARTED', '2018-01-01 11:30', '2');
INSERT INTO public.trial_session ("id", "trial_id", "start_time", "status", "paused_time", "last_trial_stage_id")
    VALUES (nextval('public.trial_session_seq'), '2', '2018-01-01 12:40', 'STARTED', '2018-01-01 13:15', '2');


INSERT INTO public.event ("id", "trial_session_id", "description", "id_event", "language_version", "name", "event_time")
    VALUES (nextval('public.event_seq'), '1', 'New Event added', '1', 'ENGLISH', 'New Event', '2018-04-04 12:30');
INSERT INTO public.event ("id", "trial_session_id", "description", "id_event", "language_version", "name", "event_time")
    VALUES (nextval('public.event_seq'), '2', 'New Event added', '2', 'ENGLISH', 'Message', '2018-04-04 14:22');
INSERT INTO public.event ("id", "trial_session_id", "description", "id_event", "language_version", "name", "event_time")
    VALUES (nextval('public.event_seq'), '1', 'New Event added', '3', 'ENGLISH', 'New Channel', '2018-04-04 12:48');
INSERT INTO public.event ("id", "trial_session_id", "description", "id_event", "language_version", "name", "event_time")
    VALUES (nextval('public.event_seq'), '3', 'New Event added', '4', 'ENGLISH', 'Rescue', '2018-04-04 12:30');
INSERT INTO public.event ("id", "trial_session_id", "description", "id_event", "language_version", "name", "event_time")
    VALUES (nextval('public.event_seq'), '4', 'New Event added', '5', 'ENGLISH', 'Communication', '2018-04-04 12:30');
INSERT INTO public.event ("id", "trial_session_id", "description", "id_event", "language_version", "name", "event_time")
    VALUES (nextval('public.event_seq'), '3', 'New Event added', '6', 'ENGLISH', 'Evacuation', '2018-04-04 12:30');


INSERT INTO public.observation_type ("id", "trial_id", "trial_stage_id", "description", "name", "multiplicity")
    VALUES (nextval('public.observation_type_seq'), '1', '1', 'Good situational awareness is observed if location based'
    'information is well handled and no mistakes are made between (pieces of) information received. Poor situational'
    'awareness is observed when location based is missed, mixed up, incomplete or incorretly handled.',
    'Situational awareness', TRUE);
INSERT INTO public.observation_type ("id", "trial_id", "trial_stage_id", "description", "name", "multiplicity")
    VALUES (nextval('public.observation_type_seq'), '1', '1', 'Good communication is defined as information sharing in'
    'an effective manner by the sender and correct information reception by the receiver. Poor communication shows a'
    'lack in effectiveness, completeness and or incorrect understanding by the receiver.', 'Communication', TRUE);
INSERT INTO public.observation_type ("id", "trial_id", "trial_stage_id", "description", "name", "multiplicity")
    VALUES (nextval('public.observation_type_seq'), '2', '2', 'Questions collect data about the connection. Remind'
    'every problem you noticed.', 'Quality of Connection', TRUE);


INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type")
    VALUES (nextval('public.question_seq'), '1', 'Decide if there were any mistakes made',
    'Mark good or poor awareness', 'RADIO_BUTTON');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type")
    VALUES (nextval('public.question_seq'), '2', 'Decide if there were any mistakes made',
    'Mark good or poor communication', 'RADIO_BUTTON');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type")
    VALUES (nextval('public.question_seq'), '1', 'Decide if there were any mistakes made',
    'Because I observed…', 'TEXT_FIELD');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type")
    VALUES (nextval('public.question_seq'), '2', 'Decide if there were any mistakes made',
    'Because I observed…', 'TEXT_FIELD');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type")
    VALUES (nextval('public.question_seq'), '3', 'Describe all problems you noticed',
    'What problems they had while connecting with db?', 'TEXT_FIELD');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type")
    VALUES (nextval('public.question_seq'), '3', 'Decide how good or how weak it was',
    'How do you feel about quality of connection?', 'SLIDER');
INSERT INTO public.question ("id", "observation_type_id", "description", "name", "answer_type")
    VALUES (nextval('public.question_seq'), '3', 'Decide how long or how short it was ',
    'How long took connection with database?', 'SLIDER');


INSERT INTO public.trial_user ("id", "auth_user_id", "user_language", "is_trial_creator")
    VALUES (nextval('public.trial_user_seq'),
    (SELECT "id" FROM public.auth_user WHERE "login" = 'Alice1'),
    'ENGLISH', FALSE);
INSERT INTO public.trial_user ("id", "auth_user_id", "user_language", "is_trial_creator")
    VALUES (nextval('public.trial_user_seq'),
    (SELECT "id" FROM public.auth_user WHERE "login" = 'Mayer1'),
    'ENGLISH', FALSE);
INSERT INTO public.trial_user ("id", "auth_user_id", "user_language", "is_trial_creator")
    VALUES (nextval('public.trial_user_seq'),
    (SELECT "id" FROM public.auth_user WHERE "login" = 'John1'),
    'ENGLISH', FALSE);
INSERT INTO public.trial_user ("id", "auth_user_id", "user_language", "is_trial_creator")
    VALUES (nextval('public.trial_user_seq'),
    (SELECT "id" FROM public.auth_user WHERE "login" = 'Doe1'),
    'ENGLISH', FALSE);
INSERT INTO public.trial_user ("id", "auth_user_id", "user_language", "is_trial_creator")
    VALUES (nextval('public.trial_user_seq'),
    (SELECT "id" FROM public.auth_user WHERE "login" = 'User1'),
    'ENGLISH', TRUE);
INSERT INTO public.trial_user ("id", "auth_user_id", "user_language", "is_trial_creator")
    VALUES (nextval('public.trial_user_seq'),
    (SELECT "id" FROM public.auth_user WHERE "login" = 'Observer1'),
    'ENGLISH', TRUE);


INSERT INTO public.trial_role ("id", "trial_id", "name", "role_type")
    VALUES (nextval('public.trial_role_seq'), '1', 'obserwator', 'OBSERVER');
INSERT INTO public.trial_role ("id", "trial_id", "name", "role_type")
    VALUES (nextval('public.trial_role_seq'), '1', 'ratownik', 'PARTICIPANT');
INSERT INTO public.trial_role ("id", "trial_id", "name", "role_type")
    VALUES (nextval('public.trial_role_seq'), '1', 'ofiara', 'PARTICIPANT');
INSERT INTO public.trial_role ("id", "trial_id", "name", "role_type")
    VALUES (nextval('public.trial_role_seq'), '2', 'obserwator', 'OBSERVER');
INSERT INTO public.trial_role ("id", "trial_id", "name", "role_type")
    VALUES (nextval('public.trial_role_seq'), '2', 'ratownik', 'PARTICIPANT');
INSERT INTO public.trial_role ("id", "trial_id", "name", "role_type")
    VALUES (nextval('public.trial_role_seq'), '2', 'ofiara', 'PARTICIPANT');


INSERT INTO public.trial_role_m2m ("trial_observer_id", "trial_participant_id")
    VALUES (
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "role_type" = 'OBSERVER'),
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'ratownik')
    );
INSERT INTO public.trial_role_m2m ("trial_observer_id", "trial_participant_id")
    VALUES (
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "role_type" = 'OBSERVER'),
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'ofiara')
    );
INSERT INTO public.trial_role_m2m ("trial_observer_id", "trial_participant_id")
    VALUES (
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "role_type" = 'OBSERVER'),
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'ratownik')
    );
INSERT INTO public.trial_role_m2m ("trial_observer_id", "trial_participant_id")
    VALUES (
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "role_type" = 'OBSERVER'),
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'ofiara')
    );


INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('1',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'obserwator'),
        '1'
    );
INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('1',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'obserwator'),
        '3'
    );

INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('2',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'ratownik'),
        '1'
    );
INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('2',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'ratownik'),
        '3'
    );

INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('3',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'ofiara'),
        '1'
    );
INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('3',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'ofiara'),
        '3'
    );

INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('4',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'ofiara'),
        '1'
    );
INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('4',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'ofiara'),
        '3'
    );

INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('5',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'obserwator'),
        '1'
    );
INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('5',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'obserwator'),
        '3'
    );

INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('6',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '1' AND "name" = 'obserwator'),
        '1'
    );
INSERT INTO public.user_role_session ("trial_user_id", "trial_role_id", "trial_session_id")
    VALUES ('6',
        (SELECT "id" FROM public.trial_role WHERE "trial_id" = '2' AND "name" = 'obserwator'),
        '3'
    );


INSERT INTO public.trial_manager ("trial_user_id", "trial_id", "management_role")
    VALUES ('1', '1', 'SESSION_MANAGER');


INSERT INTO public.trial_session_manager ("trial_user_id", "trial_session_id")
    VALUES ('2', '2');
