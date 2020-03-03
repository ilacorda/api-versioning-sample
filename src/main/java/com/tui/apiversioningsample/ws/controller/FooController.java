package com.tui.apiversioningsample.ws.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

/**
 * Base controller that returns Bad Request exceptions if the accept-version header is not set with a valid value
 *
 * @author abujosa
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@RestController
public class FooController {


  /**
   * Get a foo
   *
   * @return ResponseEntity {@link ResponseEntity}
   */
  @GetMapping(
      path = "/foos/",
      produces = {
          MediaType.APPLICATION_JSON_VALUE
      }
  )
  public ResponseEntity<?> retrieve() {
    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Bad accept-version");
  }

  /**
   * Get a foo
   *
   * @return ResponseEntity {@link ResponseEntity}
   */
  @GetMapping(
      path = "/foos/",
      produces = {
          MediaType.APPLICATION_STREAM_JSON_VALUE
      }
  )
  public Flux<?> retrieveReactive() {
    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Bad accept-version");
  }
}
