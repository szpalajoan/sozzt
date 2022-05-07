package pl.jkap.sozzt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract_basic_data")
@Getter
@Setter
public class ContractBasicData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoice_number;
    private String location;
    private String executive;
    private LocalDateTime created;
}


