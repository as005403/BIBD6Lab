package dao;

import entitys.Stadium;
import entitys.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StadiumDAO  extends AbstractDAO<Stadium,Integer>{
    public StadiumDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Stadium> findAll() {
        final String SELECTALL = "SELECT * FROM stadium";

        List<Stadium> stadiums= new LinkedList<>();
        try (PreparedStatement st = connection.prepareCall(SELECTALL);) {
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                Stadium tmp = new Stadium();
                tmp.setId(rs.getInt(1));
                tmp.setCity(rs.getString(2));
                tmp.setCountry(rs.getString(3));
                tmp.setStadiumName(rs.getString(4));
                tmp.setCapacity(rs.getInt(5));

                stadiums.add(tmp);
            }

            return stadiums;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    @Override
    public Stadium entityById(Integer key) {
        final String GETBYID = "SELECT * FROM stadium WHERE stid=?";
        Stadium tmp = new Stadium();
        try (PreparedStatement st = connection.prepareCall(GETBYID);) {
            st.setInt(1, key);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                tmp.setId(rs.getInt(1));
                tmp.setCity(rs.getString(2));
                tmp.setCountry(rs.getString(3));
                tmp.setStadiumName(rs.getString(4));
                tmp.setCapacity(rs.getInt(5));
            }

            if(tmp.getId()==0)
                return null;

            return tmp;
        } catch (SQLException e) {
            //some problem with connection or restrictions
            return null;

        }
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
        final String DELETEKEY = "DELETE FROM stadium WHERE stid=?";
        try (PreparedStatement st = connection.prepareCall(DELETEKEY);) {
            st.setInt(1, key);
            int num = st.executeUpdate();
            return num>0;
        } catch (SQLException e) {
            throw new SQLException();
        }

    }

    @Override
    public boolean delete(Stadium entity) throws SQLException {
        return delete(entity.getId());
    }

    @Override
    public boolean create(Stadium entity) throws SQLException {
        final String CREATE = "INSERT INTO stadium VALUES(?,?,?,?,?)";
        try (PreparedStatement st = connection.prepareCall(CREATE);) {
            st.setInt(1, entity.getId());
            st.setString(2, entity.getCity());
            st.setString(3, entity.getCountry());
            st.setString(4, entity.getStadiumName());
            st.setInt(5, entity.getCapacity());
            st.execute();
            return true;
        } catch (SQLException e) {
            //some problem with connection or restriction primary key
            throw new SQLException("primary key violation stadium");

        }
    }

    @Override
    public Stadium update(Stadium entity) throws SQLException {
        final String UPDATE = "UPDATE stadium SET city=?, country=?,stname=?,capacity=? where stid=?";

        Stadium ret = entityById(entity.getId());

        try (PreparedStatement st = connection.prepareCall(UPDATE);) {
            st.setString(1, entity.getCity());
            st.setString(2, entity.getCountry());
            st.setString(3, entity.getStadiumName());
            st.setInt(4, entity.getCapacity());
            st.setInt(5,entity.getId());
            st.execute();


        } catch (SQLException e) {
            //some problem with connection or restriction primary key
            throw new SQLException("primary key violation stadium");

        }
        return ret;
    }
}
