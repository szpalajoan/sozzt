package pl.jkap.sozzt.basicDataContract.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file_contract")
@Getter
@Setter
public class FileContractData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_file;
    private Long id_contract_basic_data;
    private String name_file;
    private String path_file;

}
