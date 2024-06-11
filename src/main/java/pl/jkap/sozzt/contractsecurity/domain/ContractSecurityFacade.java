package pl.jkap.sozzt.contractsecurity.domain;

import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.contractsecurity.dto.AddSecurityContractDto;
import pl.jkap.sozzt.contractsecurity.exception.ContractSecurityNotFoundException;
import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedContractAdditionException;
import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedContractScanAdditionException;
import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedPreliminaryMapAdditionException;

import java.util.UUID;

@Builder
public class ContractSecurityFacade {

    private final ContractSecurityRepository contractSecurityRepository;

    public void addSecurityContract(AddSecurityContractDto addSecurityContractDto) {
        contractSecurityRepository.save(new SecurityContract(addSecurityContractDto.getSecurityContractId()));
    }

    public void checkCanAddContract() {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("CONTRACT_INTRODUCER"))) {
            throw new UnauthorizedContractAdditionException("Contract addition not allowed");
        }
    }

    public void checkCanAddContractScan(UUID objectId) {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("CONTRACT_INTRODUCER"))) {
            throw new UnauthorizedContractScanAdditionException("Contract scan addition not allowed");
        }
        if(!contractSecurityRepository.existsById(objectId)) {
            throw new ContractSecurityNotFoundException("Contract security not found: " + objectId);
        }
    }

    public void checkCanAddPreliminaryMap(UUID uuid) {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("PRELIMINARY_PLANER"))) {
            throw new UnauthorizedPreliminaryMapAdditionException("preliminary map addition not allowed");
        }
        if(!contractSecurityRepository.existsById(uuid)) {
            throw new ContractSecurityNotFoundException("Contract security not found: " + uuid);
        }
    }
}
