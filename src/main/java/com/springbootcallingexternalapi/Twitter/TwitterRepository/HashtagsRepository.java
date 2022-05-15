package com.springbootcallingexternalapi.Twitter.TwitterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HashtagsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertHashtags(String hasthag) {
        String sql = "INSERT INTO \"TwitterHashtag\"(\"hashtagName\")VALUES (?)";
        jdbcTemplate.update(sql, hasthag);
    }

    public List<String> retrieveHashtags() {
        String sql = "SELECT \"hashtagName\" FROM \"TwitterHashtag\"";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
