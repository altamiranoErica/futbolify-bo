package com.tip.futbolifybo.service;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.model.Track;
import com.tip.futbolifybo.model.Venue;
import com.tip.futbolifybo.repository.TrackRepository;
import com.tip.futbolifybo.repository.VenueRepository;
import com.tip.futbolifybo.service.provider.SpotifyProviderService;
import com.tip.futbolifybo.service.result.TrackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TrackService {

    @Autowired
    private SpotifyProviderService providerService;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private TrackRepository trackRepository;

    public List<TrackResponse> search(String query, Integer page){
        Iterable<TrackResult> tracks = this.providerService.searchTracks(query, page);
        List<TrackResponse> response = new ArrayList<>();

        for (TrackResult track : tracks) {
            response.add(new TrackResponse(track));
        }

        return response;
    }

    public List<TrackResponse> getRandom(String venueID, Integer count) {
        Optional<Venue> _venue = this.venueRepository.findById(UUID.fromString(venueID));
        if (!_venue.isPresent()){
            return null;
        }

        Venue venue = _venue.get();
        Iterable<TrackResult> tracks = this.providerService.getRandomTracks(venue, count);
        List<TrackResponse> response = new ArrayList<>();

        for (TrackResult track : tracks) {
            response.add(new TrackResponse(track));
        }

        return response;
    }
}
