package com.springbootcallingexternalapi.LeagueOfLegends.Models;

public class TeamAccountsMetaData {

    private TeamAccountsForClashData[] players;

    public TeamAccountsMetaData(TeamAccountsForClashData[] players) {
        this.players = players;
    }

    public TeamAccountsMetaData() {
    }

    public TeamAccountsForClashData[] getPlayers() {
        return players;
    }

    public void setPlayers(TeamAccountsForClashData[] players) {
        this.players = players;
    }
}
