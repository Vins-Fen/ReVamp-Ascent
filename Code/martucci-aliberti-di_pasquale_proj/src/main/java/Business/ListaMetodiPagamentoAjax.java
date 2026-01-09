package Business;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dataAccess.DAO.MetodiPagamentoDAO;
import dataAccess.JavaBeans.*;

import java.io.IOException;

@WebServlet("/ListaMetodiPagamentoAjax")
public class ListaMetodiPagamentoAjax extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomeUtente = request.getParameter("nome_utente");
        if (nomeUtente == null) {
            Cliente u = (Cliente) request.getSession().getAttribute("utenteLoggato");
            if (u != null) {
                nomeUtente = u.getNomeUtente();
            }
        }

        if (nomeUtente != null) {
            MetodiPagamentoDAO dao = new MetodiPagamentoDAO();
            request.setAttribute("metodiPagamento", dao.doRetrieveByUser(nomeUtente));
        }

        request.getRequestDispatcher("listaMetodiPagamento.jsp").forward(request, response);
    }
}