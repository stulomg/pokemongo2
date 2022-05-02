package com.springbootcallingexternalapi.LeagueOfLegends.Exceptions;

public class AccountOrOwnerNotFoundException extends Exception {
    public AccountOrOwnerNotFoundException(String owner, String account) {
        super("LA CUENTA: " + account + ", VINCULADA AL USUARIO: " + owner + " NO FUE ENCONTRADA, PORFAVOR RECTIFICAR.");
    }
}
