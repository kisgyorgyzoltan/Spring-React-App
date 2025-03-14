package edu.bbte.idde.kzim2149.dao;


import edu.bbte.idde.kzim2149.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {

    T insert(T entity);

    void delete(Long id);

    T update(Long id, T updatedEntity);

    Collection<T> findAll();

    T findById(Long id);
}
