package pl.jkap.sozzt.landextracts.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

interface LandExtractsRepository extends CrudRepository<LandExtracts, UUID> {
} 