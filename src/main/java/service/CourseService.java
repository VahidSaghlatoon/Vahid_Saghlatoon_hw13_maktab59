package service;

import dao.CourseDao;
import entity.Course;
import entity.Student;

import java.util.Scanner;

public class CourseService extends AbstractCrudService<Course, Integer> {
    public CourseService() {
        setBaseDao(new CourseDao());
    }
    public CourseDao getBaseDao() {
        return (CourseDao) super.getBaseDao();

    }


    public   void deleteCourseById() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter id : ");
        int id = input.nextInt();
        Course course = new CourseService().getBaseDao().loadById(id);
        if (course != null){
            new CourseService().getBaseDao().delete(id);
            System.out.println( "Deleted " + course.toString());
        }
        else
            System.out.println("Cant find !");

    }

    public void findCourseById() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter id : ");
        Course course = new CourseService().getBaseDao().loadById(input.nextInt());
        if (course!= null) {
            System.out.println(course.toString());
        }else
            System.out.println("cant find!");
    }

    public   void showAllCourses() {
        System.out.println( new CourseService().getBaseDao().loadAll().toString());
    }

    public void addCourse() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter name : ");
        String name = input.nextLine();
        System.out.print("Enter unit : ");
        int unit = input.nextInt();
        Course newCourse = new Course(name,unit);
        new CourseService().saveOrUpdate(newCourse);
    }
}
