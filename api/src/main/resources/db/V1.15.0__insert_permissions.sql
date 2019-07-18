INSERT INTO public.auth_role(id, short_name, long_name)
    VALUES (2, 'ROLE_USER', 'User');
INSERT INTO public.auth_permission(id, short_name, long_name)
    VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO public.auth_permission(id, short_name, long_name)
    VALUES (2, 'ROLE_USER', 'User');
INSERT INTO public.auth_role_m2m_permissions(auth_role_id, auth_permission_id)
    VALUES (1, 1);
INSERT INTO public.auth_role_m2m_permissions(auth_role_id, auth_permission_id)
    VALUES (2, 2);
INSERT INTO public.auth_user_position(id, name, "position")
    VALUES (2, 'User', 2);
