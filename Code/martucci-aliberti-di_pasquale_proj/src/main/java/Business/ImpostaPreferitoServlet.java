package Business;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.JavaBeans.*;
import dataAccess.DAO.*;

@WebServlet(name = "ImpostaPreferitoServlet", value = "/ImpostaPreferitoServlet")
public class ImpostaPreferitoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente=(Cliente) request.getSession().getAttribute("utenteLoggato");
        if(cliente==null){
            response.sendRedirect("login.jsp");
            return;
        }

        String idIndirizzoStr=request.getParameter("id_indirizzo");

        try{
            int id_indirizzo=Integer.parseInt(idIndirizzoStr);
            IndirizzoDAO indirizzoDAO=new IndirizzoDAO();
            indirizzoDAO.setIndirizzoPreferito(cliente.getNomeUtente(),id_indirizzo);
        }catch (NumberFormatException e) {
            e.printStackTrace(); // puoi gestirlo meglio con un messaggio
        }

        response.sendRedirect("VisualizzaIndirizziServlet");
    }
}