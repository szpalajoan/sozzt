package pl.jkap.sozzt.consent.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.jkap.sozzt.consent.dto.ConsentDto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Consent")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Consent {
    @Id
    private UUID id;
    private UUID idContract;
    private String location;
    private String contact;

    @Enumerated(EnumType.STRING)
    private ConsentStatus status;
    private String comment;

    public ConsentDto dto() {
        return ConsentDto.builder()
                .id(id)
                .idContract(idContract)
                .contact(contact)
                .location(location)
                .status(status)
                .build();
    }
}

