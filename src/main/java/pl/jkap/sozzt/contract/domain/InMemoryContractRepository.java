package pl.jkap.sozzt.contract.domain;


import pl.jkap.sozzt.contract.dto.ContractDtoRepository;
import pl.jkap.sozzt.utils.InMemoryRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;


class InMemoryContractRepository extends InMemoryRepository<ContractEntity, UUID> implements ContractRepository {

    @Override
    public UUID getId(ContractEntity contractEntity) {
        return contractEntity.getId();
    }

    @Override
    public ContractEntity save(ContractEntity entity) {
        requireNonNull(entity);
        map.put(getId(entity), entity);
        return entity;
    }


    @Override
    public ContractDtoRepository findContractDataById(UUID searchValue) {
        ContractEntity contractEntity = map.get(searchValue);
        return new ContractDtoRepository() {
            @Override
            public UUID getId() {
                return contractEntity.getId();
            }

            @Override
            public String getInvoiceNumber() {
                return contractEntity.getInvoiceNumber();
            }

            @Override
            public String getLocation() {
                return contractEntity.getLocation();
            }

            @Override
            public String getExecutive() {
                return contractEntity.getExecutive();
            }

            @Override
            public boolean getScanFromTauronUpload() {
                return contractEntity.isScanFromTauronUpload();
            }

            @Override
            public boolean getPreliminaryMapUpload() {
                return contractEntity.isPreliminaryMapUpload();
            }

            @Override
            public boolean getAllConsentAccepted() {
                return contractEntity.isAllConsentAccepted();
            }

            @Override
            public String getContractStepEnum() {
                return contractEntity.getContractStepEnum()
                        .toString();
            }

            @Override
            public LocalDateTime getCreated() {
                return contractEntity.getCreated();
            }
        };
    }
}
