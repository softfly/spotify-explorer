package spotify.explorer.rest.dto;

public class ErrorResponse {

  private Integer error;

  private String message;

  public Integer getError() {
    return error;
  }

  public void setError(Integer error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
