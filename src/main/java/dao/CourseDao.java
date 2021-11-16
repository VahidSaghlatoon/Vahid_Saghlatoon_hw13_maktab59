package dao;


import config.DataSourceConfig;
import dao.core.BaseDao;
import entity.Course;
import entity.Student;
import exception.DataNotFoundException;
import exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements BaseDao<Course, Integer> {

    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
    private Connection connection;

    @Override
    public void save(Course course) {
        try {
            connection = dataSourceConfig.createDataSource().getConnection();
            try (
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO courses " +
                            "(name, unit) " +
                            "VALUES(?, ?)")) {
                ps.setString(1, course.getName());
                ps.setInt(2, course.getUnit());
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
    public void update(Integer id, Course newCourse) {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE courses " +
                     "SET name=?, unit=? " +
                     "WHERE id=" + id);){
            ps.setString(1, newCourse.getName());
            ps.setInt(2, newCourse.getUnit());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to university database");
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM univercity.courses " +
                     "WHERE id=?")){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to university database");
        }
    }

    @Override
    public Course loadById(Integer id) {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * " +
                     "FROM univercity.courses WHERE id = ?")){
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery();) {
                Course course = null;
                while (resultSet.next()) {
                    int courseId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int unit = resultSet.getInt("unit");
                    course = new Course(courseId,name,unit);
                }
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from university database");
        }
    }

    @Override
    public List<Course> loadAll() {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT *" +
                     " FROM univercity.courses");
             ResultSet resultSet = ps.executeQuery();){

            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int unit = resultSet.getInt("unit");
                Course course = new Course(courseId,name,unit);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from db");
        }
    }
}
