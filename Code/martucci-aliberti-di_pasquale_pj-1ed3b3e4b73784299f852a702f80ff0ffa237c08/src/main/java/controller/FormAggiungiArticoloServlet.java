package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import model.DAO.*;
import model.JavaBeans.*;
import java.util.*;

@WebServlet(name = "FormAggiungiArticoloServlet", value = "/FormAggiungiArticoloServlet")
public class FormAggiungiArticoloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> categorie = categoriaDAO.doRetrieveAll();

        request.setAttribute("categorie", categorie);
        RequestDispatcher dispatcher = request.getRequestDispatcher("aggiungiArticolo.jsp");
        dispatcher.forward(request, response);
    }

}
