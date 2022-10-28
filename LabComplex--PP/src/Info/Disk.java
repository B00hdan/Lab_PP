package info;
import track.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;


public class Disk {
        int size;
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
                     scanExistedDate();
                }
                try {
                        pw = new PrintWriter(folder);
                } catch (FileNotFoundException e) {
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
                if (params.length != 4)
                        return false;
                if(size <= 0){
                        System.out.println("There is no space on disk");
                        return false;
                }
                Album newAlbum = new Album(params[2], params[3]);
                set.addNewAlbum(newAlbum);
                size -= newAlbum.getNumbOfTracks();
                if(size < 0) {
                        deleteAlbumFromDisk(params[2]);
                        System.out.println("There is no space on disk");
                        return false;
                } else return true;
        }
        public boolean addNewTrackOnDisk(String[] params){
                if(size <= 0){
                        System.out.println("There is no space on disk");
                        return false;
                }
                MusicTrack track = set.addNewTrack(params);
                if(track != null) {
                        size--;
                        return true;
                } else return false;
        }
        public void scanExistedDate(){
                try {
                        Scanner buff = new Scanner(folder);
                        while(buff.hasNext()){
                                addNewTrackOnDisk(buff.nextLine().split(" "));
                        }
                } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                }

        }
        public boolean checkIfFileAlreadyExist() {
                try {
                        return folder.createNewFile();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }
        public boolean deleteTrackFromDisk(String name){
                if(set.deleteTrackByName(name)){
                        size++;
                        return true;
                } else{
                        System.out.println("Track doesn't exist on disk");
                        return false;
                }
        }
        public boolean deleteAlbumFromDisk(String name){
                int pastSize = size;
                size += set.deleteTracksByAlbumName(name);
                if(pastSize == size)
                        System.out.println("Album doesn't exist on disk");
                return pastSize != size;
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
        public void restoreTrackLastOnDisk(){
                set.restoreLastTracks();
        }
        public boolean changeTrackParamsOnDisk(String[] params){
                return set.changeTrackParams(params);
        }
        public void returnParamsForLastModified() {set.restorePreviousParamsForTrack();}
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
