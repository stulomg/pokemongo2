package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.OwnerExceptions;

public class OwnerNotFoundException extends Exception{
    public OwnerNotFoundException(String owner) {
        super ("EL OWNER "+ owner + " NO FUE ENCONTRADO, POR FAVOR RECTIFICAR");
    }
    public OwnerNotFoundException(String owner, String owner2) {
        super ("EL OWNER "+ owner + " Y EL OWNER " +owner2+ " NO FUERON ENCONTRADOS, POR FAVOR RECTIFICAR");
    }
}
