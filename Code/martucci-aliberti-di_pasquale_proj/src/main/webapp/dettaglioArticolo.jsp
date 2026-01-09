<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 29/06/2025
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.SimpleDateFormat, dataAccess.JavaBeans.Articolo, dataAccess.JavaBeans.ImmagineArticolo, dataAccess.JavaBeans.Recensione, dataAccess.JavaBeans.Cliente" %>
<%
    Articolo articolo = (Articolo) request.getAttribute("articolo");  //viene aperta solo se viene passato un articolo
    if (articolo == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<ImmagineArticolo> immagini = (List<ImmagineArticolo>) request.getAttribute("immagini");
    List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");
    Cliente utenteLoggato = (Cliente) session.getAttribute("utenteLoggato");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Articolo - <%= articolo.getNome() %></title>
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/stileRegistrazione.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        .stelle-valutazione i.fa-star {
            color: gold;
        }
        .add-to-cart-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 30px;
        }
        .add-to-cart-container p {
            margin-bottom: 8px;
            font-weight: 500;
            color: #333;
            font-size: 1.1rem;
        }
        .add-to-cart-container button:hover img {
            transform: scale(1.1);
            transition: transform 0.2s ease-in-out;
        }
        .zoom-container {
            position: relative;
            cursor: pointer;
        }
        .zoom-container img {
            transition: transform 0.3s ease;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.8);
        }
        .modal-content {
            display: block;
            margin: 5% auto;
            max-width: 90%;
            max-height: 80vh;
        }
        .close {
            position: absolute;
            top: 20px;
            right: 35px;
            color: #fff;
            font-size: 40px;
            font-weight: bold;
            cursor: pointer;
        }
        .notifica {
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #28a745;
            color: white;
            padding: 14px 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.3);
            z-index: 1000;
            font-size: 16px;
            animation: fadeinout 3s;
        }
        /* AGGIUNGI QUESTO */
        .messaggio-errore {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            text-align: center;
            font-weight: bold;
        }

        @keyframes fadeinout {
            0% {opacity: 0;}
            10% {opacity: 1;}
            90% {opacity: 1;}
            100% {opacity: 0;}
        }

    </style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<%
    double prezzoScontato = articolo.getPrezzo() * (1 - articolo.getSconto());
    String imgPrincipale = "img/default.jpg";
    List<String> altreImmagini = new ArrayList<>();
    if (immagini != null && !immagini.isEmpty()) {
        for (ImmagineArticolo img : immagini) {
            String urlImg = img.getUrl();
            if (img.isIs_principale()) {
                imgPrincipale = urlImg;
            } else {
                altreImmagini.add(urlImg);
            }
        }
        if ("img/default.jpg".equals(imgPrincipale) && !immagini.isEmpty()) {
            imgPrincipale = immagini.get(0).getUrl();
        }
    }
%>

<div class="contenitore-principale">
    <div class="immagine-articolo">
        <div class="zoom-container" onclick="apriZoom()">
            <img id="imgPrincipale" class="img-prodotto-principale" src="<%= imgPrincipale %>" alt="Immagine prodotto" />
        </div>

        <div class="galleria">
            <img src="<%= imgPrincipale %>" class="miniatura attiva" alt="Miniatura" onclick="cambiaImmaginePrincipale(this)" />
            <% for (String url : altreImmagini) {
                if (url != null && !url.trim().isEmpty()) { %>
            <img src="<%= url %>" class="miniatura" onclick="cambiaImmaginePrincipale(this)" />
            <% }} %>
        </div>
    </div>


    <div class="info-prodotto">
        <h1><%= articolo.getNome() %></h1>
        <p><%= articolo.getDescrizione() %></p>

        <p class="prezzo">
            € <%= String.format(Locale.ITALY, "%.2f", prezzoScontato) %>
            <% if (articolo.getSconto() > 0) { %>
            <del>€ <%= String.format(Locale.ITALY, "%.2f", articolo.getPrezzo()) %></del> <!--imposta barra su prezzo -->
            <% } %>
        </p>

        <p><strong>Colore:</strong> <%= articolo.getColore() %></p>


        <div class="dettagli-extra">
            <p><strong>Spedizione:</strong> Gratis</p>
            <p><strong>Disponibilità:</strong> In pronta consegna</p>
            <p><strong>Peso:</strong> <%= articolo.getPeso() %> kg</p>
            <p><strong>Dimensioni:</strong> <%= articolo.getDimensione() %> cm</p>
        </div>

        <form id="carrelloForm">
            <input type="hidden" name="idArticolo" value="<%= articolo.getCodice() %>" />
            <div class="quantita-container">
                <label for="quantita">Quantità:</label>
                <button type="button" onclick="modificaQuantita(-1)">−</button>
                <input type="number" id="quantita" name="quantita" value="1" min="1" />
                <button type="button" onclick="modificaQuantita(1)">+</button>
            </div>
            <button type="submit" class="pulsante-carrello">AGGIUNGI AL CARRELLO</button>
        </form>
        <div id="notifica-aggiunta" class="notifica" style="display:none;">Articolo aggiunto al carrello!</div>


        <div class="banner-info-servizi">
            <img src="img/no-iva-2022.png" alt="Servizi informativi" />
        </div>
    </div>

</div>




<div class="recensioni">
    <h2>Recensioni</h2>
    <% if (recensioni == null || recensioni.isEmpty()) { %>
    <p>Nessuna recensione per questo articolo.</p>
    <% } else {
        for (Recensione r : recensioni) { %>
    <div class="recensione-singola">
        <strong><%= r.getNome_utente() != null ? r.getNome_utente() : "Anonimo" %></strong>
        <small> - <%= r.getData() != null ? sdf.format(r.getData().getTime()) : "" %></small>
        <div class="stelle-valutazione">
            <% int voto = r.getValutazione();
                for (int i = 1; i <= 5; i++) {
                    if (i <= voto) { %>
            <i class="fa-solid fa-star"></i>
            <% } else { %>
            <i class="fa-regular fa-star"></i>
            <% }
            } %>
        </div>
        <p><%= r.getTesto() %></p>
    </div>
    <% } } %>
