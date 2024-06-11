package pl.jkap.sozzt.contractsecurity.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Entity
@Builder
@Getter
class SecurityContract {

    @Id
    private UUID securityContractId;
}
