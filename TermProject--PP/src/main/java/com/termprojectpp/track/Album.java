package com.termprojectpp.track;

import com.termprojectpp.info.SetOfTracks;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class Album {
    private final SetOfTracks set;
    private final String name;
    private final Connection connection;
    private int numbOfCompositions;

    public Album(String name, Connection connection){
        this.name = name;
        this.connection = connection;
        set = new SetOfTracks();
        numbOfCompositions = 0;
        if(checkIfNotEmptyAndIfExist())
            getDataFromFile();
    }

    public SetOfTracks getSet() {
        return set;
    }
    public void getDataFromFile(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resul = statement.executeQuery(("SELECT * FROM " + name));
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
                if(set.addNewTrack(newMap, new Label()))
                    numbOfCompositions++;
            }
        } catch (SQLException e) {
            System.out.println("Album table not found");
        }
    }

    public boolean checkIfNotEmptyAndIfExist() {
        try{
            ResultSet resul = connection.createStatement().executeQuery(("SELECT * FROM " + name));
            return resul.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public int getNumbOfTracks() {
        return numbOfCompositions;
    }
}
