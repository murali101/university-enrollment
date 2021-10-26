package com.university.enrollment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;

/**
 * Exception handler to send custom messages to client
 */

@Slf4j
@ControllerAdvice
public class UniversityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(StudentClassNotFoundException.class)
    public ResponseEntity handleClassNotFoundException(Exception e, Locale locale) {
        String httpCode = messageSource.getMessage("university.classNotFound.errorCode", new Object[]{},locale);
        String errorMessage = messageSource.getMessage("university.classNotFound.message", new Object[]{},locale);
        return new ResponseEntity<>(
                new ErrorMessage(Integer.parseInt(httpCode), errorMessage),
                HttpStatus.valueOf(Integer.parseInt(messageSource.getMessage("university.classNotFound.httpCode", new Object[]{},locale))));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity handleUserExistsException(Exception e, Locale locale) {
        String httpCode = messageSource.getMessage("university.userExists.errorCode", new Object[]{},locale);
        String errorMessage = messageSource.getMessage("university.userExists.message", new Object[]{},locale);
        return new ResponseEntity<>(
                new ErrorMessage(Integer.parseInt(httpCode), errorMessage),
                HttpStatus.valueOf(Integer.parseInt(messageSource.getMessage("university.classNotFound.httpCode", new Object[]{},locale))));
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity handleUStudentNotFoundException(Exception e, Locale locale) {
        String httpCode = messageSource.getMessage("university.userNotFound.errorCode", new Object[]{},locale);
        String errorMessage = messageSource.getMessage("university.userNotFound.message", new Object[]{},locale);
        return new ResponseEntity<>(
                new ErrorMessage(Integer.parseInt(httpCode), errorMessage),
                HttpStatus.valueOf(Integer.parseInt(messageSource.getMessage("university.userNotFound.httpCode", new Object[]{},locale))));
    }

    @ExceptionHandler(SemesterNotFoundException.class)
    public ResponseEntity handleSemesterNotFoundException(Exception e, Locale locale) {
        String httpCode = messageSource.getMessage("university.semesterNotFound.errorCode", new Object[]{},locale);
        String errorMessage = messageSource.getMessage("university.semesterNotFound.message", new Object[]{},locale);
        return new ResponseEntity<>(
                new ErrorMessage(Integer.parseInt(httpCode), errorMessage),
                HttpStatus.valueOf(Integer.parseInt(messageSource.getMessage("university.semesterNotFound.httpCode", new Object[]{},locale))));
    }

    @ExceptionHandler(MaxCreditsException.class)
    public ResponseEntity handleMaxCreditsException(Exception e, Locale locale) {
        String httpCode = messageSource.getMessage("university.maxCredits.errorCode", new Object[]{},locale);
        String errorMessage = messageSource.getMessage("university.maxCredits.message", new Object[]{},locale);
        return new ResponseEntity<>(
                new ErrorMessage(Integer.parseInt(httpCode), errorMessage),
                HttpStatus.valueOf(Integer.parseInt(messageSource.getMessage("university.maxCredits.httpCode", new Object[]{},locale))));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
