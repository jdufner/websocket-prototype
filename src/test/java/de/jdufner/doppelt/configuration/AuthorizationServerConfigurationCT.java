package de.jdufner.doppelt.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jürgen
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { AuthorizationServerConfiguration.class, AuthorizationServerConfigurationMock.class,
    PropertyTestConfiguration.class, RepositoryConfiguration.class })
//@ActiveProfiles(profiles = { "postgresql" })
public class AuthorizationServerConfigurationCT {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcTokenStore tokenStore;

  @Test
  @Transactional
  @Timed(millis = 10)
  public void whenStoreTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    // act
    tokenStore.storeAccessToken(token, authentication);
    // assert
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    long totalLines = jdbcTemplate.queryForObject("select count(*) from oauth_access_token where oact_access_token_id = ?",
        Long.class, extractTokenKey(token.getValue()));
    assertThat(totalLines).isEqualTo(1);
  }

  @Test
  @Transactional
  public void whenReadAccessTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    OAuth2AccessToken tokenNew = tokenStore.readAccessToken(token.getValue());
    // assert
    assertThat(tokenNew).isNotNull().isEqualTo(token);
  }

  @Test
  @Transactional
  public void whenReadAuthenticationExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    OAuth2Authentication authenticationNew = tokenStore.readAuthentication(token.getValue());
    // assert
    assertThat(authenticationNew).isNotNull().isEqualTo(authentication);
  }

  @Test
  @Transactional
  public void whenGetAccessTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    OAuth2AccessToken tokenNew = tokenStore.getAccessToken(authentication);
    // assert
    assertThat(tokenNew).isNotNull().isEqualTo(token);
  }

  @Test
  @Transactional
  public void whenFindTokensByClientIdAndUserNameExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    Collection<OAuth2AccessToken> tokens = tokenStore
        .findTokensByClientIdAndUserName(authentication.getOAuth2Request().getClientId(), authentication.getName());
    // assert
    assertThat(tokens).containsExactly(token);
  }

  @Test
  @Transactional
  public void whenFindTokensByUserNameExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByUserName(authentication.getName());
    // assert
    assertThat(tokens).containsExactly(token);
  }

  @Test
  @Transactional
  public void whenFindTokensByClientIdExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(authentication.getOAuth2Request().getClientId());
    // assert
    assertThat(tokens).contains(token);
  }

  @Test
  @Transactional
  public void whenRemoveAccessTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    tokenStore.removeAccessToken(token.getValue());
    // assert
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    long totalLines = jdbcTemplate.queryForObject("select count(*) from oauth_access_token where oact_access_token_id = ?",
        Long.class, extractTokenKey(token.getValue()));
    assertThat(totalLines).isEqualTo(0);
  }

  @Test
  @Transactional
  public void whenRemoveAccessTokenUsingRefreshTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeAccessToken(token, authentication);
    // act
    tokenStore.removeAccessTokenUsingRefreshToken(token.getRefreshToken().getValue());
    // assert
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    long totalLines = jdbcTemplate.queryForObject("select count(*) from oauth_access_token where oact_access_token_id = ?",
        Long.class, extractTokenKey(token.getValue()));
    assertThat(totalLines).isEqualTo(0);
  }

  @Test
  @Transactional
  public void whenStoreRefreshTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    // act
    tokenStore.storeRefreshToken(token.getRefreshToken(), authentication);
    // assert
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    long totalLines = jdbcTemplate.queryForObject("select count(*) from oauth_refresh_token where oret_refresh_token_id = ?",
        Long.class, extractTokenKey(token.getRefreshToken().getValue()));
    assertThat(totalLines).isEqualTo(1);
  }

  @Test
  @Transactional
  public void whenReadRefreshTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeRefreshToken(token.getRefreshToken(), authentication);
    // act
    OAuth2RefreshToken refreshTokenNew = tokenStore.readRefreshToken(token.getRefreshToken().getValue());
    // assert
    assertThat(refreshTokenNew).isNotNull().isEqualTo(token.getRefreshToken());
  }

  @Test
  @Transactional
  public void whenReadAuthenticationForRefreshTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeRefreshToken(token.getRefreshToken(), authentication);
    // act
    OAuth2Authentication authenticationNew = tokenStore.readAuthenticationForRefreshToken(token.getRefreshToken().getValue());
    // assert
    assertThat(authenticationNew).isNotNull().isEqualTo(authentication);
  }

  @Test
  @Transactional
  public void whenRemoveRefreshTokenExpectDone() {
    // arrange
    OAuth2AccessToken token = buildOAuth2AccessToken();
    OAuth2Authentication authentication = buildOAuth2Authentication();
    tokenStore.storeRefreshToken(token.getRefreshToken(), authentication);
    // act
    tokenStore.removeRefreshToken(token.getRefreshToken().getValue());
    // assert
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    long totalLines = jdbcTemplate.queryForObject("select count(*) from oauth_refresh_token where oret_refresh_token_id = ?",
        Long.class, extractTokenKey(token.getRefreshToken().getValue()));
    assertThat(totalLines).isEqualTo(0);
  }

  private OAuth2Authentication buildOAuth2Authentication() {
    OAuth2Request request = new OAuth2Request( //
        Collections.<String, String> emptyMap(), // requestParameter
        "acme", // Client-ID
        Collections.<GrantedAuthority> emptySet(), // authorities
        true, // approved
        Collections.<String> emptySet(), // scope
        Collections.<String> emptySet(), // resourceIds
        null, // redirectUri
        Collections.<String> emptySet(), // responseTypes
        Collections.<String, Serializable> emptyMap() // extensionProperties
    );
    User user = new User("user", "password", Collections.<GrantedAuthority> emptySet());
    Authentication userAuthentication = new UsernamePasswordAuthenticationToken(user, null);
    OAuth2Authentication authentication = new OAuth2Authentication(request, userAuthentication);
    return authentication;
  }

  private OAuth2AccessToken buildOAuth2AccessToken() {
    Map<String, String> tokenParams = new HashMap<String, String>();
    tokenParams.put(OAuth2AccessToken.ACCESS_TOKEN, UUID.randomUUID().toString());
    tokenParams.put(OAuth2AccessToken.EXPIRES_IN, String.valueOf(60 * 60 * 12)); // 12 Std, Defaultvalue?
    tokenParams.put(OAuth2AccessToken.REFRESH_TOKEN, UUID.randomUUID().toString());
    tokenParams.put(OAuth2AccessToken.SCOPE, "myScope");
    tokenParams.put(OAuth2AccessToken.TOKEN_TYPE, "Bearer");
    OAuth2AccessToken token = DefaultOAuth2AccessToken.valueOf(tokenParams);
    return token;
  }

  private String extractTokenKey(final String value) {
    if (value == null) {
      return null;
    }
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
    }

    try {
      byte[] bytes = digest.digest(value.getBytes("UTF-8"));
      return String.format("%032x", new BigInteger(1, bytes));
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
    }
  }

}
