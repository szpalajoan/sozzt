package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContractDto {
    private UUID id;
    private String invoiceNumber;
    private String location;
    private String createdBy;
    private Instant createdAt;
}



