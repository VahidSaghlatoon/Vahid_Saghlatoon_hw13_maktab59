package service;

import dao.MajorDao;
import entity.Major;
import entity.Student;

import java.util.Scanner;

public class MajorService extends AbstractCrudService<Major ,Integer> {
    public MajorService(){
        setBaseDao(new MajorDao());
    }
    public MajorDao getBaseDao(){
        return (MajorDao) super.getBaseDao();
    }

    public void addMajor() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter name : ");
        String name = input.next();
        new MajorService().saveOrUpdate(new Major(name));
    }

    public void showAllMajor() {
        System.out.println(new MajorService().getBaseDao().loadAll());
    }

    public   void deleteMajorById() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter id : ");
        int id = input.nextInt();
        Major major = new MajorService().getBaseDao().loadById(id);
        if (major != null){
            new MajorService().getBaseDao().delete(id);
            System.out.println( "Deleted " + major.toString());
        }
        else
            System.out.println("Cant find !");

    }

    public void findMajorById() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter id : ");
        Major major = new MajorService().getBaseDao().loadById(input.nextInt());
        if (major!= null) {
            System.out.println(major.toString());
        }else
            System.out.println("cant find!");
    }
}
