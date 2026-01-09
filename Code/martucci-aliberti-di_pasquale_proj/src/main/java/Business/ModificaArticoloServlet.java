package Business;

import dataAccess.DAO.*;
import dataAccess.JavaBeans.*;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ModificaArticoloServlet", value = "/ModificaArticoloServlet")
public class ModificaArticoloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session==null || session.getAttribute("admin")==null){
            response.sendRedirect("login.jsp");
            return;
        }

        String codiceParam=request.getParameter("codice");
        if(codiceParam==null){
            response.sendRedirect("listaArticoli.jsp");
            return;
        }

        try{
            int codice=Integer.parseInt(codiceParam);
            ArticoloDAO artDAO=new ArticoloDAO();
            CategoriaDAO catDAO=new CategoriaDAO();
            ImmagineArticoloDAO immagineArticoloDAO=new ImmagineArticoloDAO();

            Articolo articolo=artDAO.doRetrieveById(codice);
            List<Categoria> categorie=catDAO.doRetrieveAll();
            List<ImmagineArticolo> immagini = immagineArticoloDAO.doRetrieveByArticolo(codice);
            String urlImmaginePrincipale = null;
            for (ImmagineArticolo img : immagini) {
                if (img.isIs_principale()) {
                    urlImmaginePrincipale = img.getUrl();
                    break;
                }
            }

            request.setAttribute("articolo", articolo);
            request.setAttribute("categorie", categorie);
            request.setAttribute("urlImmaginePrincipale", urlImmaginePrincipale);


            request.getRequestDispatcher("modificaArticolo.jsp").forward(request, response);
        }catch(NumberFormatException e){
            response.sendRedirect("listaArticoli.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}