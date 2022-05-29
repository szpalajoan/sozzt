package pl.jkap.sozzt.contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.contract.model.StatusContract;

@Repository
public interface StatusContractRepository extends JpaRepository<StatusContract, Long> {
}
