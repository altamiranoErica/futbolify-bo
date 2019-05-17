package com.tip.futbolifybo.service;

import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.service.provider.SpotifyProviderService;
import com.tip.futbolifybo.service.result.TrackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TrackService {

    @Autowired
    private SpotifyProviderService providerService;

    public List<TrackResponse> search(String query, Integer page){
        Iterable<TrackResult> tracks = this.providerService.searchTracks(query, page);
        List<TrackResponse> response = new ArrayList<>();

        for (TrackResult track : tracks) {
            response.add(new TrackResponse(track));
        }

        return response;
    }

}
