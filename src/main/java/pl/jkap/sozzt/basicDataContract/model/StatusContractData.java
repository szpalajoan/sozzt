package pl.jkap.sozzt.basicDataContract.model;


import lombok.Getter;

import javax.persistence.*;


@Entity
@Table(name = "status_contract")
@Getter
public class StatusContractData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_status_contract;
    private String status_text;

}
