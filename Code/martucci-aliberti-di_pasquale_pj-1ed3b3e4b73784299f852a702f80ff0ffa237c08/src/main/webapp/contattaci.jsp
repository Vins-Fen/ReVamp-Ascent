<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 24/06/2025
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Contattaci - ReVamp Ascent</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <style>
        body, html {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            height: 100%;
        }

        .page-contact {
            position: relative;
            background: url("img/showroom1.png") no-repeat center center/cover;
            min-height: 140vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding-bottom: 200px;
        }



        .page-contact::before {
            content: "";
            position: absolute;
            top: 0; left: 0;
            width: 100%;
            height: 100%;
            backdrop-filter: blur(5px) brightness(0.5);
            background-color: rgba(0, 0, 0, 0.4);
            z-index: 1;
        }

        .contact-box {
            position: relative;
            z-index: 2;
            background-color: white;
            padding: 40px 30px;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            max-width: 600px;
            width: 90%;
            text-align: center;
        }

        .contact-box h2 {
            font-size: 2.2rem;
            margin-bottom: 20px;
            color: #2c3e50;
        }

        .contact-box p {
            font-size: 1.1rem;
            margin: 10px 0;
            color: #333;
        }

        .socials a {
            text-decoration: none;
            margin: 0 10px;
            color: #3498db;
            font-weight: bold;
        }

        .socials a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="page-contact">
    <div class="contact-box">
        <h2>Contattaci</h2>
        <p>Email: <strong>info@revampascent.it</strong></p>
        <p>Telefono: <strong>+39 081 123 4567</strong></p>
        <div class="socials">
            <a href="#"><i class="fab fa-facebook"></i> Facebook</a>
            <a href="#"><i class="fab fa-instagram"></i> Instagram</a>
            <a href="#"><i class="fab fa-twitter"></i> Twitter</a>
        </div>
    </div>
</div>
<jsp:include page="footerAreaUtente.jsp"></jsp:include>

</body>
</html>

