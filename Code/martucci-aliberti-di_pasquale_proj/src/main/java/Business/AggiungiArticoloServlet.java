package Business;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.DAO.*;
import dataAccess.JavaBeans.*;

@WebServlet(name = "AggiungiArticoloServlet", value = "/AggiungiArticoloServlet")
public class AggiungiArticoloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 1. RECUPERO PARAMETRI (Tutti come Stringa inizialmente)
            String codiceStr = request.getParameter("codice");
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            String colore = request.getParameter("colore");
            String scontoStr = request.getParameter("sconto");
            String prezzoStr = request.getParameter("prezzo");
            String pesoStr = request.getParameter("peso");
            String dimensione = request.getParameter("dimensione");
            String idCatStr = request.getParameter("id_categoria");

            // 2. PARSING E GESTIONE TIPI (Conversione sicura)
            int codice = Integer.parseInt(codiceStr);
            double prezzo = Double.parseDouble(prezzoStr);
            int id_categoria = Integer.parseInt(idCatStr);

            // Gestione campi opzionali (se vuoti, metto valori di default o 0)
            double peso = (pesoStr == null || pesoStr.trim().isEmpty()) ? 0.0 : Double.parseDouble(pesoStr);

            // LOGICA SPECIALE PER LO SCONTO (Per risolvere il problema del DECIMAL(5,4))
            double sconto = 0.0;
            if (scontoStr != null && !scontoStr.trim().isEmpty()) {
                double inputSconto = Double.parseDouble(scontoStr);
                // Se l'utente scrive un numero > 1 (es. "10" o "50"), lo convertiamo in decimale (0.10 o 0.50)
                // Se scrive già in decimale (es. "0.1"), lo lasciamo così.
                if (inputSconto > 1.0) {
                    sconto = inputSconto / 100.0;
                } else {
                    sconto = inputSconto;
                }
            }

            // 3. SANITIZZAZIONE INPUT (Protezione XSS)
            // Impediamo l'inserimento di script malevoli nel nome e descrizione
            if (nome != null) nome = nome.replace("<", "&lt;").replace(">", "&gt;");
            if (descrizione != null) descrizione = descrizione.replace("<", "&lt;").replace(">", "&gt;");
            if (colore != null) colore = colore.replace("<", "&lt;").replace(">", "&gt;");
            if (dimensione != null) dimensione = dimensione.replace("<", "&lt;").replace(">", "&gt;");

            // 4. VALIDAZIONE LOGICA LATO SERVER (Mirror Validation)
            // Se qualcuno disattiva il JS, questi controlli salvano il DB
            if (codice <= 0) {
                lanciaErrore(request, response, "Il codice articolo deve essere positivo.");
                return;
            }
            if (nome == null || nome.trim().length() < 3) {
                lanciaErrore(request, response, "Il nome deve avere almeno 3 caratteri.");
                return;
            }
            if (prezzo <= 0) {
                lanciaErrore(request, response, "Il prezzo deve essere maggiore di zero.");
                return;
            }
            // Controllo che lo sconto convertito non sia fuori scala (max 0.9999 cioè 99.99%)
            if (sconto < 0 || sconto >= 1.0) {
                lanciaErrore(request, response, "Lo sconto non è valido (Deve essere tra 0% e 99%).");
                return;
            }
            if (descrizione==null ||descrizione.isEmpty() ) {
                lanciaErrore(request, response, "Descrizione non inserita");
                return;
            }

            // 5. CONTROLLO DUPLICATI (Integrità DB)
            ArticoloDAO articoloDAO = new ArticoloDAO();
            if(articoloDAO.doRetrieveById(codice) != null){
                lanciaErrore(request, response, "Errore: Esiste già un articolo con il codice " + codice);
                return;
            }

            // 6. CREAZIONE BEAN E SALVATAGGIO
            // Nota: Assicurati che il costruttore del Bean Articolo rispetti quest'ordine
            Articolo articolo = new Articolo(codice, nome, descrizione, colore, sconto, prezzo, peso, dimensione, id_categoria);

            articoloDAO.doSave(articolo);

            // 7. SUCCESSO
            response.sendRedirect("confermaInserimento.jsp");

        } catch (NumberFormatException e) {
            // Gestione errore se l'utente scrive testo al posto dei numeri
            lanciaErrore(request, response, "Errore nel formato dei dati numerici (Prezzo, Codice o Sconto).");
        } catch (Exception e) {
            // Gestione errore generico database
            e.printStackTrace();
            lanciaErrore(request, response, "Errore di sistema: " + e.getMessage());
        }
    }

    // Metodo helper privato per gestire i messaggi di errore senza duplicare codice
    private void lanciaErrore(HttpServletRequest request, HttpServletResponse response, String messaggio) throws ServletException, IOException {
        request.setAttribute("errore", messaggio);
        RequestDispatcher dispatcher = request.getRequestDispatcher("erroreInserimento.jsp");
        dispatcher.forward(request, response);
    }
}