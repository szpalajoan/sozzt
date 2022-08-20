package pl.jkap.sozzt.utils;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public abstract class InMemoryRepository<T, ID> implements JpaRepository<T, ID> {

    public ConcurrentHashMap<ID, T> map = new ConcurrentHashMap<>();

    public abstract ID getId(T entity);

    @Override
    public <S extends T> S save(S entity) {
        map.put(getId(entity), entity);
        return entity;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        List<S> list = new ArrayList<>();
        for (S entity : iterable) {
            map.put(getId(entity), entity);
            list.add(entity);
        }
        return list;
    }

    @Override
    public Optional<T> findById(ID id) {
        return map.values().stream().filter(s -> getId(s).equals(id)).findFirst();
    }

    @Override
    public boolean existsById(ID id) {
        return map.values().stream().anyMatch(s -> getId(s).equals(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public List<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return new PageImpl<>(findAll(), pageable, count());
    }

    @Override
    public List<T> findAllById(Iterable<ID> iterable) {
        return map.values().stream()
                .filter(s -> StreamSupport.stream(iterable.spliterator(), false).anyMatch(id -> getId(s).equals(id)))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return map.values().size();
    }

    @Override
    public void deleteById(ID id) {
        map.remove(id);
    }

    @Override
    public void delete(T t) {
        map.remove(getId(t));
    }

    @Override
    public void deleteAll() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        iterable.forEach(this::delete);
    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        map.put(getId(entity), entity);
        return entity;
    }


    @Override
    public void deleteAllInBatch() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public T getOne(ID id) {
        return map.values().stream().filter(s -> getId(s).equals(id)).findFirst().orElseThrow(NullPointerException::new);
    }


    //NOT IMPLEMENTED
    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<T> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ID> longs) {

    }

    @Override
    public T getById(ID aLong) {
        return null;
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> longs) {

    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

}

