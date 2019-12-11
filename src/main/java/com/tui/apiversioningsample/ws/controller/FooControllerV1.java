package com.tui.apiversioningsample.ws.controller;

import com.tui.apiversioningsample.dto.FooV1DTO;
import com.tui.apiversioningsample.dto.FooV2DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(
    value = "Foo Entity",
    tags = {"Foo Entity"}
)
public class FooControllerV1 extends FooController {

  /**
   * Get a foo
   *
   * @return ResponseEntity {@link ResponseEntity}
   */
  @ApiOperation(
      tags = "Foo Entity",
      value = "Get a foo",
      notes = "Get a foo"
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
  @ApiOperation(
      tags = "Foo Entity",
      value = "Get a foo",
      notes = "Get a foo"
  )
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
