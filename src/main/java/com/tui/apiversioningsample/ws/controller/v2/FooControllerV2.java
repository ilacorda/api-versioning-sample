package com.tui.apiversioningsample.ws.controller.v2;

import com.tui.apiversioningsample.dto.FooV2DTO;
import com.tui.apiversioningsample.ws.controller.v1.FooControllerV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/", headers = "Accept-Version=" + FooV2DTO.MEDIA_TYPE)
public class FooControllerV2 extends FooControllerV1 {


  /**
   * Get a foo
   *
   * @return ResponseEntity {@link ResponseEntity}
   */
  @Operation(
      tags = "Foo Entity",
      description = "Get a foos",
      parameters = {@Parameter(name = "accept-version", required = true, in = ParameterIn.HEADER,
          schema = @Schema(name = "accept-version", type = "string", allowableValues = {FooV2DTO.MEDIA_TYPE}))}
  )
  @GetMapping(
      path = "/foos/",
      produces = {
          MediaType.APPLICATION_JSON_VALUE
      }
  )
  @Override
  public ResponseEntity<?> retrieve() {
    return new ResponseEntity<>(new FooV2DTO(), HttpStatus.OK);
  }
}
