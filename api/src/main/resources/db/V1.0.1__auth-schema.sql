------------------------------------------------------------------
-- Authentication module initial SCHEMA
------------------------------------------------------------------

---------------------------------
-- Spring Session
---------------------------------
CREATE TABLE SPRING_SESSION (
	SESSION_ID CHAR(36),
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
);

CREATE INDEX SPRING_SESSION_IX1
    ON SPRING_SESSION (LAST_ACCESS_TIME);

---------------------------------
-- Spring Session Attribute
---------------------------------
CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_ID CHAR(36),
	ATTRIBUTE_NAME VARCHAR(200),
	ATTRIBUTE_BYTES BYTEA,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_ID)
	    REFERENCES SPRING_SESSION(SESSION_ID) ON DELETE CASCADE
);

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1
    ON SPRING_SESSION_ATTRIBUTES (SESSION_ID);

---------------------------------
-- AuthPermission
---------------------------------

CREATE TABLE public.auth_permission
(
  id bigint NOT NULL,
  short_name character varying(50) NOT NULL,
  long_name character varying(160),
  description text,
  "position" bigint NOT NULL DEFAULT 0,
  meta_uuid character varying(36) NOT NULL UNIQUE,
  meta_lock bigint NOT NULL DEFAULT 0,
  CONSTRAINT auth_permission_pkey PRIMARY KEY (id),
  CONSTRAINT uk_6x8jajgulmk3m553fpi14krfj UNIQUE (short_name)
);

CREATE SEQUENCE public.auth_permission_seq
    START 1
    INCREMENT 1;

---------------------------------
-- AuthRole
---------------------------------

CREATE TABLE public.auth_role
(
  id bigint NOT NULL,
  short_name character varying(50) NOT NULL,
  long_name character varying(160),
  description text,
  "position" bigint NOT NULL DEFAULT 0,
  meta_uuid character varying(36) NOT NULL UNIQUE,
  meta_lock bigint NOT NULL DEFAULT 0,
  CONSTRAINT auth_role_pkey PRIMARY KEY (id),
  CONSTRAINT uk_4h8u6nq6s510llrujaqdbjp7e UNIQUE (short_name)
);

CREATE SEQUENCE public.auth_role_seq
    START 1
    INCREMENT 1;

CREATE TABLE public.auth_role_m2m_permissions
(
  auth_role_id bigint NOT NULL,
  auth_permission_id bigint NOT NULL,
  CONSTRAINT auth_role_permissions_pkey PRIMARY KEY (auth_role_id, auth_permission_id),
  CONSTRAINT fk89g61iev2uiloyjqstrg9v309 FOREIGN KEY (auth_permission_id)
      REFERENCES public.auth_permission (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkqpjjejibce4a9vkti568iwlih FOREIGN KEY (auth_role_id)
      REFERENCES public.auth_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

---------------------------------
-- AuthUnit
---------------------------------

CREATE TABLE public.auth_unit
(
  id bigint NOT NULL,
  short_name character varying(50) NOT NULL,
  long_name character varying(160) NOT NULL,
  meta_uuid character varying(36) NOT NULL UNIQUE,
  meta_lock bigint NOT NULL DEFAULT 0,
  meta_created_at timestamp without time zone NOT NULL,
  meta_created_by_id bigint NOT NULL,
  meta_modified_at timestamp without time zone,
  meta_modified_by_id bigint,
  meta_deleted boolean NOT NULL DEFAULT FALSE,
  CONSTRAINT auth_unit_pkey PRIMARY KEY (id),
  CONSTRAINT uk_c06uyo2vs37t0y0gooiydr8vd UNIQUE (short_name)
);

CREATE SEQUENCE public.auth_unit_seq
    START 1
    INCREMENT 1;

---------------------------------
-- AuthUserPosition
---------------------------------

CREATE TABLE public.auth_user_position
(
  id bigint NOT NULL,
  name character varying(250) NOT NULL,
  "position" bigint NOT NULL DEFAULT 0,
  meta_uuid character varying(36) NOT NULL UNIQUE,
  meta_lock bigint NOT NULL DEFAULT 0,
  CONSTRAINT auth_user_position_pkey PRIMARY KEY (id),
  CONSTRAINT uk_gmwue1ua8tlrsue8wp6jm7nru UNIQUE (name)
);

CREATE SEQUENCE public.auth_user_position_seq
    START 1
    INCREMENT 1;

---------------------------------
-- AuthUser
---------------------------------

CREATE TABLE public.auth_user
(
  id bigint NOT NULL,
  login character varying(80) NOT NULL,
  password character varying(60) NOT NULL,
  first_name character varying(80) NOT NULL,
  last_name character varying(160) NOT NULL,
  avatar character varying(160),
  email character varying(160),
  contact text,
  last_login timestamp without time zone,
  activated boolean NOT NULL,
  position_id bigint,
  unit_id bigint,
  meta_uuid character varying(36) NOT NULL UNIQUE,
  meta_lock bigint NOT NULL DEFAULT 0,
  meta_created_at timestamp without time zone NOT NULL,
  meta_created_by_id bigint,
  meta_modified_at timestamp without time zone,
  meta_modified_by_id bigint,
  meta_deleted boolean NOT NULL DEFAULT FALSE,
  created_at timestamp without time zone,
  CONSTRAINT auth_user_pkey PRIMARY KEY (id),
  CONSTRAINT fk80m8klja3v5sg2nl438yorgsa FOREIGN KEY (position_id)
      REFERENCES public.auth_user_position (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk44dijt69v3yqhv330svs6b1dc FOREIGN KEY (unit_id)
      REFERENCES public.auth_unit (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk8oatpiog6o8mgn6b8w5tenecj FOREIGN KEY (meta_created_by_id)
      REFERENCES public.auth_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkgehx7era5rowq3s7cbnqnjc7l FOREIGN KEY (meta_modified_by_id)
      REFERENCES public.auth_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_g0m5h8gjfv1tjfo7jcqgcd46r UNIQUE (login),
  CONSTRAINT uk_klvc3dss72qnlrjp2bai055mw UNIQUE (email)
);

CREATE SEQUENCE public.auth_user_seq
    START 1
    INCREMENT 1;

CREATE TABLE public.auth_user_m2m_roles
(
  auth_user_id bigint NOT NULL,
  auth_role_id bigint NOT NULL,
  CONSTRAINT auth_user_roles_pkey PRIMARY KEY (auth_user_id, auth_role_id),
  CONSTRAINT fkhbc3c80g3ccqtscpy2thxexei FOREIGN KEY (auth_user_id)
      REFERENCES public.auth_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fksftdre91o487l8ujc2uqxngbm FOREIGN KEY (auth_role_id)
      REFERENCES public.auth_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE auth_unit
    ADD CONSTRAINT FKn063keevxoyb0uwwg4ddf5heo
    FOREIGN KEY (meta_created_by_id)
    REFERENCES auth_user;

ALTER TABLE auth_unit
    ADD CONSTRAINT FKhj2nf3pvhg2nhqhajkw6uop4v
    FOREIGN KEY (meta_modified_by_id)
    REFERENCES auth_user;
