package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Track;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrackRepository extends MongoRepository<Track,String>
{

}
