package entity.base;

public interface BaseEntity<ID extends Number> {
    void setId(ID id);
    ID getId();
}
