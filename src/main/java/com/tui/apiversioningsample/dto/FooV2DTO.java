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
public class FooV2DTO implements Serializable {
  public static final String MEDIA_TYPE = "vnd.api-versioning-sample.v2";
  private String text = "Version-2";
}
