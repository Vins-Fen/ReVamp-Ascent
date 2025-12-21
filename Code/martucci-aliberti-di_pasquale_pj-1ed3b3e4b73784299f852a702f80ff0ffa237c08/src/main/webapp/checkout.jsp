<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 10/07/2025
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@500&display=swap">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f6f8fa;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
            margin: 0;
        }

        .contenitore-checkout {
            text-align: center;
            padding: 40px;
            background-color: white;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            max-width: 700px;
            width: 95%;
        }

        .contenitore-checkout img {
            width: 250px;
            height: auto;
            margin-bottom: 30px;
        }

        .contenitore-checkout h1 {
            font-size: 2.2rem;
            margin-bottom: 15px;
            color: #2ecc71;
        }

        .contenitore-checkout p {
            font-size: 1.2rem;
            color: #555;
        }

        .btn-home {
            display: inline-block;
            margin-top: 30px;
            padding: 12px 24px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }

        .btn-home:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<div class="contenitore-checkout">
    <img src="img/check.gif" alt="Checkout in corso">
    <h1>Stiamo elaborando il tuo ordine...</h1>
    <p>Grazie per il tuo acquisto! Ti invieremo presto un'email di conferma.</p>
    <a href="home.jsp" class="btn-home">Torna alla Home</a>
</div>

</body>
</html>
