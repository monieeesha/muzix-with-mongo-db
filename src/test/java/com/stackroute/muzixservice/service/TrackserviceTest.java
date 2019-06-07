package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsExceptions;
import com.stackroute.muzixservice.exceptions.TrackNotFoundExceptions;
import com.stackroute.muzixservice.repository.TrackRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TrackserviceTest {



    private Track track;


    @Mock
    private TrackRepository trackRepository;


    @InjectMocks
    private Trackserviceimpl trackService;
    List<Track> list= null;



    private Optional  optional;
    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        track = new  Track();
        track.setTrackId("101");
        track.setTrackname("melodysongs");
        track.setTrackcmnt("melodious");
        list = new ArrayList<>();
        list.add(track);
        optional=Optional.of(track);


    }

    @After
    public void teardown()
    {

        track=null;
        optional=null;
    }

    @Test
    public void saveTrackTestSuccess() throws TrackAlreadyExistsExceptions {

        when(trackRepository.save((Track) any())).thenReturn(track);
        Track savedTrack = trackService.saveTrack(track);
        Assert.assertEquals(track,savedTrack);
        verify(trackRepository,times(1)).save(track);

    }

    @Test(expected = TrackAlreadyExistsExceptions.class)
    public void saveTrackTestFailure() throws TrackAlreadyExistsExceptions {
        when(trackRepository.save((Track)any())).thenReturn(null);
        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        Track savedtrack = trackService.saveTrack(track);
        System.out.println("savedtrack" + savedtrack);
       // Assert.assertEquals(track,savedtrack);

       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/
    }
    @Test
    public void getAllTracks(){

        trackRepository.save(track);
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> tracklist = trackService.getAllTracks();
        Assert.assertEquals(list,tracklist);
        verify(trackRepository,times(1)).save(track);
        verify(trackRepository,times(1)).findAll();


    }

    @Test
    public void  deletetrack()throws TrackNotFoundExceptions
    {

        when(trackRepository.findById(track.getTrackId())).thenReturn(optional);
        Track deletedtrack=trackService.deleteTrack("101");
        Assert.assertEquals("101",deletedtrack.getTrackId());
        verify(trackRepository,times(2)).findById(track.getTrackId());
        verify(trackRepository,times(1)).deleteById(track.getTrackId());

    }

    @Test(expected = TrackNotFoundExceptions.class)
    public void deleteTrackTestFailure() throws TrackNotFoundExceptions {
          when(trackRepository.findById(any())).thenReturn(Optional.empty());
          Track deletedtrack=trackService.deleteTrack(track.getTrackId());
        verify(trackRepository,times(1)).findById(track.getTrackId());
        verify(trackRepository,times(1)).deleteById(track.getTrackId());

    }

   @Test
    public void   updateTrack()throws  TrackNotFoundExceptions
   {

       when(trackRepository.findById(track.getTrackId())).thenReturn(optional);
       track.setTrackcmnt("bad song");
       Track updatedtrack= trackService.updateTrack(track.getTrackId(),track.getTrackcmnt());
       Assert.assertEquals("bad song",updatedtrack.getTrackcmnt());

       verify(trackRepository,times(1)).save(track);
       verify(trackRepository,times(2)).findById(track.getTrackId());
   }

    @Test(expected = TrackNotFoundExceptions.class)
    public void updateTrackTestFailure() throws TrackNotFoundExceptions {
        when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.empty());
        track.setTrackcmnt("bad song");
        Track updatedtrack= trackService.updateTrack(track.getTrackId(),track.getTrackcmnt());
        verify(trackRepository,times(1)).findById(track.getTrackId());

    }




}