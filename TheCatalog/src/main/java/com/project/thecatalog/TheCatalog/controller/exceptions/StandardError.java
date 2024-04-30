package com.project.thecatalog.TheCatalog.controller.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;


}
