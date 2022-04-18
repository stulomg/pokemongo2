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
