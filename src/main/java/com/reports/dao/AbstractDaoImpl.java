package com.reports.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoImpl<E> implements BaseDao<E> {

    private final Logger logger = LoggerFactory.getLogger(AbstractDaoImpl.class);

    private Class<E> clazz = null;

    @Autowired
    private Datastore dataStore;

    @SuppressWarnings("unchecked")
    public AbstractDaoImpl() {
        clazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public List<E> find(Query<E> query, List<Criteria> criterias) {
        for (Criteria criteria : criterias) {
            query.and(criteria);
        }
        logger.debug("query.toString() {}", query.toString());
        return query.asList();
    }

    public E findById(Object id) {
        Query<E> query = dataStore.createQuery(clazz).disableValidation();
        Criteria criteria = query.criteria("_id").equal(id);
        query.and(criteria);
        return query.get();
    }

    public List<E> findByFieldNameAndValue(String field, Object value) {
        Query<E> query = dataStore.createQuery(clazz).disableValidation();
        Criteria criteria = query.criteria(field).equal(value);
        query.and(criteria);
        return query.asList();
    }

    public Key<E> save(E entity) {
        return dataStore.save(entity);
    }

    public long findCount() {
        return dataStore.find(clazz).count();
    }

    protected Query<E> getQuery() {
        return dataStore.createQuery(clazz).disableValidation();
    }

}
