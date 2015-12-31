package de.jdufner.doppelt.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = de.jdufner.doppelt.configuration.SecurityConfiguration.class)
public class SecurityConfigurationTest {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void testPasswordEncoder() {
    String rawPassword = "juergen";
    String encodedPassword = "$2a$10$rLHFZuzBOLviSEbgIQ4FXuD2jzjHOKnFomzGzDgTlvumGYBBsYlwS";
    assertThat(passwordEncoder.matches(rawPassword, encodedPassword));
  }

  @Test
  public void testEncodePassword() {
    String rawPassword = "thorsten";
    System.out.println(passwordEncoder.encode(rawPassword));
  }

}
