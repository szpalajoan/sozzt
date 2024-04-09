package pl.jkap.sozzt.contract.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryContractRepository implements ContractRepository {
    private final ConcurrentHashMap<UUID, Contract> table = new ConcurrentHashMap<>();

    @Override
    public Contract save(Contract contract) {
        requireNonNull(contract);
        table.put(contract.getId() ,contract);
        return contract;
    }

    @Override
    public Contract findById(UUID id) {
        return table.get(id);
    }

    @Override
    public void deleteById(UUID id) {
        table.remove(id);
    }

    @Override
    public Page<Contract> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(table.values()), pageable, table.size());
    }

}
