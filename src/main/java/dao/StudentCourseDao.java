package dao;

import config.DataSourceConfig;
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
import java.util.Set;

public class StudentCourseDao {

    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
    private Connection connection;

    public void save(int studentId , int courseId) {
        try {
            connection = dataSourceConfig.createDataSource().getConnection();
            try (
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO student_course " +
                            "(student_id, course_id) " +
                            "VALUES(?, ?)")) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
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

    public void update(int studentId , int courseId , double grade) {
        try {
            connection = dataSourceConfig.createDataSource().getConnection();
            try (
                    PreparedStatement ps = connection.prepareStatement("UPDATE univercity.student_course\n" +
                            "SET grade=?\n" +
                            "WHERE student_id=? AND course_id=?;\n")) {
                ps.setDouble(1,grade);
                ps.setInt(2, studentId);
                ps.setInt(3, courseId);
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

    public List<Course> loadAll(Integer id) {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT  course_id FROM univercity.student_course WHERE student_id = ?" )){
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery();) {
                Course course = null;
                List<Course> courses = new ArrayList<>();
                while (resultSet.next()) {
                    int courseId = resultSet.getInt("course_id");
                    courses.add(new CourseDao().loadById(courseId));
                }
                return  courses;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from university database");
        }
    }


    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }
    public void commit() throws SQLException {
        connection.commit();
    }
}

