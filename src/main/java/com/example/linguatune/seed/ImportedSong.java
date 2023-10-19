package com.example.linguatune.seed;

public class ImportedSong {
    private String track_uri;
    private String artists;
    private String track_name;
    private String image_uri;
    private String lyrics;

    // Constructors (default and parameterized)
    public ImportedSong() {
    }

    public ImportedSong(String track_uri, String artists, String track_name, String image_uri) {
        this.track_uri = track_uri;
        this.artists = artists;
        this.track_name = track_name;
        this.image_uri = image_uri;
    }

    // Getters and setters
    public String getTrack_uri() {
        return track_uri;
    }

    public void setTrack_uri(String track_uri) {
        this.track_uri = track_uri;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getTrack_name() {
        return track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }


    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

}



