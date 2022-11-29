package com.termprojectpp.app;

import com.termprojectpp.info.Disk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MenuEditorController implements Initializable {
    private final Disk disk = Disk.getInstance();
    @FXML
    private Label durationShow;
    @FXML
    private Label diskNameLabel;
    @FXML
    private TextField trackName;
    @FXML
    private TextField trackDuration;
    @FXML
    private TextField trackGenre;
    @FXML
    private TextField trackAuthor;
    @FXML
    private TextField trackDateOfPublication;
    @FXML
    private TextField trackDateOfWriting;
    @FXML
    private TextField albumName;
    @FXML
    private TextField deleteTrackName;
    @FXML
    private TextField deleteAlbumName;

    @FXML
    private Label infoAddTrackLabel;
    @FXML
    private Label infoAddAlbumLabel;
    @FXML
    private Label infoDeleteTrackLabel;
    @FXML
    private Label infoDeleteAlbumLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        diskNameLabel.setText("Disk name: " + disk.getDiskTableName());
        updateDuration();
    }

    public void saveChanges(){
        if(disk.saveStatus) {
            disk.saveChanges();
            disk.saveStatus = false;
        }
    }

    public void updateDuration(){
        durationShow.setText(disk.calculateDurationOfAllTracks());
    }

    public void addNewTrack(){
        Map<String, String> newMap = new HashMap<>();
        infoAddTrackLabel.setTextFill(Color.rgb(181,23,23));

        if(trackName.getText().isEmpty()) {
            infoAddTrackLabel.setText("Name field is empty!");
            return;
        } else if(trackDuration.getText().isEmpty()) {
            infoAddTrackLabel.setText("Duration field is empty!");
            return;
        } else if(trackGenre.getText().isEmpty()) {
            infoAddTrackLabel.setText("Genre field is empty!");
            return;
        }

        newMap.put("name", trackName.getText());
        newMap.put("duration", trackDuration.getText());
        newMap.put("genre", trackGenre.getText());
        if (!trackAuthor.getText().equals(""))
            newMap.put("author", trackAuthor.getText());
        if (!trackDateOfWriting.getText().equals(""))
            newMap.put("dateOfWriting",trackDateOfWriting.getText());
        if (!trackDateOfPublication.getText().equals(""))
            newMap.put("dateOfPublication", trackDateOfPublication.getText());

        if(disk.getSet().addNewTrack(newMap, infoAddTrackLabel)) {
            disk.saveStatus = true;
            updateDuration();
        }
    }

    public void addNewAlbum(){
        infoAddAlbumLabel.setTextFill(Color.rgb(181,23,23));

        if(albumName.getText().isEmpty()) {
            infoAddAlbumLabel.setText("Field is empty!");
            return;
        }

        if(disk.addAlbumOnDisk(albumName.getText())) {
            disk.saveStatus = true;
            updateDuration();
            infoAddAlbumLabel.setTextFill(Color.rgb(31,110,47));
            infoAddAlbumLabel.setText("Album was recorded successfully!");
        } else {
            infoAddAlbumLabel.setText("This album does not exist!");
        }

    }

    public void deleteTrack(){
        infoDeleteTrackLabel.setTextFill(Color.rgb(181,23,23));

        if(deleteTrackName.getText().isEmpty()) {
            infoDeleteTrackLabel.setText("Field is empty!");
            return;
        }
        if(disk.getSet().deleteTrackByName(deleteTrackName.getText())) {
            disk.saveStatus = true;
            updateDuration();
            infoDeleteTrackLabel.setTextFill(Color.rgb(31,110,47));
            infoDeleteTrackLabel.setText("Track was deleted successfully!");
        } else {
            infoDeleteTrackLabel.setText("Track does not exist on disc");
        }
    }

    public void deleteAlbum(){
        infoDeleteAlbumLabel.setTextFill(Color.rgb(181,23,23));

        if(deleteAlbumName.getText().isEmpty()) {
            infoDeleteAlbumLabel.setText("Field is empty!");
            return;
        }

        if(disk.getSet().deleteTracksByAlbumName(deleteAlbumName.getText()) != 0) {
            disk.saveStatus = true;
            updateDuration();
            infoDeleteAlbumLabel.setTextFill(Color.rgb(31,110,47));
            infoDeleteAlbumLabel.setText("Album was deleted successfully!");
        } else {
            infoDeleteAlbumLabel.setText("Album does not exist on disk!");
        }
    }

    public void gotToMenuScene1(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menuListScene.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error while trying to go to scene 1");
            throw new RuntimeException();
        }
    }

}
