package dao;

import entitys.Team;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TeamDAO extends AbstractDAO<Team, String> {
    public TeamDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Team> findAll() {
        final String SELECTALL = "SELECT * FROM team";

        List<Team> teamList= new LinkedList<>();
        try (PreparedStatement st = connection.prepareCall(SELECTALL);) {
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                Team tmp = new Team();
                tmp.setTeamName(rs.getString(1));
                tmp.setCountry(rs.getString(2));
                tmp.setCaptain(rs.getString(3));
                tmp.setTrainer(rs.getString(4));

                teamList.add(tmp);
            }

            return teamList;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    @Override
    public Team entityById(String key) {
        final String GETBYID = "SELECT * FROM team WHERE name=?";
        Team tmp = new Team();
        try (PreparedStatement st = connection.prepareCall(GETBYID);) {
            st.setString(1, key);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                tmp.setTeamName(rs.getString(1));
                tmp.setCountry(rs.getString(2));
                tmp.setCaptain(rs.getString(3));
                tmp.setTrainer(rs.getString(4));
            }

            if(tmp.getCaptain()==null)
                return null;

            return tmp;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    @Override
    public boolean delete(String key) throws SQLException {
        final String DELETEKEY = "DELETE FROM team WHERE name=?";
        try (PreparedStatement st = connection.prepareCall(DELETEKEY);) {
            st.setString(1, key);
            int num = st.executeUpdate();
            return num>0;
        } catch (SQLException e) {
            throw new SQLException("foreign key restriction");

        }
    }

    @Override
    public boolean delete(Team entity) throws SQLException{
        return delete(entity.getTeamName());
    }

    @Override
    public boolean create(Team entity) {
        final String CREATE = "INSERT INTO team VALUES(?,?,?,?)";
        try (PreparedStatement st = connection.prepareCall(CREATE);) {
            st.setString(1, entity.getTeamName());
            st.setString(2, entity.getCountry());
            st.setString(3, entity.getCaptain());
            st.setString(4, entity.getTrainer());
            st.execute();
            return true;
        } catch (SQLException e) {
            //some problem with connection or restriction primary key
            System.err.println("Primary key violation");
            return false;

        }
    }

    @Override
    public Team update(Team entity) throws SQLException {
        final String UPDATE = "UPDATE team SET country=?,captain=?,trainer=? where name=?";

        Team ret = entityById(entity.getTeamName());

        try (PreparedStatement st = connection.prepareCall(UPDATE);) {
            st.setString(1, entity.getCountry());
            st.setString(2, entity.getCaptain());
            st.setString(3, entity.getTrainer());
            st.setString(4, entity.getTeamName());
            int num = st.executeUpdate();
            if(num==0)
                throw new SQLException("foreign key violation");
        } catch (SQLException e) {
            throw new SQLException("foreign key violation");

        }
        return ret;
    }
}
