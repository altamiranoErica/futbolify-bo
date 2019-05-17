package com.tip.futbolifybo.service;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.SpaceResponse;
import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.model.Space;
import com.tip.futbolifybo.model.User;
import com.tip.futbolifybo.repository.SpaceRepository;
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
public class SpaceService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SpotifyProviderService providerService;

    public List<SpaceResponse> list(){
        Iterable<Space> spaces = this.spaceRepository.findAll();
        List<SpaceResponse> response = new ArrayList<>();

        for (Space space : spaces) {
            response.add(new SpaceResponse(space.getSpaceID(), space.getName()));
        }

        return response;
    }


    public GenericResponse add(String code, String name){
        Boolean success = providerService.registerSpace(code, name);

        if(!success){
            return new GenericResponse("ERROR", "Space could not be created.");
        }
        return new GenericResponse("SUCCESS", "Successfully created space!");

    }

    public GenericResponse update(String spaceID) {
        Optional<Space> _space = this.spaceRepository.findById(UUID.fromString(spaceID));
        if (!_space.isPresent()){
            return null;
        }

        Space space = _space.get();
        Boolean success = providerService.updateSpace(space);

        if(!success){
            return new GenericResponse("ERROR", "Space could not be updated.");
        }
        return new GenericResponse("SUCCESS", "Successfully updated space!");
    }

    public List<SpaceResponse> listWithTrack() {
        Iterable<Space> spaces = this.spaceRepository.findAll();
        List<SpaceResponse> spaceResults = new ArrayList<>();

        for (Space space : spaces) {
            TrackResult trackResult = providerService.getCurrentTrack(space);
            SpaceResponse spaceResponse = new SpaceResponse(space.getSpaceID(), space.getName());

            if(trackResult != null) {
                TrackResponse trackResponse = new TrackResponse();
                trackResponse.setName(trackResult.getName());
                trackResponse.setCode(trackResult.getEmbedCode());
                trackResponse.setArtist(trackResult.getArtist());
                trackResponse.setPlaying(trackResult.getIsPlaying());

                spaceResponse.setTrack(trackResponse);
            }

            spaceResults.add(spaceResponse);
        }

        return spaceResults;
    }

}
