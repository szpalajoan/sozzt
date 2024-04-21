package pl.jkap.sozzt.inmemory;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InMemoryRepository<T, ID extends Serializable> implements CrudRepository<T, ID> {

    protected Map<ID, T> table = new ConcurrentHashMap<>();

    @Override
    public <S extends T>  @NotNull S save(@NotNull S entity) {
        ID id = getIdFromEntity(entity);
        table.put(id, entity);
        return entity;
    }

    protected abstract <S extends T> ID getIdFromEntity(S entity);

    @Override
    public <S extends T> @NotNull Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            save(entity);
        }
        return entities;
    }

    @Override
    public @NotNull Optional<T> findById(@NotNull ID id) {
        return Optional.ofNullable(table.get(id));
    }

    @Override
    public @NotNull Iterable<T> findAll() {
        return table.values();
    }

    @Override
    public @NotNull Iterable<T> findAllById(Iterable<ID> ids) {
        List<ID> idList = new ArrayList<>();
        for (ID id : ids) {
            idList.add(id);
        }
        List<T> allById = new ArrayList<>();
        for (ID id : idList) {
            T entity = table.get(id);
            if (entity != null) {
                allById.add(entity);
            }
        }
        return allById;
    }

    @Override
    public long count() {
        return table.size();
    }

    @Override
    public void deleteById(@NotNull ID id) {
        table.remove(id);
    }

    @Override
    public void delete(@NotNull T entity) {
        table.remove(getIdFromEntity(entity));
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        table.clear();
    }

    @Override
    public boolean existsById(@NotNull ID id) {
        return table.containsKey(id);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        for (ID id : ids) {
            deleteById(id);
        }
    }
}
