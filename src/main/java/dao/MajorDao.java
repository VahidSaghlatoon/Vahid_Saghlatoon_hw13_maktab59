package dao;

import config.DataSourceConfig;
import dao.core.BaseDao;
import entity.Major;
import entity.Student;
import exception.DataNotFoundException;
import exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MajorDao implements BaseDao<Major, Integer> {
    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
    private Connection connection;
    @Override
    public void save(Major major) {
            try {
                connection = dataSourceConfig.createDataSource().getConnection();
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO majors (name) VALUES(?)")) {
                    ps.setString(1, major.getName());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModificationDataException("Can not insert data to university database");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void update(Integer id, Major newEntity) {
            try (Connection connection = dataSourceConfig.createDataSource().getConnection();
                 PreparedStatement ps = connection.prepareStatement("UPDATE majors " +
                         "SET name=? " +
                         "WHERE id=" + id);){
                ps.setString(1,newEntity.getName());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModificationDataException("Can not update data to university database");
            }
    }

    @Override
    public void delete(Integer id) {
            try (Connection connection = dataSourceConfig.createDataSource().getConnection();
                 PreparedStatement ps = connection.prepareStatement("DELETE FROM majors " +
                         "WHERE id=?")){
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModificationDataException("Can not update data to university database");
            }
    }

    @Override
    public Major loadById(Integer id) {
            try (Connection connection = dataSourceConfig.createDataSource().getConnection();
                 PreparedStatement ps = connection.prepareStatement("SELECT * " +
                         "FROM majors WHERE id = ?")){
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery();) {
                    Major major = null;
                    while (resultSet.next()) {
                        int majorId = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        major = new Major(majorId,name);
                    }
                    return major;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataNotFoundException("Can not find data from university database");
            }
    }

    @Override
    public List<Major> loadAll() {
            try (Connection connection = dataSourceConfig.createDataSource().getConnection();
                 PreparedStatement ps = connection.prepareStatement("SELECT *" +
                         " FROM majors");
                 ResultSet resultSet = ps.executeQuery();){

                List<Major> majors = new ArrayList<>();
                while (resultSet.next()) {
                    int majorId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Major major = new Major(majorId,name);
                    majors.add(major);
                }
                return majors;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataNotFoundException("Can not find data from db");
            }
    }
}
