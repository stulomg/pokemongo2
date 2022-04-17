## 1) CREATE DATABASES!

Let's start with our DB(Database), we will use PostgreSQL, and we will use pgAdmin4 for browsing and testing the DB.

1. Download pgAdmin4 and PostgreSQL.
2. open pgAdmin4, put on your local password assigned.
3. Click the **Databases** button and then Click **Schemas** -> **Tables** button.
4. Insert the following sql query so you can create each one of the tables:


Our service offer a lot of info about League of legends and Valorant stats!, using localhost and urls! so, lets start with our League of legends features!.
But first of all, the port that you have to use is: "http://localhost:8080", so check if is avaliable for this api.
Dont Forget that this api works with a RIOT_TOKEN. Get yours here: "https://developer.riotgames.com" login and insert it into com.springbootcallingexternalapi.Services.RiotRequestorService



##LEAGUE OF LEGENDS
*In here you can consult a variety of stats so lets put them one by one!*

**Check Live Match**

This function consult the live match for an account.
The url that provide this function is:
"/call-riot/live/match/{account}"

where "{account}" is the SummonerName that you want to consult

**Server Status**

This function consult the status of your regional server.
The url that provide this function is:
"/call-riot/server/status"

**Winner Winner Chicken Dinner**

This function consults who is winning the competition between 2 owners
We define "winner" who reached the highes elo during the season
The url that provide this function is:
"/account/division-comparison/{owner1}/{owner2}"

**Most popular player with his most popular champion**

This function consults who is the most popular player and get the most popular champion for this player
The url that provide this function is:
"/loldata/mostpopular"

-------------------------------------------------------------------------------------------------------------------


**AccountMasteryHistory**:

-- Table: public.AccountMasteryHistory

-- DROP TABLE IF EXISTS public."AccountMasteryHistory";

CREATE TABLE IF NOT EXISTS public."AccountMasteryHistory"
(
"timeStamp" timestamp without time zone NOT NULL,
"championId" bigint NOT NULL,
"championName" text COLLATE pg_catalog."default" NOT NULL,
"championPoints" integer NOT NULL,
"championLevel" integer NOT NULL,
CONSTRAINT "AccountMasteryHistory_pkey" PRIMARY KEY ("timeStamp")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."AccountMasteryHistory"
OWNER to postgres;

**Accounts**:

-- Table: public.Accounts

-- DROP TABLE IF EXISTS public."Accounts";

CREATE TABLE IF NOT EXISTS public."Accounts"
(
id text COLLATE pg_catalog."default" NOT NULL,
"accountId" text COLLATE pg_catalog."default" NOT NULL,
puuid text COLLATE pg_catalog."default" NOT NULL,
name text COLLATE pg_catalog."default" NOT NULL,
"profileIconId" integer NOT NULL,
"revisionDate" bigint NOT NULL,
"summonerLevel" integer NOT NULL,
owner text COLLATE pg_catalog."default" NOT NULL,
CONSTRAINT "AccountBaseModel_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Accounts"
OWNER to postgres;

**ChampionMasteries**:

-- Table: public.ChampionMasteries

-- DROP TABLE IF EXISTS public."ChampionMasteries";

CREATE TABLE IF NOT EXISTS public."ChampionMasteries"
(
id text COLLATE pg_catalog."default" NOT NULL,
name text COLLATE pg_catalog."default" NOT NULL,
"ChampionName" text COLLATE pg_catalog."default" NOT NULL,
"ChampionMastery" integer NOT NULL,
CONSTRAINT "ChampionMasteries_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."ChampionMasteries"
OWNER to postgres;

**Champions**:

-- Table: public.Champions

-- DROP TABLE IF EXISTS public."Champions";

CREATE TABLE IF NOT EXISTS public."Champions"
(
"ChampionId" bigint NOT NULL,
"ChampionName" text COLLATE pg_catalog."default" NOT NULL,
CONSTRAINT "Champions_pkey" PRIMARY KEY ("ChampionId")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Champions"
OWNER to postgres;

**LeagueInfo**:
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
CONSTRAINT "LeagueInfo_pkey" PRIMARY KEY (date)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."LeagueInfo"
OWNER to postgres;


6. Now you have the required tables on your DB!.

---


## Our features!

Our service offer a lot of info about League of legends and Valorant stats!, using localhost and urls! so, lets start with our League of legends features!.

##LEAGUE OF LEGENDS
*In here you can consult a variety of stats so lets put them one by one!*
##Account
1. First!, you can create your Owner, so you can register accounts!.

   Use the URL: http://localhost:8080/account/create


2. Consult the general info of an account and assign it to an owner!, take into account that you have to replace the word
      "account" with the summoner name you want to consult! and "owner" with your name.(if you are allowed to register accounts!)

   Use the URL: http://localhost:8080/call-riot/{account}/{owner}


3. Consult an account based only on the account name!, take into account that you have to replace the word
   "AccountName" with the summoner name you want to consult!.

   Use the URL: http://localhost:8080/account/find-by-name/{AccountName}


4. Consult the accounts that are assigned to an owner!, take into account that you have to replace the word
   "owner" with the name that you registered before.

   Use the URL: http://localhost:8080/account/find-by-owner/{owner}


5. Update an account!.

   Use the URL: http://localhost:8080/account/update


6. Delete an account!, remember to replace "owner" and "account" as instructed before.

Use the URL: http://localhost:8080/account/delete/{owner}/{account}

##Elo, Actual Division, League Points(LP), Mastery with specific champions
1. Consult the actual ELO or League of an account, take into account that you have to replace the word
"account" with the summoner name you want to consult!, you can omit upper case and lower case since Riot will search for it
automatically!.

   Use the URL http://localhost:8080/call-riot/league/soloq/{account}


3. Consult the division history of an account, take into account that you have to replace the word
   "account" with the summoner name you want to consult!.

   Use the URL: http://localhost:8080/account/division-history/{account}


6. Consult the mastery of an account with a specific champion!, take into account that you have to replace the word
   "account" with the summoner name you want to consult and "championName" with the champion name you want to get!.

   Use the URL: http://localhost:8080/call-riot/mastery/{account}/{championName}


7. Consult the mastery history of an account, take into account that you have to replace the word
   "account" with the summoner name you want to consult!.

   Use the URL: http://localhost:8080/account/masteryHistory/{account}

