package org.foxrider;

import javafx.beans.property.*;

public class GameJX {
    private SimpleIntegerProperty stadiumId;
    private SimpleStringProperty teamName;
    private SimpleStringProperty date;
    private SimpleStringProperty time;

    public GameJX(int stadiumId, String teamName, String date, String time) {
        this.stadiumId = new SimpleIntegerProperty(stadiumId);
        this.teamName = new SimpleStringProperty(teamName);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }


    public int getStadiumId() {
        return stadiumId.get();
    }

    public SimpleIntegerProperty stadiumIdProperty() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId.set(stadiumId);
    }

    public String getTeamName() {
        return teamName.get();
    }

    public SimpleStringProperty teamNameProperty() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}
