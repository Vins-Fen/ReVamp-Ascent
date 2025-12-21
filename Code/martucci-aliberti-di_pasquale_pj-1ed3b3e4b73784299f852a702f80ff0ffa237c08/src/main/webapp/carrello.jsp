<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 10/07/2025
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.JavaBeans.*, model.DAO.*, java.util.*, java.text.DecimalFormat" %>
<%
  Cliente utente = (Cliente) session.getAttribute("utenteLoggato");
  CarrelloDAO carrelloDAO = new CarrelloDAO();
  ArticoloDAO articoloDAO = new ArticoloDAO();
  ImmagineArticoloDAO imgDAO = new ImmagineArticoloDAO();

  List<Carrello> carrello;
  DecimalFormat df = new DecimalFormat("0.00");
  double totale = 0.0;

  if (utente != null) {
    carrello = carrelloDAO.getCarrelloByUtente(utente.getNomeUtente());
  } else {
    carrello = (List<Carrello>) session.getAttribute("carrelloAnonimo");
    if (carrello == null) carrello = new ArrayList<>();
  }
%>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Carrello</title>
  <link rel="stylesheet" href="css/carrello.css">
  <link rel="icon" type="image/x-icon" href="img/logo.webp">
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="page-container">
  <h2>Il tuo Carrello</h2>

  <% if (carrello.isEmpty()) { %>
  <p>Il carrello è vuoto.</p>
  <% } else { %>
  <div class="table-wrapper">
    <table>
      <thead>
      <tr>
        <th>Immagine</th>
        <th>Nome</th>
        <th>Prezzo</th>
        <th>Quantità</th>
        <th>Totale</th>
        <th>Rimuovi</th>
      </tr>
      </thead>
      <tbody>
      <% for (Carrello item : carrello) {
        Articolo art = articoloDAO.doRetrieveById(item.getCodiceArticolo());
        if (art == null) continue;

        ImmagineArticolo img = imgDAO.findMainImage(item.getCodiceArticolo());
        String urlImg = (img != null && img.getUrl() != null) ? img.getUrl() : "img/default.jpg";

        double prezzoFinale = art.getPrezzo() * (1 - art.getSconto()); //si calcola cosi poiche lo sconto è gia espresso in percentuale
        double subTotale = prezzoFinale * item.getQuantita();
        totale += subTotale;
      %>
      <tr>
        <td><img src="<%= urlImg %>" alt="img" width="70"></td>
        <td><%= art.getNome() %></td>
        <td>
            <span style="color: red; font-size: 1.2em; font-weight: bold;">
              € <%= df.format(prezzoFinale) %>
            </span><br>
            <% if (art.getSconto() > 0) { %>
            <span style="text-decoration: line-through; color: gray; font-size: 0.95em;">
            € <%= df.format(art.getPrezzo()) %>
            </span>
            <% } %>
        </td>
        <td><%= item.getQuantita() %></td>
        <td><%= df.format(subTotale) %> €</td>
        <td>
          <form action="RimuoviDalCarrelloServlet" method="post" style="margin:0">
            <input type="hidden" name="codiceArticolo" value="<%=item.getCodiceArticolo()%>">
            <button type="submit" style="background: none; border: none; cursor: pointer">
              <img src="img/trash-can.png" alt="Rimuovi" width="25" title="Rimuovi"
            </button>
          </form>
        </td>
      </tr>
      <% } %>
      </tbody>
    </table>
  </div>

  <div class="totale-carrello">
    <h3>Totale: <%= df.format(totale) %> €</h3>
    <% if (utente != null) { %>
    <form action="riepilogo.jsp">
      <input type="submit" value="Vai al riepilogo" class="bottone">
    </form>
    <% } else { %>
    <p class="avviso">Per proseguire con l’acquisto <a href="login.jsp">accedi</a> o <a href="registrazione.jsp">registrati</a>.</p>
    <% } %>
  </div>
  <% } %>
</div>


<jsp:include page="footerAreaUtente.jsp"/>
</body>
</html>
