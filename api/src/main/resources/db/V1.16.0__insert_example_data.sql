INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Alice1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Alice', 'Zandecki', 'alice.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Mayer1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Mayer', 'Zandecki', 'mayer.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Jon1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Jon', 'Zandecki', 'jon.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Doe1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Doe', 'Zandecki', 'doe.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'User1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'User', 'Zandecki', 'user.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'Observer1', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Observer', 'Zandecki', 'observer.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);


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
        (SELECT "id" FROM public.auth_user WHERE "login" = 'Jon1'),
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