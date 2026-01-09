package Business;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dataAccess.JavaBeans.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ModificaCredenzialiServletTest {

    HttpServletRequest req;
    HttpServletResponse res;
    HttpSession session;
    RequestDispatcher dispatcher;
    Cliente fakeCliente;

    @BeforeEach
    void setUp() {
        req = mock(HttpServletRequest.class);
        res = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        fakeCliente = mock(Cliente.class);
        fakeCliente.setNomeUtente("Michela77");
        dispatcher= mock(RequestDispatcher.class);


        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);
        when(req.getRequestDispatcher("modificaCredenziali.jsp")).thenReturn(dispatcher);
        when(req.getParameter("username")).thenReturn("Michela77");

    }

    @Test
    void tc_1_6_1passAttuale_vuota() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        Cliente utente = mock(Cliente.class);
        when(utente.getNomeUtente()).thenReturn("Michela122");

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(utente);
        when(req.getRequestDispatcher("modificaCredenziali.jsp")).thenReturn(dispatcher);

        when(req.getParameter("username")).thenReturn("Michela122");
        when(req.getParameter("oldPassword")).thenReturn("");
        when(req.getParameter("newPassword")).thenReturn("");
        when(req.getParameter("confirmPassword")).thenReturn("");

        try (var mocked = mockConstruction(dataAccess.DAO.ClienteDAO.class,
                (mock, ctx) -> when(mock.checkLogin("Michela122", "WRONG")).thenReturn(null))) {
            new Business.ModificaCredenzialiServlet().doPost(req, res);
        }

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        // Nota: se vuoi evitare verify, vedi Metodo 2 sotto.
        verify(req).setAttribute(eq("msg"), captor.capture());

        assertEquals("La vecchia password non è corretta.", String.valueOf(captor.getValue()));
    }
    @Test
    public void TC1_6_2_usernameVuoto() throws ServletException, IOException {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        Cliente utente = mock(Cliente.class);
        when(utente.getNomeUtente()).thenReturn("");

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(utente);
        when(req.getRequestDispatcher("modificaCredenziali.jsp")).thenReturn(dispatcher);

        when(req.getParameter("oldPassword")).thenReturn("michi12345");
        when(req.getParameter("newPassword")).thenReturn("Michi99");
        when(req.getParameter("confirmPassword")).thenReturn("Michi99");

        try (var mocked = mockConstruction(dataAccess.DAO.ClienteDAO.class,
                (mock, ctx) -> when(req.getParameter("username")).thenReturn(null))) {
            new Business.ModificaCredenzialiServlet().doPost(req, res);
        }

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        verify(req).setAttribute(eq("msg"), captor.capture());

        assertEquals("username non valido", String.valueOf(captor.getValue()));
    }

    @Test
    public void TC1_6_3_usernameLungo() throws ServletException, IOException {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        Cliente utente = mock(Cliente.class);
        when(utente.getNomeUtente()).thenReturn("michshshhsjlljsaljsaljsalsjhsshsssdsdsdsdsd");

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(utente);
        when(req.getRequestDispatcher("modificaCredenziali.jsp")).thenReturn(dispatcher);

        when(req.getParameter("username")).thenReturn("michshshhsjlljsaljsaljsalsjhsshsssdsdsdsdsd");
        when(req.getParameter("oldPassword")).thenReturn("michi12345");
        when(req.getParameter("newPassword")).thenReturn("Michi99");
        when(req.getParameter("confirmPassword")).thenReturn("Michi99");

        new ModificaCredenzialiServlet().doPost(req,res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        verify(req).setAttribute(eq("msg"), captor.capture());

        assertEquals("username non valido", String.valueOf(captor.getValue()));
    }


}

