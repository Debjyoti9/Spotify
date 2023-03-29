package com.bej.usertrackservice.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Playlist {
    private List<Track> trackList;

    @Id
    private String playListName;


    public Playlist() {
    }

    public Playlist(List<Track> trackList, String playListName) {
        this.trackList = trackList;
        this.playListName = playListName;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }
}


