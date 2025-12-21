<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 13/07/2025
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.DAO.ArticoloDAO, model.DAO.ImmagineArticoloDAO, model.DAO.CategoriaDAO" %>
<%@ page import="model.JavaBeans.Articolo, model.JavaBeans.ImmagineArticolo, model.JavaBeans.Categoria" %>
<%@ page import="java.util.*, java.math.BigDecimal" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/homePage.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <title>ReVamp Ascent - Home</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300;500;700&display=swap" rel="stylesheet">
    <style>
        .hero-promo {
            background: url("<%= request.getContextPath() %>/img/heroPromo.png") no-repeat center center/cover;
            /*usiamo request.getcontextpath per assicurarsi che il path funzioni correttamente
            da qualsiasi pagina del sito, anche se non si trova nella root.*/

            color: white;
            padding: 100px 20px;
            text-align: center;
        }

    </style>

</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="hero-promo">
    <div class="hero-promo-content">
        <div class="spedizione">Nuova Collezione</div>
        <div class="spedizione">Spedizione Gratuita</div>
        <h1>Scopri i nuovi arrivi per ogni ambiente della tua casa</h1>
        <a href="catalogo.jsp" class="btn-approfitta">Scopri ora</a>
    </div>
</div>


<section class="presentazione">
    <h2>🏡 Benvenuto su ReVamp Ascent</h2>
    <p>
        Scopri il nostro mondo di arredamento moderno e funzionale.
        <strong>Mobili di design</strong>, <strong>illuminazione elegante</strong> e soluzioni curate per dare nuova vita ai tuoi spazi.
    </p>
</section>


<div class="sconti-section">
    <h2>Approfittane Ora</h2>

    <div class="carousel-container">
        <button class="carousel-btn left" onclick="scrollCarousel(-1)">&#8592;</button>

        <div class="carousel" id="carousel">
            <%
                ArticoloDAO articoloDAO = new ArticoloDAO();
                ImmagineArticoloDAO imgDAO = new ImmagineArticoloDAO();
                List<Articolo> articoliScontati = articoloDAO.doRetrieveConSconto();

                for (Articolo articolo : articoliScontati) {
                    List<ImmagineArticolo> immagini = imgDAO.doRetrieveByArticolo(articolo.getCodice());
                    String imgPath = immagini != null && !immagini.isEmpty() ? immagini.get(0).getUrl() : "img/default.jpg";
                    double prezzoScontato = articolo.getPrezzo() * (1 - articolo.getSconto());
            %>
            <a class="card-prodotto" href="DettaglioArticoloServlet?codice=<%= articolo.getCodice() %>">
                <div class="badge-sconto">SALDI</div>
                <img src="<%= imgPath %>" alt="immagine">
                <h4><%= articolo.getNome() %></h4>
                <p>€ <%= String.format("%.2f", prezzoScontato) %></p>
            </a>


            <%
                }
            %>
        </div>

        <button class="carousel-btn right" onclick="scrollCarousel(1)">&#8594;</button>
    </div>
</div>



<section class="categorie-section">
<h2>Esplora per Categoria</h2>
    <div class="categorie-carousel-container">
        <button class="carousel-btn left" onclick="scrollCategorie(-1)">&#8592;</button>
        <div class="categorie-carousel" id="categorieCarousel">
            <%
                CategoriaDAO categoriaDAO=new CategoriaDAO();
                List<Categoria> categorie=categoriaDAO.doRetrieveAll();

                Map<String,String> immaginiCategoria=new HashMap<>();

                immaginiCategoria.put("Mobili", "img/mobili.png");
                immaginiCategoria.put("Sedie", "img/sedie.png");
                immaginiCategoria.put("Divani", "img/divani.png");
                immaginiCategoria.put("Letti", "img/letti.png");
                immaginiCategoria.put("Illuminazione", "img/illuminazione.png");
                immaginiCategoria.put("Tavolini", "img/tavolini.png");
                immaginiCategoria.put("Accessori", "img/accessori.png");
                immaginiCategoria.put("Esterni", "img/esterni.png");
                immaginiCategoria.put("Bambini", "img/bambini.png");

                for(Categoria categoria: categorie){
                    String tipologia = categoria.getTipologia();
                    String imgCat = immaginiCategoria.getOrDefault(tipologia, "img/default.jpg");
            %>
            <a class="card-prodotto" href="catalogo.jsp?categ=<%=tipologia%>">
                 <img src="<%= imgCat %>" alt="<%= tipologia %>">
                <h4><%= tipologia %></h4>
            </a>

            <% } %>
        </div>
        <button class="carousel-btn right" onclick="scrollCategorie(1)">&#8594;</button>
    </div>

</section>


<section class="inspirazione-section">
    <h2>Decora, Condividi, Lasciati Ispirare</h2>
    <div class="inspirazione-grid">
        <%
            List<Articolo> tutti=articoloDAO.doRetriveByAll();
            Collections.shuffle(tutti);

            int max=Math.min(8,tutti.size());  //prendi il numero piu piccolo tra 8 e tutti.size().
                                                //viene fatto questo controllo nel caso in cui non ci fossero almeno 8 articoli
            for(int i=0; i<max;i++){
                Articolo articolo=tutti.get(i);
                List<ImmagineArticolo> immagini=imgDAO.doRetrieveByArticolo(articolo.getCodice());
                String imgPath="img/default.jpg";
                if(immagini!=null && !immagini.isEmpty()){
                    imgPath=immagini.get(0).getUrl();
                }

                %>
        <div class="inspirazione-item">
            <img src="<%=imgPath%>" alt="<%=articolo.getNome()%>">
           <a class="lo-voglio-btn" href="DettaglioArticoloServlet?codice=<%=articolo.getCodice() %>">LO VOGLIO!</a>
        </div>
        <% } %>
    </div>
</section>

<jsp:include page="footerAreaUtente.jsp"></jsp:include>

<script>
        function scrollCarousel(direction) {
        const carousel = document.getElementById('carousel');
        const scrollAmount = 300;  //Imposta la quantità di pixel da scorrere orizzontalmente//
             carousel.scrollBy({  //Usa il metodo scrollBy() per spostare orizzontalmente il contenuto
        left: scrollAmount * direction,  //Se direction è 1, scorre verso destra di 300px.Se direction è -1, scorre verso sinistra di 300px.
        behavior: 'smooth' //lo rende animato
    });
    }

        function scrollCategorie(direction) {
        const categorieCarousel = document.getElementById('categorieCarousel');
        const scrollAmount = 300;
        categorieCarousel.scrollBy({
        left: scrollAmount * direction,
        behavior: 'smooth'
    });
    }
</script>
</body>
</html>
