package pl.jkap.sozzt.remark.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class RemarkConfiguration {

    @Bean
    public RemarkFacade remarkFacade(InstantProvider instantProvider) {
        return new RemarkFacade(new InMemoryRemarkRepository(), instantProvider);
    }
}
