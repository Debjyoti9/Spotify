package com.bej.usertrackservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class TrackLibrary {

    @Id
    private String trackId;
    private String trackName;
    private String genre;
    private List<String> leadSinger;
    private String album;
    private int length;
    private String playListName;

    private Artist artist;

    public TrackLibrary() {
    }

    public TrackLibrary(String trackId, String trackName, String genre, List<String> leadSinger, String album, int length, String playListName, Artist artist) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.genre = genre;
        this.leadSinger = leadSinger;
        this.album = album;
        this.length = length;
        this.playListName = playListName;
        this.artist = artist;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
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

    public List<String> getLeadSinger() {
        return leadSinger;
    }

    public void setLeadSinger(List<String> leadSinger) {
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

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
