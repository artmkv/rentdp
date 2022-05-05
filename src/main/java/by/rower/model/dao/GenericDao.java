package by.rower.model.dao;


import by.rower.model.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDao<P extends Serializable, E extends BaseEntity<P>> {

    List<E> findAll(int limit, int offset);

    P save(E entity);

    void update(E entity);

    void delete(E entity);

    Optional<E> findById(P id);

    boolean isExist(P id);

    Long getCountRow();
}
