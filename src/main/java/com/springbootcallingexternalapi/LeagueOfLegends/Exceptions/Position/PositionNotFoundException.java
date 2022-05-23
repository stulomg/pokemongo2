package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.Position;

public class PositionNotFoundException extends Exception{
    public PositionNotFoundException (String position){
        super ("The position " + position + " was not found, please rectify");
    }
}
