package org.foxrider;

import tools.ConnectionPool;
import dao.TeamDAO;
import entitys.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TeamsController {
    static int currentSiteId = -1;
    public TextField teamNamefieldInTeams;
    public TextField countryNameFieldInTeams;
    public TextField captainFieldInTeams;
    public TextField trainerFieldInTeams;

    @FXML
    public void nextPost(ActionEvent actionEvent) {

        try (Connection conn = ConnectionPool.getConnection();) {
            TeamDAO td = new TeamDAO(conn);
            List<Team> teams = td.findAll();
            if (teams != null && currentSiteId + 1 < teams.size()) {
                ++currentSiteId;
                teamNamefieldInTeams.setText(teams.get(currentSiteId).getTeamName());
                countryNameFieldInTeams.setText(teams.get(currentSiteId).getCountry());
                captainFieldInTeams.setText(teams.get(currentSiteId).getCaptain());
                trainerFieldInTeams.setText(teams.get(currentSiteId).getTrainer());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void saveTeam(ActionEvent actionEvent) {
        List<Team> teams = null;
        try (Connection conn = ConnectionPool.getConnection();) {
            TeamDAO td = new TeamDAO(conn);
            teams = td.findAll();
            Team tmp = new Team(teamNamefieldInTeams.getText(), countryNameFieldInTeams.getText(), captainFieldInTeams.getText(), trainerFieldInTeams.getText());
            if (currentSiteId == -1) {
                td.create(tmp);
                return;
            }
            td.update(tmp);


        } catch (SQLException e) {
            alertWindow("Foreign key restriction from table Game");
            if (currentSiteId != -1) {
                teamNamefieldInTeams.setText(teams.get(currentSiteId).getTeamName());
                countryNameFieldInTeams.setText(teams.get(currentSiteId).getCountry());
                captainFieldInTeams.setText(teams.get(currentSiteId).getCaptain());
                trainerFieldInTeams.setText(teams.get(currentSiteId).getTrainer());
            }
        }
    }

    @FXML
    public void previousPost(ActionEvent actionEvent) {

        try (Connection conn = ConnectionPool.getConnection();) {
            TeamDAO td = new TeamDAO(conn);
            List<Team> teams = td.findAll();
            if (teams != null && currentSiteId - 1 >= 0) {
                currentSiteId--;
                teamNamefieldInTeams.setText(teams.get(currentSiteId).getTeamName());
                countryNameFieldInTeams.setText(teams.get(currentSiteId).getCountry());
                captainFieldInTeams.setText(teams.get(currentSiteId).getCaptain());
                trainerFieldInTeams.setText(teams.get(currentSiteId).getTrainer());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void newPost(ActionEvent actionEvent) {
        currentSiteId = -1;
        teamNamefieldInTeams.setText("");
        countryNameFieldInTeams.setText("");
        captainFieldInTeams.setText("");
        trainerFieldInTeams.setText("");
    }

    public void deletePost(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection();) {
            TeamDAO td = new TeamDAO(conn);
            List<Team> teams = td.findAll();
            Team tmp = new Team(teamNamefieldInTeams.getText(), countryNameFieldInTeams.getText(), captainFieldInTeams.getText(), trainerFieldInTeams.getText());
            boolean isDeleted = td.delete(tmp);
            if(!isDeleted)
                alertWindow("No such element");

            newPost(new ActionEvent());

        } catch (SQLException e) {
            alertWindow("Foreign key restriction from table Game");
        }
    }

    public void backToSpotgames(ActionEvent actionEvent) throws IOException {
        currentSiteId = -1;
        App.setRoot("SportGames");
    }

    private void alertWindow(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(s);

        alert.showAndWait();
    }
}
