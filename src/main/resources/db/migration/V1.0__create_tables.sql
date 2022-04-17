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