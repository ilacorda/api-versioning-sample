package com.tui.apiversioningsample.ws.controller;

import com.tui.apiversioningsample.dto.FooV1DTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author abujosa
 * @version 1.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@RestController
@RequestMapping(value = "/", headers = "Accept-Version=" + FooV1DTO.MEDIA_TYPE)
@Tag(
    name = "Foo Entity",
    description = "Foo operations"
)
public class FooControllerV1 extends FooController {

  /**
   * Get a foo
   *
   * @return ResponseEntity {@link ResponseEntity}
   */
  @Operation(
      tags = "Foo Entity",
      description = "Get a foo"
  )
  @GetMapping(
      path = "/foos/",
      produces = {
          MediaType.APPLICATION_JSON_VALUE
      }
  )
  public ResponseEntity<?> retrieve() {
    return new ResponseEntity<>(new FooV1DTO(), HttpStatus.OK);
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
    return Flux.just(new FooV1DTO());
  }
}
