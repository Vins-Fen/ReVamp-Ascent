<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 10/07/2025
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.JavaBeans.*, model.DAO.*, java.util.*, java.text.DecimalFormat" %>
<%
    Cliente utente = (Cliente) session.getAttribute("utenteLoggato");
    if (utente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    CarrelloDAO carrelloDAO = new CarrelloDAO();
    ArticoloDAO articoloDAO = new ArticoloDAO();
    ImmagineArticoloDAO imgDAO = new ImmagineArticoloDAO();
    IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
    MetodiPagamentoDAO metodiPagamentoDAO = new MetodiPagamentoDAO();

    List<Carrello> carrello = carrelloDAO.getCarrelloByUtente(utente.getNomeUtente());
    List<MetodiPagamento> metodiPagamento = metodiPagamentoDAO.doRetrieveByUser(utente.getNomeUtente());
    Indirizzo indirizzo = indirizzoDAO.getPreferito(utente.getNomeUtente());

    DecimalFormat df = new DecimalFormat("0.00");
    double totale = 0.0;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Riepilogo Ordine</title>
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="page-container">
    <h2>🧾 Riepilogo Ordine</h2>

    <% if (carrello.isEmpty()) { %>
    <p>Il tuo carrello è vuoto.</p>
    <% } else { %>

    <table style="width:100%; text-align:center;" cellpadding="10" border="1">
        <tr>
            <th>Immagine</th>
            <th>Articolo</th>
            <th>Prezzo</th>
            <th>Sconto</th>
            <th>Prezzo Finale</th>
            <th>Quantità</th>
            <th>Totale</th>
        </tr>
        <% for (Carrello item : carrello) {
            Articolo art = articoloDAO.doRetrieveById(item.getCodiceArticolo());
            if (art == null) continue;

            ImmagineArticolo img = imgDAO.findMainImage(item.getCodiceArticolo());
            String imgUrl = (img != null && img.getUrl() != null) ? img.getUrl() : "img/default.jpg";

            double prezzoFinale = art.getPrezzo() * (1 - art.getSconto());
            double subTotale = prezzoFinale * item.getQuantita();
            totale += subTotale;
        %>
        <tr>
            <td><img src="<%= imgUrl %>" width="60" alt="img" /></td>
            <td><%= art.getNome() %></td>
            <td><%= df.format(art.getPrezzo()) %> €</td>
            <td><%= df.format(art.getPrezzo() * art.getSconto()) %> €</td>
            <td><%= df.format(prezzoFinale) %> €</td>
            <td><%= item.getQuantita() %></td>
            <td><%= df.format(subTotale) %> €</td>
        </tr>
        <% } %>
    </table>

    <h3 style="margin-top: 20px;">Totale ordine: <%= df.format(totale) %> €</h3>

    <hr>

    <h3>🏠 Indirizzo di Spedizione</h3>
    <% if (indirizzo != null) { %>
    <p>
        <strong><%= indirizzo.getVia() %></strong><br>
        <%= indirizzo.getCap() %>, <%= indirizzo.getCitta() %> (<%= indirizzo.getProvincia() %>)<br>
        <%= indirizzo.getPaese() %>
    </p>
    <% } else { %>
    <p><em>Nessun indirizzo preferito trovato.</em></p>
    <% } %>
    <form action="VisualizzaIndirizziServlet">
        <input type="submit" value="Gestisci indirizzi" class="bottone">
    </form>

    <hr>

    <form action="ConfermaOrdineServlet" method="post">
        <h3>💳 Seleziona un Metodo di Pagamento</h3>

        <% if (metodiPagamento.isEmpty()) { %>
    <p><em>Nessun metodo di pagamento salvato.</em></p>
</form>

<form action="<%= request.getContextPath() %>/metodiPagamento.jsp" method="get">
    <input type="submit" value="Gestisci metodi di pagamento" class="bottone">
</form>
<% } else { %>
        <div class="contcarte">
            <% for (MetodiPagamento carta : metodiPagamento) {
                GregorianCalendar dataScad = carta.getDataScadenza();
                int mese = dataScad.get(GregorianCalendar.MONTH) + 1;
                int anno = dataScad.get(GregorianCalendar.YEAR);
                String scadenza = String.format("%02d/%d", mese, anno);
                String ultime4 = carta.getNumCarta().substring(carta.getNumCarta().length() - 4);
            %>
            <div class="singolopagamento" style="border:1px solid #ccc; padding:10px; margin:10px 0;">
                <label style="display:flex; align-items:center; gap:15px;">
                    <input type="radio" name="metodoSelezionato" value="<%= carta.getNumCarta() %>" required />
                    <div class="fotcarta">
                        <img src="img/credit-card.png" alt="Carta" width="40">
                    </div>
                    <div class="datcarta">
                        •••• <%= ultime4 %><br>
                        <%= carta.getIntestatario() %><br>
                        Scadenza: <%= scadenza %><br>
                        CVV: ***
                    </div>
                </label>
            </div>
            <% } %>
        </div>
        <input type="submit" value="Conferma e Procedi al Pagamento" class="bottone">
        <% } %>
    </form>
    <% } %>
</div>

<jsp:include page="footerAreaUtente.jsp" />
</body>
</html>
