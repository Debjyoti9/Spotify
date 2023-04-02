package com.bej.usertrackservice.domain;

import org.springframework.data.annotation.Id;

public class Track {
    @Id
    private String id;
    private String trackName;
    private String genre;
    private String leadSinger;
    private String album;
    private int length;
    private String artist;

    public Track() {
    }

    public Track(String id, String trackName, String genre, String leadSinger, String album, int length, String artist) {
        this.id = id;
        this.trackName = trackName;
        this.genre = genre;
        this.leadSinger = leadSinger;
        this.album = album;
        this.length = length;
//        this.playListName = playListName;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLeadSinger() {
        return leadSinger;
    }

    public void setLeadSinger(String leadSinger) {
        this.leadSinger = leadSinger;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

//    public String getPlayListName() {
//        return playListName;
//    }
//
//    public void setPlayListName(String playListName) {
//        this.playListName = playListName;
//    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
