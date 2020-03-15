package com.spotify.accounts.api.token;

public class SpotifyInvalidAuthKeysException extends Exception {

  public SpotifyInvalidAuthKeysException(Throwable cause) {
    super("Invalid Spotify API Authorization. Check the keys.", cause);
}

}
