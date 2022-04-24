## 1) CREATE DATABASES!
For our project we implement that the databases are executed automatically.
In order to create them correctly, it is necessary to add annotations (#) to the flyway lines in the application.properties folder,
immediately execute the program, then remove them and run the program again.

Our service offer a lot of info about League of legends!, using localhost and urls! so, lets start with our League of legends features!.
But first of all, the port that you have to use is: "http://localhost:8080", so check if is avaliable for this api.
Dont Forget that this api works with a RIOT_TOKEN. Get yours here: "https://developer.riotgames.com" login and insert it into com.springbootcallingexternalapi.Services.RiotRequestorService


##LEAGUE OF LEGENDS
*In here you can consult a variety of stats so lets put them one by one!*

**Login**
To be able to access the functions of our project it is necessary to have an account that can be created with the following endpoint: "/auth/newUser"
the information you need to create the account are the following: name,userName,email and password.
and to log in for the use of the application, use the following endpoint: "/auth/login"

**Winner Winner Chicken Dinner**
First you need to add the accounts you want to compare, you can do it with the endpoint: "/call-riot/league/soloq/{account}/{owner}"

This function consults who is winning the competition between 2 owners
We define "winner" who reached the highes elo during the season
The url that provide this function is:
"/account/max-division/{owner}/{owner2}"

**Check Live Match**

This function consult the live match for an account.
The url that provide this function is:
"/call-riot/live/match/{account}"

where "{account}" is the SummonerName that you want to consult

**Most popular player with his most popular champion**

For this endpoint to work, it is necessary to add information on the following endpoints: /call-riot/mastery/{account}/{championName} 
and /call-riot/league/soloq/{account}/{owner}

This function consults who is the most popular player and get the most popular champion for this player
The url that provide this function is:
"/loldata/mostpopular"

**Server Status**

This function consult the status of your regional server.
The url that provide this function is:
"/call-riot/server/status"

**Clash**

Here we divide the functions into 3 parts with 3 different endpoints. 
The first one is: "/call-riot/clash/{account}" : this endpoint brings us the clash players and what position do they have.
As there is no clash at this time, the endpoint does not give an answer, but this is the response it should give:


{
"id": "2559537",
"tournamentId": 2803,
"name": "Lagrimas y risas",
"iconId": 7,
"tier": 1,
"captain": "C1vd0rZ2wP_QB4ITDolRg3j4UyHmzFc7hK8UkDKCa7IVBw",
"abbreviation": "jin",
"players": 

"summonerId": "AkNC60GpLAwQCKf41R0kpBM8D_QIDTrkMrN76Bvs2CFzGCI",
"position": "UTILITY",
"role": "MEMBER"


"summonerId": "1S1HEJI3r_WXyKKWYK5Ke1EZpMwizcF6ntciLxvWENyNnhk",
"position": "MIDDLE",
"role": "MEMBER"


"summonerId": "lDDmX6ji2FqtQCulNzEOe8Hq1YdBxNvxE7ByPC43FYfZTg",
"position": "TOP",
"role": "MEMBER"


"summonerId": "8QjCzyQqAyqZ5m1bg9CEp5zrIuCyJzT4WKy3ZPzYYrmoFvo70dp8yQibkw",
"position": "JUNGLE",
"role": "MEMBER"


"summonerId": "C1vd0rZ2wP_QB4ITDolRg3j4UyHmzFc7hK8UkDKCa7IVBw",
"position": "BOTTOM",
"role": "CAPTAIN"





-------------------------------------------------------------------------------------------------------------------

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

