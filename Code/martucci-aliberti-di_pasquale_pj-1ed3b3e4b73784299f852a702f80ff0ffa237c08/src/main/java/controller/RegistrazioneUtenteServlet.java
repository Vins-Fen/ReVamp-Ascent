package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.JavaBeans.*;
import model.DAO.*;

import java.io.IOException;
@WebServlet(name = "Registrazione", value = "/RegistrazioneUtenteServlet")
public class RegistrazioneUtenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // delega tutto al POST
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");  //serve a specificare la codifica dei caratteri per i dati ricevuti dal client

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confermaPassword = request.getParameter("confermaPassword");
        String username = request.getParameter("username");
        String sesso = request.getParameter("sesso");

        int eta = 0;
        String telefono = "N/D";

        ClienteDAO dao = new ClienteDAO();

        try {
            if (nome == null || cognome == null || email == null || password == null || confermaPassword == null || username == null || sesso == null ||
                    nome.isBlank() || cognome.isBlank() || email.isBlank() || password.isBlank() || confermaPassword.isBlank() || username.isBlank()) {
                response.sendRedirect("registrazione.jsp?errore=campi");
                return;
            }

            if (!password.equals(confermaPassword)) {
                response.sendRedirect("registrazione.jsp?errore=password");
                return;
            }

            if (dao.doRetrieveByUsername(username) != null) {
                response.sendRedirect("registrazione.jsp?errore=username");
                return;
            }

            if (dao.doRetrieveByEmail(email) != null) {
                response.sendRedirect("registrazione.jsp?errore=email");
                return;
            }


            Cliente nuovoCliente = new Cliente(username, password, nome, cognome, email, sesso, eta, telefono);
            dao.doSave(nuovoCliente);
            response.sendRedirect("login.jsp?success=reg");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("registrazione.jsp?errore=generico");
        }
    }
}
