package com.tip.futbolifybo.service;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.VenueResponse;
import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.model.Venue;
import com.tip.futbolifybo.repository.VenueRepository;
import com.tip.futbolifybo.service.provider.SpotifyProviderService;
import com.tip.futbolifybo.service.result.TrackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
    private SpotifyProviderService providerService;

    public List<VenueResponse> list(){
        Iterable<Venue> venues = this.venueRepository.findAll();
        List<VenueResponse> response = new ArrayList<>();

        for (Venue venue : venues) {
            response.add(new VenueResponse(venue.getVenueID(), venue.getName()));
        }

        return response;
    }


    public GenericResponse add(String code, String name){
        Boolean success = providerService.registerVenue(code, name);

        if(!success){
            return new GenericResponse("ERROR", "Venue could not be created.");
        }
        return new GenericResponse("SUCCESS", "Successfully created venue!");

    }

    public GenericResponse update(String venueID) {
        Optional<Venue> _venue = this.venueRepository.findById(UUID.fromString(venueID));
        if (!_venue.isPresent()){
            return null;
        }

        Venue venue = _venue.get();
        Boolean success = providerService.updateVenue(venue);

        if(!success){
            return new GenericResponse("ERROR", "Venue could not be updated.");
        }
        return new GenericResponse("SUCCESS", "Successfully updated venue!");
    }

    public List<VenueResponse> listWithTrack() {
        Iterable<Venue> venues = this.venueRepository.findAll();
        List<VenueResponse> venueResults = new ArrayList<>();

        for (Venue venue : venues) {
            TrackResult trackResult = providerService.getCurrentTrack(venue);
            VenueResponse venueResponse = new VenueResponse(venue.getVenueID(), venue.getName());

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

}
