package com.tui.apiversioningsample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

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
@Data
@Schema(name = "Foo", description = "FooDTO")
public class FooV1DTO implements Serializable {
  public static final String MEDIA_TYPE = "vnd.api-versioning-sample.v1";

  private String text = "Version-1";
}
