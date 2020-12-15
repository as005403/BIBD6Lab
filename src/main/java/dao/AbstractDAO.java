package dao;

import entitys.EntityDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<T extends EntityDB,K> {
    protected Connection connection;
    AbstractDAO(Connection connection){
        this.connection = connection;
    }

    public abstract List<T> findAll();
    public abstract T entityById(K key);
    public abstract boolean delete(K key) throws SQLException;
    public abstract boolean delete(T entity) throws SQLException;
    public abstract boolean create(T entity) throws SQLException;
    public abstract T update(T entity) throws SQLException;

    public void close(Statement st){
        if(st!=null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                System.out.println("Cannot close statement"+ st+ "error");
            }
        }
    }
}
