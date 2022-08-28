package pl.jkap.sozzt.consent.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsentRepository extends JpaRepository<Consent, UUID> {
}
