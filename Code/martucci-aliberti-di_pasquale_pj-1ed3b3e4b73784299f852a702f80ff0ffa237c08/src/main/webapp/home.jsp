<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.JavaBeans.Cliente" %>
<%@ page session="true" %>

<%
    Cliente utente=(Cliente) session.getAttribute("utenteLoggato");
    if(utente==null){
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8" />
    <title>Area Utente -  Benvenuto <%=utente.getNome() %></title>
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <link rel="icon" href="img/logo.webp">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>
<jsp:include page="header.jsp" />

<div class="user-dashboard">

    <div class="user-header">
        <h2>Ciao, <%=utente.getNome()%>!</h2>
        <p class="user-balance">Benvenuto nella tua area personale</p>
    </div>

    <!-- Griglia dei menu -->
    <div class="menu-grid">
        <a href="storicoOrdini.jsp" class="menu-card">
            <img src="img/ordini.png" alt="Ordini" class="menu-icon" />
            <p>Ordini</p>
        </a>

        <a href="metodiPagamento.jsp" class="menu-card">
            <img src="img/credit-card.png" alt="Metodi di Pagamento" class="menu-icon" />
            <p>Metodi di Pagamento</p>
        </a>

        <a href="VisualizzaIndirizziServlet" class="menu-card">
            <img src="img/indrizzi.png" alt="Indirizzi" class="menu-icon" />
            <p>Indirizzi</p>
        </a>

        <a href="modificaCredenziali.jsp" class="menu-card">
            <img src="img/impostazioni.png" alt="Impostazioni" class="menu-icon" />
            <p>Impostazioni</p>
        </a>
    </div>
<%
    if (session.getAttribute("utenteLoggato") != null) {
%>
    <form action="LogoutServlet" method="get" class="bott" style="text-align:center; margin-top:30px;">
        <input type="submit" value="Logout" class="bott1" />
    </form>
<%
    } else {
%>
    <a href="login.jsp" style="display:block; text-align:center; margin-top:30px;">
        <div class="boxAreaUtente">
            <img src="img/login.png" class="imgBoxAreaUtente" alt="Login" />
        </div>
    </a>
<%
    }
%>
</div>

<jsp:include page="footerAreaUtente.jsp" />

</body>
</html>
