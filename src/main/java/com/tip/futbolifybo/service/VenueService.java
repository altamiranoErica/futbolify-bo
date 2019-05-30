package com.tip.futbolifybo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.PlaylistResponse;
import com.tip.futbolifybo.api.response.VenueResponse;
import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.exception.InvalidVenueDataException;
import com.tip.futbolifybo.model.AccessData;
import com.tip.futbolifybo.model.Venue;
import com.tip.futbolifybo.repository.AccessDataRepository;
import com.tip.futbolifybo.repository.VenueRepository;
import com.tip.futbolifybo.service.provider.SpotifyProviderService;
import com.tip.futbolifybo.service.result.PlaylistResult;
import com.tip.futbolifybo.service.result.TrackResult;
import com.tip.futbolifybo.service.result.VenueResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VenueService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private AccessDataRepository accessDataRepository;

    @Autowired
    private SpotifyProviderService providerService;


    public List<VenueResponse> list(){
        Iterable<Venue> venues = this.venueRepository.findAll();
        List<VenueResponse> response = new ArrayList<>();

        for (Venue venue : venues) {
            response.add(new VenueResponse(venue));
        }

        return response;
    }


    public GenericResponse add(String json){
        VenueResult venueResult = this.getVenueResultFromJSON(json);
        if(venueResult == null){
            return new GenericResponse("ERROR", "Venue could not be created.");
        }

        List<AccessData> accessData = null;
        try {
            accessData = providerService.getAccessData(venueResult.getCode());
        } catch (InvalidVenueDataException e) {
            return new GenericResponse("ERROR", "Venue could not be created.");
        }

        Venue venue = new Venue(venueResult.getName(), venueResult.getImage(),
                venueResult.getPlaylist(), "SPOTIFY");
        venue = this.venueRepository.save(venue);
        for (AccessData data : accessData) {
            data.setVenue(venue);
            this.accessDataRepository.save(data);
        }

        return new GenericResponse("SUCCESS", "Successfully created venue!");

    }

    public List<VenueResponse> listWithTrack() {
        Iterable<Venue> venues = this.venueRepository.findAll();
        List<VenueResponse> venueResults = new ArrayList<>();

        for (Venue venue : venues) {
            TrackResult trackResult = providerService.getCurrentTrack(venue);
            VenueResponse venueResponse = new VenueResponse(venue);

            if(trackResult != null) {
                TrackResponse trackResponse = new TrackResponse();
                trackResponse.setName(trackResult.getName());
                trackResponse.setCode(trackResult.getEmbedCode());
                trackResponse.setArtist(trackResult.getArtist());
                trackResponse.setPlaying(trackResult.getIsPlaying());

                venueResponse.setTrack(trackResponse);
            }

            venueResults.add(venueResponse);
        }

        return venueResults;
    }

    public List<PlaylistResponse> getPlaylist(String venueID) {
        List<PlaylistResponse> response = new ArrayList<>();
        Optional<Venue> _venue = this.venueRepository.findById(UUID.fromString(venueID));
        if (!_venue.isPresent()){
            return null;
        }

        Venue venue = _venue.get();
        List<PlaylistResult> playlists = this.providerService.getPlaylistFromVenue(venue);
        for (PlaylistResult playlist : playlists) {
            response.add(new PlaylistResponse(playlist));
        }

        return response;
    }


    private VenueResult getVenueResultFromJSON(String productJSON){
        VenueResult result = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            result = mapper.readValue(productJSON, VenueResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
