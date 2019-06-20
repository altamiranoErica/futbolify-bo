package com.tip.futbolifybo.service.provider;

import com.tip.futbolifybo.exception.InvalidProviderAccess;
import com.tip.futbolifybo.exception.InvalidVenueDataException;
import com.tip.futbolifybo.model.AccessData;
import com.tip.futbolifybo.model.Venue;
import com.tip.futbolifybo.repository.AccessDataRepository;
import com.tip.futbolifybo.repository.VenueRepository;
import com.tip.futbolifybo.service.result.PlaylistResult;
import com.tip.futbolifybo.service.result.TrackResult;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SpotifyProviderService {

    protected static String CLIENT_ID = "ef56629f44be41e895cac7d03141b9e0";
    protected static String CLIENT_SECRET = "7a1a681c2b9a45e48d95a5da81610dd9";
    protected static URI REDIRECT_URI = SpotifyHttpManager.makeUri("http://localhost:4200/dashboard/venue/callback");

    @Autowired
    private AccessDataRepository accessDataRepository;

    @Autowired
    private VenueRepository venueRepository;

    public List<AccessData> getAccessData(String code) throws InvalidVenueDataException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(REDIRECT_URI)
                .build();


        AuthorizationCodeRequest codeRequest = spotifyApi.authorizationCode(code).build();
        AuthorizationCodeCredentials credentials;
        try {
            credentials = codeRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            throw new InvalidVenueDataException(e);
        }

        List<AccessData> data = new ArrayList<>();
        data.add(new AccessData("accessToken", credentials.getAccessToken()));
        data.add(new AccessData("refreshToken", credentials.getRefreshToken()));

        return data;
    }

    public List<TrackResult> searchTracks(String query, Integer page) {
        List<TrackResult> result = new ArrayList<>();
        if(query == null || query.isEmpty()) return result;

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        ClientCredentials clientCredentials;
        Paging<Track> trackPaging;
        try {
            clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query)
                    .offset((page == null) ? 0 : page)
                    .limit(10)
                    .build();
            trackPaging = searchTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
            return result;
        }

        for (Track track : trackPaging.getItems()) {
            TrackResult trackResult = new TrackResult().loadFewInfo(track);
            result.add(trackResult);
        }

        return result;
    }

    public TrackResult getCurrentTrackWithPositionIntoPlaylist(Venue venue) {
        String token;

        try {
            token = this.getAccessToken(venue);
        } catch (InvalidProviderAccess invalidProviderAccess) {
            return null;
        }

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token)
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

        GetPlaylistsTracksRequest getPlaylistsTracksRequest = spotifyApi
                .getPlaylistsTracks(venue.getPlaylistID())
                .build();

        Paging<PlaylistTrack> playlistTrackPaging;
        try {
            playlistTrackPaging = getPlaylistsTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
            return null;
        }

        List<String> tracks = Arrays.asList(playlistTrackPaging.getItems()).stream()
                .map(playlistTrack -> playlistTrack.getTrack().getId())
                .collect(Collectors.toList());

        TrackResult trackResult = new TrackResult(currentlyPlaying.getItem(),
                currentlyPlaying.getIs_playing(),currentlyPlaying.getProgress_ms());
        trackResult.setPosition(tracks.indexOf(currentlyPlaying.getItem().getId()));

        return trackResult;
    }


    public TrackResult getCurrentTrack(Venue venue) {
        String token;

        try {
            token = this.getAccessToken(venue);
        } catch (InvalidProviderAccess invalidProviderAccess) {
            return null;
        }

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token)
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

        TrackResult trackResult = new TrackResult(currentlyPlaying.getItem(),
                currentlyPlaying.getIs_playing(),currentlyPlaying.getItem().getDurationMs());

        return trackResult;
    }

    public Boolean addTrackToPlaylist(Venue venue, String trackID, Integer positionTrack) {
        String token;

        try {
            token = this.getAccessToken(venue);
        } catch (InvalidProviderAccess invalidProviderAccess) {
            return null;
        }

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token)
                .build();
        AddTracksToPlaylistRequest addTracksToPlaylistRequest = spotifyApi
                .addTracksToPlaylist(venue.getPlaylistID(), new String[]{"spotify:track:" + trackID})
                .position(positionTrack)
                .build();

        try {
            addTracksToPlaylistRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getAccessToken(Venue venue) throws InvalidProviderAccess {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRefreshToken(venue.getAccessData("refreshToken").getValue())
                .build();

        AuthorizationCodeRefreshRequest codeRefreshRequest = spotifyApi.authorizationCodeRefresh().build();
        AuthorizationCodeCredentials credentials = null;
        try {
            credentials = codeRefreshRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            throw new InvalidProviderAccess(e.getMessage());
        }

        return credentials.getAccessToken();
    }

    public List<TrackResult> getRandomTracks(Venue venue, Integer count) {
        List<TrackResult> result = new ArrayList<>();
        String token;

        try {
            token = this.getAccessToken(venue);
        } catch (InvalidProviderAccess invalidProviderAccess) {
            return null;
        }

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token)
                .build();
        GetPlaylistsTracksRequest getPlaylistsTracksRequest = spotifyApi
                .getPlaylistsTracks(venue.getPlaylistID())
                .build();

        Paging<PlaylistTrack> playlistTrackPaging;
        try {
            playlistTrackPaging = getPlaylistsTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
            return new ArrayList<>();
        }

        for (int i = 0; i < count; i++) {
            Integer trackNumber = new Random().nextInt(playlistTrackPaging.getTotal());
            PlaylistTrack playlistTrack = playlistTrackPaging.getItems()[trackNumber];

            TrackResult track = new TrackResult().loadFewInfo(playlistTrack.getTrack());
            result.add(track);
        }

        return result;
    }


    public List<PlaylistResult> getPlaylistFromVenue(Venue venue){
        List<PlaylistResult> result = new ArrayList<>();
        String token;
        try {
            token = this.getAccessToken(venue);
        } catch (InvalidProviderAccess invalidProviderAccess) {
            return null;
        }

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token)
                .build();

        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .build();

        Paging<PlaylistSimplified> playlistSimplifiedPaging;
        try {
            playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();

        } catch (IOException | SpotifyWebApiException e) {
            return null;
        }

        for (PlaylistSimplified playlist : playlistSimplifiedPaging.getItems()) {
            PlaylistResult playlistResult = new PlaylistResult();
            playlistResult.setPlaylistID(playlist.getId());
            playlistResult.setName(playlist.getName());

            result.add(playlistResult);
        }

        return result;
    }


}
