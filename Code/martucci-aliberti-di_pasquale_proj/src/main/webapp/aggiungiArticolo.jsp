<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dataAccess.JavaBeans.Categoria" %>
<%@ page import="java.util.List" %>

<%
  if (session.getAttribute("admin") == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Aggiungi Articolo</title>
  <link rel="stylesheet" href="css/stileRegistrazione.css">
  <link rel="icon" href="img/logo.webp" type="image/x-icon">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="headerAdmin.jsp"></jsp:include>


<img src="img/logo.webp" class="logo">
<div id="contenitoreForm">
  <h2 class="titoloLogin">Inserisci un nuovo articolo</h2>

  <form action="AggiungiArticoloServlet" method="post">
    <input type="number" name="codice" placeholder="Codice Articolo" class="dati" required><br>
    <input type="text" name="nome" placeholder="Nome" class="dati" required><br>
    <textarea name="descrizione" placeholder="Descrizione" class="dati" required></textarea><br>
    <input type="text" name="colore" placeholder="Colore" class="dati"><br>
    <input type="number" name="sconto" step="0.01" placeholder="Sconto (%)" class="dati"><br>
    <input type="number" name="prezzo" step="0.01" placeholder="Prezzo (€)" class="dati" required><br>
    <input type="number" name="peso" step="0.001" placeholder="Peso (kg)" class="dati"><br>
    <input type="text" name="dimensione" placeholder="Dimensione" class="dati"><br>

    <select name="id_categoria" class="dati" required>
      <option value="" disabled selected>Seleziona Categoria</option>
      <% for (Categoria c : categorie) { %>
      <option value="<%= c.getId_categoria() %>"><%= c.getTipologia() %></option>
      <% } %>
    </select><br>

    <input type="submit" value="Aggiungi Articolo" class="bottone">
  </form>
</div>

</body>
</html>


