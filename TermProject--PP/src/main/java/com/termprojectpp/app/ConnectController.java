package com.termprojectpp.app;

import com.termprojectpp.info.Disk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class ConnectController {
    private final Disk disk = Disk.getInstance();
    @FXML
    private TextField tableName;
    @FXML
    private Label errorLabel;

    public void tryingToConnect(ActionEvent event){
        String url = "jdbc:sqlserver://DESKTOP-FJHHV6V:1433;DatabaseName=TermDB;encrypt=true;" +
                "trustServerCertificate=true;user=app;password=0011564";
        errorLabel.setText("");

        try {
            Connection connection = DriverManager.getConnection(url);
            String name = tableName.getText();

            if(name.isEmpty()) {
                errorLabel.setText("Field is empty");
                return;
            }

            if(!tableExists(connection, name)){
                errorLabel.setText("This table not exist! Try again");
                return;
            }

            disk.setData(name, connection);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menuListScene.fxml")));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(event2 -> {
                event2.consume();
                logout(stage);
            });
        } catch (SQLException e) {
            System.out.println("Error while trying connect to DB");
        } catch (IOException e) {
            System.out.println("Error while trying to create new scene");
        }
    }

    private boolean tableExists(Connection connection, String tableName)  {
        try {
            ResultSet resultSet = connection.getMetaData()
                    .getTables(null, null, tableName, new String[] {"TABLE"});
            if(resultSet.next()){
                return true;
            } else {
                errorLabel.setText("This table not exist! Try again");
                return false;
            }
        } catch (SQLException e) {
            errorLabel.setText("Problem with connection");
        }
        return false;
    }

    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Changes are not saved!");
        alert.setContentText("Do you want to leave without save?");

        if(disk.saveStatus) {
            Optional<ButtonType> logoutScene = alert.showAndWait();
            if(logoutScene.isPresent() && logoutScene.get() == ButtonType.OK) {
                disk.disconnectDisk();
                stage.close();
            }
        } else {
            disk.disconnectDisk();
            stage.close();
        }
    }
}
