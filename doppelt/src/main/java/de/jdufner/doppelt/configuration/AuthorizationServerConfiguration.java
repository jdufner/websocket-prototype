package de.jdufner.doppelt.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  // @Autowired
  // private DataSource dataSource;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager);
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    // @formatter:off
    clients.inMemory()
        .withClient("acme")
        .secret("acmesecret")
        .authorizedGrantTypes("client_credentials", "password", "refresh_token")
        .accessTokenValiditySeconds(60) // default = 12 Stunden
        .refreshTokenValiditySeconds(3600) // default = ???
        .scopes("read")
    ;
//    clients.jdbc(dataSource).passwordEncoder(passwordEncoder)
  // @formatter:on
  }

  @Configuration
  protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void init(final AuthenticationManagerBuilder auth) throws Exception {
      // @formatter:off
      auth.jdbcAuthentication()
          .dataSource(dataSource)
          .usersByUsernameQuery("select benu_name, benu_kennwort, true from benutzer where benu_name = ?")
          .authoritiesByUsernameQuery("select bero_benu_name, bero_roll_name from benutzer_rollen where bero_benu_name = ?")
          .passwordEncoder(passwordEncoder)
      ;
      // @formatter:on
    }

  }
}
