<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 23/06/2025
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Articolo Inserito</title>
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<img src="img/logo.webp" class="logo">
<div id="contenitoreForm">
    <h2 class="titoloLogin">Articolo aggiunto con successo</h2>

    <div style="text-align: center; margin-top: 20px">
        <a href="FormAggiungiArticoloServlet" class="bottone" style="text-decoration: none; display: inline-block; width: auto; padding: 10px 20px;">Aggiungi un altro</a>
        <a href="admin.jsp" class="bottone" style="text-decoration: none; display: inline-block; width: auto; padding: 10px 20px; background-color: #999;">Torna al pannello</a>    </div>
</div>
</body>
</html>
