<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 24/06/2025
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Informazioni Consegna - ReVamp Ascent</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <style>
        body, html {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            height: 100%;
        }

        .page-info {
            position: relative;
            background: url("img/showroom2.png") no-repeat center center/cover;
            min-height: 130vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding-bottom: 150px;
        }

        .page-info::before {
            content: "";
            position: absolute;
            top: 0; left: 0;
            width: 100%;
            height: 100%;
            backdrop-filter: blur(5px) brightness(0.5);
            background-color: rgba(0, 0, 0, 0.4);
            z-index: 1;
        }

        .info-box {
            position: relative;
            z-index: 2;
            background-color: white;
            padding: 40px;
            border-radius: 16px;
            max-width: 800px;
            width: 90%;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            color: #2c3e50;
        }

        .info-box h2 {
            font-size: 2.4rem;
            margin-bottom: 20px;
            text-align: center;
        }

        .info-box p {
            font-size: 1.1rem;
            line-height: 1.7;
            margin-bottom: 16px;
        }

        .info-box ul {
            padding-left: 20px;
        }

        .info-box ul li {
            margin-bottom: 10px;
            font-size: 1.05rem;
        }
    </style>
</head>
<body>

<div class="page-info">
    <div class="info-box">
        <h2>Informazioni sulla Consegna</h2>
        <p>ReVamp Ascent garantisce consegne rapide, sicure e tracciabili in tutta Italia. Ecco tutto ciò che devi sapere:</p>

        <ul>
            <li><strong>Tempi di spedizione:</strong> 2-5 giorni lavorativi dalla conferma dell’ordine.</li>
            <li><strong>Corrieri utilizzati:</strong> SDA, Bartolini, GLS.</li>
            <li><strong>Ritardi:</strong> In caso di ritardo riceverai una notifica automatica.</li>
            <li><strong>Ritiro in sede:</strong> Disponibile previo appuntamento presso il nostro showroom.</li>
        </ul>

        <p>Per ulteriori dettagli o assistenza, contattaci all’indirizzo <strong>info@revampascent.it</strong>.</p>
    </div>
</div>

<jsp:include page="footerAreaUtente.jsp" />

</body>
</html>
