package de.jdufner.doppelt.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Bean(name = "defaultJdbcTokenStore")
  public JdbcTokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  @Primary
  @Bean
  public JdbcTokenStore tokenStore(final @Qualifier(value = "defaultJdbcTokenStore") JdbcTokenStore jdbcTokenStore) {
    jdbcTokenStore.setInsertAccessTokenSql(
        "insert into oauth_access_token (oact_access_token_id, oact_token, oact_authentication_id, oact_user_name, oact_client_id, oact_authentication, oact_refresh_token_id) values (?, ?, ?, ?, ?, ?, ?)");
    jdbcTokenStore
        .setSelectAccessTokenSql("select oact_access_token_id, oact_token from oauth_access_token where oact_access_token_id = ?");
    jdbcTokenStore.setSelectAccessTokenAuthenticationSql(
        "select oact_access_token_id, oact_authentication from oauth_access_token where oact_access_token_id = ?");
    jdbcTokenStore.setSelectAccessTokenFromAuthenticationSql(
        "select oact_access_token_id, oact_token from oauth_access_token where oact_authentication_id = ?");
    jdbcTokenStore.setSelectAccessTokensFromUserNameAndClientIdSql(
        "select oact_access_token_id, oact_token from oauth_access_token where oact_user_name = ? and oact_client_id = ?");
    jdbcTokenStore.setSelectAccessTokensFromUserNameSql(
        "select oact_access_token_id, oact_token from oauth_access_token where oact_user_name = ?");
    jdbcTokenStore.setSelectAccessTokensFromClientIdSql(
        "select oact_access_token_id, oact_token from oauth_access_token where oact_client_id = ?");
    jdbcTokenStore.setDeleteAccessTokenSql("delete from oauth_access_token where oact_access_token_id = ?");
    jdbcTokenStore.setDeleteAccessTokenFromRefreshTokenSql("delete from oauth_access_token where oact_refresh_token_id = ?");
    jdbcTokenStore.setInsertRefreshTokenSql(
        "insert into oauth_refresh_token (oret_refresh_token_id, oret_token, oret_authentication) values (?, ?, ?)");
    jdbcTokenStore.setSelectRefreshTokenSql(
        "select oret_refresh_token_id, oret_token from oauth_refresh_token where oret_refresh_token_id = ?");
    jdbcTokenStore.setSelectRefreshTokenAuthenticationSql(
        "select oret_refresh_token_id, oret_authentication from oauth_refresh_token where oret_refresh_token_id = ?");
    jdbcTokenStore.setDeleteRefreshTokenSql("delete from oauth_refresh_token where oret_refresh_token_id = ?");
    return jdbcTokenStore;
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore());
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    // @formatter:off
    clients.inMemory()
        .withClient("acme")
        .secret("acmesecret")
        .authorizedGrantTypes("client_credentials", "password", "refresh_token")
        .accessTokenValiditySeconds(60 * 60 * 12) // default = 12 Stunden
        .refreshTokenValiditySeconds(60 * 60 * 24 * 30) // default = ???
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
