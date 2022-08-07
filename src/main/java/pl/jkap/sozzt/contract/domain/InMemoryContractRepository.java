package pl.jkap.sozzt.contract.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

class InMemoryContractRepository implements ContractRepository {
    private final ConcurrentHashMap<Long, ContractEntity> map = new ConcurrentHashMap<>();

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public ContractEntity save(ContractEntity contractEntity) {
        requireNonNull(contractEntity);
        map.put(contractEntity.getId() ,contractEntity);
        return contractEntity;
    }

    @Override
    public Optional<ContractEntity> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public Page<ContractEntity> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }

    @Override
    public void delete(ContractEntity entity) {
        map.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ContractEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }


    @Override
    public List<ContractEntity> findAll() {
        return null;
    }

    @Override
    public List<ContractEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<ContractEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends ContractEntity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ContractEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ContractEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ContractEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ContractEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public ContractEntity getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ContractEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ContractEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ContractEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ContractEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ContractEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ContractEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ContractEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
