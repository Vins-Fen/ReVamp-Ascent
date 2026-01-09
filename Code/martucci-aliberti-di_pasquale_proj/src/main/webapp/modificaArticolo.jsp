<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="dataAccess.JavaBeans.Articolo, dataAccess.JavaBeans.Categoria, java.util.*"
%>

<%
    // Recupero attributi dalla Servlet
    Articolo articolo = (Articolo) request.getAttribute("articolo");
    List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");

    // Controllo Sessione
    if(session.getAttribute("admin") == null){
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta charset="UTF-8">
    <title>Modifica Articolo</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 40px; background-color: #f9f9f9; }
        h2 { color: #333; }
        form { background-color: white; padding: 30px; border-radius: 12px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 650px; margin: auto; }
        label { font-weight: bold; display: block; margin-top: 15px; }
        small { color: #666; display: block; margin-bottom: 5px; font-size: 0.9em; }
        input, textarea, select { width: 100%; padding: 10px; margin-top: 2px; border-radius: 6px; border: 1px solid #ccc; font-size: 15px; }
        input[type="submit"] { background-color: #0066cc; color: white; font-weight: bold; cursor: pointer; margin-top: 20px; }
        input[type="submit"]:hover { background-color: #004b99; }
    </style>
</head>
<body>

<jsp:include page="headerAdmin.jsp"></jsp:include>

<% if (articolo != null) { %>
<h2>Modifica Articolo</h2>

<% String urlImg = (String) request.getAttribute("urlImmaginePrincipale"); %>
<% if (urlImg != null) { %>
<div style="text-align:center; margin-bottom:20px;">
    <img src="<%= urlImg %>" alt="Immagine Principale" style="max-width:300px; max-height:200px; object-fit:contain; border:1px solid #ccc; border-radius:8px;">
</div>
<% } %>

<form action="SalvaModificaArticoloServlet" method="post" id="formId">
    <input type="hidden" name="codice" value="<%= articolo.getCodice() %>">

    <label>Codice (non modificabile)</label>
    <input type="text" value="<%= articolo.getCodice() %>" disabled style="background-color: #e9ecef;">

    <label>Nome</label>
    <small>Attuale: <%=articolo.getNome() %> </small>
    <input type="text" name="nome" id="nome" required value="<%=articolo.getNome()%>">

    <label>Descrizione</label>
    <small>Attuale: <%= articolo.getDescrizione() %></small>
    <textarea name="descrizione" id="descrizione" rows="4" required><%= articolo.getDescrizione() %></textarea>

    <label>Colore</label>
    <small>Attuale: <%= articolo.getColore() %></small>
    <input type="text" name="colore" id="colore" value="<%= articolo.getColore() %>">

    <label>Sconto (%)</label>
    <small>Attuale: <%= articolo.getSconto() %></small>
    <%
        // Calcolo sconto visivo: Se DB ha 0.10, qui diventa 10.0
        double scontoVisivo = articolo.getSconto();
        if(scontoVisivo < 1.0 && scontoVisivo > 0) {
            scontoVisivo = scontoVisivo * 100;
        }
    %>
    <input type="number" step="0.01" name="sconto" id="sconto" value="<%= scontoVisivo %>">

    <label>Prezzo (€)</label>
    <small>Attuale: <%= articolo.getPrezzo() %></small>
    <input type="number" step="0.01" name="prezzo" id="prezzo" required value="<%= articolo.getPrezzo() %>">

    <label>Peso (kg)</label>
    <small>Attuale: <%= articolo.getPeso() %></small>
    <input type="number" step="0.01" name="peso" id="peso" value="<%= articolo.getPeso() %>">

    <label>Dimensione</label>
    <small>Attuale: <%= articolo.getDimensione() %></small>
    <input type="text" name="dimensione" id="dimensione" value="<%= articolo.getDimensione() %>">

    <label>Categoria</label>
    <select name="id_categoria">
        <% for(Categoria c : categorie) { %>
        <option value="<%= c.getId_categoria() %>" <%= c.getId_categoria() == articolo.getId_categoria() ? "selected" : "" %>>
            <%= c.getTipologia() %>
        </option>
        <% } %>
    </select>
    <span id="errorModifica" class="formError" style="display:none; color:red;">Inserisci correttamente i campi</span><br>
    <br><br>
    <input type="submit" value="Salva Modifiche" name="btn">
</form>
<% } else { %>
<div style="text-align: center; margin-top: 50px;">
    <h2>Errore: Nessun articolo trovato per la modifica.</h2>
    <a href="catalogoAdmin.jsp">Torna al Catalogo</a>
</div>
<% } %>


<script>
    const form = document.getElementById("formId");

    if(form){
        form.addEventListener("submit", function (e){
            if(!validaCampi()){
                e.preventDefault();
                mostraErroreModifica();
            }else{
                nascondiErroreModifica();
            }
        });
    }

    function validaCampi() {
        const nome = document.getElementById("nome").value.trim();
        const descrizione = document.getElementById("descrizione").value.trim();
        const prezzo = document.getElementById("prezzo").value.trim();
        const sconto = document.getElementById("sconto").value.trim();
        const peso = document.getElementById("peso").value.trim();

        // 1. Controllo Nome
        if (nome.length < 3) {
            alert("Il nome deve contenere almeno 3 caratteri.");
            return false;
        }

        // 2. Controllo Descrizione
        if (descrizione === "") {
            alert("La descrizione è obbligatoria.");
            return false;
        }

        // 3. Controllo Prezzo
        if (prezzo === "" || isNaN(prezzo) || Number(prezzo) <= 0) {
            alert("Il prezzo deve essere un numero positivo valido.");
            return false;
        }

        // 4. Controllo Sconto (0 - 100)
        if (sconto !== "") {
            if (isNaN(sconto) || Number(sconto) < 0 || Number(sconto) > 100) {
                alert("Lo sconto deve essere un numero compreso tra 0 e 100.");
                return false;
            }
        }

        // 5. Controllo Peso (non negativo)
        if (peso !== "") {
            if (isNaN(peso) || Number(peso) < 0) {
                alert("Il peso non può essere negativo.");
                return false;
            }
        }

        return true;
    }


    function mostraErroreModifica() {
        const errore = document.getElementById('errorModifica');
        if (errore) {
            errore.style.display = "inline";
        }
    }

    function nascondiErroreModifica() {
        const errore = document.getElementById('errorModifica');
        if (errore) {
            errore.style.display = "none";
        }
    }
</script>

</body>
</html>