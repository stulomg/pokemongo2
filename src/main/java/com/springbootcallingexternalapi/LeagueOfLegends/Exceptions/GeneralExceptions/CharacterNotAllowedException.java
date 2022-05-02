package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions;

public class CharacterNotAllowedException extends Exception {
    public CharacterNotAllowedException(String name) {

        super(name + " has characters not allowed");
    }

    public CharacterNotAllowedException(String name, String account) {
        super(name + " or " + account + " has characters not allowed");
    }

}
