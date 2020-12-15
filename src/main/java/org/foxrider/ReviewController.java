package org.foxrider;

import dao.GameDAO;
import entitys.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import tools.ConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReviewController {
    public ListView<String> lv2;
    public ListView<String> lv1;


    public void build(ActionEvent actionEvent) {
        ObservableList<String> names = FXCollections.observableArrayList();
        ObservableList<String> values = FXCollections.observableArrayList();
        List<Game>games = null;
        try(Connection conn = ConnectionPool.getConnection()) {
            GameDAO dao = new GameDAO(conn);
             games = dao.findAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Game g:games){
            names.addAll("Stadium ID", "Team Name", "Date", "Time","-----------------");
            values.addAll(Integer.toString(g.getStadiumId()),g.getTeamName(),g.getDate(),g.getTime(),"-----------------");

        }
        lv2.setItems(values);
        lv1.setItems(names);

    }

    public void back(ActionEvent actionEvent) throws IOException {
        App.setRoot("games");
    }
}
