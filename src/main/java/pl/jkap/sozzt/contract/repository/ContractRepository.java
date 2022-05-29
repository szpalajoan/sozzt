package pl.jkap.sozzt.contract.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.contract.model.Contract;

import java.util.List;


@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("Select p From Contract p")
    List<Contract> findAllContract(Pageable page);
}