package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.TwitterExceptions;

public class HashtagAlreadyRegisterException extends Exception {

  public HashtagAlreadyRegisterException(String hashtag) {
    super("The hashtag: " + hashtag + " was previously registered");
  }
}
