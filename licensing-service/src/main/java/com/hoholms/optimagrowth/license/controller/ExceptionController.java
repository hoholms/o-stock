package com.hoholms.optimagrowth.license.controller;

import static java.util.Collections.singletonMap;

import com.hoholms.optimagrowth.license.model.utils.ErrorMessage;
import com.hoholms.optimagrowth.license.model.utils.ResponseWrapper;
import com.hoholms.optimagrowth.license.model.utils.RestErrorList;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@EnableWebMvc
public class ExceptionController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public @ResponseBody ResponseEntity<ResponseWrapper> handleException(
      HttpServletRequest request, ResponseWrapper responseWrapper) {
    return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ResponseWrapper> handleIOException(
      HttpServletRequest request, RuntimeException e) {
    RestErrorList errorList =
        new RestErrorList(
            HttpStatus.NOT_ACCEPTABLE, new ErrorMessage(e.getMessage(), e.getMessage()));
    ResponseWrapper responseWrapper =
        new ResponseWrapper(null, singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorList);

    return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
