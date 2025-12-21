package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.DAO.*;
import model.JavaBeans.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;

@WebServlet("/InserisciMetodoPagamentoAjax")
public class InserisciMetodoPagamentoAjax extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        MetodiPagamentoDAO dao = new MetodiPagamentoDAO();


        try {
            // Recupera parametri
            String numCarta = request.getParameter("numcarta");
            String scadenza = request.getParameter("scadenza");
            String intestatario = request.getParameter("proprietario");
            String nomeUtente = request.getParameter("nome_utente");

            // Validazione base
            if (numCarta == null || scadenza == null || intestatario == null || nomeUtente == null) {
                out.print("{\"success\":false, \"message\":\"Parametri mancanti\"}");
                return;
            }

            if (dao.esisteMetodoPagamento(numCarta, nomeUtente)) {
                out.print("{\"success\":false, \"message\":\"Hai già registrato questa carta\"}");
                return;
            }


            // Parsing scadenza (MM/YY)
            String[] parts = scadenza.split("/");
            if (parts.length != 2) {
                out.print("{\"success\":false, \"message\":\"Formato scadenza non valido (usa MM/YY)\"}");
                return;
            }

            int mese, anno;
            try {
                mese = Integer.parseInt(parts[0]) - 1; // -1 perché Calendar.MONTH è 0-based
                anno = 2000 + Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                out.print("{\"success\":false, \"message\":\"Data non valida\"}");
                return;
            }

            GregorianCalendar dataScadenza = new GregorianCalendar(anno, mese, 1);

            MetodiPagamento metodo = new MetodiPagamento(numCarta, intestatario, dataScadenza, nomeUtente);
            boolean success = dao.doSave(metodo);

            if (success) {
                out.print("{\"success\":true}");
            } else {
                out.print("{\"success\":false, \"message\":\"Impossibile salvare il metodo di pagamento\"}");
            }

        } catch (Exception e) {
            out.print("{\"success\":false, \"message\":\"Errore nel server\"}");
            e.printStackTrace();
        }
    }
}
