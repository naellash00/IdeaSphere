package com.example.ideasphere.ControllerAdvise;

import com.example.ideasphere.ApiResponse.ApiException;
import com.example.ideasphere.ApiResponse.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse> ApiException(ApiException e) {

        String message = e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> MethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String message = e.getFieldError().getDefaultMessage();


        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> HttpMessageNotReadableException(HttpMessageNotReadableException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> NoResourceFoundException(NoResourceFoundException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> DataIntegrityViolationException(DataIntegrityViolationException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = CannotCreateTransactionException.class)
    public ResponseEntity<ApiResponse> CannotCreateTransactionException(CannotCreateTransactionException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = MissingPathVariableException.class)
    public ResponseEntity<ApiResponse> MissingPathVariableException(MissingPathVariableException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> ConstraintViolationException(ConstraintViolationException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> RuntimeException(RuntimeException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> MissingServletRequestParameterException(MissingServletRequestParameterException e) {

        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }


    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<ApiResponse> ServletRequestBindingException(ServletRequestBindingException e) {
        String message = e.getMessage();

        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
}
