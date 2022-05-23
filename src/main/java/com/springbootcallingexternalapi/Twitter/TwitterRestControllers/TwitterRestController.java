package com.springbootcallingexternalapi.Twitter.TwitterRestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.DBNotAvaliableException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.TwitterExceptions.HashtagAlreadyRegisterException;
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

    @GetMapping (value = "/call-twitter/community/tweets/riot_games")
    public ResponseEntity<Object> getRiotTweets(){
        Object response = null;
        try {
            response = twitterRequestorService.getRiotTweets();
        } catch (DBNotAvaliableException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.REQUEST_TIMEOUT);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping (value = "/insert/twitter/{hashtag}")
    public ResponseEntity<Object> insertHashtags(@PathVariable String hashtag){
       try {
           twitterRequestorService.insertHashtag(hashtag);
       }catch (HashtagAlreadyRegisterException | CharacterNotAllowedException e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
       }
        return new ResponseEntity<>( hashtag + " hashtag created successfully", HttpStatus.OK);
    }
}