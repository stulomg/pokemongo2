package com.springbootcallingexternalapi.Twitter.TwitterServices;

import com.springbootcallingexternalapi.Twitter.TwitterRepository.HashtagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TwitterRequestorService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HashtagsRepository hashtagsRepository;

    private static final String BEARER_TOKEN = "Bearer AAAAAAAAAAAAAAAAAAAAAExubwEAAAAAvYw3i9RIo%2BKmUT8flxq%2BT%2BNWwS4%3DEmw0pG38aWj1rjNL5mMYoZZ5yPJU4iqFWtpwJNQYH3OKWmPfjE";

    public <T> ResponseEntity<T> requestToTwitter(String uri, HttpMethod method, Class<T> clazz) {
        String finalUrl = "https://api.twitter.com/2" + uri;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", BEARER_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        List<String> hashtags = hashtagsRepository.retrieveHashtags();
        hashtags.add("#RiotGames");
        hashtags.add("#RitoGames");
        hashtags.add("#LeagueOfLegends");
        hashtags.add("#Valorant");

        String query = String.join(" OR ", hashtags);
        System.out.println(query);

        Map<String, String> variables = new HashMap<>();
        variables.put("query", query);
        variables.put("max_results", "11");
        variables.put("sort_order", "relevancy");


        return restTemplate.exchange(finalUrl, method, entity, clazz, variables);
    }

    public void insertHashtag (String hashtag){
        hashtagsRepository.insertHashtags(hashtag);
    }

    public Object getTweet(Long id) {
        String uri = "/tweets/" + id;
        ResponseEntity<Object> response = requestToTwitter(uri, HttpMethod.GET, Object.class);
        return response.getBody();
    }

    public Object getRiotTweets() {
        String uri = "/tweets/search/recent?query={query}&max_results={max_results}&sort_order={sort_order}";
        ResponseEntity<Object> response = requestToTwitter(uri, HttpMethod.GET, Object.class);
        return response.getBody();
    }
}
