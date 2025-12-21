package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.DAO.ArticoloDAO;
import model.JavaBeans.Articolo;

import java.io.IOException;

@WebServlet(name = "SalvaModificaArticoloServlet", value = "/SalvaModificaArticoloServlet")
public class SalvaModificaArticoloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("admin") == null){
            response.sendRedirect("login.jsp");
            return;
        }

        try{
            int codice=Integer.parseInt(request.getParameter("codice"));
            String nome = request.getParameter("nome");
            String colore = request.getParameter("colore");
            String descrizione=request.getParameter("descrizione");
            double sconto = Double.parseDouble(request.getParameter("sconto"));
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            double peso = Double.parseDouble(request.getParameter("peso"));
            String dimensione = request.getParameter("dimensione");
            int id_categoria = Integer.parseInt(request.getParameter("id_categoria"));

            Articolo articolo = new Articolo(
                    codice,
                    nome,
                    descrizione,
                    colore,
                    sconto,
                    prezzo,
                    peso,
                    dimensione,
                    id_categoria
            );

            ArticoloDAO articoloDAO=new ArticoloDAO();
            articoloDAO.doUpdate(articolo);

            response.sendRedirect("listaArticoli.jsp");
        }catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore nella modifica dell'articolo.");
            request.getRequestDispatcher("errore.jsp").forward(request, response);
        }
    }
}