package com.termprojectpp.track;

import java.util.Comparator;

public class SortByGenre implements Comparator<MusicTrack> {
    @Override
    public int compare(MusicTrack o1, MusicTrack o2) {
        return o1.getGenre().compareTo(o2.getGenre());
    }
}
