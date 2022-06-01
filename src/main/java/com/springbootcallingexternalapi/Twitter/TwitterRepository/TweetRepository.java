package com.springbootcallingexternalapi.Twitter.TwitterRepository;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.TweetsBigDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This class allows you to connect to mongoDB dataBase.
 */
@Repository
public interface TweetRepository extends MongoRepository<TweetsBigDataModel, String> {

}
