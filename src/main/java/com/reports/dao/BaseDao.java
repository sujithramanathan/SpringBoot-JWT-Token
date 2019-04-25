package com.reports.dao;

import java.util.List;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

public interface BaseDao<E> {

    public List<E> find(Query<E> query, List<Criteria> criterias);

    public E findById(Object id);

    public List<E> findByFieldNameAndValue(String field, Object value);

    public Key<E> save(E entity);

    public long findCount();

}
