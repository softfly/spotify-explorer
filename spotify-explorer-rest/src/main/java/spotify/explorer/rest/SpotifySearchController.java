package spotify.explorer.rest;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import com.spotify.accounts.api.token.SpotifyInvalidAuthKeysException;
import com.spotify.api.v1.search.SpotifySearchClient;
import com.spotify.api.v1.search.dto.SpotifySearchResponse;
import spotify.explorer.rest.dto.ErrorResponse;

@RestController
@RequestMapping("api/spotify/v1/")
@Validated
public class SpotifySearchController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpotifySearchController.class);

  @Autowired
  private SpotifySearchClient searchClient;

  @GetMapping("/search")
  @ResponseBody
  @CrossOrigin(origins = "http://localhost:3000")
  public SpotifySearchResponse search(@RequestParam("q") @NotBlank @Size(min = 1) String q)
      throws SpotifyInvalidAuthKeysException {
    return searchClient.search(q);
  }

  /**
   * Missing {@link@RequestParam} in the request
   */
  @ExceptionHandler({MissingServletRequestParameterException.class,
      ConstraintViolationException.class})
  protected ResponseEntity<ErrorResponse> handleRequestParameterException(Exception ex) {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(createErrorResponse(ex.getMessage(), httpStatus), new HttpHeaders(),
        httpStatus);
  }

  /**
   * Incorrect auth credentials for Spotify API
   */
  @ExceptionHandler(SpotifyInvalidAuthKeysException.class)
  protected ResponseEntity<ErrorResponse> handleSpotifyInvalidAuthKeys(Exception ex) {
    LOGGER.error(ex.getMessage(), ex);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    return new ResponseEntity<>(createErrorResponse(ex.getMessage(), httpStatus), new HttpHeaders(),
        httpStatus);
  }

  /**
   * No connection with Spotify API
   */
  @ExceptionHandler(ResourceAccessException.class)
  protected ResponseEntity<ErrorResponse> handleResourceAccessException(Exception ex) {
    LOGGER.error(ex.getMessage(), ex);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    return new ResponseEntity<>(createErrorResponse("Spotify API unreachable.", httpStatus),
        new HttpHeaders(), httpStatus);
  }

  /**
   * Unknown error that should not occur.
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
    LOGGER.error(ex.getMessage(), ex);
    HttpStatus httpStatus = HttpStatus.FORBIDDEN;
    return new ResponseEntity<>(
        createErrorResponse("Internal Server Error. Contact the administrator.", httpStatus),
        new HttpHeaders(), httpStatus);
  }

  protected ErrorResponse createErrorResponse(String message, HttpStatus httpStatus) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(httpStatus.value());
    errorResponse.setMessage(message);
    return errorResponse;
  }
}
