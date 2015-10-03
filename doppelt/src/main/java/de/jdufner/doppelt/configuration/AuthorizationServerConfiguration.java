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
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcTokenStore jdbcTokenStore;

  @Autowired
  private JdbcClientDetailsService jdbcClientDetailsService;

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
    endpoints.authenticationManager(authenticationManager).tokenStore(jdbcTokenStore);
  }

  @Bean(name = "defaultJdbcClientDetailsService")
  public JdbcClientDetailsService jdbcClientDetailsService() {
    return new JdbcClientDetailsService(dataSource);
  }

  @Primary
  @Bean
  public JdbcClientDetailsService jdbcClientDetailsService(
      final @Qualifier(value = "defaultJdbcClientDetailsService") JdbcClientDetailsService jdbcClientDetailsService) {
    jdbcClientDetailsService.setDeleteClientDetailsSql("delete from oauth_client_details where ocld_client_id = ?");
    jdbcClientDetailsService.setFindClientDetailsSql(
        "select ocld_client_id, ocld_client_secret, ocld_resource_ids, ocld_scope, ocld_authorized_grant_types, ocld_web_server_redirect_uri, ocld_authorities, ocld_access_token_validity, ocld_refresh_token_validity, ocld_additional_information, ocld_autoapprove from oauth_client_details order by ocld_client_id");
    jdbcClientDetailsService.setUpdateClientDetailsSql(
        "update oauth_client_details set ocld_resource_ids = ?, ocld_scope = ?, ocld_authorized_grant_types = ?, ocld_web_server_redirect_uri = ?, ocld_authorities = ?, ocld_access_token_validity = ?, ocld_refresh_token_validity = ?, ocld_additional_information = ?, ocld_autoapprove=? where ocld_client_id = ?");
    jdbcClientDetailsService
        .setUpdateClientSecretSql("update oauth_client_details set ocld_client_secret = ? where ocld_client_id = ?");
    jdbcClientDetailsService.setInsertClientDetailsSql(
        "insert into oauth_client_details (ocld_client_secret, ocld_resource_ids, ocld_scope, ocld_authorized_grant_types, ocld_web_server_redirect_uri, ocld_authorities, ocld_access_token_validity, ocld_refresh_token_validity, ocld_additional_information, ocld_autoapprove, ocld_client_id) values (?,?,?,?,?,?,?,?,?,?,?)");
    jdbcClientDetailsService.setSelectClientDetailsSql(
        "select ocld_client_id, ocld_client_secret, ocld_resource_ids, ocld_scope, ocld_authorized_grant_types, ocld_web_server_redirect_uri, ocld_authorities, ocld_access_token_validity, ocld_refresh_token_validity, ocld_additional_information, ocld_autoapprove from oauth_client_details where ocld_client_id = ?");
    return jdbcClientDetailsService;
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(jdbcClientDetailsService);
  }

  @Configuration
  public static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

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
