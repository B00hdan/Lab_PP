package info;

import track.*;
import java.util.*;
import java.util.stream.Stream;

public class SetOfTracks {
    private final List<MusicTrack> trackList;
    private List<Integer> lastDeletedTracks;
    private Map<String, String> lastModifiedTrackParams;
    public SetOfTracks(){
        trackList = new ArrayList<>();
    }
    public MusicTrack addNewTrack(String[] params) {
        MusicTrack track;

        Map<String, String> paramsSimple = makeMapForParams(params);

        if (Stream.of(paramsSimple).noneMatch(param -> param.containsKey("genre")) ||
                checkForDuplicateByName(paramsSimple.get("name")))
            return null;

        switch (paramsSimple.get("genre")) {
            case "Pop" -> track = new PopMusic();
            case "Electronic" -> track = new ElectronicMusic();
            case "Rock" -> track = new RockMusic();
            case "Country" -> track = new CountryMusic();
            case "Classical" -> track = new ClassicalMusic();
            default -> {
                return null;
            }
        }

        if (!track.builder(paramsSimple))
            return null;

        this.trackList.add(track);
        return track;

    }
    public void addNewAlbum(Album data){
        for(MusicTrack track : data.getSet().getTrackList())
            if(trackList.stream().filter(object -> object.getName().equals(track.getName())).count() != 1)
                trackList.add(track);
    }
    public boolean checkForDuplicateByName(String name) {
        if(trackList.stream().filter(object -> object.getName().equals(name) && !object.isDeleteStatus()).count() == 1){
            System.out.println("Track already exist on disk");
            return true;
        } else return false;
    }
    public boolean deleteTrackByName(String name) {
        lastDeletedTracks = new ArrayList<>();
        if(setIsEmpty())
            return false;
        List<MusicTrack> trackToDelete = trackList.stream().filter(track -> track.getName().equals(name)).toList();
        if(trackToDelete.get(0) != null){
            trackToDelete.get(0).setDeleteStatus(true);
            lastDeletedTracks.add(trackList.indexOf(trackToDelete.get(0)));
            return true;
        } else return false;
    }
    public int deleteTracksByAlbumName(String name) {
        lastDeletedTracks = new ArrayList<>();
        if(setIsEmpty())
            return trackList.size();
        List<MusicTrack> tracksToDelete = trackList.stream().filter(track -> track.getAlbumName().equals(name)).toList();
        tracksToDelete.forEach(track -> lastDeletedTracks.add(trackList.indexOf(track)));
        tracksToDelete.forEach(track -> track.setDeleteStatus(true));
        return tracksToDelete.size();
    }
    public void restoreLastTracks(){
        lastDeletedTracks.forEach(index -> trackList.get(index).setDeleteStatus(false));
        lastDeletedTracks.clear();
    }
    public boolean changeTrackParams(String[] params){
        lastModifiedTrackParams = new HashMap<>();
        if(setIsEmpty())
            return false;
        Map<String, String> newParamsSimple = makeMapForParams(params);
        if(newParamsSimple.entrySet().stream().filter(param -> param.getKey().contains("name")).count() > 1 ||
                trackList.stream().noneMatch(track -> track.getName().equals(newParamsSimple.get("name"))))
            return false;

        List<MusicTrack> trackForChanges = trackList.stream().filter(track -> track.getName()
                .equals(newParamsSimple.get("name"))).toList();

        lastModifiedTrackParams.put("name", trackForChanges.get(0).getName());
        lastModifiedTrackParams.put("duration", trackForChanges.get(0).getDuration());
        lastModifiedTrackParams.put("author", trackForChanges.get(0).getAuthor());
        lastModifiedTrackParams.put("dateOfWriting", trackForChanges.get(0).getDateOfWriting());
        lastModifiedTrackParams.put("dateOfPublication", trackForChanges.get(0).getDateOfPublication());
        lastModifiedTrackParams.put("album", trackForChanges.get(0).getAlbumName());

        trackForChanges.get(0).builder(newParamsSimple);
        return true;
    }
    public void restorePreviousParamsForTrack(){
        List<MusicTrack> trackForChanges = trackList.stream().filter(track -> track.getName()
                .equals(lastModifiedTrackParams.get("name"))).toList();
        trackForChanges.get(0).builder(lastModifiedTrackParams);
    }
    public boolean printTracksBy(String[] condition){
        if(setIsEmpty() || condition.length == 1)
            return false;
        List<MusicTrack> tempList = switch (condition[0]) {
            case "name" -> trackList.stream()
                    .filter(track -> track.getName().equals(condition[1])).toList();
            case "author" -> trackList.stream()
                    .filter(track -> track.getAuthor().equals(condition[1])).toList();
            case "dateOfWriting" -> trackList.stream()
                    .filter(track -> track.getDateOfWriting().equals(condition[1])).toList();
            case "dateOfPublication" -> trackList.stream()
                    .filter(track -> track.getDateOfPublication().equals(condition[1])).toList();
            case "genre" -> trackList.stream()
                    .filter(track -> track.getGenre().equals(condition[1])).toList();
            case "album" -> trackList.stream()
                    .filter(track -> track.getAlbumName().equals(condition[1])).toList();
            default -> new ArrayList<>();
        };
        if (tempList.isEmpty()) {
            System.out.println("There are no tracks with this parameter on the disc");
            return false;
        }
        for(MusicTrack track: tempList)
            if(!track.isDeleteStatus())
                System.out.println(track);
        return true;
    }
    public boolean printTracksByDurationBorders(String[] params){
        List<MusicTrack> tempList = new ArrayList<>();
        String less = "null", more = "null";

        if(params.length > 4 || setIsEmpty())
            return false;

        for(String param: params){
            if(param.contains(">")) {
                less = param.replace(">", "");
                if(!less.matches("\\d{2}:\\d{2}:\\d{2}"))
                    return false;
            }else if(param.contains("<")) {
                more = param.replace("<", "");
                if(!more.matches("\\d{2}:\\d{2}:\\d{2}"))
                    return false;
            }
        }

        String finalMore = more;
        String finalLess = less;

        if(!less.equals("null") && !finalMore.equals(" null")){
            tempList = trackList.stream().filter(track -> track.getDuration().compareTo(finalLess) > 0 &&
                    track.getDuration().compareTo(finalMore) < 0).toList();
        } else if(!finalLess.equals("null")){
            tempList = trackList.stream().filter(track -> track.getDuration().compareTo(finalLess) > 0).toList();
        } else if(!finalMore.equals("null"))
            tempList = trackList.stream().filter(track -> track.getDuration().compareTo(finalMore) < 0).toList();

        for(MusicTrack track: tempList)
            if(!track.isDeleteStatus())
                System.out.println(track);
        return true;
    }
    public void sortListBy(String condition){
        switch (condition){
            case "name" -> trackList.sort(new SortByName());
            case "genre" -> trackList.sort(new SortByGenre());
            case "duration" -> trackList.sort(new SortByDuration());
        }
    }
    public void printDurationForAllTracks(){
        int timeInSeconds = 0;
        int timeInMinutes = 0;
        int timeInHours = 0;
        String[] tempSplit;
        if(setIsEmpty())
            return;

        for(MusicTrack track : trackList){
            if(track.isDeleteStatus())
                continue;
            tempSplit = track.getDuration().split(":");
            timeInSeconds += Integer.parseInt(tempSplit[2]);
            timeInMinutes += Integer.parseInt(tempSplit[1]);
            timeInHours += Integer.parseInt(tempSplit[0]);
        }
        while(timeInSeconds > 60) {
            timeInMinutes++;
            timeInSeconds -= 60;
        }
        while(timeInMinutes > 60) {
            timeInHours++;
            timeInMinutes -= 60;
        }
        System.out.println("Duration of all tracks together: " + timeInHours
                + ":" + timeInMinutes + ":" + timeInSeconds);
    }
    private boolean setIsEmpty(){
        if (trackList.isEmpty()) {
            System.out.println("Disk is empty");
            return true;
        } else return false;
    }
    private Map<String, String> makeMapForParams(String[] params){
        Map<String, String> newMap = new HashMap<>();
        for(String param :params) {
            if (!param.contains("="))
                continue;
            String[] paramSplit = param.split("=");
            newMap.put(paramSplit[0], paramSplit[1]);
        }
        return newMap;
    }
    public List<MusicTrack> getTrackList() {
        return trackList;
    }

}