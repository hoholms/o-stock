package com.hoholms.optimagrowth.organization.model.utils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(NON_NULL)
@Getter
@Setter
public class ResponseWrapper {

  private Object data;
  private Object metadata;
  private List<ErrorMessage> errors;

  public ResponseWrapper(Object data, Object metadata, List<ErrorMessage> errors) {
    super();
    this.data = data;
    this.metadata = metadata;
    this.errors = errors;
  }
}
