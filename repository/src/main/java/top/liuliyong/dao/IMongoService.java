package top.liuliyong.dao;


import java.util.List;
import java.util.Optional;

public interface IMongoService<T> {

    <S extends T> S save(S entity);

//    <S extends T> Iterable<S> saveMany(Collection<S> entityList);

    Optional<T> findById(String id);

    Iterable<T> findAll();

    long count();

    List delete(String... ids);

    <S extends T> S update(S entity);
}
