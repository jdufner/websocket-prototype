package de.jdufner.doppelt.configuration;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 *
 * @author Jürgen
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorizationServerConfigurationTest {

  @InjectMocks
  private AuthorizationServerConfiguration authorizationServerConfiguration;

  @Test
  public void whenTokenStoreExpectSqlsAreSet() {
    // arrange
    JdbcTokenStore jdbcTokenStore = mock(JdbcTokenStore.class);
    // act
    authorizationServerConfiguration.tokenStore(jdbcTokenStore);
    // verify
    verify(jdbcTokenStore).setInsertAccessTokenSql(anyString());
    verify(jdbcTokenStore).setSelectAccessTokenSql(anyString());
    verify(jdbcTokenStore).setSelectAccessTokenAuthenticationSql(anyString());
    verify(jdbcTokenStore).setSelectAccessTokenFromAuthenticationSql(anyString());
    verify(jdbcTokenStore).setSelectAccessTokensFromUserNameAndClientIdSql(anyString());
    verify(jdbcTokenStore).setSelectAccessTokensFromUserNameSql(anyString());
    verify(jdbcTokenStore).setSelectAccessTokensFromClientIdSql(anyString());
    verify(jdbcTokenStore).setDeleteAccessTokenSql(anyString());
    verify(jdbcTokenStore).setDeleteAccessTokenFromRefreshTokenSql(anyString());
    verify(jdbcTokenStore).setInsertRefreshTokenSql(anyString());
    verify(jdbcTokenStore).setSelectRefreshTokenSql(anyString());
    verify(jdbcTokenStore).setSelectRefreshTokenAuthenticationSql(anyString());
    verify(jdbcTokenStore).setDeleteRefreshTokenSql(anyString());
    verifyNoMoreInteractions(jdbcTokenStore);
  }

}
