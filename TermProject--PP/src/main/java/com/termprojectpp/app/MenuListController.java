package com.termprojectpp.app;

import com.termprojectpp.info.Disk;
import com.termprojectpp.track.MusicTrack;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuListController implements Initializable {
    private Disk disk = Disk.getInstance();
    private final String[] sortByThis = {"name", "duration", "genre"};
    @FXML
    private TableView<MusicTrack> mainTable;
    @FXML
    private TableColumn<MusicTrack, String> nameColumn;
    @FXML
    private TableColumn<MusicTrack, String> durationColumn;
    @FXML
    private TableColumn<MusicTrack, String> genreColumn;
    @FXML
    private TableColumn<MusicTrack, String> authorColumn;
    @FXML
    private TableColumn<MusicTrack, String> dateOfWritingColumn;
    @FXML
    private TableColumn<MusicTrack, String> dateOfPublicationColumn;
    @FXML
    private TableColumn<MusicTrack, String> albumColumn;
    @FXML
    private ChoiceBox<String> sortByChoiceBox;
    @FXML
    private Label durationShow;
    @FXML
    private Label diskNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sortByChoiceBox.getItems().addAll(sortByThis);
        sortByChoiceBox.setOnAction(this::sortTracksOnDisk);
        diskNameLabel.setText(diskNameLabel.getText() + " " + disk.getDiskTableName());
        updateTable();
    }

    public void sortTracksOnDisk(ActionEvent event){
        disk.sortOnDiskBy(sortByChoiceBox.getValue());
        disk.saveStatus = true;
        updateTable();
    }

    public void updateTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateOfWritingColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfWriting"));
        dateOfPublicationColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfPublication"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));

        List<MusicTrack> newData = new ArrayList<>();
        disk.getSet().getTrackList().stream()
                .filter(track -> !track.isDeleteStatus())
                .forEach(newData::add);
        mainTable.setItems(FXCollections.observableList(newData));

        durationShow.setText(disk.calculateDurationOfAllTracks());
    }

    public void saveChanges(){
        if(disk.saveStatus) {
            disk.saveChanges();
            disk.saveStatus = false;
        }
    }

    public void gotToMenuScene2(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menuEditorScene.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error while trying to go to scene 2");
            throw new RuntimeException();
        }
    }

}
