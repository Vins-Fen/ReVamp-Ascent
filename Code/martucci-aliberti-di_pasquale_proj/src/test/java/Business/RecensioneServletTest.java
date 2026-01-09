package Business;

import jakarta.servlet.ServletException;
import dataAccess.DAO.RecensioneDAO;
import dataAccess.JavaBeans.Recensione;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dataAccess.JavaBeans.Cliente;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RecensioneServletTest {
    @Test
    public void TC_3_1_1_valutazioneMancante() throws ServletException, IOException {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Cliente fakeCliente = mock(Cliente.class);
        when(fakeCliente.getNomeUtente()).thenReturn("Michela122");

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);

        when(req.getParameter("titolo")).thenReturn("Parere personale");
        when(req.getParameter("testo")).thenReturn("Non entusiasma troppo mio fratello");
        when(req.getParameter("idArticolo")).thenReturn("40");

        // TC_3.1_1: valutazione non inserita => parametro assente (null)
        when(req.getParameter("valutazione")).thenReturn(null);

        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);

        try (MockedConstruction<RecensioneDAO> daoCtor = mockConstruction(RecensioneDAO.class)) {


            new RecensioneServlet().doPost(req, res);

            verify(res).sendRedirect(redirectCaptor.capture());

            assertEquals(
                    "DettaglioArticoloServlet?codice=40&error=datiMancanti",
                    redirectCaptor.getValue()
            );

        }
    }

    @Test
    public void TC_3_1_2_titoloMancante() throws ServletException, IOException {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Cliente fakeCliente = mock(Cliente.class);
        when(fakeCliente.getNomeUtente()).thenReturn("Michela122");

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);

        when(req.getParameter("valutazione")).thenReturn("3");
        when(req.getParameter("titolo")).thenReturn(null);
        when(req.getParameter("testo")).thenReturn("Non entusiasma troppo mio fratello");
        when(req.getParameter("idArticolo")).thenReturn("40");


        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);

        try (MockedConstruction<RecensioneDAO> daoCtor = mockConstruction(RecensioneDAO.class)) {


            new RecensioneServlet().doPost(req, res);

            verify(res).sendRedirect(redirectCaptor.capture());

            assertEquals(
                    "DettaglioArticoloServlet?codice=40&error=datiMancanti",
                    redirectCaptor.getValue()
            );

        }
    }

    @Test
    public void TC_3_1_3_descrizioneMancante() throws ServletException, IOException {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Cliente fakeCliente = mock(Cliente.class);
        when(fakeCliente.getNomeUtente()).thenReturn("Michela122");

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);

        when(req.getParameter("valutazione")).thenReturn("3");
        when(req.getParameter("titolo")).thenReturn("Parere personale");
        when(req.getParameter("testo")).thenReturn(null);
        when(req.getParameter("idArticolo")).thenReturn("40");


        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);

        try (MockedConstruction<RecensioneDAO> daoCtor = mockConstruction(RecensioneDAO.class)) {


            new RecensioneServlet().doPost(req, res);

            verify(res).sendRedirect(redirectCaptor.capture());

            assertEquals(
                    "DettaglioArticoloServlet?codice=40&error=datiMancanti",
                    redirectCaptor.getValue()
            );

        }
    }

    @Test
    public void TC_3_1_4_titoloTroppoLungo() throws ServletException, IOException {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Cliente fakeCliente = mock(Cliente.class);
        when(fakeCliente.getNomeUtente()).thenReturn("Michela122");

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);

        when(req.getParameter("titolo")).thenReturn("Questo è un titolo volutamente molto lungo per testare errore servlet");
        when(req.getParameter("testo")).thenReturn("Non entusiasma troppo mio fratello ");
        when(req.getParameter("idArticolo")).thenReturn("40");

        when(req.getParameter("valutazione")).thenReturn("3");

        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);

        try (MockedConstruction<RecensioneDAO> daoCtor = mockConstruction(RecensioneDAO.class)) {


            new RecensioneServlet().doPost(req, res);

            verify(res).sendRedirect(redirectCaptor.capture());

            assertEquals(
                    "DettaglioArticoloServlet?codice=40&error=titoloTroppoLungo",
                    redirectCaptor.getValue()
            );

        }
    }
    @Test
    public void TC_3_1_5_descrizioneLunga() throws ServletException, IOException {

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Cliente fakeCliente = mock(Cliente.class);
        when(fakeCliente.getNomeUtente()).thenReturn("Michela122");

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);

        when(req.getParameter("titolo")).thenReturn("Parere personale ");
        when(req.getParameter("testo")).thenReturn("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem. ");
        when(req.getParameter("idArticolo")).thenReturn("40");

        when(req.getParameter("valutazione")).thenReturn("3");

        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);

        try (MockedConstruction<RecensioneDAO> daoCtor = mockConstruction(RecensioneDAO.class)) {


            new RecensioneServlet().doPost(req, res);

            verify(res).sendRedirect(redirectCaptor.capture());

            assertEquals(
                    "DettaglioArticoloServlet?codice=40&error=testoTroppoLungo",
                    redirectCaptor.getValue()
            );

        }
    }

    @Test
    public void TC_3_1_6() throws ServletException, IOException {



                HttpServletRequest req = mock(HttpServletRequest.class);
                HttpServletResponse res = mock(HttpServletResponse.class);
                HttpSession session = mock(HttpSession.class);

                Cliente fakeCliente = mock(Cliente.class);
                when(fakeCliente.getNomeUtente()).thenReturn("Michela122");

                when(req.getSession(false)).thenReturn(session);
                when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);

                // Parametri validi (tutti presenti e nel formato corretto)
                when(req.getParameter("titolo")).thenReturn("Parere personale");
                when(req.getParameter("testo")).thenReturn("Non entusiasma troppo mio fratello");
                when(req.getParameter("valutazione")).thenReturn("4");
                when(req.getParameter("idArticolo")).thenReturn("40");

                ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);
                ArgumentCaptor<Recensione> recensioneCaptor = ArgumentCaptor.forClass(Recensione.class);

                try (MockedConstruction<RecensioneDAO> daoCtor =
                             mockConstruction(RecensioneDAO.class, (dao, ctx) -> {
                                 doNothing().when(dao).doSave(any(Recensione.class));
                             })) {

                    // Act
                    new RecensioneServlet().doPost(req, res);

                    RecensioneDAO dao = daoCtor.constructed().get(0);
                    verify(dao).doSave(recensioneCaptor.capture());

                    verify(res).sendRedirect(redirectCaptor.capture());
                    assertEquals("DettaglioArticoloServlet?codice=40", redirectCaptor.getValue());


        }

    }
}

