package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsExceptions;
import com.stackroute.muzixservice.exceptions.TrackNotFoundExceptions;
import com.stackroute.muzixservice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Trackserviceimpl2 implements  Trackservice
{


    private TrackRepository trackRepository;

    @Autowired
    public  Trackserviceimpl2(TrackRepository trackRepository)
    {
        this.trackRepository=trackRepository;
    }
    @Override
    public Track saveTrack(Track trackInfo)throws TrackAlreadyExistsExceptions {

        if(trackRepository.existsById(trackInfo.getTrackId()))
        {
            throw  new TrackAlreadyExistsExceptions("trackInfo already exists");
        }
        Track savedtrack=trackRepository.save(trackInfo);
        if(savedtrack==null)
        {
            throw  new TrackAlreadyExistsExceptions("trackInfo already exists");
        }
        System.out.println(savedtrack);
        return savedtrack;
    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public Track updateTrack(String id,String   comment)throws TrackNotFoundExceptions
    {
        Track trackInfo;
        if (trackRepository.existsById(id))
        {
            trackInfo =trackRepository.findById(id).get();
            trackInfo.setTrackcmnt(comment);
            trackRepository.save(trackInfo);
        }
        else
        {
            throw new TrackNotFoundExceptions("no trackInfo found to update");
        }

       System.out.println(trackInfo);
        return trackInfo;
    }

    @Override
    public Track deleteTrack(String trackId)throws TrackNotFoundExceptions
    {
        Track trackInfo =null;
        if (trackRepository.existsById(trackId))
        {

            trackInfo =trackRepository.findById(trackId).get();
            trackRepository.deleteById(trackId);
        }
        else
        {

            throw  new TrackNotFoundExceptions("no trackInfo found to delete");
        }
        System.out.println(trackInfo);
        return trackInfo;

    }

    @Override
    public Track findByTrackName(String name)throws TrackNotFoundExceptions
    {

        Track trackInfo =trackRepository.findByTrackName(name);
        if(trackInfo ==null)
        {

            throw  new TrackNotFoundExceptions("no trackInfo found to display");

        }
        System.out.println(trackInfo);
        return trackInfo;

    }



}
