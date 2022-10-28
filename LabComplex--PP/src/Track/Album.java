package track;

import info.SetOfTracks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Album {
    private final SetOfTracks set;
    private final File folder;
    private final String name;
    private int numbOfCompositions;

    public Album(String name, String location){
        this.name = name;
        folder = new File(location);
        set = new SetOfTracks();
        numbOfCompositions = 0;
        getDataFromFile();

    }
    public SetOfTracks getSet() {
        return set;
    }
    public void getDataFromFile(){
        try {
            Scanner buff = new Scanner(folder);
            while(buff.hasNext()){
                MusicTrack newComposition = set.addNewTrack(buff.nextLine().split(" "));
                newComposition.setAlbumName(name);
                numbOfCompositions++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public int getNumbOfTracks() {
        return numbOfCompositions;
    }
}
