package Business;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.JavaBeans.*;
import dataAccess.DAO.*;
import java.util.*;

@WebServlet(name = "ConfermaOrdineServlet", value = "/ConfermaOrdineServlet")
public class ConfermaOrdineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cliente utente = (Cliente) session.getAttribute("utenteLoggato");

        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Recupera metodo selezionato dal form
        String numCarta = request.getParameter("metodoSelezionato");
        if (numCarta == null || numCarta.isEmpty()) {
            response.sendRedirect("riepilogo.jsp");
            return;
        }

        MetodiPagamentoDAO metodoDAO = new MetodiPagamentoDAO();
        List<MetodiPagamento> metodi = metodoDAO.doRetrieveByUser(utente.getNomeUtente());
        MetodiPagamento metodoPreferito = null;

        for (MetodiPagamento m : metodi) {
            if (m.getNumCarta().equals(numCarta)) {
                metodoPreferito = m;
                break;
            }
        }

        IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
        Indirizzo indirizzo = indirizzoDAO.getPreferito(utente.getNomeUtente());

        if (metodoPreferito == null || indirizzo == null) {
            response.sendRedirect("riepilogo.jsp");
            return;
        }

        CarrelloDAO carrelloDAO = new CarrelloDAO();
        ArticoloDAO articoloDAO = new ArticoloDAO();
        OrdineDAO ordineDAO = new OrdineDAO();
        ContenimentoDAO contenimentoDAO = new ContenimentoDAO();

        List<Carrello> carrello = carrelloDAO.getCarrelloByUtente(utente.getNomeUtente());
        int numArticoli = 0;
        double totale = 0.0;

        for (Carrello item : carrello) {
            Articolo art = articoloDAO.doRetrieveById(item.getCodiceArticolo());
            if (art != null) {
                double prezzoFinale = art.getPrezzo() * (1 - art.getSconto());
                totale += prezzoFinale * item.getQuantita();
                numArticoli += item.getQuantita();
            }
        }

        Ordine ordine = new Ordine(
                0,
                numArticoli,
                new GregorianCalendar(),
                totale,
                indirizzo.getId_indirizzo(),
                utente.getNomeUtente()
        );
        ordineDAO.doSave(ordine);
        int idOrdine = ordine.getId_ordine();

        // Inserimento in tabella Contenimento (snapshot)
        for (Carrello item : carrello) {
            Articolo art = articoloDAO.doRetrieveById(item.getCodiceArticolo());
            if (art != null) {
                Contenimento contenimento = new Contenimento(
                        item.getCodiceArticolo(),
                        idOrdine,
                        item.getQuantita(),
                        art.getNome(),
                        art.getPrezzo(),
                        art.getSconto()
                );
                contenimentoDAO.doSave(contenimento);
            }
        }

        carrelloDAO.pulisciCarrello(utente.getNomeUtente());

        // Imposta dati per conferma
        session.setAttribute("totaleOrdine", totale);
        session.setAttribute("idUltimoOrdine", idOrdine);
        session.setAttribute("indirizzoSpedizione", indirizzo);

        response.sendRedirect("checkout.jsp");
    }
}
