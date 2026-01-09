<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 26/06/2025
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         import="dataAccess.JavaBeans.Admin"
         import="dataAccess.DAO.OrdineDAO"
         import="dataAccess.JavaBeans.Ordine"
         import="java.util.*"
         import="java.util.GregorianCalendar"
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Ordini</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="image/x-icon" href="img/logo.webp">
</head>
<body>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin != null) {
        OrdineDAO ordineDAO = new OrdineDAO();
        List<Ordine> ordini;

        String data1 = request.getParameter("data1");
        String data2 = request.getParameter("data2");

        if (data1 != null && !data1.isEmpty() && data2 != null && !data2.isEmpty()) {
            GregorianCalendar gc1 = new GregorianCalendar();
            GregorianCalendar gc2 = new GregorianCalendar();
            gc1.setTime(java.sql.Date.valueOf(data1));
            gc2.setTime(java.sql.Date.valueOf(data2));
            ordini = ordineDAO.doRetrieveByDate(gc1, gc2);
        } else {
            ordini = ordineDAO.doRetrieveAll();
        }
%>
<jsp:include page="headerAdmin.jsp"></jsp:include>
<div class="page-container">
    <div class="header-section">
        <img src="<%= request.getContextPath() %>/img/logo.webp" alt="Logo" class="logo">
        <h2>Gestione Ordini</h2>
    </div>

    <form method="get" action="ordini.jsp" class="search-form-container">
        <input type="date" name="data1" value="<%= (data1 != null) ? data1 : "" %>">
        <input type="date" name="data2" id="data2" value="<%= (data2 != null) ? data2 : "" %>">
        <button type="submit" class="search-button"><i class="fas fa-search"></i></button>
    </form>

    <div class="table-wrapper">
        <table>
            <thead>
            <tr>
                <th><i class="fas fa-hashtag"></i> ID</th>
                <th><i class="fas fa-user"></i> Utente</th>
                <th><i class="fas fa-calendar-alt"></i> Data</th>
                <th><i class="fas fa-box"></i> Articoli</th>
                <th><i class="fas fa-euro-sign"></i> Totale</th>
                <th><i class="fas fa-info-circle"></i> Dettagli</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (!ordini.isEmpty()) {
                    for (Ordine ordine : ordini) {
                        int giorno = ordine.getData().get(Calendar.DAY_OF_MONTH);
                        int mese = ordine.getData().get(Calendar.MONTH) + 1;
                        int anno = ordine.getData().get(Calendar.YEAR);
                        int quantitaTotale = ordineDAO.getQuantitaTotaleOrdine(ordine.getId_ordine());
            %>
            <tr>
                <td><%= ordine.getId_ordine() %></td>
                <td><%= ordine.getNome_utente() %></td>
                <td><%= String.format("%02d/%02d/%04d", giorno, mese, anno) %></td>
                <td><%= quantitaTotale %></td>
                <td><%= String.format("€%.2f", ordine.getImporto_totale()) %></td>
                <td>
                    <button class="detail-button" onclick="toggleDettagli(<%= ordine.getId_ordine() %>)">
                        <i class="fas fa-eye"></i>
                    </button>
                </td>
            </tr>
            <tr id="dettagli-<%= ordine.getId_ordine() %>" class="dettagli-riga" style="display:none;">
                <td colspan="6">
                    <div id="contenuto-dettagli-<%= ordine.getId_ordine() %>">
                        <i>Caricamento in corso...</i>
                    </div>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="6" class="no-results-message">Nessun ordine trovato nel periodo selezionato.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<% } else {
    response.sendRedirect("invalidLogin.jsp");
} %>
<script>
    const oggi = new Date().toISOString().split("T")[0];
    document.getElementById('data2').max = oggi;

    function toggleDettagli(idOrdine){
        const riga=document.getElementById("dettagli-" + idOrdine);
        const contenuto = document.getElementById("contenuto-dettagli-" + idOrdine);

        if(riga.style.display=="none"){
            riga.style.display="table-row";
            if(!contenuto.dataset.caricato){
                fetch("DettaglioOrdineServlet?id=" +idOrdine)
                    .then(response=>response.text())
                    .then(html=>{
                        contenuto.innerHTML=html;
                        contenuto.dataset.caricato="true";
                    })
                    .catch(() => {
                        contenuto.innerHTML = "<span style='color:red;'>Errore nel caricamento dei dettagli.</span>";
                    });
            }
        }else{
            riga.style.display="none";
        }
    }
</script>
</body>
</html>
