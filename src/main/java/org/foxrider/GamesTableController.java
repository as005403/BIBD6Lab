package org.foxrider;

import dao.GameDAO;
import entitys.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tools.ConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamesTableController {
    public RadioButton stidRB;
    public RadioButton tnRB;
    public RadioButton dateRB;
    public RadioButton timeRB;
    public TextField regexTF;


    public static String columnToSort = "stid";
    public static String order = "ASC";
    public TableView<GameJX> table;
    private ObservableList<GameJX> gamesData = FXCollections.observableArrayList();

    public void stRBcheck(ActionEvent actionEvent) {
        columnToSort = "stid";
    }

    public void tnRBcheck(ActionEvent actionEvent) {
        columnToSort = "teamname";
    }

    public void dateRBcheck(ActionEvent actionEvent) {
        columnToSort = "date";
    }

    public void timeRBcheck(ActionEvent actionEvent) {
        columnToSort = "time";
    }

    public void ascCheck(ActionEvent actionEvent) {
        order = "ASC";
    }

    public void descCheck(ActionEvent actionEvent) {
        order = "DESC";
    }

    public void sortWithSettings(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection()) {
            GameDAO dao = new GameDAO(conn);
            List<Game> games = toListGame(gamesData);
            dao.sort(games, columnToSort, order);
            showOnTable(games);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void filter(ActionEvent actionEvent) {
        String regex = regexTF.getText();
        List<Game> games = toListGame(gamesData);
        List<Game>tmp = new LinkedList<>();

        Pattern p = Pattern.compile(regex);
        for(Game g:games){
            if(p.matcher(Integer.toString(g.getStadiumId())).matches()){
                tmp.add(g);
            }else if(p.matcher(g.getTeamName()).matches())
                tmp.add(g);
            else if(p.matcher(g.getDate()).matches())
                tmp.add(g);
            else if(p.matcher(g.getTime()).matches())
                tmp.add(g);
        }


        showOnTable(tmp);
    }

    public void backToGames(ActionEvent actionEvent) throws IOException {
        App.setRoot("games");
    }

    public void showAllOnTable(ActionEvent actionEvent) {
        try (Connection conn = ConnectionPool.getConnection()) {
            GameDAO dao = new GameDAO(conn);
            List<Game> games = dao.findAll();
            showOnTable(games);


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    void showOnTable(List<Game> games) {
        // Clearing
        gamesData = table.getItems();
        gamesData.clear();

        // Add All from List
        gamesData.addAll(toListGameJX(games));

        // Clearing columns
        table.setItems(gamesData);
        table.getColumns().clear();

        // stadium id column
        TableColumn<GameJX, Integer> StIdColumn = new TableColumn<>("Stadium ID");
        StIdColumn.setCellValueFactory(new PropertyValueFactory<>("stadiumId"));
        table.getColumns().add(StIdColumn);

        //Team name column
        TableColumn<GameJX, String> tnColumn = new TableColumn<>("Team Name");
        tnColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        table.getColumns().add(tnColumn);

        //date column
        TableColumn<GameJX, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getColumns().add(dateColumn);

        //time column
        TableColumn<GameJX, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        table.getColumns().add(timeColumn);

        //System.out.println(toListGame(gamesData));

    }

    List<Game> toListGame(List<GameJX> lgx) {
        List<Game> g = new LinkedList<>();
        for (GameJX gf : lgx) {
            g.add(Game.toGame(gf));
        }
        return g;
    }

    List<GameJX> toListGameJX(List<Game> games) {
        List<GameJX> g = new LinkedList<>();
        for (Game gf : games) {
            g.add(Game.toGameJX(gf));
        }
        return g;
    }
}
