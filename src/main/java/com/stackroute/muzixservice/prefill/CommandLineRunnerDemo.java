package com.stackroute.muzixservice.prefill;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.repository.TrackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerDemo implements CommandLineRunner {

   @Value("${track1.id}")
    private String id;
    @Value("${track1.name}")
    private String tname;
    @Value("${track1.comment}")
   private String tcmnt;

    Track track=new Track();
    @Autowired
    TrackRepository trackRepository;

    @Override
    public void run(String... args) throws Exception
    {

        track.setTrackId(id);
        track.setTrackname(tname);
        track.setTrackcmnt(tcmnt);
        trackRepository.save(track);
    }
}
