package com.hoholms.optimagrowth.license.model.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.ArrayList;

import static java.util.Arrays.asList;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class RestErrorList extends ArrayList<ErrorMessage> {

    @Serial
    private static final long serialVersionUID = -721424777198115589L;
    private final HttpStatus status;

    public RestErrorList(HttpStatus status, ErrorMessage... errors) {
        this(status.value(), errors);
    }

    public RestErrorList(int status, ErrorMessage... errors) {
        super();
        this.status = HttpStatus.valueOf(status);
        addAll(asList(errors));
    }
}