</div>

<% if (utenteLoggato != null) { %>
<div class="scrivi-recensione">
    <h3>Lascia una recensione</h3>
    <%
        String error = request.getParameter("error");
        if(error != null) {
    %>
    <div class="messaggio-errore">
        <%
            if(error.equals("datiMancanti")) {
                out.print("Errore: Devi compilare Titolo, Testo e selezionare le Stelle!");
            } else if(error.equals("titoloTroppoLungo")) {
                out.print("Errore: Il titolo è troppo lungo (Max 50 caratteri).");
            } else if(error.equals("testoTroppoLungo")) {
                out.print("Errore: Il testo è troppo lungo (Max 500 caratteri).");
            } else if(error.equals("generico")) {
                out.print("Errore generico durante il salvataggio.");
            }
        %>
    </div>
    <% } %>
    <form action="RecensioneServlet" method="post">
        <input type="hidden" name="idArticolo" value="<%= articolo.getCodice() %>">
        <div class="stelle" title="Vota con le stelle">
            <input type="radio" id="stella5" name="valutazione" value="5" ><label for="stella5">&#9733;</label>
            <input type="radio" id="stella4" name="valutazione" value="4" ><label for="stella4">&#9733;</label>
            <input type="radio" id="stella3" name="valutazione" value="3" ><label for="stella3">&#9733;</label>
            <input type="radio" id="stella2" name="valutazione" value="2" ><label for="stella2">&#9733;</label>
            <input type="radio" id="stella1" name="valutazione" value="1" ><label for="stella1">&#9733;</label>
        </div>
        <input type="text" name="titolo" placeholder="Scrivi un titolo..." required>
        <textarea name="testo" rows="5" placeholder="Racconta la tua esperienza con questo prodotto..." required></textarea>
        <button type="submit" class="btn"><i class="fa fa-pencil-alt"></i> Invia recensione</button>
    </form>
</div>
<% } else { %>
<p style="text-align: center; margin-top: 30px;">Per scrivere una recensione devi essere <a href="login.jsp">loggato</a>.</p>
<% } %>

<!-- Modale Zoom -->
<div id="modalZoom" class="modal" onclick="chiudiZoom()">
    <span class="close" onclick="chiudiZoom()">&times;</span>
    <img class="modal-content" id="imgZoom">
</div>

<script>
    function cambiaImmaginePrincipale(img) {
        const imgPrincipale = document.getElementById('imgPrincipale');
        imgPrincipale.src = img.src; // Cambia la sorgente dell’immagine principale assegnandole la stessa src (URL dell'immagine) della miniatura cliccata.In questo modo la miniatura selezionata diventa l’immagine grande.
        document.querySelectorAll('.galleria img.miniatura').forEach(el => el.classList.remove('attiva'));// Seleziona tutte le immagini con classe miniatura dentro l'elemento con classe galleria.Rimuove da tutte la classe attiva, che viene usata per evidenziare la miniatura selezionata
        img.classList.add('attiva'); // Aggiunge la classe attiva alla miniatura cliccata, così è evidenziata visivamente.
    }

    function apriZoom() {
        const modal = document.getElementById("modalZoom");
        const zoomImg = document.getElementById("imgZoom");
        const imgPrincipale = document.getElementById("imgPrincipale");
        zoomImg.src = imgPrincipale.src;  //copia l immagine principale nella modale
        modal.style.display = "block";  //la mostra
    }

    function chiudiZoom() {
        document.getElementById("modalZoom").style.display = "none";
    }


    function modificaQuantita(delta) {
        const input = document.getElementById("quantita");
        let val = parseInt(input.value);
        if (!isNaN(val)) {
            val += delta;
            if (val < 1) val = 1;
            input.value = val;
        }
    }

    document.getElementById("carrelloForm").addEventListener("submit", function (e) {
        e.preventDefault();  //evita di ricaricare la pagina

        const idArticolo = document.querySelector("input[name='idArticolo']").value;  //legge i dati del form
        const quantita = document.querySelector("input[name='quantita']").value;

        const xhr = new XMLHttpRequest();  //prepara richiesta ajax
        xhr.open("POST", "CarrelloServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); //tipo di un contenuto

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) { //se la risposta è completamente ricevuta e il server ha risposto con successo
                mostraNotifica();
                mostraBadge();
            }
        };
        xhr.send("idArticolo=" + encodeURIComponent(idArticolo) + "&quantita=" + encodeURIComponent(quantita));  //invia i dati al server
    });

    function mostraNotifica() {
        const notifica = document.getElementById("notifica-aggiunta");
        notifica.style.display = "block";
        notifica.style.opacity = "1";  //lo rende visibile

        setTimeout(() => {
            notifica.style.opacity = "0";
            setTimeout(() => {
                notifica.style.display = "none";
            }, 500); //Dopo altri 500 ms, lo nasconde completamente con display: none
        }, 3000);  //Dopo 3 secondi (3000 ms) imposta opacity: 0 (effetto dissolvenza)
    }


    function mostraBadge() {
        const badge = document.getElementById("carrello-badge");
        if (badge) {
            badge.textContent = ""; // Nessun numero
            badge.style.display = "inline-block";
        }
    }


</script>

<jsp:include page="footerAreaUtente.jsp"></jsp:include>
</body>
</html>