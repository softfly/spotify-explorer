package com.spotify.api.v1.search.dto;

public class SpotifySearchResponse {

  private SpotifyAlbums albums;

  public SpotifyAlbums getAlbums() {
    return albums;
  }

  public void setAlbums(SpotifyAlbums albums) {
    this.albums = albums;
  }
}
