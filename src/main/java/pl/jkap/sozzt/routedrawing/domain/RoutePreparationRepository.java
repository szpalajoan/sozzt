package pl.jkap.sozzt.routedrawing.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoutePreparationRepository extends CrudRepository<RoutePreparation, UUID> {
}
