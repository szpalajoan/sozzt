package pl.jkap.sozzt.Contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.Contract.model.StatusContract;

@Repository
public interface StatusContractRepository extends JpaRepository<StatusContract, Long> {
}
