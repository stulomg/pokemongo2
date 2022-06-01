package com.springbootcallingexternalapi.Twitter.TwitterRepository;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.DbNotAvaliableException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**This class host the functions that insert or retreive hashtag to our DataBase. */
@Repository
public class HashtagsRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insertHashtags(String hasthag) {
    String sql = "INSERT INTO \"TwitterHashtag\"(\"hashtagName\")VALUES (?)";
    jdbcTemplate.update(sql, hasthag);
  }

  /**This function retrieve a list of hashtags. */
  public List<String> retrieveHashtags() throws DbNotAvaliableException {
    String sql = "SELECT \"hashtagName\" FROM \"TwitterHashtag\"";
    try {
      return jdbcTemplate.queryForList(sql, String.class);
    } catch (DataAccessException e) {
      throw new DbNotAvaliableException();
    }
  }
}