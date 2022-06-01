package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

/**
 * Exception for when a account has not Soloq data.
 */
public class QueueNotFoundException extends Exception {

  public QueueNotFoundException(String message) {
    super("The summoner has no history in the queue: " + message
        + ", or the summoner has been incorrectly inserted, please verify");
  }
}