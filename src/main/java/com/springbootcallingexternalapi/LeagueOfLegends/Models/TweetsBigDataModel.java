package com.springbootcallingexternalapi.LeagueOfLegends.Models;

/**
 * Model for the Big tweets data.
 */
public class TweetsBigDataModel {

  private TweetModel[] data;
  private Object meta;

  public TweetsBigDataModel(TweetModel[] data, Object meta) {
    this.data = data;
    this.meta = meta;
  }

  public TweetsBigDataModel() {
  }

  public TweetModel[] getData() {
    return data;
  }

  public void setData(TweetModel[] data) {
    this.data = data;
  }

  public Object getMeta() {
    return meta;
  }

  public void setMeta(Object meta) {
    this.meta = meta;
  }
}
