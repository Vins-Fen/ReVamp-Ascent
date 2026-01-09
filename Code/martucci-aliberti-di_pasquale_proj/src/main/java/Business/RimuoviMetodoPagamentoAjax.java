package Business;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dataAccess.DAO.MetodiPagamentoDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/RimuoviMetodoPagamentoAjax")
public class RimuoviMetodoPagamentoAjax extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String numCarta = request.getParameter("numcarta");
            String nomeUtente = request.getParameter("nome_utente");

            if (numCarta == null || nomeUtente == null) {
                out.print("{\"success\":false, \"message\":\"Parametri mancanti\"}");
                return;
            }

            MetodiPagamentoDAO dao = new MetodiPagamentoDAO();
            boolean success = dao.doDelete(numCarta, nomeUtente);

            if (success) {
                out.print("{\"success\":true}");
            } else {
                out.print("{\"success\":false, \"message\":\"Impossibile eliminare il metodo\"}");
            }

        } catch (Exception e) {
            out.print("{\"success\":false, \"message\":\"Errore nel server\"}");
            e.printStackTrace();
        }
    }
}