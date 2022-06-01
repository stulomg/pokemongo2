package com.springbootcallingexternalapi.Twitter.TwitterServices;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.DbNotAvaliableException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.TwitterExceptions.HashtagAlreadyRegisterException;
import com.springbootcallingexternalapi.LeagueOfLegends.Models.TweetsBigDataModel;
import com.springbootcallingexternalapi.Twitter.TwitterRepository.HashtagsRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TwitterRequestorService {

  private static final String BEARER_TOKEN = "Bearer AAAAAAAAAAAAAAAAAAAAAExubwEAAAAAvYw3i9RIo%2BKmUT8flxq%2BT%2BNWwS4%3DEmw0pG38aWj1rjNL5mMYoZZ5yPJU4iqFWtpwJNQYH3OKWmPfjE";
  private static final String MONKEY_TOKEN = "Token 553c05d728aa3a61547ce5c7741114cca31f8917";
  @Autowired
  RestTemplate restTemplate;
  @Autowired
  HashtagsRepository hashtagsRepository;
  @Autowired
  TweetService tweetService;
  private StanfordCoreNLP pipeline;

  public <T> ResponseEntity<T> requestToTwitterHashtags(String uri, HttpMethod method,
      Class<T> clazz) throws DbNotAvaliableException {
    String finalUrl = "https://api.twitter.com/2" + uri;
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", BEARER_TOKEN);
    HttpEntity<String> entity = new HttpEntity<>("", headers);
    List<String> hashtags = hashtagsRepository.retrieveHashtags();
    String query = String.join(" OR ", hashtags);
    Map<String, String> variables = new HashMap<>();
    variables.put("query", query);
    variables.put("max_results", "11");
    variables.put("sort_order", "relevancy");

    return restTemplate.exchange(finalUrl, method, entity, clazz, variables);
  }

  public void insertHashtag(String hashtag)
      throws HashtagAlreadyRegisterException, CharacterNotAllowedException {
    hashtag = "#" + hashtag;
    try {
      hashtagsRepository.insertHashtags(hashtag);
    } catch (DataIntegrityViolationException e) {
      throw new HashtagAlreadyRegisterException(hashtag);
    }
  }

  public ResponseEntity<TweetsBigDataModel> getRiotTweets() throws DbNotAvaliableException {
    String uri = "/tweets/search/recent?query={query}&max_results={max_results}&sort_order={sort_order}";
    ResponseEntity<TweetsBigDataModel> response = requestToTwitterHashtags(uri, HttpMethod.GET,
        TweetsBigDataModel.class);
    tweetService.saveTweet(response.getBody());
    //getSentimentAnalysis(response.getBody());
    //TweetModel[] response2 = response.getBody().getData();
    //List<String> results = new ArrayList<>();
       /* for (int i = 0; i <response2.length ; i++) {
            System.out.println(response2[i].getText());
            int sentiment = findSentiment(response2[i].getText().toLowerCase(Locale.ROOT));
            //results.add(sentiment + "::" + response2[i].getText());
        }*/
    return response;
  }

  public int findSentiment(String tweetData) {
    int mainSentiment = 0;
    int longest = 0;
    Annotation annotation = pipeline.process(tweetData);
    for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
      Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
      int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
      String partText = sentence.toString();
      if (partText.length() > longest) {
        mainSentiment = sentiment;
        longest = partText.length();
      }
    }
    return mainSentiment;
  }

  public <T> ResponseEntity<T> requestToSentimentAnalysis(String uri, HttpMethod method,
      Class<T> clazz) {
    String finalUrl = "https://api.monkeylearn.com/v3" + uri;
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", MONKEY_TOKEN);
    HttpEntity<String> entity = new HttpEntity<>("", headers);
    //StreamingHttpOutputMessage.Body body = new Body
    return restTemplate.exchange(finalUrl, method, entity, clazz);
  }

  public Object getSentimentAnalysis(Json json) {
    String uri = "/classifiers/cl_pi3C7JiL/classify/";
    ResponseEntity<Object> response = requestToSentimentAnalysis(uri, HttpMethod.GET, Object.class);
    return response;
  }
}