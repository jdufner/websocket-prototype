package de.jdufner.doppelt.configuration;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Jürgen
 *
 */
public class AuthorizationServerConfigurationMock {

  @Bean
  public AuthenticationManager authenticationManager() {
    return mock(AuthenticationManager.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return mock(PasswordEncoder.class);
  }

  @Bean
  public AuthenticationConfiguration authenticationConfiguration() {
    return mock(AuthenticationConfiguration.class);
  }

  @SuppressWarnings("rawtypes")
  @Bean
  public ObjectPostProcessor objectPostProcessor() {
    return mock(ObjectPostProcessor.class);
  }

}
