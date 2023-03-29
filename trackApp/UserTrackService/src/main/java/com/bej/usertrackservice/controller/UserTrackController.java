package com.bej.usertrackservice.controller;

import com.bej.usertrackservice.domain.Playlist;
import com.bej.usertrackservice.domain.TrackLibrary;
import com.bej.usertrackservice.exception.*;
import com.bej.usertrackservice.service.UserTrackService;
import com.bej.usertrackservice.domain.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2")
public class UserTrackController {
private UserTrackService userTrackService;
private ResponseEntity<?> responseEntity;
@Autowired
    public UserTrackController(UserTrackService userTrackService) {
        this.userTrackService = userTrackService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
    try {
        responseEntity =  new ResponseEntity<>(userTrackService.registerUser(user), HttpStatus.CREATED);
    }
    catch(UserAlreadyExistsException e)
    {
        throw new UserAlreadyExistsException();
    }
    return responseEntity;
    }

    @PostMapping("/user/savePlayList")
    public ResponseEntity<?> createPlaylistAndAddSongs(@RequestBody Playlist playListName, HttpServletRequest request) throws PlayListAlreadyExistException, UserNotFoundException{


        try {
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("email from claims :: " + claims.getSubject());
            String email = claims.getSubject();
            System.out.println("email :: "+email);
            responseEntity = new ResponseEntity<>(userTrackService.createPlayListAndAddSongToPlayList(email,playListName),HttpStatus.CREATED);
        }catch (PlayListAlreadyExistException playListAlreadyExistException){
            throw new PlayListAlreadyExistException();

        }catch (UserNotFoundException userNotFoundException){
            throw new UserNotFoundException();
        }
        return responseEntity;
    }



    @PostMapping("/saveTrack")
    public ResponseEntity<?> saveTrack(@RequestBody TrackLibrary trackLibrary) {
            return new ResponseEntity<>(this.userTrackService.saveTrack(trackLibrary), HttpStatus.OK);

    }

    @GetMapping("/tracks")
    public ResponseEntity<?> getAllUserTracksFromList() throws TrackNotFoundException {
        try{
            return new ResponseEntity<>(userTrackService.getAllTracks(), HttpStatus.OK);
        }catch(TrackNotFoundException trackNotFoundException)
            {
                throw new TrackNotFoundException();
            }

    }

    @GetMapping("/user/playListNames")
    public ResponseEntity<?> getPlayLists(HttpServletRequest request) throws PlayListNotFoundException{
        try {

            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("email from claims :: " + claims.getSubject());
            String email = claims.getSubject();
            System.out.println("email :: "+email);

            return new ResponseEntity<>(userTrackService.getAllPlaylistsForUser(email),HttpStatus.FOUND);
        }catch (PlayListNotFoundException playListNotFoundException){
            throw new PlayListNotFoundException();
        }


    }

    @GetMapping("/user/{playListName}/{trackId}")
    public ResponseEntity<?> getSingleTrackFromPlayList(@PathVariable String playListName,HttpServletRequest request,@PathVariable String trackId) throws TrackNotFoundException{
        try {
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("email from claims :: " + claims.getSubject());
            String email = claims.getSubject();
            System.out.println("email :: "+email);

            return new ResponseEntity<>(userTrackService.getSingleTrackFromPlaylist(email,trackId,playListName),HttpStatus.FOUND);
        } catch (TrackNotFoundException trackNotFoundException) {
            throw new TrackNotFoundException();
        }
    }


    @DeleteMapping("/user/{playListName}")
    public ResponseEntity<?> deleteUserTrackFromList(@PathVariable String playListName,HttpServletRequest request) throws PlayListNotFoundException
    {

        try {

            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("email from claims :: " + claims.getSubject());
            String email = claims.getSubject();
            System.out.println("email :: "+email);
            responseEntity = new ResponseEntity<>(userTrackService.deleteSinglePlayListFromPlaylists(email, playListName), HttpStatus.OK);
        } catch (PlayListNotFoundException e) {
            throw new RuntimeException(e);
        }
        return responseEntity;
    }

    @DeleteMapping("/user/{playListName}/{trackId}")
    public ResponseEntity<?> deleteSingleTrackFromPlayList(@PathVariable String playListName, @PathVariable String trackId,HttpServletRequest request) throws PlayListNotFoundException,TrackNotFoundException
    {
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("email from claims :: " + claims.getSubject());
        String email = claims.getSubject();
        System.out.println("email :: "+email);
        try {
            responseEntity = new ResponseEntity<>(userTrackService.deleteSingleTrackFromPlayList(email,trackId,playListName), HttpStatus.OK);
        } catch (PlayListNotFoundException playListNotFoundException) {
            throw new PlayListNotFoundException();
        }catch (TrackNotFoundException trackNotFoundException){
            throw new TrackNotFoundException();
        }
        return responseEntity;
    }
}

