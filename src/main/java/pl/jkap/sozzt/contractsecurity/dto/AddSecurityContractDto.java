package pl.jkap.sozzt.contractsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AddSecurityContractDto {

    private UUID securityContractId;
}
