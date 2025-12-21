<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.JavaBeans.Admin" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ include file="headerAdmin.jsp" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Area Admin - Benvenuto <%= admin.getNome() %></title>
    <link rel="icon" type="image/x-icon" href="img/logo.webp">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            background-color: #f6f9fc;
            font-family: 'Inter', sans-serif;
        }
        .user-dashboard {
            max-width: 1000px;
            margin: 0 auto;
            padding: 30px 20px;
            animation: fadeIn 0.8s ease-in-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .user-header {
            text-align: center;
            margin-bottom: 40px;
        }
        .user-header h2 {
            color: #2c3e50;
            font-size: 2.6rem;
            margin-bottom: 10px;
            font-weight: 700;
        }
        .user-header h4 {
            font-weight: 500;
            color: #666;
        }
        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 25px;
            margin-bottom: 40px;
        }
        .menu-card {
            background: linear-gradient(to top, #ffffff, #f3f6f9);
            border-radius: 16px;
            padding: 30px 20px;
            text-align: center;
            box-shadow: 0 8px 18px rgba(0,0,0,0.06);
            transition: all 0.3s ease;
            text-decoration: none;
            color: #2c3e50;
            border: 1px solid #e3e8ee;
        }
        .menu-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 12px 24px rgba(0,0,0,0.1);
        }
        .menu-icon {
            width: 65px;
            height: 65px;
            margin: 0 auto 20px;
            object-fit: contain;
        }
        .menu-card p {
            font-weight: 600;
            font-size: 1.1rem;
            margin: 0;
        }
        .bott1 {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 12px 24px;
            font-size: 1rem;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 600;
            transition: background-color 0.3s ease;
        }
        .bott1:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>
<div class="user-dashboard">
    <div class="user-header">
        <h2>Benvenuto, <%= admin.getNome() %> <%= admin.getCognome() %>!</h2>
        <h4>Area riservata amministratore</h4>
    </div>
    <div class="menu-grid">
        <a href="FormAggiungiArticoloServlet" class="menu-card">
            <img src="img/aggiunta.png" alt="Aggiungi Articolo" class="menu-icon">
            <p>Aggiungi Articolo</p>
        </a>
        <a href="listaArticoli.jsp" class="menu-card">
            <img src="img/modifica.jpg" alt="Modifica Articolo" class="menu-icon">
            <p>Modifica Articolo</p>
        </a>
        <a href="ordini.jsp" class="menu-card">
            <img src="img/visOrdine.png" alt="Visualizza Ordini" class="menu-icon">
            <p>Visualizza Lista Ordini</p>
        </a>
        <a href="listaUtentiAdmin.jsp" class="menu-card">
            <img src="img/visualizza.png" alt="Visualizza utenti" class="menu-icon">
            <p>Visualizza Lista Utenti</p>
        </a>
    </div>
    <form action="LogoutServlet" method="get" style="text-align:center;">
        <input type="submit" value="Logout" class="bott1">
    </form>
</div>
</body>
</html>