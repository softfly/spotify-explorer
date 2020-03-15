package com.spotify.accounts.api.token;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @see https://developer.spotify.com/documentation/general/guides/authorization-guide/ 2. Have your
 *      application request refresh and access tokens; Spotify returns access and refresh tokens
 * @see https://tools.ietf.org/html/rfc6749#section-4.4
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpotifyOnlyAppToken implements SpotifyToken {

  @Value("${SPOTIFY_CLIENT_ID}")
  private String clientId;

  @Value("${SPOTIFY_CLIENT_SECRET}")
  private String clientSecret;

  private RestTemplate restTemplate;

  public SpotifyOnlyAppToken(RestTemplateBuilder restTemplateBuilder) {
    restTemplate = restTemplateBuilder.build();
  }

  @CacheEvict(value = "SpotifyOnlyAppTokenCache", allEntries = true)
  @Override
  public void refreshAccessToken() {}

  @Cacheable("SpotifyOnlyAppTokenCache")
  @Override
  public String getAccessToken() throws SpotifyInvalidAuthKeysException {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "client_credentials");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Authorization", getBasicAuthorization(clientId, clientSecret));

    try {
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
      ResponseEntity<TokenResponse> output = restTemplate.exchange(
          "https://accounts.spotify.com/api/token", HttpMethod.POST, request, TokenResponse.class);
      return output.getBody().getAccessToken();
    } catch (HttpClientErrorException e) {
      throw new SpotifyInvalidAuthKeysException(e);
    }
  }

  protected String getBasicAuthorization(String username, String password) {
    String auth = username + ":" + password;
    return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
  }
}
