package top.liuliyong.treatmentServer.repository.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import top.liuliyong.treatmentServer.repository.dao.IMongoService;

import java.util.Optional;


/**
 * @Author liyong.liu
 * @Date 2019/3/11
 **/
public abstract class AbstractUserDao<T> implements IMongoService<T> {
    @Autowired
    MongoOperations mongoTemplate;

    protected abstract Class<T> getEntityClass();

    protected abstract String getCollection();

    @Override
    public <S extends T> S save(S entity) {
        return mongoTemplate.save(entity, getCollection());
    }

    @Override
    public Optional<T> findById(String id) {
        T result = mongoTemplate.findById(new ObjectId(id), getEntityClass(), getCollection());
        return Optional.of(result);
    }

    @Override
    public Iterable<T> findAll() {
        return mongoTemplate.findAll(getEntityClass(), getCollection());
    }

    @Override
    public long count() {
        return mongoTemplate.count(Query.query(Criteria.where("_id").exists(true)), getEntityClass(), getCollection());
    }

}
