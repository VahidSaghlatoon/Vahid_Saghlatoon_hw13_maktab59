package service;


import java.util.Scanner;

public class University {

    public void start() {
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            printMainMenu();
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    courseMenu();
                    break;
                case 3:
                    majorMenu();
                    break;
                case 4:
//                    printStudentCourseMenu();
                    break;
                case 5:
                    flag = false;
                    break;
            }
        }
    }

    private void printMainMenu() {
        System.out.println("---Choose your entity--- ");
        System.out.print("1--> Student\n" +
                "2--> Course\n" +
                "3--> Major\n" +
                "4--> Grade\n" +
                "5--> Exit\n" +
                "---> ");
    }

    private void majorMenu() {
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            printOptions("Major");
            int choice = input.nextInt();
            switch (choice) {
                case 1: new MajorService().addMajor();break;
                case 2 : new MajorService().showAllMajor(); break;
                case 3 : new MajorService().findMajorById(); break;
                case 4 : new MajorService().deleteMajorById(); break;
                case 5:
                    flag = false;
                    break;
            }
        }
        start();
    }

    private void courseMenu() {
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            printOptions("Course");
            int choice = input.nextInt();
            switch (choice) {
                case 1: new CourseService().addCourse();break;
                case 2 : new CourseService().showAllCourses();break;
                case 3 : new CourseService().findCourseById();break;
                case 4 : new CourseService().deleteCourseById();break;
                case 5:
                    flag = false;
                    break;
            }
        }
        start();
    }

    private void studentMenu() {
        Scanner input = new Scanner(System.in);
        StudentService studentService = new StudentService();
        boolean flag = true;
        while (flag) {
            printStudentOptions();
            int choice = input.nextInt();
            switch (choice) {
                case 1: studentService.addStudent();break;
                case 2 : studentService.showAllStudent(); break;
                case 3 : studentService.findStudentById(); break;
                case 4 : studentService.deleteStudentById(); break;
                case 5 : studentService.addCourse();break;
                case 6 : studentService.showAllCourseSelected();break;
                case 7 : studentService.registerGrade();break;
                case 8:
                    flag = false;
                    break;
            }
        }
        start();
    }

    private void printStudentOptions() {
        System.out.println("--Student Options--");
        System.out.print("1--> Add \n" +
                "2--> Show all  \n" +
                "3--> Find by id\n" +
                "4--> Delete by id\n" +
                "5--> Select course by student id and course id \n" +
                "6--> Show all course selected by student by id \n" +
                "7--> Register grade \n" +
                "8--> Main Menu\n" +
                "---> ");
    }

    private void printOptions(String entityName) {
        System.out.println("--"+entityName+" Options--");
        System.out.print("1--> Add \n" +
                "2--> Show all  \n" +
                "3--> Find by id\n" +
                "4--> Delete by id\n" +
                "5--> Main Menu\n" +
                "---> ");
    }

}
