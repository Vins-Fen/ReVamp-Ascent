<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 23/06/2025
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String errore = (String) request.getAttribute("errore");
%>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore Inserimento</title>
  <link rel="icon" type="image/webp" href="${pageContext.request.contextPath}/img/logo.webp">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f8f9fa;
      margin: 0;
      padding: 0;
      text-align: center;
    }

    .logo {
      width: 120px;
      margin: 30px auto 10px;
      display: block;
    }

    .containerMessaggio {
      max-width: 500px;
      margin: 30px auto;
      background-color: #fff3cd;
      color: #856404;
      border: 1px solid #ffeeba;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      padding: 25px;
      position: relative;
    }

    .erroreMessaggio {
      width: 60px;
      margin-bottom: 15px;
    }

    .chiudiMessaggio {
      position: absolute;
      top: 15px;
      right: 15px;
      width: 24px;
      height: 24px;
      cursor: pointer;
      border: none;
      background: none;
    }

    .chiudiMessaggio img {
      width: 100%;
    }

    #contenitoreForm {
      margin-top: 20px;
    }

    .bottone {
      background-color: #00695C;
      color: white;
      padding: 12px 24px;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      text-decoration: none;
      font-size: 16px;
      transition: background-color 0.3s ease;
    }

    .bottone:hover {
      background-color: #004d40;
    }

    span {
      display: block;
      font-size: 18px;
      margin-top: 10px;
    }
  </style>
</head>
<body>

<img src="${pageContext.request.contextPath}/img/logo.webp" alt="Logo" class="logo">

<div class="containerMessaggio" id="messaggioConferma">
  <img src="${pageContext.request.contextPath}/img/error.png" alt="Errore" class="erroreMessaggio">
  <button class="chiudiMessaggio" onclick="nascondiMessaggio()">
    <img src="${pageContext.request.contextPath}/img/close.png" alt="Chiudi">
  </button>
  <span><%= errore != null ? errore : "Errore sconosciuto durante l'inserimento." %></span>
</div>

<div id="contenitoreForm">
  <a href="${pageContext.request.contextPath}/FormAggiungiArticoloServlet" class="bottone">Riprova</a>
</div>

<script>
  function nascondiMessaggio() {
    document.getElementById("messaggioConferma").style.display = "none";
  }
</script>

</body>
</html>
