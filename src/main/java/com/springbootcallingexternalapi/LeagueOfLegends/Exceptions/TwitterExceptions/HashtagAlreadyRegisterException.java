package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.TwitterExceptions;

/**Exception for an already registered hashtag. */
public class HashtagAlreadyRegisterException extends Exception {

  public HashtagAlreadyRegisterException(String hashtag) {
    super("The hashtag: " + hashtag + " was previously registered");
  }
}
