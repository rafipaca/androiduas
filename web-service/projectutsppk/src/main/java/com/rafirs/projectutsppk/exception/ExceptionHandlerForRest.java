
package com.rafirs.projectutsppk.exception;

/**
 *
 * @author Rafi
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import com.rafirs.projectutsppk.payload.ErrorResponseAPI;

/**
 *
 * @author RafiRS
 */

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerForRest {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(ex.getLocalizedMessage())
            .errors(errors)
            .build();
        
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getParameterName() + " tidak boleh kosong");

        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(ex.getLocalizedMessage())
            .errors(errors)
            .build();

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(String.format(
                "%s %s: %s",
                violation.getRootBeanClass().getName(),
                violation.getPropertyPath(),
                violation.getMessage()
            ));
        }

        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(ex.getLocalizedMessage())
            .errors(errors)
            .build();

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(String.format(
            "%s should be of type %s",
            ex.getName(),
            ex.getRequiredType().getName()
        ));

        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(ex.getLocalizedMessage())
            .errors(errors)
            .build();

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<?> handleNoHandlerFound(NoHandlerFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(String.format(
            "No handler found for %s %s",
            ex.getHttpMethod(),
            ex.getRequestURL()
        ));

        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.NOT_FOUND)
            .message(ex.getLocalizedMessage())
            .errors(errors)
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<?> handleAuthentication(AuthenticationException ex) {
        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.UNAUTHORIZED)
            .message(ex.getMessage())
            .errors(Arrays.asList("authentication error"))
            .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.FORBIDDEN)
            .message(ex.getLocalizedMessage())
            .errors(Arrays.asList("Unauthorized"))
            .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<?> handleWrongPassword(WrongPasswordException ex) {
        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.UNAUTHORIZED)
            .message("Wrong password")
            .errors(Arrays.asList("Wrong password"))
            .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.UNAUTHORIZED)
            .message(ex.getLocalizedMessage())
            .errors(Arrays.asList("Wrong email or password"))
            .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> handleAll(Exception ex) {
        ErrorResponseAPI apiError = ErrorResponseAPI.builder()
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(ex.getLocalizedMessage())
            .errors(Arrays.asList("error!"))
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

}
