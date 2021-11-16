package dao;

import config.DataSourceConfig;
import dao.core.BaseDao;
import entity.Student;
import exception.DataNotFoundException;
import exception.ModificationDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements BaseDao<Student, Integer> {

    private final DataSourceConfig dataSourceConfig = DataSourceConfig.getInstance();
    private Connection connection;


    @Override
    public void save(Student student) {
        try {
            connection = dataSourceConfig.createDataSource().getConnection();
            try (
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO students " +
                            "(name, family_name, major_id) " +
                            "VALUES(?, ?, ?)")) {
                ps.setString(1, student.getName());
                ps.setString(2, student.getFamilyName());
                ps.setInt(3, student.getMajor().getId());
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
    public void update(Integer id, Student newStudent) {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE students " +
                     "SET name=?, family_name=?, major_id=? " +
                     "WHERE id=" + id);){
            ps.setString(1, newStudent.getName());
            ps.setString(2, newStudent.getFamilyName());
            ps.setInt(3, newStudent.getMajor().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to university database");
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM students " +
                     "WHERE id=?")){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModificationDataException("Can not update data to university database");
        }
    }

    @Override
    public Student loadById(Integer id) {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * " +
                     "FROM students WHERE id = ?")){
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery();) {
                Student student = null;
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String familyName = resultSet.getString("family_name");
                    int majorId = resultSet.getInt("major_id");
                    student = Student.builder()
                            .id(studentId)
                            .name(name)
                            .familyName(familyName)
                            .major(new MajorDao().loadById(majorId))
                            .build();
                }
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from university database");
        }
    }

    @Override
    public List<Student> loadAll() {
        try (Connection connection = dataSourceConfig.createDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT *" +
                     " FROM students");
             ResultSet resultSet = ps.executeQuery();){

            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String familyName = resultSet.getString("family_name");
                int majorId = resultSet.getInt("major_id");
                Student student = Student.builder()
                        .id(studentId)
                        .name(name)
                        .familyName(familyName)
                        .major(new MajorDao().loadById(majorId))
                        .build();
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataNotFoundException("Can not find data from db");
        }
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }
}
