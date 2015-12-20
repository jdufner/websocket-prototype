package de.jdufner.doppelt.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()
      .antMatchers("/manage/health/**").permitAll()
      .antMatchers("/manage/**").permitAll()//.hasAuthority("Admin")
      .antMatchers("/api/**").permitAll()//.hasAuthority("User")
      .antMatchers("/ui/**").permitAll()//.hasAuthority("User")
    ;
    // @formatter:on
  }

}
