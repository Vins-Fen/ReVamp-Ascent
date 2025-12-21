package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import model.JavaBeans.*;
import model.DAO.*;
import java.util.*;

@WebServlet(name = "RecensioneServlet", value = "/RecensioneServlet")
public class RecensioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession(false) == null || request.getSession(false).getAttribute("utenteLoggato") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            if (request.getParameter("testo") == null || request.getParameter("testo").trim().isEmpty() ||
                    request.getParameter("valutazione") == null || request.getParameter("titolo") == null) {
                response.sendRedirect("dettaglioArticolo.jsp?id=" + request.getParameter("idArticolo") + "&error=datiMancanti");
                return;
            }

            try {
                new RecensioneDAO().doSave(
                        new Recensione(
                                0,
                                Integer.parseInt(request.getParameter("valutazione")),
                                request.getParameter("titolo").trim(),
                                request.getParameter("testo").trim(),
                                new GregorianCalendar(),
                                Integer.parseInt(request.getParameter("idArticolo")),
                                ((Cliente) request.getSession(false).getAttribute("utenteLoggato")).getNomeUtente()
                        )
                );

                response.sendRedirect("DettaglioArticoloServlet?codice=" + request.getParameter("idArticolo"));

            } catch (Exception e) {
                response.sendRedirect("DettaglioArticoloServlet?codice=" + request.getParameter("idArticolo"));
            }
        }
    }

