package de.jdufner.doppelt.configuration;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import de.jdufner.doppelt.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = { Application.class })
public class ResourceServerConfigurationTest {

  private enum TokenGroup {

    ACCESS_TOKEN(1), REFRESH_TOKEN(2);

    private int value;

    private TokenGroup(final int value) {
      this.value = value;
    }

  }

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
  }

  @Test
  public void testAccessApiRessource() throws Exception {
    mockMvc.perform(get("/api/greeting")).andDo(MockMvcResultHandlers.print()).andExpect(status().isUnauthorized()).andReturn();
  }

  @Test
  public void whenPostOAuthTokenByClientCredentialsAndUsernamePasswordExpectToken() throws Exception {
    getTokenAsJson("acme", "acmesecret", "juergen", "juergen");
  }

  @Test
  public void whenPostOAuthTokenByRefreshTokenExpectToken() throws Exception {
    String token = getToken(getTokenAsJson("acme", "acmesecret", "juergen", "juergen"), TokenGroup.REFRESH_TOKEN);
    String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";
    // @formatter:off
    mockMvc
        .perform(
            post("/oauth/token")
                .with(httpBasic("acme", "acmesecret"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("grant_type", "refresh_token")
                .param("refresh_token", token)
                )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(content().string(containsString("access_token")))
        .andReturn()
            .getResponse().getContentAsString();
    // @formatter:on
  }

  @Test
  public void whenGetApiGretingExpectGreets() throws Exception {
    String token = getToken(getTokenAsJson("acme", "acmesecret", "juergen", "juergen"), TokenGroup.ACCESS_TOKEN);
    String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";
    // @formatter:off
    mockMvc
        .perform(
            get("/api/greeting")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(content().string(containsString("Hello, World!")))
        .andReturn()
            .getResponse().getContentAsString();
    // @formatter:on
  }

  private String getTokenAsJson(final String clientId, final String clientSecret, final String username, final String password)
      throws Exception {
    String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";
    // @formatter:off
    String content = mockMvc
        .perform(
            post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("grant_type", "password")
                .param("username", username)
                .param("password", password))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(content().string(containsString("access_token")))
        .andExpect(jsonPath("$.access_token", is(notNullValue())))
        .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
        .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
        .andExpect(jsonPath("$.expires_in", is(greaterThan(60))))
        .andReturn()
            .getResponse().getContentAsString();
    // @formatter:on
    return content;
  }

  private String getToken(final String jsonToken, final TokenGroup tokenGroup) {
    Assert.notNull(jsonToken);
    Pattern tokenPattern = Pattern.compile("\"access_token\":\"(.+?)\"[0-9A-Za-z\\-_\":,]+\"refresh_token\":\"(.+?)\"");
    Matcher tokenMatcher = tokenPattern.matcher(jsonToken);
    if (tokenMatcher.find()) {
      return tokenMatcher.group(tokenGroup.value);
    }
    return null;
  }

  /*
   * Brauch' ich das noch?
   */
  private OAuth2Authentication oAuth2Authentication() {
    Map<String, String> requestParameter = new HashMap<>();
    requestParameter.put("grant_type", "password");
    OAuth2Request storedRequest = new OAuth2Request(requestParameter, // requestParameter
        "acme", // clientId
        Collections.<GrantedAuthority> emptyList(), // authorities
        true, // approved
        Collections.<String> emptySet(), // scope
        Collections.<String> emptySet(), // resouceIds
        "", // redirectUri
        Collections.<String> emptySet(), // responseType
        Collections.<String, Serializable> emptyMap() // extensionProperties
    );
    UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken("juergen", "juergen",
        Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")));
    return new OAuth2Authentication(storedRequest, userAuthentication);
  }

}
