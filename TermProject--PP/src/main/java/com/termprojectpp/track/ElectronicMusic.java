package com.termprojectpp.track;

public class ElectronicMusic extends MusicTrack {

    public ElectronicMusic(){
        this.genre = "Electronic";
    }

    @Override
    public String getGenre() {
        return genre;
    }

}
