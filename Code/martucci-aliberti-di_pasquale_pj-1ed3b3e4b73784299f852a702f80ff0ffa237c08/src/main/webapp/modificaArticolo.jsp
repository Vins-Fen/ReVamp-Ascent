<%@ page import="model.DAO.ArticoloDAO" %><%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 29/06/2025
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="model.JavaBeans.Articolo, model.JavaBeans.Categoria, model.DAO.ArticoloDAO, model.DAO.CategoriaDAO, java.util.*"
%>

<%
    Articolo articolo = (Articolo) request.getAttribute("articolo");
    List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
%>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta charset="UTF-8">
    <title>Modifica Articolo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f9f9f9;
        }

        h2 {
            color: #333;
        }

        form {
            background-color: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            max-width: 650px;
            margin: auto;
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 15px;
        }

        small {
            color: #666;
            display: block;
            margin-bottom: 5px;
        }

        input, textarea, select {
            width: 100%;
            padding: 10px;
            margin-top: 2px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 15px;
        }

        input[type="submit"] {
            background-color: #0066cc;
            color: white;
            font-weight: bold;
            cursor: pointer;
            margin-top: 20px;
        }

        input[type="submit"]:hover {
            background-color: #004b99;
        }
    </style>
</head>
<body>

<jsp:include page="headerAdmin.jsp"></jsp:include>

<%

    if(session.getAttribute("admin")!=null){
        %>
<% if (articolo != null) { %>
<h2>Modifica Articolo</h2>
<% String urlImg = (String) request.getAttribute("urlImmaginePrincipale"); %>
<% if (urlImg != null) { %>
<div style="text-align:center; margin-bottom:20px;">
    <img src="<%= urlImg %>" alt="Immagine Principale" style="max-width:300px; max-height:200px; object-fit:contain; border:1px solid #ccc; border-radius:8px;">
</div>
<% } %>

<form action="SalvaModificaArticoloServlet" method="post">
    <label>Codice (non modificabile) </label>
    <input type="hidden" name="codice" value="<%= articolo.getCodice() %>">
    <input type="text" value="<%= articolo.getCodice() %>" disabled>

    <label>Nome</label>
    <small>Attuale: <%=articolo.getNome() %> </small>
    <input type="text" name="nome" value="<%=articolo.getNome()%>">

    <label>Descrizione</label>
    <small>Attuale: <%= articolo.getDescrizione() %></small>
    <textarea name="descrizione"><%= articolo.getDescrizione() %></textarea>

    <label>Colore</label>
    <small>Attuale: <%= articolo.getColore() %></small>
    <input type="text" name="colore" value="<%= articolo.getColore() %>">

    <label>Sconto (%)</label>
    <small>Attuale: <%= articolo.getSconto() %></small>
    <input type="number" step="0.01" name="sconto" value="<%= articolo.getSconto() %>">

    <label>Prezzo (€)</label>
    <small>Attuale: <%= articolo.getPrezzo() %></small>
    <input type="number" step="0.01" name="prezzo" value="<%= articolo.getPrezzo() %>">

    <label>Peso (kg)</label>
    <small>Attuale: <%= articolo.getPeso() %></small>
    <input type="number" step="0.01" name="peso" value="<%= articolo.getPeso() %>">

    <label>Dimensione</label>
    <small>Attuale: <%= articolo.getDimensione() %></small>
    <input type="text" name="dimensione" value="<%= articolo.getDimensione() %>">

    <label>Categoria</label>
    <select name="id_categoria">
        <% for(Categoria c : categorie) { %>
        <option value="<%= c.getId_categoria() %>" <%= c.getId_categoria() == articolo.getId_categoria() ? "selected" : "" %>>
            <%= c.getTipologia() %>
        </option>
        <% } %>
    </select>

    <br><br>
    <input type="submit" value="Salva Modifiche">
</form>
<% } else { %>
<h2>Errore: nessun articolo trovato per la modifica.</h2>
<% } %>

<% } %>


</body>
</html>
