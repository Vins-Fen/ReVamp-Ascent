<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 12/07/2025
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, dataAccess.DAO.OrdineDAO" %>
<%@ page import="dataAccess.JavaBeans.Cliente" %>
<%
    Cliente utente = (Cliente) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    OrdineDAO ordineDAO = new OrdineDAO();
    Map<Integer, List<Map<String, Object>>> ordini = ordineDAO.doRetrieveStoricoUtente(utente.getNomeUtente());
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Storico Ordini</title>
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
    <link rel="stylesheet" href="css/storicoOrdini.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="page-container">
    <div class="header-section">
        <img src="<%= request.getContextPath() %>/img/logo.webp" alt="Logo" class="logo">
        <h2>Storico Ordini</h2>
    </div>

    <div class="table-wrapper">
        <table>
            <thead>
            <tr>
                <th><i class="fas fa-hashtag"></i> ID</th>
                <th><i class="fas fa-calendar-alt"></i> Data</th>
                <th><i class="fas fa-boxes"></i> N. Articoli</th>
                <th><i class="fas fa-euro-sign"></i> Totale</th>
                <th><i class="fas fa-info-circle"></i> Dettagli</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (ordini.isEmpty()) {
            %>
            <tr>
                <td colspan="5" class="no-results-message">Nessun ordine trovato.</td>
            </tr>
            <%
            } else {
                for (Map.Entry<Integer, List<Map<String, Object>>> entry : ordini.entrySet()) {
                    int id = entry.getKey();
                    List<Map<String, Object>> articoli = entry.getValue();
                    int quantitaTotale = 0;
                    for (Map<String, Object> art : articoli) {
                        quantitaTotale += (int) art.get("quantita");
                    }

                    Map<String, Object> primo = articoli.get(0);
            %>
            <tr>
                <td><%= id %></td>
                <td><%= primo.get("data") %></td>
                <td><%= quantitaTotale %></td>
                <td>€<%= String.format("%.2f", primo.get("importo_totale")) %></td>
                <td>
                    <button class="detail-button" onclick="toggleDetails('<%= id %>')">
                        <i class="fas fa-eye"></i> Mostra
                    </button>
                </td>
            </tr>
            <tr id="detail-<%= id %>" class="detail-row">
                <td colspan="5">
                    <div class="detail-content">
                        <% for (Map<String, Object> art : articoli) {
                            String img = (String) art.get("immagine");
                            if (img == null || img.isEmpty()) {
                                img = "assets/img/default.jpg";
                            }
                            double prezzo = (Double) art.get("prezzo_unitario");
                            double sconto = (Double) art.get("sconto");
                            int quantita = (int) art.get("quantita");
                            double prezzoFinale = Math.round(prezzo * (1 - sconto) * 100.0) / 100.0;
                            double totale = Math.round(prezzoFinale * quantita * 100.0) / 100.0;
                        %>
                        <div class="articolo-riga">
                            <img src="<%= img %>" alt="Articolo">
                            <div>
                                <strong><%= art.get("nome_articolo") %></strong><br>
                                Quantità: <%= quantita %><br>
                                Prezzo unitario: €<%= String.format("%.2f", prezzoFinale) %><br>
                                Totale: €<%= String.format("%.2f", totale) %>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</div>

<script>
    function toggleDetails(id) {
        const row = document.getElementById('detail-' + id);
        row.style.display = (row.style.display === 'table-row') ? 'none' : 'table-row';
    }
</script>

<jsp:include page="footerAreaUtente.jsp" />
</body>
</html>
