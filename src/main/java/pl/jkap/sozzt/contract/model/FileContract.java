package pl.jkap.sozzt.contract.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file_contract")
@Getter
@Setter
public class FileContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFile;
    private Long idContract;
    private String nameFile;
    private String pathFile;

}
