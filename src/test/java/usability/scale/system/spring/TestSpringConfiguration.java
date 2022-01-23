package usability.scale.system.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import usability.scale.system.FakeScoreRepository;
import usability.scale.system.calculator.ScoreRepository;

@Configuration
class TestSpringConfiguration
{
    @Bean
    @Primary
    @Profile("test")
    ScoreRepository createScoreRepository() {
        return new FakeScoreRepository();
    }
}