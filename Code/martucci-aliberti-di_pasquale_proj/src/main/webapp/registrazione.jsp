<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrazione Utente</title>
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<%
    String errore = request.getParameter("errore");
    if (errore != null) {
        String messaggioErrore = switch (errore) {
            case "username" -> "Username già in uso.";
            case "email" -> "Email già registrata.";
            case "password" -> "Le password non coincidono.";
            case "campi" -> "Tutti i campi sono obbligatori.";
            default -> "Errore generico durante la registrazione.";
        };
%>
<div class="containerMessaggio">
    <div id="messaggioConferma">
        <img src="img/error.png" class="erroreMessaggio">
        <input type="image" src="img/close.png" class="chiudiMessaggio" onclick="nascondiMessaggio()">
        <span><%= messaggioErrore %></span>
    </div>
</div>
<% } %>

<% if ("reg".equals(request.getParameter("success"))) { %>
<div class="containerMessaggio">
    <div id="messaggioConferma" style="color: green;">
        <img src="img/success.png" class="successoMessaggio">
        <input type="image" src="img/close.png" class="chiudiMessaggio" onclick="nascondiMessaggio()">
        <span>Registrazione completata con successo! Ora puoi accedere.</span>
    </div>
</div>
<% } %>

<img src="img/logo.webp" class="logo">
<div id="contenitoreForm">
    <form id="formRegistrazione" action="RegistrazioneUtenteServlet" method="POST">
        <input type="text" name="nome" placeholder="Nome" class="dati" maxlength="30" id="nome"><br>
        <input type="text" name="cognome" placeholder="Cognome" class="dati" id="cognome"><br>

        <label>Sesso:</label>
        <input type="radio" id="maschio" name="sesso" value="m" required>
        <label for="maschio">Maschio</label>
        <input type="radio" id="femmina" name="sesso" value="f">
        <label for="femmina">Femmina</label><br><br>

        <input type="email" name="email" placeholder="Email" class="dati" id="email"><br>
        <input type="text" name="username" placeholder="Username" class="dati" id="username"><br>
        <input type="password" name="password" placeholder="Password" class="dati" id="password"><br>
        <input type="password" name="confermaPassword" placeholder="Conferma Password" class="dati" id="confpass"><br><br>

        <input type="submit" value="Registrati" class="bottone">
    </form>
    <br>
    <span id="account">Hai già un account? <a href="login.jsp">Vai al login</a></span>
</div>

<script src="js/script.js"></script>
</body>
</html>

