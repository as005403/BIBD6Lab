package entitys;

public class Team implements EntityDB {
    private String teamName;
    private String country;
    private String captain;
    private String trainer;


    public Team(String teamName, String country, String captain, String trainer) {
        this.teamName = teamName;
        this.country = country;
        this.captain = captain;
        this.trainer = trainer;
    }
    public Team(){}

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", country='" + country + '\'' +
                ", captain='" + captain + '\'' +
                ", trainer='" + trainer + '\'' +
                '}';
    }
}
