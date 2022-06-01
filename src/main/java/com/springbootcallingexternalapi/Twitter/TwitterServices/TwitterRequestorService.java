package com.springbootcallingexternalapi.Twitter.TwitterServices;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.DbNotAvaliableException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.TwitterExceptions.HashtagAlreadyRegisterException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.TweetsBigDataModel;
import com.springbootcallingexternalapi.Twitter.TwitterRepository.HashtagsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This class host all the function that request to Twitter Api.
 */
@Service
public class TwitterRequestorService {

  private static final String BEARER_TOKEN = "Bearer AAAAAAAAAAAAAAAAAAAAAExubwEAAAAAvYw3i9RIo%"
      + "2BKmUT8flxq%2BT%2BNWwS4%3DEmw0pG38aWj1rjNL5mMYoZZ5yPJU4iqFWtpwJNQYH3OKWmPfjE";
  @Autowired
  RestTemplate restTemplate;
  @Autowired
  HashtagsRepository hashtagsRepository;
  @Autowired
  TweetService tweetService;

  /**
   * This function allows us to request to Twitter.
   */
  public <T> ResponseEntity<T> requestToTwitterHashtags(String uri, HttpMethod method,
      Class<T> clazz) throws DbNotAvaliableException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", BEARER_TOKEN);
    List<String> hashtags = hashtagsRepository.retrieveHashtags();
    String query = String.join(" OR ", hashtags);
    Map<String, String> variables = new HashMap<>();
    variables.put("query", query);
    variables.put("max_results", "11");
    variables.put("sort_order", "relevancy");
    String finalUrl = "https://api.twitter.com/2" + uri;
    HttpEntity<String> entity = new HttpEntity<>("", headers);
    return restTemplate.exchange(finalUrl, method, entity, clazz, variables);
  }

  /**
   * This function insert a hashtag into a database.
   */
  public void insertHashtag(String hashtag)
      throws HashtagAlreadyRegisterException, CharacterNotAllowedException {
    hashtag = "#" + hashtag;
    try {
      hashtagsRepository.insertHashtags(hashtag);
    } catch (DataIntegrityViolationException e) {
      throw new HashtagAlreadyRegisterException(hashtag);
    }
  }

  /**
   * This function get all tweets related to RiotGames.
   */
  public ResponseEntity<TweetsBigDataModel> getRiotTweets() throws DbNotAvaliableException {
    String uri = "/tweets/search/recent?query={query}&max_results={max_results}"
        + "&sort_order={sort_order}";
    ResponseEntity<TweetsBigDataModel> response = requestToTwitterHashtags(uri, HttpMethod.GET,
        TweetsBigDataModel.class);
    tweetService.saveTweet(response.getBody());
    return response;
  }
}