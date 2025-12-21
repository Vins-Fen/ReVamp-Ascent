<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.JavaBeans.Categoria" %>
<%@ page import="java.util.List" %>

<%
  if (session.getAttribute("admin") == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Aggiungi Articolo</title>
  <link rel="stylesheet" href="css/stileRegistrazione.css">
  <link rel="icon" href="img/logo.webp" type="image/x-icon">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="headerAdmin.jsp"></jsp:include>


<img src="img/logo.webp" class="logo">
<div id="contenitoreForm">
  <h2 class="titoloLogin">Inserisci un nuovo articolo</h2>

  <form action="AggiungiArticoloServlet" id="formId" method="post">
    <input type="number" id="codice" name="codice" placeholder="Codice Articolo" class="dati" required><br>
    <input type="text"  id="nome" name="nome" placeholder="Nome" class="dati" required><br>
    <textarea name="descrizione" placeholder="Descrizione" class="dati" required></textarea><br>
    <input type="text" id="colore" name="colore" placeholder="Colore" class="dati"><br>
    <input type="number"  id="sconto" name="sconto" step="0.01" placeholder="Sconto (%)" class="dati"><br>
    <input type="number" id="prezzo" name="prezzo" step="0.01" placeholder="Prezzo (€)" class="dati" required><br>
    <input type="number" name="peso" step="0.001" placeholder="Peso (kg)" class="dati"><br>
    <input type="text" name="dimensione" placeholder="Dimensione" class="dati"><br>

    <select name="id_categoria" class="dati" required>
      <option value="" disabled selected>Seleziona Categoria</option>
      <% for (Categoria c : categorie) { %>
      <option value="<%= c.getId_categoria() %>"><%= c.getTipologia() %></option>
      <% } %>
    </select><br>

    <input type="submit" value="Aggiungi Articolo" class="bottone">
  </form>
</div>

<script>
  const form=document.getElementById("formId");
  if(form){
    form.addEventListener("submit",function(event){
      if(!validaCampi()){
        event.preventDefault();
      }
    });
  }

  function validaCampi() {
    const codice = document.getElementById("codice");
    const nome = document.getElementById("nome");
    const sconto = document.getElementById("sconto");
    const colore = document.getElementById("colore");
    const prezzo = document.getElementById("prezzo");

    if (!codice || !nome || !prezzo) {
      alert("Codice, Nome e Prezzo sono obbligatori.");
      return false;
    }

    const codiceVal = codice.value.trim();
    const nomeVal = nome.value.trim();
    const scontoVal = sconto ? sconto.value.trim() : "";
    const coloreVal = colore ? colore.value.trim() : "";
    const prezzoVal = prezzo.value.trim();

    // Controllo codice
    if (codiceVal === "" || isNaN(codiceVal) || Number(codiceVal) <= 0) {
      alert("Il codice articolo deve essere un numero positivo.");
      return false;
    }

    // Controllo nome
    if (nomeVal.length < 3) {
      alert("Il nome deve avere almeno 3 caratteri.");
      return false;
    }

    // Controllo prezzo
    if (prezzoVal === "" || isNaN(prezzoVal) || Number(prezzoVal) <= 0) {
      alert("Il prezzo deve essere un numero positivo.");
      return false;
    }

    // Controllo sconto (se presente)
    if (scontoVal !== "") {
      if (isNaN(scontoVal) || Number(scontoVal) < 0 || Number(scontoVal) > 100) {
        alert("Lo sconto deve essere un numero tra 0 e 100.");
        return false;
      }
    }

    return true; // tutto valido
  }
</script>
</body>
</html>


