package io.github.vonner04.contact_game.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoomNotFoundException(RoomNotFoundException ex) {
        ErrorResponse errResponse = new ErrorResponse(
                404,
                ex.getMessage(),
                "The requested room was not found.",
                LocalDateTime.now());
        return new ResponseEntity<>(errResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomFullException.class)
    public ResponseEntity<ErrorResponse> handleRoomFulLException(RoomFullException ex) {
        ErrorResponse errResponse = new ErrorResponse(
                409,
                ex.getMessage(),
                "The requested room was full",
                LocalDateTime.now());
        return new ResponseEntity<>(errResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errResponse = new ErrorResponse(
                500,
                "An unexpected error occurred.",
                ex.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
