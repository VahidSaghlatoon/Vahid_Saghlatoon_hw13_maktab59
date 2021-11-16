package service;

import dao.core.BaseDao;
import entity.base.BaseEntity;

public class AbstractCrudService<T extends BaseEntity<ID>, ID extends Number> {
    private BaseDao<T, ID> baseDao;

    public void saveOrUpdate(T entity) {
        if (entity.getId() == null) {
            baseDao.save(entity);
        } else {
            baseDao.update(entity.getId(), entity);
        }
    }

    public void setBaseDao(BaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    public BaseDao<T, ID> getBaseDao() {
        return baseDao;
    }
}
