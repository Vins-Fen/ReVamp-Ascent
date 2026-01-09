<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="dataAccess.DAO.*"
         import="dataAccess.JavaBeans.*"
         import="java.util.ArrayList"
         import="java.util.List"
%>

<%@ include file="headerAdmin.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <title>Lista Clienti</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%
    if (session.getAttribute("admin") != null) {

        List<Cliente> clienti = (List<Cliente>) request.getAttribute("listaClienti");
        String ricercaPrecedente = (String) request.getAttribute("termineRicerca");

        if (ricercaPrecedente == null) {
            ricercaPrecedente = "";
        }

        if (clienti == null) {
            clienti = new ArrayList<>();
        }
%>

<div class="page-container">
    <div class="header-section">
        <img src="<%= request.getContextPath() %>/img/logo.webp" alt="Logo" class="logo">
        <h2>Lista Clienti</h2>
    </div>

    <form method="get" action="<%= request.getContextPath() %>/cercaUtenteServlet" class="search-form-container">
        <input type="text" placeholder="Cerca Cliente per username..." name="nomeUtente" value="<%= ricercaPrecedente %>">
        <button type="submit" class="search-button">
            <i class="fas fa-search"></i>
        </button>
    </form>

    <div class="table-wrapper">
        <table>
            <thead>
            <tr>
                <th><i class="fas fa-user"></i> Username</th>
                <th><i class="fas fa-id-badge"></i> Nome</th>
                <th><i class="fas fa-id-badge"></i> Cognome</th>
                <th><i class="fas fa-envelope"></i> E-Mail</th>
                <th><i class="fas fa-venus-mars"></i> Sesso</th>
                <th><i class="fas fa-birthday-cake"></i> Età</th>
                <th><i class="fas fa-phone"></i> Telefono</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (!clienti.isEmpty()) {
                    for (Cliente c : clienti) {
            %>
            <tr>
                <td><%= c.getNomeUtente() %></td>
                <td><%= c.getNome() %></td>
                <td><%= c.getCognome() %></td>
                <td><%= c.getEmail() %></td>
                <td data-sesso="<%= c.getSesso().toUpperCase() %>"><%= c.getSesso().toUpperCase() %></td>
                <td><%= c.getEta() %></td>
                <td><%= c.getNumTelefono() %></td>
            </tr>
            <%
                }
            } else {
                if (!ricercaPrecedente.isEmpty()) {
            %>
            <tr><td colspan="7" class="no-results-message">Nessun cliente trovato con l'username '<%= ricercaPrecedente %>'</td></tr>
            <%  } else { %>
            <tr><td colspan="7" class="no-results-message">Nessun cliente registrato nel sistema.</td></tr>
            <%  }
            }
            %>
            </tbody>
        </table>
    </div>
</div>

<%
    } else {
        response.sendRedirect(request.getContextPath() + "/invalidLogin.jsp");
    }
%>
</body>
</html>

