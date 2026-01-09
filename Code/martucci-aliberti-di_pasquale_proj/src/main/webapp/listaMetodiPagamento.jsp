<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 27/06/2025
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dataAccess.JavaBeans.MetodiPagamento" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="dataAccess.DAO.MetodiPagamentoDAO" %>
<%@ page import="dataAccess.JavaBeans.Cliente" %>

<%
  List<MetodiPagamento> carte = (List<MetodiPagamento>) request.getAttribute("metodiPagamento");
  if (carte == null) {
    MetodiPagamentoDAO metodiPagamentoDAO = new MetodiPagamentoDAO();
    Cliente u = (Cliente) session.getAttribute("utenteLoggato");
    if (u != null) {
      carte = metodiPagamentoDAO.doRetrieveByUser(u.getNomeUtente()); //doppio controllo per sicurezza,poiche la servlet viene richiamata da piu pagine
    }
  }
%>

<% if (carte == null || carte.isEmpty()) { %>
<p>Non hai ancora metodi di pagamento salvati.</p>
<% } else { %>
<h2>I Tuoi Metodi Di Pagamento</h2>
<div class="contcarte">
  <% for (MetodiPagamento carta : carte) {
    GregorianCalendar dataScad = carta.getDataScadenza();
    int mese = dataScad.get(GregorianCalendar.MONTH) + 1;
    int anno = dataScad.get(GregorianCalendar.YEAR);
    String scadenzaFormattata = String.format("%02d/%d", mese, anno);
  %>
  <div class="singolopagamento">
    <div class="fotcarta">
      <img alt="creditcard" src="img/credit-card.png" class="iconaMenu">
    </div>
    <div class="datcarta">
      <%= carta.getNumCarta() %><br/>
      <%= carta.getIntestatario() %><br/>
      Scadenza: <%= scadenzaFormattata %><br/>
      CVV: ***<br/>
      <button class="btn-elimina" onclick="eliminaMetodoPagamento('<%= carta.getNumCarta().replace("'", "\\'") %>')">Elimina</button>
    </div>
  </div>
  <% } %>
</div>
<% } %>