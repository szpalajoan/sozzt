package pl.jkap.sozzt.contractsecurity.domain;

import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.contractsecurity.dto.AddSecurityContractDto;
import pl.jkap.sozzt.contractsecurity.exception.*;

import java.util.UUID;

@Builder
public class ContractSecurityFacade {

    private final ContractSecurityRepository contractSecurityRepository;

    public void addSecurityContract(AddSecurityContractDto addSecurityContractDto) {
        contractSecurityRepository.save(new SecurityContract(addSecurityContractDto.getSecurityContractId()));
    }

    public void checkCanAddContract() {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_CONTRACT_INTRODUCER"))) {
            throw new UnauthorizedContractAdditionException("Contract addition not allowed");
        }
    }

    public void checkCanEditContract() {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_CONTRACT_INTRODUCER"))) {
            throw new UnauthorizedContractAdditionException("Contract edit not allowed");
        }
    }

    public void checkCanAddContractScan(UUID contractId) {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_CONTRACT_INTRODUCER"))) {
            throw new UnauthorizedContractScanAdditionException("Contract scan addition not allowed");
        }
        if(!contractSecurityRepository.existsById(contractId)) {
            throw new ContractSecurityNotFoundException("Contract security not found: " + contractId);
        }
    }

    public void checkCanAddPreliminaryMap(UUID uuid) {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_PRELIMINARY_PLANER"))) {
            throw new UnauthorizedPreliminaryMapAdditionException("preliminary map addition not allowed");
        }
        if(!contractSecurityRepository.existsById(uuid)) {
            throw new ContractSecurityNotFoundException("Contract security not found: " + uuid);
        }
    }

    public void checkCanFinalizeContractIntroduction() {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_CONTRACT_INTRODUCER"))) {
            throw new UnauthorizedContractFinalizeException("Contract introduction is not allowed");
        }
    }

    public void checkCanFinalizePreliminaryPlan() {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_PRELIMINARY_PLANER"))) {
            throw new UnauthorizedPreliminaryMapAdditionException("finalize preliminary plan is not allowed");
        }
    }
}
