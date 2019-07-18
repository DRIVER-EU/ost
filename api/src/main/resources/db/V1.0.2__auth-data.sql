------------------------------------------------------------------
-- Authentication module initial DATA
------------------------------------------------------------------

---------------------------------
-- AuthRole
---------------------------------

INSERT INTO public.auth_role ("id", "short_name", "long_name", "position", "meta_uuid", "meta_lock")
    VALUES (nextval('public.auth_role_seq'), 'ROLE_ADMIN', 'Administrator', 1, 'bef9c7b5-079e-49ec-a60f-636d232bc5a0', 0);

---------------------------------
-- AuthUser
---------------------------------

INSERT INTO public.auth_user ("id", "login", "password", "first_name", "last_name", "email", "activated", "meta_created_at", "meta_deleted", "meta_uuid", "meta_lock")
    VALUES (nextval('public.auth_user_seq'), 'admin', '$2a$06$toZrZNp15elBurQefVYgsO..XzqFZbf3sDjYpsGaeKFFCbjrzSbtW',
        'Jan', 'Zandecki', 'jan.zandecki@perpixel.co', TRUE, CURRENT_TIMESTAMP, FALSE, '3235bc78-e837-4c50-bd19-44da04383ef5', 0);
INSERT INTO public.auth_user_m2m_roles ("auth_user_id", "auth_role_id")
    VALUES (
        (SELECT "id" FROM public.auth_user WHERE "login" = 'admin'),
        (SELECT "id" FROM public.auth_role WHERE "short_name" = 'ROLE_ADMIN')
    );

---------------------------------
-- AuthUserPosition
---------------------------------

INSERT INTO public.auth_user_position ("id", "name", "position", "meta_uuid", "meta_lock")
    VALUES (nextval('public.auth_user_position_seq'), 'Administrator', 1, '7db249c4-3d0d-4b27-a653-a9d80c1707ab', 0);

UPDATE public.auth_user
    SET "position_id" = (SELECT "id" FROM public.auth_user_position WHERE "name" = 'Administrator')
    WHERE "login" IN ('admin');

---------------------------------
-- AuthUnit
---------------------------------

INSERT INTO public.auth_unit ("id", "short_name", "long_name", "meta_created_by_id", "meta_created_at", "meta_deleted", "meta_uuid", "meta_lock")
    VALUES (nextval('public.auth_unit_seq'), 'default', 'Domyślna jednostka',
        (SELECT "id" FROM public.auth_user WHERE "login" = 'admin'), CURRENT_TIMESTAMP, FALSE, '15ddc7a6-5bbb-47b2-a332-32e8c1e55974', 0);

UPDATE public.auth_user
    SET "unit_id" = (SELECT "id" FROM public.auth_unit WHERE "short_name" = 'default')
    WHERE "login" IN ('admin');
