<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 28/06/2025
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.DAO.*, model.JavaBeans.*" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo Articoli</title>
    <link rel="stylesheet" href="css/stileRegistrazione.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">

</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<h1 style="text-align: center;">Catalogo Articoli</h1>
<div class="contenitoreProdotto">
  <%
    ArticoloDAO articoloDAO = new ArticoloDAO();
    ImmagineArticoloDAO imgDAO = new ImmagineArticoloDAO();
    CategoriaDAO categoriaDAO = new CategoriaDAO();

    List<Articolo> articoli = articoloDAO.doRetriveByAll();
    List<Categoria> categorie = categoriaDAO.doRetrieveAll();
    Map<Integer,Categoria> categoriaMap=new HashMap<>();  //usiamo una map per trovare velocemente una categoria, in base al suo id(Integer)
    for(Categoria c:categorie) {
      categoriaMap.put(c.getId_categoria(),c);  //popoliamo la mappa con idCategoria e categoria
    }

    // String filtroNome = request.getParameter("nome");
    String filtroCategoria = request.getParameter("categ");
    %>

<h1 style="text-align: center">
  <% if(filtroCategoria !=null && !filtroCategoria.trim().isEmpty()){ %>
  Catalogo: <%= filtroCategoria %>
  <% } else { %>

  <% } %>
</h1>

    <div class="contenitoreProdotto">
      <%
        for (Articolo a : articoli) {
          boolean mostra = true;

       /*  if (filtroNome != null && !filtroNome.trim().isEmpty()) {
            if (!a.getNome().toLowerCase().contains(filtroNome.toLowerCase())) {
              mostra = false;
            }
          }
            */

            //qui si entra solo se il parametro categ non è null(quando si preme su una categoria)
          Categoria categoria = categoriaMap.get(a.getId_categoria());  //recupera la categoria
          if (filtroCategoria != null && !filtroCategoria.trim().isEmpty()) {
            if (categoria == null || !categoria.getTipologia().equalsIgnoreCase(filtroCategoria)) { //Se l’articolo non ha una categori// a associata oppure se la tipologia della categoria dell’articolo non corrisponde al filtro selezionato
              //permette di filtrare gli articoli
                mostra = false;
            }
          }

          if (mostra) {
            List<ImmagineArticolo> immagini = imgDAO.doRetrieveByArticolo(a.getCodice());
            String imgURL = "img/default.jpg";

            for (ImmagineArticolo img : immagini) {
              if (img.isIs_principale()) {
                imgURL = img.getUrl();
                break;
              }
            }
      %>
      <div class="articolo">
        <a href="DettaglioArticoloServlet?codice=<%=a.getCodice()%>">
          <img class="fotoArticolo" src="<%= imgURL %>" alt="<%= a.getNome() %>">
          <span class="nomeArticolo"><%= a.getNome() %></span>
        </a><br>
<%
    double prezzo = a.getPrezzo();
    double sconto = a.getSconto();
    double prezzoScontato = prezzo - (prezzo * sconto);
%>
<div class="prezzo-box">
    <span class="prezzo-scontato">€ <%= String.format(Locale.ITALY, "%.2f", prezzoScontato) %></span>
    <% if (sconto > 0) { %>
        <span class="prezzo-originale"><del>€ <%= String.format(Locale.ITALY, "%.2f", prezzo) %></del></span>
    <% } %>
</div>

      </div>

      <%
    }
    }
  %>
</div>
<jsp:include page="footerAreaUtente.jsp"></jsp:include>
</body>
</html>
