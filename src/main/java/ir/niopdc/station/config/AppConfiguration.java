package ir.niopdc.station.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = {"ir.niopdc"})
@EnableJpaRepositories(basePackages="ir.niopdc.domain")
@EntityScan(basePackages="ir.niopdc.domain")
public class AppConfiguration {
}
