package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest

public class TrackRepositoryTest {


        @Autowired
        private TrackRepository trackRepository;
        private Track track;

        @Before
        public void setUp()
        {
            track = new Track();
            track.setTrackId("101");
            track.setTrackname("horror");
            track.setTrackcmnt("awesome");


        }

        @After
        public void tearDown(){

            trackRepository.deleteAll();
        }


        @Test
        public void testSaveTrack(){
            trackRepository.save(track);
            Track fetchtrack = trackRepository.findById(track.getTrackId()).get();
            Assert.assertEquals("101",fetchtrack.getTrackId());

        }

        @Test
        public void testSaveTrackFailure(){
            Track track1 = new Track("201","Horror","awesome");
            trackRepository.save(track);
            Track fetchtrack = trackRepository.findById(track.getTrackId()).get();
            Assert.assertNotSame(track1.getTrackId(),fetchtrack.getTrackId());
        }

        @Test
        public void testGetAllTracks(){
            Track t= new Track("102","melody","romantic");
            Track t1 = new Track("103","rock","party");
            trackRepository.save(t);
            trackRepository.save(t1);

            List<Track> list = trackRepository.findAll();
            Assert.assertEquals("102",list.get(0).getTrackId());
        }




        @Test
        public   void testdeleteTrack()
        {
            Track track1=new  Track("104","pop","Eminensongs");
            trackRepository.save(track1);
            trackRepository.deleteById(track1.getTrackId());
            Optional value=trackRepository.findById(track1.getTrackId());
            Assert.assertEquals(Optional.empty(),value);

        }
        @Test
        public  void  testdeleteTrackFailure()
        {

            Track t=new  Track("104","pop","Eminensongs");
            trackRepository.save(t);
            trackRepository.deleteById(t.getTrackId());
            Optional value=trackRepository.findById(track.getTrackId());
            boolean flag=value.isPresent();
            Assert.assertNotEquals(Optional.empty(),flag);

        }


}