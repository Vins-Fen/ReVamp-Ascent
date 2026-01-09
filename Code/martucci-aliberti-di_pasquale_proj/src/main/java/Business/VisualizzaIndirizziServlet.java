package Business;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.JavaBeans.*;
import java.util.*;
import dataAccess.DAO.*;

@WebServlet(name = "VisualizzaIndirizziServlet", value = "/VisualizzaIndirizziServlet")
public class VisualizzaIndirizziServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Cliente cliente=(Cliente) session.getAttribute("utenteLoggato");

        if(cliente==null){
            response.sendRedirect("login.jsp");
            return;
        }

        IndirizzoDAO indirizzoDAO=new IndirizzoDAO();


        System.out.println("Cliente dalla sessione: " + cliente.getNomeUtente());

        List<Indirizzo> listaIndirizzi = indirizzoDAO.doRetrieveByUser(cliente.getNomeUtente());
        System.out.println("Numero indirizzi trovati: " + listaIndirizzi.size());
        for (Indirizzo i : listaIndirizzi) {
            System.out.println(i.getVia() + ", " + i.getCitta() + ", preferito: " + i.isPreferito());
        }


        request.setAttribute("listaIndirizzi", listaIndirizzi);
        RequestDispatcher dispatcher=request.getRequestDispatcher("indirizzi.jsp");


        System.out.println("Cliente dalla sessione: " + cliente.getNomeUtente());


        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}