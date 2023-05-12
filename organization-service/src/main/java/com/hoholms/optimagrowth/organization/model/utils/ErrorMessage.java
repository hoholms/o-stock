package com.hoholms.optimagrowth.organization.model.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    private String message;
    private String code;
    private String detail;

    public ErrorMessage(String message, String detail) {
        super();
        this.message = message;
        this.detail = detail;
    }
}