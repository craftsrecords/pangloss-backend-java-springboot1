package org.owasp.pangloss.infra.controller;

import org.owasp.pangloss.domain.item.NoItemsFoundForThisCategoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class PanglossRestControllerAdvice {

    @ExceptionHandler(NoItemsFoundForThisCategoryException.class)
    public void handleNoItemsFoundForThisCategoryException(HttpServletResponse response, NoItemsFoundForThisCategoryException e) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handle(Exception e) {
        e.printStackTrace();
    }
}
