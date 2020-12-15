package org.foxrider;

import tools.ConnectionPool;
import dao.GameDAO;
import entitys.Game;
import entitys.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GamesController {
    public TextField idTF;
    public TextField teamTF;
    public TextField dateTF;
    public TextField timeTF;

    List<Game> games;
    static int currentSiteId = -1;

    @FXML
    public void saveOrUpdate(ActionEvent actionEvent) {
        List<Team> teams = null;
        try (Connection conn = ConnectionPool.getConnection();) {
            GameDAO td = new GameDAO(conn);
            games = td.findAll();
            Game tmp;
            try {
                tmp = new Game(Integer.parseInt(idTF.getText()), teamTF.getText(), dateTF.getText(), timeTF.getText());
            }catch(NumberFormatException nfe) {
                alertWindow("id and capacity field should be integers");
                return;
            }catch (IllegalArgumentException e){
                alertWindow("time and date should be written in format hh:mm and dd-mm-yyyy");
                return;
            }

            if (currentSiteId == -1) {
                td.create(tmp);
                return;
            }
            td.update(tmp);


        } catch (SQLException e) {
            alertWindow("No such Stadium Id or/and team name");
            if (currentSiteId != -1) {
                showFromDB();
            }
        }
    }

    @FXML
    public void prevPage(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection();) {
            GameDAO td = new GameDAO(conn);
            games = td.findAll();
            if (games != null && currentSiteId - 1 >= 0) {
                currentSiteId--;
                showFromDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backToSportgames(ActionEvent actionEvent) throws IOException {
        currentSiteId = -1;
        App.setRoot("SportGames");
    }

    @FXML
    public void nextPage(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection();) {
            GameDAO td = new GameDAO(conn);
            games = td.findAll();
            if (games != null && currentSiteId + 1 < games.size()) {
                ++currentSiteId;
                showFromDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showFromDB() {
        idTF.setText(Integer.toString(games.get(currentSiteId).getStadiumId()));
        teamTF.setText(games.get(currentSiteId).getTeamName());
        dateTF.setText(games.get(currentSiteId).getDate());
        timeTF.setText(games.get(currentSiteId).getTime());
    }

    @FXML
    public void newPage(ActionEvent actionEvent) {
        currentSiteId = -1;
        idTF.setText("");
        teamTF.setText("");
        dateTF.setText("");
        timeTF.setText("");
    }

    @FXML
    public void deletePage(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection();) {
            GameDAO td = new GameDAO(conn);
            games = td.findAll();
            Game tmp;
            try {
                tmp = new Game(Integer.parseInt(idTF.getText()), teamTF.getText(), dateTF.getText(), timeTF.getText());
            }catch(NumberFormatException nfe) {
                alertWindow("id and capacity field should be integers");
                return;
            }catch (IllegalArgumentException e){
                alertWindow("time and date should be written in format hh:mm and dd-mm-yyyy");
                return;
            }
            boolean isDeleted = td.delete(tmp);
            if(!isDeleted)
                alertWindow("No such element");

            newPage(new ActionEvent());

        } catch (SQLException e) {
            alertWindow("Foreign key restriction from table Game");
        }
    }

    private void alertWindow(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(s);

        alert.showAndWait();
    }

    public void toTable(ActionEvent actionEvent) throws IOException {
        App.setRoot("gamesTable");
    }

    public void toReview(ActionEvent actionEvent) throws IOException {
        App.setRoot("review");
    }
}
