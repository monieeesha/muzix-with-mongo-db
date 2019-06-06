package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsExceptions;
import com.stackroute.muzixservice.exceptions.TrackNotFoundExceptions;
import com.stackroute.muzixservice.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = {"track"})
@Service
@Primary
public class Trackserviceimpl  implements  Trackservice {

    private TrackRepository trackRepository;

    private Track track;

    public void simulateDelay()
    {
        try {
            Thread.sleep(3000l);
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }

    }

    @Autowired
    public  Trackserviceimpl(TrackRepository trackRepository)
    {
        this.trackRepository=trackRepository;
    }
    @CacheEvict(allEntries = true)
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
        return savedtrack;
    }

    @Cacheable(value="track")
    @Override
    public List<Track> getAllTracks() {

        simulateDelay();
        List<Track> tracklist =(List<Track>)trackRepository.findAll();
        return tracklist;
    }


    @CacheEvict(allEntries=true)
    @Override
    public Track updateTrack(String id, String comment)throws TrackNotFoundExceptions
    {
        Optional optional=trackRepository.findById(id);
        if (optional.isPresent())
        {
            track =trackRepository.findById(id).get();
            track.setTrackcmnt(comment);
            trackRepository.save(track);

        }
        else
            {

                throw new TrackNotFoundExceptions("no trackInfo found to update");
           }


        return track;
    }


    @Override
    @CacheEvict(allEntries = true)
    public Track deleteTrack(String trackId)throws TrackNotFoundExceptions
    {
        Optional optional=trackRepository.findById(trackId);
        if (optional.isPresent())
        {

            track =trackRepository.findById(trackId).get();
            trackRepository.deleteById(trackId);
        }
        else
            {

            throw  new TrackNotFoundExceptions("no trackInfo found to delete");
            }
        return track;

    }

    @Override
    public Track findByTrackName(String name)throws TrackNotFoundExceptions
    {

        Track trackInfo =trackRepository.findByTrackName(name);
        if(trackInfo ==null)
        {

            throw  new TrackNotFoundExceptions("no trackInfo found to display");

        }
        return trackInfo;

    }

}
