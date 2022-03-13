package pl.jkap.sozzt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkap.sozzt.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
