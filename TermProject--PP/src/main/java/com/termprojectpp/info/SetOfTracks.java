package com.termprojectpp.info;

import com.termprojectpp.track.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Stream;

public class SetOfTracks {
    private final List<MusicTrack> trackList;
    public SetOfTracks(){
        trackList = new ArrayList<>();
    }
    public boolean addNewTrack(Map<String, String> paramsSimple, Label infoLabel) {
        MusicTrack newTrack;

        if (checkForDuplicateByName(paramsSimple.get("name"))) {
            List<MusicTrack> oldTrack = trackList.stream()
                    .filter(track -> track.getName().equals(paramsSimple.get("name"))).toList();
            if(oldTrack.get(0).isDeleteStatus()) {
                oldTrack.get(0).setDeleteStatus(false);
                infoLabel.setText("Track recorded successfully!");
                infoLabel.setTextFill(Color.rgb(31,110,47));
                return true;
            } else {
                infoLabel.setText("Track with this name exist already!");
                infoLabel.setTextFill(Color.rgb(181,23,23));
                return false;
            }
        }

        switch (paramsSimple.get("genre")) {
            case "Pop" -> newTrack = new PopMusic();
            case "Electronic" -> newTrack = new ElectronicMusic();
            case "Rock" -> newTrack = new RockMusic();
            case "Country" -> newTrack = new CountryMusic();
            case "Classical" -> newTrack = new ClassicalMusic();
            default -> {
                infoLabel.setText("Genre is not correct!");
                infoLabel.setTextFill(Color.rgb(181,23,23));
                return false;
            }
        }

        if (!newTrack.builder(paramsSimple)) {
            infoLabel.setText("Data is in wrong format!");
            infoLabel.setTextFill(Color.rgb(181,23,23));
            return false;
        }

        this.trackList.add(newTrack);
        infoLabel.setText("Track recorded successfully!");
        infoLabel.setTextFill(Color.rgb(31,110,47));
        return true;
    }
    public void addNewAlbum(Album data) {
        for (MusicTrack track : data.getSet().getTrackList())
            if (trackList.stream().filter(object -> object.getName().equals(track.getName())).count() != 1) {
                trackList.add(track);
            } else {
                List<MusicTrack> oldTrack = trackList.stream()
                        .filter(object -> object.getName().equals(track.getName())).toList();
                oldTrack.get(0).setDeleteStatus(false);
            }
    }
    public boolean checkForDuplicateByName(String name) {
        if(trackList.stream().filter(object -> object.getName().equals(name) && !object.isDeleteStatus()).count() == 1){
            System.out.println("Track already exist on disk");
            return true;
        } else return false;
    }
    public boolean deleteTrackByName(String name) {
        if(trackList.isEmpty())
            return false;
        List<MusicTrack> trackToDelete = trackList.stream().filter(track -> track.getName().equals(name)).toList();
        if(trackToDelete.size() != 0 && trackToDelete.get(0) != null){
            trackToDelete.get(0).setDeleteStatus(true);
            return true;
        } else return false;
    }
    public int deleteTracksByAlbumName(String name) {
        if(trackList.isEmpty())
            return trackList.size();
        List<MusicTrack> tracksToDelete = trackList.stream().filter(track -> track.getAlbumName().equals(name)).toList();
        tracksToDelete.forEach(track -> track.setDeleteStatus(true));
        return tracksToDelete.size();
    }
    public void sortListBy(String condition){
        switch (condition){
            case "name" -> trackList.sort(new SortByName());
            case "genre" -> trackList.sort(new SortByGenre());
            case "duration" -> trackList.sort(new SortByDuration());
        }
    }
    public String getDurationForAllTracks(){
        int timeInSeconds = 0;
        int timeInMinutes = 0;
        int timeInHours = 0;
        String[] tempSplit;
        if(trackList.isEmpty())
            return "00:00:00";

        for(MusicTrack track : trackList){
            if(track.isDeleteStatus())
                continue;
            tempSplit = track.getDuration().split(":");
            timeInSeconds += Integer.parseInt(tempSplit[2]);
            timeInMinutes += Integer.parseInt(tempSplit[1]);
            timeInHours += Integer.parseInt(tempSplit[0]);
        }
        while(timeInSeconds >= 60) {
            timeInMinutes++;
            timeInSeconds -= 60;
        }
        while(timeInMinutes >= 60) {
            timeInHours++;
            timeInMinutes -= 60;
        }
        return (timeInHours + ":" + timeInMinutes + ":" + timeInSeconds);
    }
    public List<MusicTrack> getTrackList() {
        return trackList;
    }

}