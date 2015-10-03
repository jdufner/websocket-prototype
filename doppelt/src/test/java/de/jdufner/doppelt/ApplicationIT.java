package de.jdufner.doppelt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jürgen
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
//@ActiveProfiles(value = { "postgresql" })
public class ApplicationIT {

  @Test
  public void test() {
    OAuth2AccessToken token = authenticate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token.getValue());
    HttpEntity<String> entity = new HttpEntity<String>("", headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/greeting", HttpMethod.GET, entity,
        String.class);
    System.out.println(response.getBody());
  }

  private OAuth2AccessToken authenticate() {
    ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
    resource.setAccessTokenUri("http://localhost:8080/oauth/token");
    resource.setClientId("acme");
    resource.setClientSecret("acmesecret");
    resource.setGrantType("password");
    resource.setUsername("juergen");
    resource.setPassword("juergen");
    //resource.setScope(Arrays.asList("read"));
    OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resource,
        new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
    OAuth2AccessToken token = oAuth2RestTemplate.getAccessToken();
    return token;
  }

}
