<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 04/07/2025
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="dataAccess.JavaBeans.Categoria, dataAccess.DAO.CategoriaDAO, java.util.List" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>ReVamp Ascent - Admin</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@100;300;500;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

  <style>
    body {
      margin: 0;
      font-family: 'Raleway', sans-serif;
    }

    .header-admin {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px 40px;
      background: linear-gradient(to right, #2c3e50, #34495e);
      color: white;
      box-shadow: 0 2px 8px rgba(0,0,0,0.3);
    }

    .header-admin .left {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .header-admin .left img {
      height: 60px;
    }

    .header-admin .left h1 {
      font-size: 1.8em;
      margin: 0;
      font-weight: 700;
    }

    .header-admin .right {
      display: flex;
      gap: 15px;
    }

    .admin-button {
      background-color: #3498db;
      color: white;
      border: none;
      padding: 10px 18px;
      border-radius: 8px;
      text-decoration: none;
      font-weight: bold;
      display: flex;
      align-items: center;
      gap: 8px;
      transition: background-color 0.3s ease;
    }

    .admin-button i {
      font-size: 1rem;
    }

    .admin-button:hover {
      background-color: #2980b9;
    }

    @media (max-width: 768px) {
      .header-admin {
        flex-direction: column;
        align-items: flex-start;
        gap: 10px;
      }

      .header-admin .right {
        flex-direction: column;
        width: 100%;
      }

      .admin-button {
        width: 100%;
        justify-content: center;
      }
    }
  </style>
</head>

<body>

<div class="header-admin">
  <div class="left">
    <a href="admin.jsp">
      <img src="img/logo.webp" alt="Logo Admin">
    </a>
    <h1>ReVamp Admin</h1>
  </div>

  <div class="right">
    <a href="admin.jsp" class="admin-button"><i class="fa fa-home"></i> Home</a>
    <a href="LogoutServlet" class="admin-button"><i class="fa fa-sign-out-alt"></i> Logout</a>
  </div>
</div>

</body>
</html>
