package pl.jkap.sozzt.fileContract.domain;

import lombok.Getter;
import lombok.Setter;
import pl.jkap.sozzt.contract.old.model.FileType;

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
    private FileType fileType;

}
