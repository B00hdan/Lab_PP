package com.termprojectpp.track;

public class ClassicalMusic extends MusicTrack {
    String genre;

    public ClassicalMusic(){
        this.genre = "Classical";
    }

    @Override
    public String getGenre() {
        return genre;
    }
}
