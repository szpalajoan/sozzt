package pl.jkap.sozzt.basicDataContract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.basicDataContract.model.StatusContractData;

@Repository
public interface StatusContractRepository extends JpaRepository<StatusContractData, Long> {
}
