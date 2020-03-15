package com.spotify.accounts.api.token;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response from https://accounts.spotify.com/api/token
 *
 */
public class TokenResponse {

  @JsonProperty("access_token")
  String accessToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
