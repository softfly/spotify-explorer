package com.spotify.accounts.api.token;

public interface SpotifyToken {

  void refreshAccessToken();

  String getAccessToken() throws SpotifyInvalidAuthKeysException;

}
