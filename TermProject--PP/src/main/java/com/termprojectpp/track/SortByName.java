package com.termprojectpp.track;

import java.util.Comparator;

public class SortByName implements Comparator<MusicTrack> {
    @Override
    public int compare(MusicTrack o1, MusicTrack o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
