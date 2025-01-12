package pl.jkap.sozzt.remark.domain;

import org.springframework.context.annotation.Bean;
import pl.jkap.sozzt.instant.InstantProvider;

public class RemarkConfiguration {

    @Bean
    public RemarkFacade remarkFacade(InstantProvider instantProvider) {
        return new RemarkFacade(new InMemoryRemarkRepository(), instantProvider);
    }
}
