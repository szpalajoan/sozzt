package pl.jkap.sozzt.basicDataContract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.basicDataContract.model.FileContractData;


@Repository
public interface FileContractRepository extends JpaRepository<FileContractData, Long> {
}
