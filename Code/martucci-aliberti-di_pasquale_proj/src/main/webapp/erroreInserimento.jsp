<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 23/06/2025
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String errore=(String) request.getAttribute("errore");
%>
<html>
<head>
  <meta charset="UTF-8">
    <title>Errore Inserimento</title>
  <link rel="stylesheet" href="css/stileRegistrazione.css">
  <link rel="icon" type="image/webp" href="img/logo.webp">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>

<img src="img/logo.webp">
<div class="containerMessaggio">
  <div id="messaggioConferma">
    <img src="img/error.png" class="erroreMessaggio">
    <input type="image" src="../img/close.png" class="chiudiMessaggio" onclick="nascondiMessaggio()">
    <span><%= errore != null ? errore : "Errore sconosciuto durante l'inserimento." %></span>
  </div>
</div>

<div id="contenitoreForm">
  <div style="text-align:center;">
    <a href="../aggiungiArticolo.jsp" class="bottone" style="text-decoration: none; display: inline-block; width: auto; padding: 10px 20px;">Riprova</a>
  </div>
</div>
<script src="js/script.js"></script>
</body>
</html>
