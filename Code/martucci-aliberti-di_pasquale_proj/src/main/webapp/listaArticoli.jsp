<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 29/06/2025
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dataAccess.DAO.ArticoloDAO, dataAccess.DAO.ImmagineArticoloDAO, dataAccess.JavaBeans.Articolo, dataAccess.JavaBeans.ImmagineArticolo, java.util.List" %>
<%
    if(session.getAttribute("admin") == null){
      response.sendRedirect("login.jsp");
      return;
    }
  ArticoloDAO dao = new ArticoloDAO();
  ImmagineArticoloDAO imgDao = new ImmagineArticoloDAO();
  List<Articolo> articoli = dao.doRetriveByAll();
%>

<%@ include file="headerAdmin.jsp" %>

<html>
<head>
    <title>Lista Articoli - Admin</title>
  <style>
    table {
      border-collapse: collapse;
      width: 90%;
      margin: 30px auto;
      font-family: Arial, sans-serif;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 12px 15px;
      text-align: left;
      vertical-align: middle;
    }
    th {
      background-color: #0066cc;
      color: white;
    }
    tr:hover {background-color: #f5f5f5;}
    a.modifica-link {
      color: #0066cc;
      text-decoration: none;
      font-weight: bold;
    }
    a.modifica-link:hover {
      text-decoration: underline;
    }
    img.art-img {
      max-width: 80px;
      max-height: 60px;
      object-fit: contain;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
  </style>
</head>
<body>
<h2 style="text-align: center;">Lista Articoli</h2>
<table>
  <thead>
  <tr>
    <th>Immagine</th>
    <th>Codice</th>
    <th>Nome</th>
    <th>Prezzo (€)</th>
    <th>Colore</th>
    <th>Azione</th>
  </tr>
  </thead>
  <tbody>
  <% for(Articolo art : articoli) {
    List<ImmagineArticolo> immagini = imgDao.doRetrieveByArticolo(art.getCodice());
    String urlImmaginePrincipale = null;
    for(ImmagineArticolo img : immagini){
      if(img.isIs_principale()){
        urlImmaginePrincipale = img.getUrl();
        break;
      }
    }
  %>
  <tr>
    <td>
      <% if(urlImmaginePrincipale != null) { %>
      <img src="<%= urlImmaginePrincipale %>" alt="Immagine Articolo" class="art-img" />
      <% } else { %>
      <span>Nessuna immagine</span>
      <% } %>
    </td>
    <td><%= art.getCodice() %></td>
    <td><%= art.getNome() %></td>
    <td><%= art.getPrezzo() %></td>
    <td><%= art.getColore() %></td>
    <td>
      <a class="modifica-link" href="ModificaArticoloServlet?codice=<%= art.getCodice() %>">Modifica</a>
    </td>
  </tr>
  <% } %>
  </tbody>
</table>

</body>
</html>
