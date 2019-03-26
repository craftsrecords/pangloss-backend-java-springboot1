package org.owasp.pangloss.infra.controllers;

import com.ctc.wstx.exc.WstxParsingException;
import org.owasp.pangloss.domain.item.NoItemsFoundForThisCategoryException;
import org.owasp.pangloss.domain.item.UnknownItemIdException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class PanglossRestControllerAdvice {

    @ExceptionHandler(UnknownItemIdException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleUnknownItemIdException() {
    }

    @ExceptionHandler(NoItemsFoundForThisCategoryException.class)
    public void handleNoItemsFoundForThisCategoryException(HttpServletResponse response, NoItemsFoundForThisCategoryException e) throws IOException {
        response.sendError(SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpServletResponse response, HttpMessageNotReadableException e) throws Exception {
        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof WstxParsingException && rootCause.getMessage().contains("Undeclared general entity")) {
            response.sendError(SC_FORBIDDEN);
        } else {
            response.sendError(SC_BAD_REQUEST, "Unable to process the request");
        }
    }

    @ExceptionHandler(TransactionSystemException.class)
    public void handleTransactionSystemException(HttpServletResponse response, TransactionSystemException e) throws IOException {
        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof ConstraintViolationException) {
            response.sendError(SC_BAD_REQUEST, "Forbidden HTML characters detected");
        } else {
            response.sendError(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
