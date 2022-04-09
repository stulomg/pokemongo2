package com.springbootcallingexternalapi.Exceptions.MapExceptions;

public class MapNotFoundException extends Exception {
    public MapNotFoundException (String mapName){
        super (mapName + "doesnt exist");
    }

}
