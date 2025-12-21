<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 25/06/2025
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Chi siamo - ReVamp Ascent</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <style>
        body, html {
            margin: 0;
            padding: 0;
            font-family: 'Inter', sans-serif;
        }

        .hero {
            background: url('img/casa_sfondo.png') center/cover no-repeat;
            height: 70vh;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            color: white;
            text-align: center;
        }

        .hero::after {
            content: "";
            position: absolute;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .hero-content {
            position: relative;
            z-index: 2;
            max-width: 800px;
            padding: 20px;
            animation: fadeUp 1s ease-out;
        }

        .hero-content h1 {
            font-size: 3rem;
            margin-bottom: 10px;
        }

        .hero-content p {
            font-size: 1.2rem;
            font-weight: 300;
        }

        .section {
            padding: 60px 20px;
            max-width: 1000px;
            margin: 0 auto;
            animation: fadeIn 1s ease;
        }

        .section h2 {
            font-size: 2rem;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        .section p {
            font-size: 1.1rem;
            color: #555;
            line-height: 1.6;
        }

        .values {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 40px;
            margin-top: 40px;
        }

        .value-box {
            flex: 1 1 200px;
            max-width: 250px;
            background-color: #f9fbfc;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.07);
            padding: 20px;
            text-align: center;
        }

        .value-box img {
            width: 50px;
            margin-bottom: 15px;
        }

        .value-box h3 {
            margin-bottom: 10px;
            color: #2c3e50;
        }

        .value-box p {
            color: #555;
            font-size: 0.95rem;
        }
        /*@keyframes permettono di dire al browser come un elemento deve cambiare nel tempo.*/
        @keyframes fadeUp {  /* permette all elemento di apparirre gradualmente */
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
    </style>
</head>
<body>
<div class="hero">
    <div class="hero-content">
        <h1>Chi siamo</h1>
        <p>La nostra passione per l’arredo incontra l’innovazione</p>
    </div>
</div>

<div class="section">
    <h2>Il nostro obiettivo</h2>
    <p>
        ReVamp Ascent nasce dall'idea di tre studenti dell'Università di Salerno, Antonio Aliberti, Raffaella Di Pasquale e Vincenzo Martucci ,con l’ambizione di offrire soluzioni d’arredo di qualità, moderne e accessibili.
        Ispirandoci allo stile dei grandi brand come IKEA, proponiamo un’esperienza intuitiva, personalizzabile e vicina alle esigenze di ogni cliente.
        Ogni prodotto viene scelto con attenzione per qualità, sostenibilità e design.
    </p>

    <h2>La nostra missione</h2>
    <p>
        Vogliamo rendere ogni spazio un luogo da vivere e amare. Dal soggiorno alla cameretta, dalla cucina all’ufficio,
        ReVamp Ascent ti accompagna in ogni fase della trasformazione della tua casa.
    </p>

    <div class="values">
        <div class="value-box">
            <img src="img/qualita.png" alt="Qualità">
            <h3>Qualità</h3>
            <p>Materiali scelti con cura e lavorazioni affidabili per garantire durata e comfort.</p>
        </div>
        <div class="value-box">
            <img src="img/design.webp" alt="Design">
            <h3>Design</h3>
            <p>Stile moderno e minimale per ambienti accoglienti e funzionali.</p>
        </div>
        <div class="value-box">
            <img src="img/sostenibilita.jpg" alt="Sostenibilità">
            <h3>Sostenibilità</h3>
            <p>Scelte consapevoli per ridurre l’impatto ambientale e favorire il riciclo.</p>
        </div>
    </div>
</div>
<jsp:include page="footerAreaUtente.jsp"></jsp:include>

</body>
</html>
