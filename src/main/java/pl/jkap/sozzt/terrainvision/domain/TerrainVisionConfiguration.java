package pl.jkap.sozzt.terrainvision.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class TerrainVisionConfiguration {

    @Bean
    public TerrainVisionFacade terrainVisionFacade(InstantProvider instantProvider, TerrainVisionEventPublisher terrainVisionEventPublisher) {
        return TerrainVisionFacade.builder()
                .terrainVisionRepository(new InMemoryTerrainVisionRepository())
                .instantProvider(instantProvider)
                .terrainVisionEventPublisher(terrainVisionEventPublisher)
                .build();
    }

//    public TerrainVisionFacade terrainVisionFacade(InstantProvider instantProvider) {
//        return terrainVisionFacade(new InMemoryTerrainVisionRepository(), instantProvider);
//    }
}
