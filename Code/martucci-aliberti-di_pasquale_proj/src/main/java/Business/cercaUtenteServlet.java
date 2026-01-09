package Business;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import dataAccess.DAO.*;
import dataAccess.JavaBeans.*;
import java.util.*;

@WebServlet(name = "cercaUtenteServlet", value = "/cercaUtenteServlet")
public class cercaUtenteServlet extends HttpServlet {

    public cercaUtenteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            response.sendRedirect(request.getContextPath() + "/invalidLogin.jsp"); // Reindirizza a una pagina di errore/login
            return; // Termina l'esecuzione della servlet
        }

        String ricerca=request.getParameter("nomeUtente");
        ClienteDAO clienteDAO=new ClienteDAO();
        List<Cliente> clienti;
        clienti=clienteDAO.doRetrieveByUsernamePartial(ricerca); //vengono restituiti tutti se la ricerca è vuota

        request.setAttribute("listaClienti",clienti);
        if (ricerca != null) {
            request.setAttribute("termineRicerca", ricerca);
        } else {
            request.setAttribute("termineRicerca", "");
        }

        RequestDispatcher dispatcher=request.getRequestDispatcher("listaUtentiAdmin.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}