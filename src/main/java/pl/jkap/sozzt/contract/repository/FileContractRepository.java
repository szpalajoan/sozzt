package pl.jkap.sozzt.contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.contract.model.FileContract;
import pl.jkap.sozzt.contract.model.FileType;

import java.util.List;


@Repository
public interface FileContractRepository extends JpaRepository<FileContract, Long> {

    List<FileContract> findAllByIdContractIn(List<Long> ids);

    FileContract findAnyByIdContractAndFileType(Long idContract, FileType fileType);
}
