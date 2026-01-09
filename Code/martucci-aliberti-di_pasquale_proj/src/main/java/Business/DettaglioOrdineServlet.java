package Business;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;
import dataAccess.ConPool;

@WebServlet(name = "DettaglioOrdineServlet", value = "/DettaglioOrdineServlet")
public class DettaglioOrdineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection con = ConPool.getConnection()) {
            int idOrdine = Integer.parseInt(idParam);

            PreparedStatement ps = con.prepareStatement("""
               SELECT c.nome_articolo AS nome, c.codice, c.prezzo_unitario AS prezzo, COALESCE(c.sconto, 0) AS sconto, c.quantita,
               ia.url AS immagine
               FROM Contenimento c
               LEFT JOIN ImmagineArticolo ia ON ia.codice_articolo = c.codice AND ia.is_principale = TRUE
               WHERE c.id_ordine = ?
                                     
            """);

            ps.setInt(1, idOrdine);
            ResultSet rs = ps.executeQuery();

            boolean trovato = false;
            double sommaTotale = 0.0;

            out.println("<div class='dettagli-container'>");

            while (rs.next()) {
                trovato = true;

                String nome = rs.getString("nome");
                int codice = rs.getInt("codice");
                double prezzo = rs.getDouble("prezzo");
                double sconto = rs.getDouble("sconto");
                int quantita = rs.getInt("quantita");
                String immagine = rs.getString("immagine") != null ? rs.getString("immagine") : "img/default.jpg";

                double prezzoFinale = Math.round(prezzo * (1 - sconto) * 100.0) / 100.0;
                double totale = Math.round(prezzoFinale * quantita * 100.0) / 100.0;
                sommaTotale += totale;

                out.println("<div class='riga-articolo'>");
                out.println("<img src='" + immagine + "' alt='immagine' class='miniatura'>");
                out.println("<div>");
                out.println("<strong>" + nome + "</strong><br>");
                out.println("Quantità: " + quantita + "<br>");
                out.println("Prezzo unitario: €" + String.format("%.2f", prezzoFinale) + "<br>");
                out.println("Totale: €" + String.format("%.2f", totale));
                out.println("</div>");
                out.println("</div>");
            }

            if (trovato) {
                out.println("<hr style='margin-top:10px;'>");
                out.println("<div style='text-align:right; font-weight:bold;'>Totale ordine: €" + String.format("%.2f", sommaTotale) + "</div>");
            } else {
                out.println("<p>Nessun articolo trovato per questo ordine.</p>");
            }

            out.println("</div>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<span style='color:red;'>Errore nel caricamento dettagli ordine.</span>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
