**Edit a file, create a new file, and clone from Bitbucket in under 2 minutes**

When you're done, you can delete the content in this README and update the file with details for others getting started with your repository.

*We recommend that you open this README in another tab as you perform the tasks below. You can [watch our video](https://youtu.be/0ocf7u76WSo) for a full demo of all the steps in this tutorial. Open the video in a new tab to avoid leaving Bitbucket.*

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: 
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

## 1) CREATE DATABASES!

Let's start with our DB(Database), we will use PostgreSQL, and we will use pgAdmin4 for browsing and testing the DB.

1. Download pgAdmin4 and PostgreSQL.
2. open pgAdmin4, put on your local password assigned.
3. Click the **Databases** button and then Click **Schemas** -> **Tables** button.
4. Insert the following sql query so you can create each one of the tables:


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


Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).