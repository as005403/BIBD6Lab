package org.foxrider;

import tools.ConnectionPool;
import dao.StadiumDAO;
import entitys.Stadium;
import entitys.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StadiumsController {
    public TextField idTF;
    public TextField cityTF;
    public TextField countryTF;
    public TextField stNameTF;
    public TextField capacityTF;
    List<Stadium>stadiums;
    static int currentSiteId = -1;


    @FXML
    public void saveOrUpdate(ActionEvent actionEvent) {
        List<Team> teams = null;
        try (Connection conn = ConnectionPool.getConnection();) {
            StadiumDAO td = new StadiumDAO(conn);
            stadiums = td.findAll();
            Stadium tmp;
            try {
                tmp = new Stadium(Integer.parseInt(idTF.getText()), cityTF.getText(), countryTF.getText(), stNameTF.getText(), Integer.parseInt(capacityTF.getText()));
            }catch(NumberFormatException nfe) {
                alertWindow("id and capacity field should be integers");
                return;
            }

            if (currentSiteId == -1) {
                td.create(tmp);
                return;
            }
            td.update(tmp);


        } catch (SQLException e) {
            alertWindow("Foreign key restriction from table Game");
            if (currentSiteId != -1) {
                showFromDB();
            }
        }
    }


    @FXML
    public void prevPage(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection();) {
            StadiumDAO td = new StadiumDAO(conn);
            stadiums = td.findAll();
            if (stadiums != null && currentSiteId - 1 >= 0) {
                currentSiteId--;
                showFromDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showFromDB() {
        idTF.setText(Integer.toString(stadiums.get(currentSiteId).getId()));
        cityTF.setText(stadiums.get(currentSiteId).getCity());
        countryTF.setText(stadiums.get(currentSiteId).getCountry());
        stNameTF.setText(stadiums.get(currentSiteId).getStadiumName());
        capacityTF.setText(Integer.toString(stadiums.get(currentSiteId).getCapacity()));
    }

    @FXML
    public void backToSportgames(ActionEvent actionEvent) throws IOException {
        currentSiteId = -1;
        App.setRoot("SportGames");
    }

    @FXML
    public void nextPage(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection();) {
            StadiumDAO td = new StadiumDAO(conn);
            stadiums = td.findAll();
            if (stadiums != null && currentSiteId + 1 < stadiums.size()) {
                ++currentSiteId;
                showFromDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void newPage(ActionEvent actionEvent) {
        currentSiteId = -1;
        idTF.setText("");
        cityTF.setText("");
        countryTF.setText("");
        stNameTF.setText("");
        capacityTF.setText("");
    }

    @FXML
    public void deletePage(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection();) {
            StadiumDAO td = new StadiumDAO(conn);
            stadiums = td.findAll();
            Stadium tmp;
            try {
                tmp = new Stadium(Integer.parseInt(idTF.getText()), cityTF.getText(), countryTF.getText(), stNameTF.getText(), Integer.parseInt(capacityTF.getText()));
            }catch(NumberFormatException nfe) {
                alertWindow("id and capacity field should be integers");
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
}
