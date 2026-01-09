package Business;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.DAO.*;
import dataAccess.JavaBeans.*;
import java.util.*;

@WebServlet(name = "CarrelloServlet", value = "/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cliente utente = (Cliente) session.getAttribute("utenteLoggato");

        String idArticoloStr = request.getParameter("idArticolo");
        String quantitaStr = request.getParameter("quantita");

        if (idArticoloStr == null || quantitaStr == null) {
            response.sendRedirect("errore.jsp");
            return;
        }

        int codiceArticolo = Integer.parseInt(idArticoloStr);
        int quantita = Integer.parseInt(quantitaStr);

        if (utente != null) {
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            carrelloDAO.aggiungiAlCarrello(utente.getNomeUtente(), codiceArticolo, quantita);
        } else {
            List<Carrello> carrello = (List<Carrello>) session.getAttribute("carrelloAnonimo");
            if (carrello == null) {
                carrello = new ArrayList<>();
            }

            boolean trovato = false;
            for (Carrello item : carrello) {
                if (item.getCodiceArticolo() == codiceArticolo) {
                    item.setQuantita(item.getQuantita() + quantita);
                    trovato = true;
                    break;
                }
            }

            if (!trovato) {
                carrello.add(new Carrello(0, null, codiceArticolo, quantita));
            }

            session.setAttribute("carrelloAnonimo", carrello);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":\"ok\"}");
    }
}