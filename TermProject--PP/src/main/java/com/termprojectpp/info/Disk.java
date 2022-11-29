package com.termprojectpp.info;

import java.sql.*;
import java.util.*;

import com.termprojectpp.track.*;
import javafx.scene.control.Label;
public class Disk {
        private static final Disk instance = new Disk();
        private String diskTableName;
        private SetOfTracks set = null;
        private Connection connection = null;
        public boolean saveStatus;

        public static Disk getInstance(){
                return instance;
        }
        public void setData(String name, Connection connection){
                diskTableName = name;
                set = new SetOfTracks();
                this.connection = connection;
                saveStatus = false;
                if(checkIfNotEmpty())
                        scanExistData();
        }
        public void disconnectDisk(){
                try {
                        connection.close();
                } catch (SQLException e) {
                        //log.error("Error closing connection to disk");
                        throw new RuntimeException(e);
                }
        }
        public String getDiskTableName() {
                return diskTableName;
        }
        public boolean checkIfNotEmpty() {
                try{
                        ResultSet resul = connection.createStatement().executeQuery(("SELECT * FROM " + diskTableName));
                        return resul.next();
                } catch (SQLException e) {
                        System.out.println("Table not exist");
                        return false;
                }
        }
        public void scanExistData() {
                try {
                        ResultSet resul = connection.createStatement().executeQuery(("SELECT * FROM " + diskTableName));
                        while(resul.next()){
                                Map<String, String> newMap = new HashMap<>();
                                newMap.put("name", resul.getString("name"));
                                newMap.put("duration", resul.getString("duration"));
                                newMap.put("genre", resul.getString("genre"));
                                if (resul.getString("author") != null)
                                        newMap.put("author", resul.getString("author"));
                                if (resul.getString("dateOfWriting") != null)
                                        newMap.put("dateOfWriting", resul.getString("dateOfWriting"));
                                if (resul.getString("dateOfPublication") != null)
                                        newMap.put("dateOfPublication", resul.getString("dateOfPublication"));
                                if (resul.getString("album") != null)
                                        newMap.put("album", resul.getString("album"));
                                set.addNewTrack(newMap, new Label());
                        }
                } catch (SQLException e) {
                        System.out.println("Error while scanning data from table");
                }
        }
        public boolean addAlbumOnDisk(String albumName) {
                Album newAlbum = new Album(albumName, connection);
                if(newAlbum.getNumbOfTracks() != 0) {
                        set.addNewAlbum(newAlbum);
                        return true;
                } else return false;
        }
        public void saveChanges(){
                try {
                        connection.createStatement().executeUpdate("DELETE FROM "+ diskTableName);
                        for(MusicTrack track : set.getTrackList()) {
                                if (track.isDeleteStatus())
                                        continue;
                                String insertNewTrack = "INSERT INTO " + diskTableName + " VALUES(";
                                insertNewTrack = insertNewTrack.concat("'" + track.getName() + "'");
                                insertNewTrack = insertNewTrack.concat(",'" + track.getDuration() + "'");
                                insertNewTrack = insertNewTrack.concat(",'" + track.getGenre() + "'");
                                if (!Objects.equals(track.getAuthor(), "")) {
                                        insertNewTrack = insertNewTrack.concat(",'" + track.getAuthor() + "'");
                                } else insertNewTrack = insertNewTrack.concat(",null");
                                if (!Objects.equals(track.getDateOfWriting(), "")) {
                                        insertNewTrack = insertNewTrack.concat(",'" + track.getDateOfWriting() + "'");
                                } else insertNewTrack = insertNewTrack.concat(",null");
                                if (!Objects.equals(track.getDateOfPublication(), "")) {
                                        insertNewTrack = insertNewTrack.concat(",'" + track.getDateOfPublication() + "'");
                                } else insertNewTrack = insertNewTrack.concat(",null");
                                if (!Objects.equals(track.getAlbumName(), "")) {
                                        insertNewTrack = insertNewTrack.concat(",'" + track.getAlbumName() + "'");
                                } else insertNewTrack = insertNewTrack.concat(",null");
                                insertNewTrack = insertNewTrack.concat(")");
                                connection.createStatement().executeUpdate(insertNewTrack);
                        }
                } catch (SQLException e) {
                        System.out.println("Error while saving new data");
                }
        }
        public void sortOnDiskBy(String condition){
                set.sortListBy(condition);
        }
        public String calculateDurationOfAllTracks(){
                return set.getDurationForAllTracks();
        }
        public SetOfTracks getSet() {
                return set;
        }
}
