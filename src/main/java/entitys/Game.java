package entitys;

import org.foxrider.GameJX;
import tools.Validator;

public class Game implements EntityDB{
    private int stadiumId;
    private String teamName;
    private String date;
    private String time;

    public Game(int stadiumId, String teamName, String date, String time) {
        this.stadiumId = stadiumId;
        this.teamName = teamName;
        setDate(date);
        setTime(time);
    }

    public static GameJX toGameJX(Game g){
        GameJX gjx = new GameJX(g.getStadiumId(),g.getTeamName(),g.getDate(),g.getTime());
        return gjx;
    }
    public static Game toGame(GameJX gjx){
        Game g = new Game(gjx.getStadiumId(),gjx.getTeamName(),gjx.getDate(),gjx.getTime());
        return g;
    }

    public Game(){}

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        Validator validator = new Validator();
        if (validator.validateDate(date))
            this.date = date;
        else throw new IllegalArgumentException("Validator date exeption");

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        Validator validator = new Validator();
        if (validator.validateTime(time))
            this.time = time;
        else throw new IllegalArgumentException("Validator time exeption");

    }

    @Override
    public String toString() {
        return "Game{" +
                "stadiumId='" + stadiumId + '\'' +
                ", teamName='" + teamName + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
