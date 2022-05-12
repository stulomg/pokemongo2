package com.springbootcallingexternalapi.Twitter.TwitterRestControllers;

import com.springbootcallingexternalapi.Twitter.TwitterServices.TwitterRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TwitterRestController {

    @Autowired
    TwitterRequestorService twitterRequestorService;

    @RequestMapping(value = "/call-twitter/tweets/{tweetId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> callTwitter (@PathVariable Long tweetId){
        Object response = twitterRequestorService.getTweet(tweetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/call-twitter/community/tweets/riot_games")
    public ResponseEntity<Object> getRiotTweets(){
        Object response = twitterRequestorService.getRiotTweets();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping (value = "/insert/twitter/{hashtag}")
    public ResponseEntity<Object> insertHashtags(@PathVariable String hashtag){
        twitterRequestorService.insertHashtag(hashtag);
        return new ResponseEntity<>( hashtag + "created successfully", HttpStatus.OK);
    }
}
