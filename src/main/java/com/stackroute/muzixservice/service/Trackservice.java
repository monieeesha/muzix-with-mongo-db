package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsExceptions;
import com.stackroute.muzixservice.exceptions.TrackNotFoundExceptions;

import java.util.List;

public interface Trackservice {

    public Track saveTrack(Track trackInfo)throws TrackAlreadyExistsExceptions;
    public List<Track> getAllTracks();
    public Track updateTrack(String id, String comment)throws TrackNotFoundExceptions;
    public Track deleteTrack(String trackId)throws TrackNotFoundExceptions;
    public Track findByTrackName(String name)throws TrackNotFoundExceptions;

}
