package pl.jkap.sozzt.contract.old.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.fileContract.domain.FileContract;
import pl.jkap.sozzt.contract.old.model.FileType;

import java.util.List;


@Repository
public interface FileContractRepository extends JpaRepository<FileContract, Long> {

    List<FileContract> findAllByIdContractIn(List<Long> ids);

    FileContract findAnyByIdContractAndFileType(Long idContract, FileType fileType);
}
