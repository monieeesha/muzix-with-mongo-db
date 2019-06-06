package com.stackroute.muzixservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exceptions.ExceptionController;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsExceptions;
import com.stackroute.muzixservice.exceptions.TrackNotFoundExceptions;
import com.stackroute.muzixservice.service.Trackservice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest
public class TrackControllerTest
{

    @Autowired
    private MockMvc mockMvc;
    private Track track;
    @MockBean
    private Trackservice trackservice;
    @InjectMocks
    private TrackController trackcontroller;

    private List<Track> list =null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trackcontroller).setControllerAdvice(new ExceptionController()).build();
        track = new Track();
        track.setTrackId("001");
        track.setTrackname("horror");
        track.setTrackcmnt("awesome");
        list = new ArrayList();
        list.add(track);
       }

    @Test
    public void saveTrack() throws Exception {
        when(trackservice.saveTrack(any())).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tracksave")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(trackservice,times(1)).saveTrack(track);



    }
    @Test
    public void saveTrackFailure() throws TrackAlreadyExistsExceptions,Exception {
        when(trackservice.saveTrack(any())).thenThrow(TrackAlreadyExistsExceptions.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tracksave")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
               .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());

        verify(trackservice,times(1)).saveTrack(track);

    }

    @Test
    public void getAllTracks() throws Exception {
        when(trackservice.getAllTracks()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackservice,times(1)).getAllTracks();


    }

    @Test
    public void updateTrack() throws Exception {
        when(trackservice.updateTrack(track.getTrackId(),track.getTrackcmnt())).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/track/id/comment")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackservice,times(1)).updateTrack((String)any(),(String)any());
    }

    @Test
    public void updateTrackFailure() throws TrackNotFoundExceptions, Exception {
        when(trackservice.updateTrack(any(),any())).thenThrow(TrackNotFoundExceptions.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/track/id/comment")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
               verify(trackservice,times(1)).updateTrack((String)any(),(String)any());
    }
    @Test
    public void getByNameTrack() throws TrackNotFoundExceptions,Exception {
        when(trackservice.findByTrackName(track.getTrackname())).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track/id")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackservice,times(1)).findByTrackName((String)any());
    }
    @Test
    public void getByNameTrackFailure() throws TrackNotFoundExceptions,Exception {
        when(trackservice.findByTrackName(any())).thenThrow(TrackNotFoundExceptions.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track/id")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
               .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(trackservice,times(1)).findByTrackName((String)any());
    }

    @Test
    public void deleteTrack() throws TrackNotFoundExceptions,Exception {
        when(trackservice.deleteTrack(track.getTrackId())).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/trackdelete/id")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(trackservice,times(1)).deleteTrack((String)any());
    }

    @Test
    public void deleteTrackFailure() throws  TrackNotFoundExceptions, Exception {
        when(trackservice.deleteTrack(any())).thenThrow(TrackNotFoundExceptions.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/trackdelete/id")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
         verify(trackservice,times(1)).deleteTrack((String)any());
    }


    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }



}