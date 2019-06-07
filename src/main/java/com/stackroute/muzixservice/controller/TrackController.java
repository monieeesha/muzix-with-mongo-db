package com.stackroute.muzixservice.controller;

import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsExceptions;
import com.stackroute.muzixservice.exceptions.TrackNotFoundExceptions;
import com.stackroute.muzixservice.service.Trackservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Api(value="TrackControllerAPI:",produces= MediaType.APPLICATION_JSON_VALUE)
public class TrackController {


    @Autowired
    private Trackservice trackservice;


    public TrackController(Trackservice trackservice)
    {
        this.trackservice=trackservice;
    }

    @PostMapping("tracksave")
    public ResponseEntity<?> saveTrack(@RequestBody Track trackInfo)throws TrackAlreadyExistsExceptions
    {
        ResponseEntity responseEntity;

            trackservice.saveTrack(trackInfo);
            responseEntity=new ResponseEntity<String>("successfully added the trackInfo ", HttpStatus.CREATED);

        return responseEntity;
    }
    @ApiOperation("gets the track with specific id")
    @ApiResponses(value={@ApiResponse(code=200,message = "ok",response= Track.class)})
    @GetMapping("track")
    public ResponseEntity<?> getAllTracks()
    {
          ResponseEntity responseEntity;

          return new ResponseEntity<List<Track>>(trackservice.getAllTracks(), HttpStatus.OK);

    }

    @PutMapping("track/{id}/{comment}")
    public ResponseEntity<?> updateTrack(@RequestBody  @PathVariable("id") String id,@PathVariable ("comment") String comment)throws  TrackNotFoundExceptions
    {
        ResponseEntity responseEntity;
        Track updatedTrackInfo =null;


            updatedTrackInfo =trackservice.updateTrack(id,comment);
            responseEntity=new ResponseEntity<Track>(updatedTrackInfo, HttpStatus.OK);
            return responseEntity;
    }
    @DeleteMapping("trackdelete/{id}")
    public ResponseEntity<?> deleteTrack(@RequestBody @PathVariable("id") String id)throws TrackNotFoundExceptions
    {
        ResponseEntity responseEntity;
        Track deletetrack=null;


            deletetrack=trackservice.deleteTrack(id);
            responseEntity=new ResponseEntity<Track>(deletetrack, HttpStatus.OK);
            return responseEntity;
    }


}