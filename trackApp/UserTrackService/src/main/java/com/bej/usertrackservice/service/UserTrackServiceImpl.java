package com.bej.usertrackservice.service;

import com.bej.usertrackservice.domain.Playlist;
import com.bej.usertrackservice.domain.Track;
import com.bej.usertrackservice.domain.TrackLibrary;
import com.bej.usertrackservice.exception.*;
import com.bej.usertrackservice.proxy.UserProxy;
import com.bej.usertrackservice.repository.TrackLibraryRepository;
import com.bej.usertrackservice.repository.UserTrackRepository;
import com.bej.usertrackservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
@Service
public class UserTrackServiceImpl implements UserTrackService {
    private UserTrackRepository userTrackRepository;

    @Autowired
    private TrackLibraryRepository trackLibraryRepository;
    @Autowired
    public UserTrackServiceImpl(UserTrackRepository userTrackRepository) {
        this.userTrackRepository = userTrackRepository;
    }

    @Autowired
    private UserProxy userProxy;

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if(userTrackRepository.findById(user.getEmail()).isPresent())
        {
            throw new UserAlreadyExistsException();
        }
        User userFormDataBase = userTrackRepository.save(user);
        if (!(userFormDataBase.getEmail().isEmpty())) {
            ResponseEntity responseEntity = userProxy.saveUser(userFormDataBase);
            System.out.println(responseEntity.getBody());
        }
        return userFormDataBase;

    }

    @Override
    public User createPlayListAndAddSongToPlayList(String email, Playlist playlistName) throws PlayListAlreadyExistException, UserNotFoundException {
        if(userTrackRepository.findById(email).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = userTrackRepository.findByEmail(email);
        if(user.getPlayLists() == null)
        {
            
            user.setPlayLists(Arrays.asList(playlistName));
        }
        else {
            List<Playlist> playlists = user.getPlayLists();
            Iterator<Playlist> iterator = playlists.listIterator();
            int flag = 0;

            for (int i = 0; i < playlists.size(); i++) {
                if (playlists.get(i).getPlayListName().equals(playlistName.getPlayListName())) {
                        playlists.get(i).getTrackList().add(playlistName.getTrackList().get(0));
                        user.setPlayLists(playlists);

                }
            }

            while (iterator.hasNext()){
                if (iterator.next().getPlayListName().equals(playlistName.getPlayListName())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                playlists.add(playlistName);
                user.setPlayLists(playlists);
            }


        }
        return userTrackRepository.save(user);

//----------------------------------playlist created ---------------------------------------------
    }

    @Override
    public User deleteSingleTrackFromPlayList(String email, String trackId, String playlistName) throws TrackNotFoundException, PlayListNotFoundException{

        User user = userTrackRepository.findByEmail(email);
        if(user.getPlayLists() == null)
        {
            throw new PlayListNotFoundException();
        }
        else {
            List<Playlist> playlists = user.getPlayLists();

            for (int i = 0; i < playlists.size(); i++) {
                if (playlists.get(i).getPlayListName().equals(playlistName)) {

                    List<Track> trackList = playlists.get(i).getTrackList();
                    for (int j = 0; j < trackList.size(); j++) {

                        if (trackList.get(j).getTrackId().equals(trackId)) {
                            playlists.get(i).getTrackList().remove(trackList.get(j));
                            user.setPlayLists(playlists);
                            break;
                        }else {
                            throw new TrackNotFoundException();
                        }

                    }

                    break;

                }
            }
        }
        return userTrackRepository.save(user);
    }

    @Override
    public User deleteSinglePlayListFromPlaylists(String email, String playlistName) throws PlayListNotFoundException {


        User user = userTrackRepository.findByEmail(email);
        if(user.getPlayLists() == null)
        {
            throw new PlayListNotFoundException();
        }
        else {
            List<Playlist> playlists = user.getPlayLists();

            for (int i = 0; i < playlists.size(); i++) {
                if (playlists.get(i).getPlayListName().equals(playlistName)) {

                    playlists.remove(playlists.get(i));
                    user.setPlayLists(playlists);
                    break;

                }
            }

        }
        return userTrackRepository.save(user);
    }

    @Override
    public List<TrackLibrary> getAllTracks() throws TrackNotFoundException{
        return this.trackLibraryRepository.findAll();
    }

    @Override
    public List<String> getAllPlaylistsForUser(String email) throws PlayListNotFoundException{

        List<String> playListNames = new ArrayList<>();
        User user = userTrackRepository.findByEmail(email);

        List<Playlist> playlistList = user.getPlayLists();
        for (int i = 0; i < playlistList.size(); i++) {
            playListNames.add(playlistList.get(i).getPlayListName());
        }

        return playListNames;

    }

    @Override
    public Track getSingleTrackFromPlaylist(String email,String trackId,String playListName) throws TrackNotFoundException {
        User user = userTrackRepository.findByEmail(email);

        List<Track> tracks = null;
        Track track = null;

        for (int i = 0; i < user.getPlayLists().size(); i++) {
            if (user.getPlayLists().get(i).getPlayListName().equals(playListName)) {
                tracks = user.getPlayLists().get(i).getTrackList();
                break;
            }
        }
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getTrackId().equals(trackId)) {
                track = tracks.get(i);
                break;
            }else {
                throw new TrackNotFoundException();
            }
        }
        return track;
    }

    @Override
    public TrackLibrary getSingleTrackFromLibrary(String trackId) throws TrackNotFoundException {
        return this.trackLibraryRepository.findById(trackId).get();
    }

    @Override
    public TrackLibrary saveTrack(TrackLibrary trackLibrary){

        return this.trackLibraryRepository.save(trackLibrary);

    }
}
