package info;
import org.apache.log4j.Logger;
import track.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;

import static org.apache.log4j.Logger.getLogger;


public class Disk {
        private final Logger log = getLogger(Disk.class);
        private int size;
        private SetOfTracks set = null;
        private File folder;
        private PrintWriter pw;

        public String diskLocation(){
                return folder.getPath();
        }
        public void connectDisk(String location, int size){
                this.size = size;
                set = new SetOfTracks();
                folder = new File(location);
                if(!checkIfFileAlreadyExist()){
                        scanExistData();
                }
                try{
                        pw = new PrintWriter(folder);
                } catch (FileNotFoundException e) {
                        log.error("Disk file not found");
                        throw new RuntimeException(e);
                }
        }
        public void disconnectDisk(){
                writeToFile();
                set = null;
                folder = null;
        }
        public boolean connectionStatus(){
                return set != null;
        }
        public boolean addAlbumOnDisk(String[] params){
                if (params.length != 4) {
                        log.warn("New album has not been added to disc");
                        return false;
                }
                else if(size <= 0){
                        System.out.println("There is no space on disk");
                        log.warn("New album has not been added to disc");
                        return false;
                }
                Album newAlbum = new Album(params[2], params[3]);
                set.addNewAlbum(newAlbum);
                size -= newAlbum.getNumbOfTracks();
                if(size < 0) {
                        deleteAlbumFromDisk(params[2]);
                        System.out.println("There is no space on disk");
                        log.warn("New album has not been added to disc");
                        return false;
                } else {
                        log.info("New album has been added to disc");
                        return true;
                }
        }
        public boolean addNewTrackOnDisk(String[] params){
                if(size <= 0){
                        System.out.println("There is no space on disk");
                        log.warn("New track has not been added to disc");
                        return false;
                }
                MusicTrack track = set.addNewTrack(params);
                if(track != null) {
                        size--;
                        log.info("New track has been added to disc");
                        return true;
                } else {
                        log.warn("New track has not been added to disc");
                        return false;
                }
        }
        public boolean checkIfFileAlreadyExist() {
                try {
                        return folder.createNewFile();
                } catch (IOException e) {
                        log.error("Disk file not exist");
                        throw new RuntimeException(e);
                }
        }
        public void scanExistData() {
                try {
                        Scanner buff = new Scanner(folder);
                        while (buff.hasNext()) {
                                addNewTrackOnDisk(buff.nextLine().split(" "));
                        }
                } catch (FileNotFoundException e) {
                        log.error("Disk file not found");
                        throw new RuntimeException(e);
                }
        }
        public boolean deleteTrackFromDisk(String name){
                if(set.deleteTrackByName(name)){
                        size++;
                        log.info("Track has been removed from disc");
                        return true;
                } else{
                        System.out.println("Track doesn't exist on disk");
                        log.warn("Track has not been removed from disc");
                        return false;
                }
        }
        public boolean deleteAlbumFromDisk(String name){
                int pastSize = size;
                size += set.deleteTracksByAlbumName(name);
                if(pastSize == size) {
                        System.out.println("Album doesn't exist on disk");
                        log.warn("Album has not been removed from disc");
                        return false;
                } else {
                        log.info("Album has been removed from disc");
                        return true;
                }
        }
        public void writeToFile(){
                for(MusicTrack track : set.getTrackList()){
                        if(track.isDeleteStatus())
                                continue;
                        pw.print("name=" + track.getName());
                        pw.print(" duration=" + track.getDuration());
                        if(!Objects.equals(track.getAuthor(), ""))
                                pw.print(" author=" + track.getAuthor());
                        if(!Objects.equals(track.getDateOfWriting(), ""))
                                pw.print(" dateOfWriting=" + track.getDateOfWriting());
                        if(!Objects.equals(track.getDateOfPublication(), ""))
                                pw.print(" dateOfPublication=" + track.getDateOfPublication());
                        if(!Objects.equals(track.getAlbumName(), ""))
                                pw.print(" album=" + track.getAlbumName());
                        pw.println(" genre=" + track.getGenre());
                }
                pw.close();
        }
        public void restoreLastTrackOnDisk(){
                set.restoreLastTracks();
        }
        public boolean changeTrackParamsOnDisk(String[] params){
                return set.changeTrackParams(params);
        }
        public void returnParamsForLastModified() { set.restorePreviousParamsForTrack(); }
        public boolean printFromDiskAllBy(String[] params){
                if(params.length == 1) {
                        return false;
                } else if(params[1].equals("duration")){
                        return set.printTracksByDurationBorders(params);
                } else if(params[1].equals("all")){
                        for(MusicTrack track : set.getTrackList()){
                                if(!track.isDeleteStatus())
                                        System.out.println(track);
                        }
                        return true;
                } else{
                        String[] paramSplit = params[1].split("=");
                        return set.printTracksBy(paramSplit);
                }

        }
        public void sortOnDiskBy(String condition){
                set.sortListBy(condition);
        }
        public void calculateDurationOfAllTracks(){
                set.printDurationForAllTracks();
        }
}
