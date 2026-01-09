package Business;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.JavaBeans.*;
import dataAccess.DAO.*;
import java.util.*;

@WebServlet(name = "RecensioneServlet", value = "/RecensioneServlet")
public class RecensioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Controllo Login
        if (request.getSession(false) == null || request.getSession(false).getAttribute("utenteLoggato") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String testo = request.getParameter("testo");
        String titolo = request.getParameter("titolo");
        String valStr = request.getParameter("valutazione");
        String idArtStr = request.getParameter("idArticolo");

        // 2. Controllo Campi Vuoti (Required)
        if (testo == null || testo.trim().isEmpty() ||
                titolo == null || titolo.trim().isEmpty() ||
                valStr == null || idArtStr == null) {
            response.sendRedirect("DettaglioArticoloServlet?codice=" + idArtStr + "&error=datiMancanti");
            return;
        }

        try {
            int valutazione = Integer.parseInt(valStr);
            int idArticolo = Integer.parseInt(idArtStr);

            // 3. Controllo Lunghezza (Importante per il Database!)
            // Verifica se nel tuo DB i campi sono VARCHAR(X). Se sì, adatta questi numeri.
            if (titolo.length() > 50) {
                response.sendRedirect("DettaglioArticoloServlet?codice=" + idArtStr + "&error=titoloTroppoLungo");
                return;
            }

            if (testo.length() > 500) {
                response.sendRedirect("DettaglioArticoloServlet?codice=" + idArtStr + "&error=testoTroppoLungo");
                return;
            }

            // Salvataggio
            new RecensioneDAO().doSave(
                    new Recensione(
                            0,
                            valutazione,
                            titolo.trim(),
                            testo.trim(),
                            new GregorianCalendar(),
                            idArticolo,
                            ((Cliente) request.getSession(false).getAttribute("utenteLoggato")).getNomeUtente()
                    )
            );

            response.sendRedirect("DettaglioArticoloServlet?codice=" + idArticolo);

        } catch (Exception e) {
            // Gestione errori generici o di parsing
            // Se idArtStr è null o non parsabile, rimanda alla home o gestisci l'errore
            String redirectUrl = (idArtStr != null) ? "DettaglioArticoloServlet?codice=" + idArtStr : "index.jsp";
            response.sendRedirect(redirectUrl + "&error=generico");
        }
    }
}