package org.owasp.pangloss.infra.controller;

import com.ctc.wstx.exc.WstxParsingException;
import org.owasp.pangloss.domain.item.NoItemsFoundForThisCategoryException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@RestControllerAdvice
public class PanglossRestControllerAdvice {

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
}
