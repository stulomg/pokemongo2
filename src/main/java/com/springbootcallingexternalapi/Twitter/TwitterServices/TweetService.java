package com.springbootcallingexternalapi.Twitter.TwitterServices;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.TweetsBigDataModel;
import com.springbootcallingexternalapi.Twitter.TwitterRepository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
    @Autowired
    TweetRepository tweetRepository;
    public void saveTweet (TweetsBigDataModel tweetModel){
        tweetRepository.save(tweetModel);
    }
}
