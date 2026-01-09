package Business;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.DAO.*;
import dataAccess.JavaBeans.*;

import static java.lang.System.out;


@WebServlet(name = "AggiungiIndirizzoServlet", value = "/AggiungiIndirizzoServlet")
public class AggiungiIndirizzoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente=(Cliente) request.getSession().getAttribute("utenteLoggato");
        if(cliente==null){
            response.sendRedirect("index.jsp");
            return;
        }

        HttpSession session=request.getSession();

        String via=request.getParameter("via");
        String citta=request.getParameter("citta");
        String cap=request.getParameter("cap");
        String provincia=request.getParameter("provincia");
        String paese=request.getParameter("paese");
        boolean preferito=request.getParameter("preferito")!=null;
        String nome_utente=((Cliente) session.getAttribute("utenteLoggato")).getNomeUtente();

        if(via==null || via.isBlank() || citta==null || citta.isBlank() || cap== null || cap.isBlank() || provincia==null|| provincia.isBlank()|| paese==null || paese.isBlank() || nome_utente== null || nome_utente.isBlank()) {
            out.print("{\"success\":false, \"message\":\"Parametri mancanti\"}");
            return;
        }

        Indirizzo indirizzo = new Indirizzo(0, via, citta, provincia, cap, paese, preferito, nome_utente);

        IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
        indirizzoDAO.doSave(indirizzo);

        response.sendRedirect("VisualizzaIndirizziServlet");


    }
}