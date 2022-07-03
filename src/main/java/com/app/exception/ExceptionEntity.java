package com.app.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionEntity {

    @JsonProperty(value = "error")
    private String errorName;

    @JsonProperty(value = "message")
    private String message;
}
