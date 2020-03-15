package com.spotify.api.v1.search.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifyAlbum {

  private String name;

  @JsonProperty("release_date")
  private String releaseDate;

  @JsonProperty("total_tracks")
  private Integer totalTracks;

  private List<SpotifyArtist> artists;

  private List<SpotifyImage> images;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Integer getTotalTracks() {
    return totalTracks;
  }

  public void setTotalTracks(Integer totalTracks) {
    this.totalTracks = totalTracks;
  }

  public List<SpotifyArtist> getArtists() {
    return artists;
  }

  public void setArtists(List<SpotifyArtist> artists) {
    this.artists = artists;
  }

  public List<SpotifyImage> getImages() {
    return images;
  }

  public void setImages(List<SpotifyImage> images) {
    this.images = images;
  }
}
