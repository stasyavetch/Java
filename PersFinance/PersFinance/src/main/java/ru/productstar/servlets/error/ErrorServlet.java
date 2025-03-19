package ru.productstar.servlets.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = (String) req.getAttribute("jakarta.servlet.error.message");
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        Throwable exception = (Throwable) req.getAttribute("jakarta.servlet.error.exception");

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        writer.println("<h1>Error Information</h1>");
        if (errorMessage != null) {
            writer.println("<p>Error Message: " + errorMessage + "</p>");
        }
        if (statusCode != null) {
            writer.println("<p>Status Code: " + statusCode + "</p>");
        }
        if (exception != null) {
            writer.println("<p>Exception: " + exception.getMessage() + "</p>");
        }
    }
}
