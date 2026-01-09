package Business;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.DAO.*;
import dataAccess.JavaBeans.*;
import java.util.*;

@WebServlet(name = "RimuoviDalCarrelloServlet", value = "/RimuoviDalCarrelloServlet")
public class RimuoviDalCarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        Cliente utente=(Cliente) session.getAttribute("utenteLoggato");
        ClienteDAO clienteDAO=new ClienteDAO();
        String codArticoloStr=request.getParameter("codiceArticolo");
        if(codArticoloStr==null){
            response.sendRedirect("carrello.jsp");
            return;
        }

        int codiceArticolo=Integer.parseInt(codArticoloStr);

        if(utente!=null){
            CarrelloDAO carrelloDAO=new CarrelloDAO();
            carrelloDAO.rimuoviArticoloCarrello(utente.getNomeUtente(),codiceArticolo);
        }
        else{
            List<Carrello> carrello=(List<Carrello>) session.getAttribute("carrelloAnonimo");
            for(int i=0;i<carrello.size();i++){
                if(carrello.get(i).getCodiceArticolo() == codiceArticolo){
                    carrello.remove(i);
                    break;
                }
                session.setAttribute("carrelloAnonimo",carrello);
            }
        }

        response.sendRedirect("carrello.jsp");

    }
}