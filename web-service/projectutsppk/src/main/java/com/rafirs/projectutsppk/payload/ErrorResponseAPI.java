package com.rafirs.projectutsppk.payload;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author RafiRS
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseAPI {
    private HttpStatus httpStatus;
    private String message;
    private List<String> errors;
}
