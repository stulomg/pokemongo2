package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Position;

/**
 * Exception for when a position was not found.
 */
public class PositionNotFoundException extends Exception {

  public PositionNotFoundException(String position) {
    super("The position " + position + " was not found, please rectify");
  }
}