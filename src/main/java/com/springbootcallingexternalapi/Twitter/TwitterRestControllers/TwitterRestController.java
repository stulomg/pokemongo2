package com.springbootcallingexternalapi.Twitter.TwitterRestControllers;

import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.DbNotAvaliableException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.GeneralExceptions.CharacterNotAllowedException;
import com.springbootcallingexternalapi.LeagueOfLegends.Exceptions.TwitterExceptions.HashtagAlreadyRegisterException;
import com.springbootcallingexternalapi.Twitter.TwitterServices.TwitterRequestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**This class host the function to add hashtags. */
@RestController
public class TwitterRestController {

  @Autowired
  TwitterRequestorService twitterRequestorService;

  /**This function get tweets with pre saved hashtags. */
  @GetMapping(value = "/call-twitter/community/tweets/riot_games")
  public ResponseEntity<Object> getRiotTweets() {
    Object response = null;
    try {
      response = twitterRequestorService.getRiotTweets();
    } catch (DbNotAvaliableException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**This function insert a new hashtag to the list. */
  @PostMapping(value = "/insert/twitter/{hashtag}")
  public ResponseEntity<Object> insertHashtags(@PathVariable String hashtag) {
    try {
      twitterRequestorService.insertHashtag(hashtag);
    } catch (HashtagAlreadyRegisterException | CharacterNotAllowedException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(hashtag + " hashtag created successfully", HttpStatus.OK);
  }
}