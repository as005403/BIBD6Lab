package dao;

import entitys.Game;
import entitys.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class GameDAO extends AbstractDAO<Game,Integer>{
    static String SORT = "SELECT * FROM game ORDER BY %s %s";

    public GameDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Game> findAll() {
        final String SELECTALL = "SELECT * FROM game";

        List<Game> teamList= new LinkedList<>();
        try (PreparedStatement st = connection.prepareCall(SELECTALL);) {
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                Game tmp = new Game();
                tmp.setStadiumId(rs.getInt(1));
                tmp.setTeamName(rs.getString(2));
                tmp.setDate(rs.getString(3));
                tmp.setTime(rs.getString(4));

                teamList.add(tmp);
            }

            return teamList;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    @Override
    public Game entityById(Integer key) {
        throw new UnsupportedOperationException("No key in this table");
    }

    public Game entityByStidAndTeam(Integer stid,String teamname){
        final String GETBYID = "SELECT * FROM game WHERE stid=? and teamname=?";
        Game tmp = new Game();
        try (PreparedStatement st = connection.prepareCall(GETBYID);) {
            st.setInt(1, stid);
            st.setString(2,teamname);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                tmp.setStadiumId(rs.getInt(1));
                tmp.setTeamName(rs.getString(2));
                tmp.setDate(rs.getString(3));
                tmp.setTime(rs.getString(4));
            }

            if(tmp.getTeamName()==null)
                return null;

            return tmp;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    @Override
    public boolean delete(Integer key) {
        throw new UnsupportedOperationException("No key in this table");
    }

    @Override
    public boolean delete(Game entity) {
        final String DELETEKEY = "DELETE FROM game WHERE stid=? and teamname=? and date=? and time=?";
        try (PreparedStatement st = connection.prepareCall(DELETEKEY);) {
            st.setInt(1, entity.getStadiumId());
            st.setString(2, entity.getTeamName());
            st.setString(3, entity.getDate());
            st.setString(4, entity.getTime());
            int num = st.executeUpdate();
            return num>0;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return false;

        }
    }

    @Override
    public boolean create(Game entity) throws SQLException {
        final String CREATE = "INSERT INTO game VALUES(?,?,?,?)";
        try (PreparedStatement st = connection.prepareCall(CREATE);) {
            st.setInt(1, entity.getStadiumId());
            st.setString(2, entity.getTeamName());
            st.setString(3, entity.getDate());
            st.setString(4, entity.getTime());
            st.execute();
            return true;
        } catch (SQLException e) {
            //some problem with connection or restriction primary key
            throw new SQLException("нарушение foreign ключей");

        }
    }

    @Override
    public Game update(Game entity) throws SQLException {
        final String UPDATE = "UPDATE game SET date=?,time=?where stid=? and teamname=?";

        Game ret = entityByStidAndTeam(entity.getStadiumId(),entity.getTeamName());
        if(ret==null)
            return null;

        try (PreparedStatement st = connection.prepareCall(UPDATE);) {
            st.setString(1, entity.getDate());
            st.setString(2, entity.getTime());
            st.setInt(3, entity.getStadiumId());
            st.setString(4, entity.getTeamName());
            st.execute();

        } catch (SQLException e) {
            //some problem with connection or restriction primary key
            throw new SQLException("can't update due to some problems");

        }
        return ret;
    }


    public void sort(List<Game>games, String columnToSort, String order) {
        Comparator<Game> stidComp = (o1,o2) -> Integer.compare(o1.getStadiumId(),o2.getStadiumId());
        Comparator<Game> tnComp = (o1,o2)->o1.getTeamName().compareToIgnoreCase(o2.getTeamName());
        Comparator<Game> dateComp = (o1,o2)->o1.getDate().compareToIgnoreCase(o2.getDate());
        Comparator<Game> timeComp = (o1,o2)->o1.getTime().compareToIgnoreCase(o2.getTime());

        Comparator<Game> stidCompDESC = (o2,o1) -> Integer.compare(o1.getStadiumId(),o2.getStadiumId());
        Comparator<Game> tnCompDESC = (o2,o1)->o1.getTeamName().compareToIgnoreCase(o2.getTeamName());
        Comparator<Game> dateCompDESC = (o2,o1)->o1.getDate().compareToIgnoreCase(o2.getDate());
        Comparator<Game> timeCompDESC = (o2,o1)->o1.getTime().compareToIgnoreCase(o2.getTime());


        switch (columnToSort){
            case "stid":
                if(order.equalsIgnoreCase("ASC"))
                    Collections.sort(games,stidComp);
                else Collections.sort(games,stidCompDESC);
                break;
            case "teamname":
                if(order.equalsIgnoreCase("ASC"))
                    Collections.sort(games,tnComp);
                else Collections.sort(games,tnCompDESC);
                break;
            case "date":
                if(order.equalsIgnoreCase("ASC"))
                    Collections.sort(games,dateComp);
                else Collections.sort(games,dateCompDESC);
                break;
            case "time":
                if(order.equalsIgnoreCase("ASC"))
                    Collections.sort(games,timeComp);
                else Collections.sort(games,timeCompDESC);
                break;
            default:
        }

    }

}
