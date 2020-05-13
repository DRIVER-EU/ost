ALTER TABLE public.trial_user
ADD COLUMN keycloak_user_id VARCHAR,
DROP COLUMN auth_user_id;