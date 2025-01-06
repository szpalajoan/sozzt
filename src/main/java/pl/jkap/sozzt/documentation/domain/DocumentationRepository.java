package pl.jkap.sozzt.documentation.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DocumentationRepository extends CrudRepository<Documentation, UUID> {
}
