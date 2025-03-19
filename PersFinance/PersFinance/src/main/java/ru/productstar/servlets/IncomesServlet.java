package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/incomes/add")
public class IncomesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();

        var session = req.getSession(false);
        if (session == null) {
            resp.getWriter().println("Not authorized");
            return;
        }

        int freeMoney = (int)context.getAttribute("freeMoney");
        var incomes = new ArrayList<Transaction>((List)context.getAttribute("incomes"));
        var transactions = new ArrayList<Transaction>((List)context.getAttribute("transactions"));

        for(var k : req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k));
            freeMoney += value;
            incomes.add(new Transaction(k, value));
            transactions.add(new Transaction(k, value));
        }

        context.setAttribute("incomes", incomes);
        context.setAttribute("freeMoney", freeMoney);
        context.setAttribute("transactions", transactions);
        resp.getWriter().println("Incomes were added");
        resp.sendRedirect("/summary");
    }
}
