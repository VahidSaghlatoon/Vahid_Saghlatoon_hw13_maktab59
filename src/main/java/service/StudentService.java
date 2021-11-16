package service;

import dao.StudentCourseDao;
import dao.StudentDao;
import entity.Course;
import entity.Student;

import java.util.Scanner;
import java.util.Set;

public class StudentService extends AbstractCrudService<Student, Integer> {
    public StudentService() {
        setBaseDao(new StudentDao());
    }

    public StudentDao getBaseDao() {
        return (StudentDao) super.getBaseDao();
    }


    public   void deleteStudentById() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter id : ");
        int id = input.nextInt();
        Student student = new StudentService().getBaseDao().loadById(id);
        if (student != null){
            new StudentService().getBaseDao().delete(id);
            System.out.println( "Deleted " + student.toString());
        }
        else
            System.out.println("Cant find !");

    }

    public void findStudentById() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter id : ");
        Student student = new StudentService().getBaseDao().loadById(input.nextInt());
        if (student!= null) {
            System.out.println(student.toString());
        }else
            System.out.println("cant find!");
    }

    public   void showAllStudent() {
        System.out.println( new StudentService().getBaseDao().loadAll().toString());
    }

    public void addStudent() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter name : ");
        String name = input.next();
        System.out.print("Enter family name : ");
        String familyName = input.next();
        System.out.print("Enter Major id : ");
        int majorId = input.nextInt();
        new StudentService().saveOrUpdate(new Student.StudentBuilder().name(name).familyName(familyName).major(new MajorService().getBaseDao().loadById(majorId)).build());
    }

    public void addCourse() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter student id : ");
        int studentId = input.nextInt();
        System.out.print("Enter course id : ");
        int courseId = input.nextInt();
        if (new StudentService().getBaseDao().loadById(studentId)!= null && new CourseService().getBaseDao().loadById(courseId)!=null)
        {
            new StudentCourseDao().save(studentId,courseId);
        }
        else
            System.out.println("invalid student or course id ");
    }

    public void showAllCourseSelected() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter student id : ");
        int studentId = input.nextInt();
        if (new StudentService().getBaseDao().loadById(studentId)!= null )
        {
            System.out.println(new StudentCourseDao().loadAll(studentId).toString());
        }
        else
            System.out.println("invalid student id ");
    }
    public void registerGrade() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter student id : ");
        int studentId = input.nextInt();
        System.out.print("Enter course id : ");
        int courseId = input.nextInt();
        System.out.print("Enter grade : ");
        double grade = input.nextDouble();
        if (new StudentService().getBaseDao().loadById(studentId)!= null && new CourseService().getBaseDao().loadById(courseId)!=null )
        {
            new StudentCourseDao().update(studentId,courseId,grade);
        }
        else
            System.out.println("invalid student or course id ");
    }
}
