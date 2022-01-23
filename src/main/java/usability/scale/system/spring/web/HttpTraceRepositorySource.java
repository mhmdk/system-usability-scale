package usability.scale.system.spring.web;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTraceRepositorySource {
    @Bean
    public HttpTraceRepository createHttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
