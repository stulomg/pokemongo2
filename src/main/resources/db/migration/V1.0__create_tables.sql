-- --------------------------------------------------------------
-- Tabla : Owner
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."Owner"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT "Owner_pkey" PRIMARY KEY (id),
    CONSTRAINT "UK_Owner" UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Owner"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : Position
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."Position"
(
    id serial NOT NULL,
    "namePosition" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Position_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Position"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : Account
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."Account"
(
    "id_BD" serial NOT NULL,
    id text COLLATE pg_catalog."default" NOT NULL,
    puuid text COLLATE pg_catalog."default" NOT NULL,
    accountid text COLLATE pg_catalog."default" NOT NULL,
    "revisionDate" bigint NOT NULL,
    owner integer NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Account_pkey" PRIMARY KEY ("id_BD"),
    CONSTRAINT "UK_nameAccount" UNIQUE (name),
    CONSTRAINT id_owner FOREIGN KEY (owner)
            REFERENCES public."Owner" (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
    )

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Account"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : Champion
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."Champion"
(
    "ChampionId" bigint NOT NULL,
    "ChampionName" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Champion_pkey" PRIMARY KEY ("ChampionId")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Champion"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : MasteryHistory
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."MasteryHistory"
(
    id serial NOT NULL,
    champion bigint NOT NULL,
    "championPoints" integer NOT NULL,
    "championLevel" integer,
    date timestamp with time zone NOT NULL,
    account integer NOT NULL,
    CONSTRAINT "MasteryHistory_pkey" PRIMARY KEY (id),
    CONSTRAINT id_account FOREIGN KEY (account)
        REFERENCES public."Account" ("id_BD") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT id_champion FOREIGN KEY (champion)
        REFERENCES public."Champion" ("ChampionId") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."MasteryHistory"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : LegueHistory
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."LeagueHistory"
(
    id serial NOT NULL,
    date timestamp with time zone NOT NULL,
    leagueid text COLLATE pg_catalog."default" NOT NULL,
    "queueType" text COLLATE pg_catalog."default" NOT NULL,
    tier text COLLATE pg_catalog."default" NOT NULL,
    rank text COLLATE pg_catalog."default" NOT NULL,
    "leaguePoints" integer NOT NULL,
    "Elo" integer NOT NULL,
    account integer NOT NULL,
    owner integer NOT NULL,
    CONSTRAINT "LeagueHistory_pkey" PRIMARY KEY (id),
    CONSTRAINT id_account FOREIGN KEY (account)
        REFERENCES public."Account" ("id_BD") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."LeagueHistory"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : MatchHistory
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."MatchHistory"
(
    id serial NOT NULL,
    account integer NOT NULL,
    "position" integer NOT NULL,
    champion integer NOT NULL,
    "championPoints" integer,
    win boolean NOT NULL,
    CONSTRAINT "MatchHistory_pkey" PRIMARY KEY (id),
    CONSTRAINT id_account FOREIGN KEY (account)
        REFERENCES public."Account" ("id_BD") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT id_champion FOREIGN KEY (champion)
        REFERENCES public."Champion" ("ChampionId") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT id_position FOREIGN KEY ("position")
        REFERENCES public."Position" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."MatchHistory"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : ServerStatus
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."ServerStatus"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    locales text[] COLLATE pg_catalog."default" NOT NULL,
    maintenances text[] COLLATE pg_catalog."default" NOT NULL,
    incidents text[] COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "ServerStatus_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."ServerStatus"
    OWNER to postgres;

-- --------------------------------------------------------------
-- Tabla : TwitterHashtag
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public."TwitterHashtag"
(
    id serial NOT NULL,
    "hashtagName" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "TwitterHashtag_pkey" PRIMARY KEY (id),
    CONSTRAINT "UK_hashtagName" UNIQUE ("hashtagName")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."TwitterHashtag"
    OWNER to postgres;
-- --------------------------------------------------------------
-- Tabla : SpecificQuery
-- --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public."SpecificQuery"
(
    id serial NOT NULL,
    criteria text COLLATE pg_catalog."default",
    query text COLLATE pg_catalog."default",
    CONSTRAINT "querySpecific_pkey" PRIMARY KEY (id),
    CONSTRAINT "UK_criteria" UNIQUE (criteria)
        INCLUDE(criteria)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."SpecificQuery"
    OWNER to postgres;

-- --------------------------------------------------------------
-- Tabla : Jugador
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public."Player"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Player_pkey" PRIMARY KEY (id),
    CONSTRAINT "UK_name" UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Player"
    OWNER to postgres;

-- --------------------------------------------------------------
-- Tabla : Account_Player
-- --------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public."Account_Player"
(
    "id_Account" integer NOT NULL,
    "id_Player" integer NOT NULL,
    CONSTRAINT "FK_AccountId" FOREIGN KEY ("id_Account")
        REFERENCES public."Account" ("id_BD") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT "FK_PlayerId" FOREIGN KEY ("id_Player")
        REFERENCES public."Player" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Account_Player"
    OWNER to postgres;