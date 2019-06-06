package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TrackRepository extends JpaRepository<Track,String>
{
    @Query("select t from Track t where LOWER(t.trackname)=Lower(:name)")
    public Track findByTrackName(String name);
}
