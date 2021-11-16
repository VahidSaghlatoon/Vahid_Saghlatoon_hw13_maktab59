package entity;

import entity.base.BaseEntity;

import java.util.Objects;

public class Major implements BaseEntity<Integer> {
    private Integer id;
    private String name;

    public Major(Integer id) {
        this.id = id;
    }

    public Major(String name) {
        this.name = name;
    }


    public Major(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Major major = (Major) o;
        return Objects.equals(id, major.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';

    }
    public  static MajorBuilder builder(){ return new MajorBuilder();}

    private static class MajorBuilder {
        private Integer id ;
        private String name ;

        public MajorBuilder id (Integer id){
            this.id = id ;
            return this ;
        }

        public MajorBuilder name(String name){
            this.name = name ;
            return this ;
        }

        public Major build(){
            return new Major(id,name);
        }
    }

}
