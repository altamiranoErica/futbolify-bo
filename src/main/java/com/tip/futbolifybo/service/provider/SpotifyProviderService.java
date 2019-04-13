package com.tip.futbolifybo.service.provider;

import com.tip.futbolifybo.model.AccessData;
import com.tip.futbolifybo.model.Space;
import com.tip.futbolifybo.repository.AccessDataRepository;
import com.tip.futbolifybo.repository.SpaceRepository;
import com.tip.futbolifybo.service.result.TrackResult;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyProviderService {

    protected static String CLIENT_ID = "ef56629f44be41e895cac7d03141b9e0";
    protected static String CLIENT_SECRET = "7a1a681c2b9a45e48d95a5da81610dd9";
    protected static URI REDIRECT_URI = SpotifyHttpManager.makeUri("http://localhost:4200/authorize/callback");

    @Autowired
    private AccessDataRepository accessDataRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRedirectUri(REDIRECT_URI)
            .build();

    public Boolean registerSpace(String code, String name){
        AuthorizationCodeRequest codeRequest = spotifyApi.authorizationCode(code).build();
        AuthorizationCodeCredentials credentials;
        try {
            credentials = codeRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            return false;
        }

        Space space = new Space(name, "SPOTIFY");
        space = this.spaceRepository.save(space);

        List<AccessData> data = new ArrayList<>();
        data.add(new AccessData("accessToken", credentials.getAccessToken(), space));
        data.add(new AccessData("refreshToken", credentials.getRefreshToken(), space));
        this.accessDataRepository.saveAll(data);

        return true;
    }

    public TrackResult getCurrentTrack(Space space) {
        this.updateSpace(space);

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(space.getAccessData("accessToken").getValue())
                .build();
        GetUsersCurrentlyPlayingTrackRequest currentlyPlayingTrackRequest = spotifyApi
                .getUsersCurrentlyPlayingTrack()
                .build();

        CurrentlyPlaying currentlyPlaying = null;
        try {
            currentlyPlaying = currentlyPlayingTrackRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }

        if(currentlyPlaying == null) {
            return null;
        }

        TrackResult trackResult = new TrackResult();
        trackResult.setName(currentlyPlaying.getItem().getName());
        trackResult.setUri(currentlyPlaying.getItem().getUri());
        trackResult.setId(currentlyPlaying.getItem().getId());
        trackResult.setAlbumID(currentlyPlaying.getItem().getAlbum().getId());
        trackResult.setEmbedCode("https://open.spotify.com/embed/track/" + trackResult.getId());
        trackResult.setIsPlaying(currentlyPlaying.getIs_playing());
        trackResult.setProgressMS(currentlyPlaying.getProgress_ms());

        ArtistSimplified[] artists = currentlyPlaying.getItem().getArtists();
        if(artists != null && artists.length > 0) trackResult.setArtist(artists[0].getName());

        return trackResult;
    }

    public Boolean updateSpace(Space space) {
        SpotifyApi _spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRefreshToken(space.getAccessData("refreshToken").getValue())
                .build();

        AuthorizationCodeRefreshRequest codeRefreshRequest = _spotifyApi.authorizationCodeRefresh().build();
        AuthorizationCodeCredentials credentials = null;
        try {
            credentials = codeRefreshRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            return false;
        }

        space.getAccessData("accessToken").setValue(credentials.getAccessToken());
        this.accessDataRepository.saveAll(space.getAccessData());

        return true;
    }
}
