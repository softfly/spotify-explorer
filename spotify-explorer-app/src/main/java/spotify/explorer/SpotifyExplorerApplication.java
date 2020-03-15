package spotify.explorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.spotify", "spotify.explorer"})
@EnableCaching
public class SpotifyExplorerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpotifyExplorerApplication.class, args);
  }
}
