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
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RequestMapping(value = "/")
@Api(
    value = "Foo Entity",
    tags = {"Foo Entity"}
)
public class FooController {

  // If you want to be able to select the version in the swagger you have to define the Allowed versions & add it inside the method with the ApiImplicitParams
  //@ApiImplicitParams({
  //    @ApiImplicitParam(name = "Accept-Version", allowableValues = allowedVersions, required = true, dataType = "String", paramType = "header")
  //})
  private static final String allowedVersions = FooV1DTO.MEDIA_TYPE + "," + FooV2DTO.MEDIA_TYPE;

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
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Accept-Version", allowableValues = allowedVersions, required = true, dataType = "String", paramType = "header")
  })
  public ResponseEntity<?> retrieve() {
    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Bad accept-version");
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
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Accept-Version", allowableValues = allowedVersions, required = true, dataType = "String", paramType = "header")
  })
  public Flux<?> retrieveReactive() {
    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Bad accept-version");
  }
}
