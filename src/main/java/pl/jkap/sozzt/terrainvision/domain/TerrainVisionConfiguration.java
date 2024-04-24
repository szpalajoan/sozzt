package pl.jkap.sozzt.terrainvision.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class TerrainVisionConfiguration {

    @Bean
    TerrainVisionFacade terrainVisionFacade(TerrainVisionRepository terrainVisionRepository, InstantProvider instantProvider) {
        return TerrainVisionFacade.builder()
                .terrainVisionRepository(terrainVisionRepository)
                .instantProvider(instantProvider)
                .build();
    }
}
