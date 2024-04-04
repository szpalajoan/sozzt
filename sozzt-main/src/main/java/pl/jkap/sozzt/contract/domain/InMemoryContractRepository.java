package pl.jkap.sozzt.contract.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryContractRepository implements ContractRepository {
    private ConcurrentHashMap<Long, Contract> map = new ConcurrentHashMap<>();

    @Override
    public Contract save(Contract contract) {
        requireNonNull(contract);
        map.put(contract.getId() ,contract);
        return contract;
    }

    @Override
    public Contract findById(Long id) {
        return map.get(id);
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public Page<Contract> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }

}
