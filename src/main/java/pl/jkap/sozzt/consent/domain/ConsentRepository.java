package pl.jkap.sozzt.consent.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface ConsentRepository extends JpaRepository<Consent, UUID> {
    List<Consent> findByIdContract(UUID idContract);
}
