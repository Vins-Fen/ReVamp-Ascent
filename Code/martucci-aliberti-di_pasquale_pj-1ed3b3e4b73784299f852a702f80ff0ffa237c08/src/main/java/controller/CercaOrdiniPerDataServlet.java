package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeParseException;

import model.JavaBeans.*;
import model.DAO.*;

@WebServlet(name = "CercaOrdiniPerDataServlet", value = "/CercaOrdiniPerDataServlet")
public class CercaOrdiniPerDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect("login.jsp?");
            return;
        }

        String data1Str = request.getParameter("data1");
        String data2Str = request.getParameter("data2");

        if (data1Str != null && !data1Str.isEmpty() && data2Str != null && !data2Str.isEmpty()) {
            try {
                // Converti le stringhe in java.sql.Date
                java.sql.Date sqlData1 = java.sql.Date.valueOf(data1Str);
                java.sql.Date sqlData2 = java.sql.Date.valueOf(data2Str);

                // Converti in GregorianCalendar
                GregorianCalendar gc1 = new GregorianCalendar();
                GregorianCalendar gc2 = new GregorianCalendar();
                gc1.setTime(sqlData1);
                gc2.setTime(sqlData2);

                OrdineDAO ordineDAO = new OrdineDAO();
                List<Ordine> ordini = ordineDAO.doRetrieveByDate(gc1, gc2);

                request.setAttribute("ordini", ordini);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("ordini.jsp");
        dispatcher.forward(request, response);
    }

        @Override
        protected void doPost (HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
            doGet(request, response);
        }
    }
