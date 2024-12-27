package pl.jkap.sozzt.consents.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ConsentsRepository extends CrudRepository<Consents, UUID> {

}
