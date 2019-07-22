------------------------------------------------------------------
-- Authentication module initial DATA
------------------------------------------------------------------

---------------------------------
-- AuthRole
---------------------------------

INSERT INTO public.auth_role ("id", "short_name", "long_name")
    VALUES (nextval('public.auth_role_seq'), 'ROLE_ADMIN', 'Administrator');

---------------------------------
-- AuthUser
---------------------------------

INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "created_at", "deleted", "activated")
    VALUES (nextval('public.auth_user_seq'), 'admin', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Jan', 'Zandecki', 'jan.zandecki@perpixel.co', CURRENT_TIMESTAMP, FALSE, TRUE);
INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'admin'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_ADMIN')
    );

---------------------------------
-- AuthUnit
---------------------------------

INSERT INTO public.auth_unit ("id", "short_name", "long_name", "created_by_id", "created_at", "deleted")
    VALUES (nextval('public.auth_unit_seq'), 'default', 'Domyślna jednostka',
        (SELECT "id" FROM public.auth_user WHERE "login" = 'admin'), CURRENT_TIMESTAMP, FALSE);

UPDATE public.auth_user
    SET "unit_id" = (SELECT "id" FROM public.auth_unit WHERE "short_name" = 'default')
    WHERE "login" IN ('admin');

---------------------------------
-- AuthPosition
---------------------------------

INSERT INTO public.auth_user_position ("id", "name", "position")
    VALUES (nextval('public.auth_user_position_seq'), 'Administrator', 1);

UPDATE public.auth_user
    SET "position_id" = (SELECT "id" FROM public.auth_user_position WHERE "name" = 'Administrator')
    WHERE "login" IN ('admin');