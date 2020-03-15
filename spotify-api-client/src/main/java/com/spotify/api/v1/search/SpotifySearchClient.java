package com.spotify.api.v1.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.spotify.accounts.api.token.SpotifyInvalidAuthKeysException;
import com.spotify.accounts.api.token.SpotifyToken;
import com.spotify.api.v1.search.dto.SpotifySearchResponse;

/**
 * @see https://developer.spotify.com/documentation/web-api/reference/search/search/
 */
@Component
public class SpotifySearchClient {

  private RestTemplate restTemplate;

  @Autowired
  private SpotifyToken token;

  public SpotifySearchClient(RestTemplateBuilder restTemplateBuilder) {
    restTemplate = restTemplateBuilder.build();
  }

  public SpotifySearchResponse search(@RequestParam(required = true) String q)
      throws SpotifyInvalidAuthKeysException {
    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")//
            .queryParam("q", q)//
            .queryParam("type", "album");
    String url = builder.build(false).toUriString();
    try {
      HttpEntity<String> entity = new HttpEntity<>(getAuthHeader(token.getAccessToken()));
      return restTemplate.exchange(url, HttpMethod.GET, entity, SpotifySearchResponse.class)
          .getBody();
    } catch (HttpClientErrorException e) {
      if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())) {
        token.refreshAccessToken();
        HttpEntity<String> entity = new HttpEntity<>(getAuthHeader(token.getAccessToken()));
        return restTemplate.exchange(url, HttpMethod.GET, entity, SpotifySearchResponse.class)
            .getBody();
      } else {
        throw e;
      }
    }
  }

  protected HttpHeaders getAuthHeader(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + token);
    return headers;
  }
}
