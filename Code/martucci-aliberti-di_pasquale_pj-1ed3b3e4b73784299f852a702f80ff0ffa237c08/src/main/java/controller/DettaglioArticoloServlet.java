package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import model.JavaBeans.*;
import java.sql.*;
import java.util.*;
import model.DAO.*;

@WebServlet(name = "DettaglioArticoloServlet", value = "/DettaglioArticoloServlet")
public class DettaglioArticoloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codiceStr = request.getParameter("codice");

        if (codiceStr == null || codiceStr.isEmpty()) {
            response.sendRedirect("invalidLogin.jsp");
            return;
        }

        int codice = Integer.parseInt(codiceStr);

        ArticoloDAO artDAO=new ArticoloDAO();
        ImmagineArticoloDAO imDAO=new ImmagineArticoloDAO();
        RecensioneDAO recDAO=new RecensioneDAO();

        Articolo articolo=artDAO.doRetrieveById(codice);
        List<ImmagineArticolo> immagini=imDAO.doRetrieveByArticolo(codice);
        List<Recensione> recensioni=recDAO.doRetrieveByCodiceArticolo(codice);


        if (articolo == null) {
            response.sendRedirect("errore.jsp");
            return;
        }
        
        request.setAttribute("articolo", articolo);
        request.setAttribute("immagini", immagini);
        request.setAttribute("recensioni", recensioni);

        RequestDispatcher dispatcher=request.getRequestDispatcher("dettaglioArticolo.jsp");
        dispatcher.forward(request,response);


    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}