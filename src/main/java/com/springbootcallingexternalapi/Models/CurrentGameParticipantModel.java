package com.springbootcallingexternalapi.Models;

public class CurrentGameParticipantModel {

  private Long teamId;

  private Long spell1Id;

  private Long spell2Id;

  private Long championId;

  private String summonerName;

    public CurrentGameParticipantModel(Long teamId, Long spell1Id, Long spell2Id, Long championId, String summonerName) {
        this.teamId = teamId;
        this.spell1Id = spell1Id;
        this.spell2Id = spell2Id;
        this.championId = championId;
        this.summonerName = summonerName;
    }

    public CurrentGameParticipantModel() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(Long spell1Id) {
        this.spell1Id = spell1Id;
    }

    public Long getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(Long spell2Id) {
        this.spell2Id = spell2Id;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }
}
