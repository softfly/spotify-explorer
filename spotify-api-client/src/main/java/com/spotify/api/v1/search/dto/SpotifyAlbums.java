package com.spotify.api.v1.search.dto;

import java.util.List;

public class SpotifyAlbums {

  private List<SpotifyAlbum> items;

  public List<SpotifyAlbum> getItems() {
    return items;
  }

  public void setItems(List<SpotifyAlbum> items) {
    this.items = items;
  }
}
