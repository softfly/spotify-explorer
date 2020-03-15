package com.spotify.api.v1.search;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import com.spotify.accounts.api.token.SpotifyInvalidAuthKeysException;
import com.spotify.accounts.api.token.SpotifyToken;
import com.spotify.api.v1.search.dto.SpotifyAlbum;
import com.spotify.api.v1.search.dto.SpotifyArtist;
import com.spotify.api.v1.search.dto.SpotifyImage;
import com.spotify.api.v1.search.dto.SpotifySearchResponse;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@RestClientTest(SpotifySearchClient.class)
public class SpotifySearchClientTests {

  @Autowired
  private SpotifySearchClient client;

  @Autowired
  private MockRestServiceServer server;

  @MockBean
  private SpotifyToken token;

  @Test
  public void Should_GetResponse_When_ClientMakesCorrectCall()
      throws IOException, SpotifyInvalidAuthKeysException {
    this.server.expect(requestTo("https://api.spotify.com/v1/search?q=search&type=album"))
        .andRespond(withSuccess(fileToString("src/test/resources/SpotifySearchResponse.json"),
            MediaType.APPLICATION_JSON));
    asserSearchResponseNotNull(client.search("search"));
  }

  @Test
  public void Should_GetResponse_When_TokenExpired()
      throws IOException, SpotifyInvalidAuthKeysException {
    this.server.expect(requestTo("https://api.spotify.com/v1/search?q=search&type=album"))
        .andRespond(withUnauthorizedRequest());
    this.server.expect(requestTo("https://api.spotify.com/v1/search?q=search&type=album"))
        .andRespond(withSuccess(fileToString("src/test/resources/SpotifySearchResponse.json"),
            MediaType.APPLICATION_JSON));
    asserSearchResponseNotNull(client.search("search"));
  }

  protected void asserSearchResponseNotNull(SpotifySearchResponse search) {
    assertNotNull(search);
    assertNotNull(search.getAlbums());
    assertNotNull(search.getAlbums().getItems());

    SpotifyAlbum album = search.getAlbums().getItems().get(0);
    assertNotNull(album);

    assertNotNull(album.getName());
    assertNotNull(album.getReleaseDate());
    assertNotNull(album.getTotalTracks());

    assertNotNull(album.getArtists().get(0));
    SpotifyArtist artist = album.getArtists().get(0);
    assertNotNull(artist.getName());

    assertNotNull(album.getImages().get(0));
    SpotifyImage image = album.getImages().get(0);
    assertNotNull(image.getUrl());
    assertNotNull(image.getHeight());
    assertNotNull(image.getWidth());
  }

  protected String fileToString(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, StandardCharsets.UTF_8);
  }
}
