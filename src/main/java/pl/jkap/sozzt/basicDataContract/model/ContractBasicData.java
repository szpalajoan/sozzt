package pl.jkap.sozzt.basicDataContract.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contract_basic_data")
@Getter
@Setter
public class ContractBasicData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_contract_basic_data;
    private String invoice_number;
    private String location;
    private String executive;
    private LocalDateTime created;

    @OneToOne
    @JoinColumn(name = "id_status_contract")
    private StatusContractData id_status_contract;

    @OneToMany (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_contract_basic_data")
    private List<FileContractData> filesContractData;
}


