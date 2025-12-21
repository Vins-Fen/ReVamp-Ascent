<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .page-container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 16px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
        }

        .header-section {
            text-align: center;
            margin-bottom: 30px;
            max-width: 100%;
        }
    </style>
</head>
<body>

<!-- serve per far vedere a che si registra il messaggio di successo -->
<% if ("reg".equals(request.getParameter("success"))) { %>
<div class="containerMessaggio" style="justify-content:center; margin-bottom: 15px;">
    <div id="messaggioConferma" style="background-color: #d4edda; color: #155724; padding: 10px; border-radius: 5px; border: 1px solid #c3e6cb;">
        <img src="img/success.png" class="successoMessaggio" style="width: 20px; vertical-align: middle; margin-right: 10px;">
        Registrazione avvenuta con successo! Ora puoi effettuare il login.
        <span class="chiudiMessaggio" style="float:right; cursor:pointer;" onclick="this.parentElement.parentElement.style.display='none';">×</span>
    </div>
</div>
<% } %>


<!--
<% if (request.getAttribute("msg") != null) { %>
<div class="containerMessaggio" style="justify-content:center; margin-bottom: 15px;">
    <div id="messaggioConferma">
        <%= request.getAttribute("msg") %>
        <span class="chiudiMessaggio" onclick="this.parentElement.parentElement.style.display='none';">×</span>
    </div>
</div>
<% } %>
-->


<% if (request.getAttribute("erroreLogin") != null) { %>
<div class="containerMessaggio" style="justify-content:center; margin-bottom: 15px;">
    <div style="background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; border: 1px solid #f5c6cb;">
        <img src="img/error.png" alt="Errore" style="width: 20px; vertical-align: middle; margin-right: 10px;">
        <%= request.getAttribute("erroreLogin") %>
        <span class="chiudiMessaggio" style="float:right; cursor:pointer;" onclick="this.parentElement.parentElement.style.display='none';">×</span>
    </div>
</div>
<% } %>


<div class="page-container">

    <div class="header-section">
        <a href="homePage.jsp">
        <img src="<%= request.getContextPath() %>/img/logo.webp" alt="Logo"
             style="width: 110px; display: block; margin: 0 auto 10px;">
        </a>
        <h2>Accedi</h2>
    </div>

<div id="contenitoreForm">
    <form action="${pageContext.request.contextPath}/LoginServlet" method="POST" id="formLogin">
        <input type="text" name="username" placeholder="Username" class="dati" id="username" required><br><br>
        <input type="password" name="password" placeholder="Password" class="dati" id="password" required><br><br>
        <span id="errorLogin" class="formError" style="display:none; color:red;">Inserisci correttamente le credenziali</span><br>
        <input type="submit" value="Login" class="bottone">
    </form>
    <br>
    <span id="account">
        Non hai un account? <a href="registrazione.jsp">Creane uno</a><br>
    </span>
</div>
</div>
<script src="js/script.js"></script>
</body>
</html>
