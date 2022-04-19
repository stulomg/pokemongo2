-- --------------------------------------------------------------
-- Tabla : Account
-- --------------------------------------------------------------
-- Table: public.Account
-- DROP TABLE IF EXISTS public."Accounts";

CREATE TABLE IF NOT EXISTS public."Account"
(
    id text COLLATE pg_catalog."default" NOT NULL,
    "accountId" text COLLATE pg_catalog."default" NOT NULL,
    puuid text COLLATE pg_catalog."default" NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    "profileIconId" integer NOT NULL,
    "revisionDate" bigint NOT NULL,
    "summonerLevel" bigint NOT NULL,
    owner text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "AccountBaseModel_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Account"
    OWNER to postgres;


-- --------------------------------------------------------------
-- Tabla : LegueInfo
-- --------------------------------------------------------------
-- Table: public.LeagueInfo
-- DROP TABLE IF EXISTS public."LeagueInfo";

CREATE TABLE IF NOT EXISTS public."LeagueInfo"
(
    date timestamp without time zone NOT NULL,
    "leagueId" text COLLATE pg_catalog."default" NOT NULL,
    "queueType" text COLLATE pg_catalog."default" NOT NULL,
    tier text COLLATE pg_catalog."default" NOT NULL,
    rank text COLLATE pg_catalog."default" NOT NULL,
    "summonerName" text COLLATE pg_catalog."default" NOT NULL,
    "LeaguePoints" integer NOT NULL,
    "Elo" integer NOT NULL,
    owner text COLLATE pg_catalog."default",
    CONSTRAINT "LeagueInfo_pkey" PRIMARY KEY (date)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."LeagueInfo"
    OWNER to postgres;

-- --------------------------------------------------------------
-- Tabla : AccountMasteryHistory
-- --------------------------------------------------------------
-- Table: public.AccountMasteryHistory
-- DROP TABLE IF EXISTS public."AccountMasteryHistory";

CREATE TABLE IF NOT EXISTS public."AccountMasteryHistory"
(
    "timeStamp" timestamp without time zone NOT NULL,
    "championId" bigint NOT NULL,
    "championName" text COLLATE pg_catalog."default" NOT NULL,
    "championPoints" integer NOT NULL,
    "championLevel" integer NOT NULL,
    "Account" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "AccountMasteryHistory_pkey" PRIMARY KEY ("timeStamp")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."AccountMasteryHistory"
    OWNER to postgres;

-- --------------------------------------------------------------
-- Tabla : Champion
-- --------------------------------------------------------------

-- Table: public.Champion
-- DROP TABLE IF EXISTS public."Champion";

CREATE TABLE IF NOT EXISTS public."Champion"
(
    "ChampionId" bigint NOT NULL,
    "ChampionName" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Champion_pkey" PRIMARY KEY ("ChampionId")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Champion"
    OWNER to postgres;

-- Table: public.role

-- DROP TABLE IF EXISTS public.role;

-- --------------------------------------------------------------
-- Tabla : Role
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.role
(
    id serial NOT NULL ,
    role_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.role
    OWNER to postgres;

-- --------------------------------------------------------------
-- Tabla : User
-- --------------------------------------------------------------
-- Table: public.user

-- DROP TABLE IF EXISTS public."user";

CREATE TABLE IF NOT EXISTS public."user"
(
    id serial NOT NULL ,
    name character varying(255) COLLATE pg_catalog."default",
    user_name character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_lqjrcobrh9jc8wpcar64q1bfh UNIQUE (user_name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : user_rol
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk6c4lt3635uhrcn2lxcf5edl92 FOREIGN KEY (user_id)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkje05j8n9jg14gbosrflgjcjk FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_role
    OWNER to postgres;