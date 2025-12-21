<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.JavaBeans.Cliente" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    Cliente u = (Cliente) session.getAttribute("utenteLoggato");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>I tuoi indirizzi</title>
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<img src="img/logo.webp" class="logo">

<div id="contenitoreForm">
    <h2 class="titoloLogin">I tuoi indirizzi</h2>

    <c:if test="${not empty listaIndirizzi}"><!-- In questo caso non serve recuperare request.getAttribute()-->
        <div class="lista-indirizzi">         <!--le expression language cercano listaIndirizzi negli scope disponibili,  -->
            <c:forEach var="indirizzo" items="${listaIndirizzi}"> <!-- pageScope, requestScope, sessionScope, application -->
                <div class="indirizzo-card">
                    <p>
                        <strong>Via: </strong> ${indirizzo.via}<br>
                        <strong>Città: </strong> ${indirizzo.citta}<br>
                        <strong>Provincia: </strong> ${indirizzo.provincia}<br>
                        <strong>CAP: </strong> ${indirizzo.cap}<br>
                        <strong>Provincia: </strong> ${indirizzo.paese}<br>
                        <strong>Preferito:</strong>
                        <c:choose>
                            <c:when test="${indirizzo.preferito}">✔</c:when>
                            <c:otherwise>✘</c:otherwise>
                        </c:choose>
                    </p>
                    <form action="ImpostaPreferitoServlet" method="post" onsubmit="return confermaCambioPreferito();">
                        <input type="hidden" name="id_indirizzo" value="${indirizzo.id_indirizzo}"><br>
                        <input type="submit" value="Imposta come preferito"
                               <c:if test="${indirizzo.preferito}">disabled</c:if> >
                    </form><br>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${empty listaIndirizzi}">
        <p>Nessun indirizzo associato</p>
    </c:if>
</div>

<div class="contnuovopag">
    <span class="grassetto" id="nuovometodo">Inserisci un nuovo indirizzo di spedizione</span><br/><br/>
    <form action="AggiungiIndirizzoServlet" method="post" id="formNuovoIndirizzo">

        <b><label>Via</label><br></b>
        <input type="text" name="via" class="dati" placeholder="Via" id="via" required><br><br>

        <b><label>Città</label><br></b>
        <input type="text" name="citta" class="dati" placeholder="Es. Roma" id="citta" required><br><br>

        <b><label>CAP</label><br></b>
        <input type="text" id="cap" name="cap" class="dati" required pattern="\d{5}" title="Inserisci un CAP valido a 5 cifre"><br><br>

        <b><label>Provincia</label><br></b>
        <input type="text" name="provincia" id="provincia" class="dati" placeholder="Es. RM" required><br><br>

        <b><label>Nazione</label><br></b>
        <input type="text" name="paese" id="paese" class="dati" placeholder="Es. Italia" required><br><br>

        <label>
            <input type="checkbox" name="preferito" value="true" />
            Imposta come indirizzo preferito
        </label><br/><br/>

        <input type="submit" class="bottone" value="Inserisci Indirizzo"><br/><br/>
    </form>
</div>

<jsp:include page="footerAreaUtente.jsp"></jsp:include>

<script src="js/script.js"></script>
</body>
</html>
