package pl.jkap.sozzt.contract.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.contract.dto.CreateContractDto;
import pl.jkap.sozzt.globalvalueobjects.AuditInfo;
import pl.jkap.sozzt.instant.InstantProvider;

import java.util.ArrayList;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
class ContractCreator {

    private InstantProvider instantProvider;

    Contract from(CreateContractDto createContractDto) {
        requireNonNull(createContractDto);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return Contract.builder()
                .contractId(createContractDto.getContractId().orElseGet(UUID::randomUUID))
                .contractDetails(new ContractDetails(createContractDto.getContractDetailsDto()))
                .location(new Location(createContractDto.getLocation()))
                .auditInfo(new AuditInfo(instantProvider.now(), userName))
                .contractSteps(new ArrayList<>())
                .zudConsentRequired(createContractDto.isZudConsentRequired())
                .build();
    }

}
