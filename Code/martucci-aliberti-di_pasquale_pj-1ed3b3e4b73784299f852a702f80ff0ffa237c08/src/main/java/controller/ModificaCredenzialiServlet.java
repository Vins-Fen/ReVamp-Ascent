package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import model.DAO.ClienteDAO;
import model.JavaBeans.*;
import java.sql.SQLException;

@WebServlet(name = "ModificaCredenzialiServlet", value = "/ModificaCredenzialiServlet")
public class ModificaCredenzialiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente utente = (Cliente) request.getSession().getAttribute("utenteLoggato");
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = request.getParameter("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        username = username.trim();
        oldPassword = oldPassword.trim();
        newPassword = newPassword.trim();
        confirmPassword = confirmPassword.trim();

        ClienteDAO clienteDAO = new ClienteDAO();

        try {
            Cliente autenticato = clienteDAO.checkLogin(utente.getNomeUtente(), oldPassword);
            if (autenticato == null) {
                request.setAttribute("msg", "La vecchia password non è corretta.");
                request.getRequestDispatcher("modificaCredenziali.jsp").forward(request, response);
                return;
            }

            String vecchioUsername = utente.getNomeUtente();

            if (!vecchioUsername.equals(username)) {
                Cliente altro = clienteDAO.doRetrieveByUsername(username);
                if (altro != null) {
                    request.setAttribute("msg", "Username già in uso.");
                    request.getRequestDispatcher("modificaCredenziali.jsp").forward(request, response);
                    return;
                }
                utente.setNomeUtente(username);
            }

            boolean cambiaPassword = false;    //serve per capire se l utente vuole aggiornare solo il nome utente
                                               //o anche la password
            if (!newPassword.isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    request.setAttribute("msg", "Le nuove password non corrispondono.");
                    request.getRequestDispatcher("modificaCredenziali.jsp").forward(request, response);
                    return;
                }
                utente.setPass(newPassword);
                cambiaPassword = true;
            }

            clienteDAO.doUpdate(utente, vecchioUsername, cambiaPassword);
            request.getSession().setAttribute("utenteLoggato", utente);  //serve per aggiornare i dati dell'utente loggato nella sessione, dopo che ha modificato le sue credenziali
            request.setAttribute("msg", "Credenziali modificate con successo.");
            request.getRequestDispatcher("modificaCredenziali.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "Errore durante l'aggiornamento dei dati.");
            request.getRequestDispatcher("modificaCredenziali.jsp").forward(request, response);
        }
    }
}



