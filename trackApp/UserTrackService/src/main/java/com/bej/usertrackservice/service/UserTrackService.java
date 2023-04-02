package com.bej.usertrackservice.service;

import com.bej.usertrackservice.domain.Playlist;
import com.bej.usertrackservice.domain.Track;
import com.bej.usertrackservice.domain.TrackLibrary;
import com.bej.usertrackservice.domain.User;
import com.bej.usertrackservice.exception.*;

import java.util.List;

public interface UserTrackService {
    User registerUser(User user) throws UserAlreadyExistsException;
    User createPlayListAndAddSongToPlayList(String email, Playlist playlistName) throws PlayListAlreadyExistException,UserNotFoundException;

    public User deleteSingleTrackFromPlayList(String email, String trackId, String playlistName) throws TrackNotFoundException, PlayListNotFoundException;
    public User deleteSinglePlayListFromPlaylists(String email, String playlistName) throws PlayListNotFoundException;
    List<String> getAllPlaylistsForUser(String email) throws PlayListNotFoundException;
    Track getSingleTrackFromPlaylist(String email,String trackId,String playListName) throws TrackNotFoundException;
    TrackLibrary getSingleTrackFromLibrary(String trackId) throws TrackNotFoundException;
    List<TrackLibrary> getAllTracks() throws TrackNotFoundException;
    List<TrackLibrary> saveTrack(List<TrackLibrary> trackLibrary) ; //Admin only
    List<Track> getAllTracksFromAPlayList(String email, String playListName) throws TrackNotFoundException;

    User getUser(String email) throws UserNotFoundException;

}
