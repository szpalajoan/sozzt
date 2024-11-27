package pl.jkap.sozzt.routepreparation.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;


public class InMemoryRoutePreparationRepository extends InMemoryRepository<RoutePreparation, UUID> implements RoutePreparationRepository {
    @Override
    protected <S extends RoutePreparation> UUID getIdFromEntity(S entity) {
        return entity.getRoutePreparationId();
    }

}
