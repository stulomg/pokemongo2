package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.TwitterExceptions;

public class HashtagAlreadyRegisterException extends Exception{
    public HashtagAlreadyRegisterException (String hashtag){
        super("el hashtag: " + hashtag + " ya fue registrado previamente");
    }
}
