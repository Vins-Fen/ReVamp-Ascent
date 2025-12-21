package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import model.DAO.*;
import model.JavaBeans.*;

@WebServlet(name = "AggiungiArticoloServlet", value = "/AggiungiArticoloServlet")
public class AggiungiArticoloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            String colore = request.getParameter("colore");
            double sconto = Double.parseDouble(request.getParameter("sconto"));
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            double peso = Double.parseDouble(request.getParameter("peso"));
            String dimensione = request.getParameter("dimensione");
            int id_categoria = Integer.parseInt(request.getParameter("id_categoria"));


            Articolo articolo = new Articolo(codice, nome, descrizione, colore, sconto, prezzo, peso,dimensione,id_categoria);

            ArticoloDAO articoloDAO = new ArticoloDAO();
            if(articoloDAO.doRetrieveById(codice)!=null){
                request.setAttribute("errore","Esiste gia un articolo con questo codice");
                RequestDispatcher dispatcher=request.getRequestDispatcher("erroreInserimento.jsp");
                dispatcher.forward(request, response);
                return;
            }

            articoloDAO.doSave(articolo);

            response.sendRedirect("confermaInserimento.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore durante l'inserimento dell'articolo: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("erroreInserimento.jsp");
            dispatcher.forward(request, response);
        }
    }
}
