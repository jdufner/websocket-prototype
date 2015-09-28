package de.jdufner.doppelt.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("de.jdufner.doppelt.repository")
@EntityScan("de.jdufner.doppelt.domain")
@EnableTransactionManagement
public class RepositoryConfiguration {

}
