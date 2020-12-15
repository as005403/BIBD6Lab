package org.foxrider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class SportGames {
    @FXML
    public void switchTeamsScreen(ActionEvent actionEvent) throws IOException {
        App.setRoot("teams");
    }

    @FXML
    public void switchStadiumsScreen(ActionEvent actionEvent) throws IOException {
        App.setRoot("stadiums");
    }

    @FXML
    public void switchtoGameScreen(ActionEvent actionEvent) throws IOException {
        App.setRoot("games");
    }
}
