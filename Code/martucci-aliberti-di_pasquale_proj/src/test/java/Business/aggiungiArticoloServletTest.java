package Business;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dataAccess.DAO.ArticoloDAO;
import dataAccess.JavaBeans.Admin;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AggiungiArticoloServletTest {


    @Test
    void TC_4_1_1_codiceMancante_forwardErroreInserimento() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        // Dispatcher pagina errore
        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        // Parametri: codice mancante
        when(req.getParameter("codice")).thenReturn("");

        // Altri campi validi (non devono influire, qui fallisce prima sul parsing codice)
        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Errore nel formato dei dati numerici (Prezzo, Codice o Sconto).",
                captor.getValue()
        );
    }


    @Test
    void TC_4_1_2_nomeMancante() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("2"); // oppure null

        when(req.getParameter("nome")).thenReturn("");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Il nome deve avere almeno 3 caratteri.",
                captor.getValue()
        );


    }


    @Test
    void TC_4_1_3_descrizioneMancante() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("2");

        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals("Descrizione non inserita",
                captor.getValue()
        );

    }


    @Test
    void TC_4_1_4_prezzoMancante() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("2");

        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Errore nel formato dei dati numerici (Prezzo, Codice o Sconto).",
                captor.getValue()
        );
    }


    @Test
    void TC_4_1_5_categoriaMancante() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("2");

        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Errore nel formato dei dati numerici (Prezzo, Codice o Sconto).",
                captor.getValue()
        );
    }

    @Test
    void TC_4_1_6_codiceNonCorretto() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        // Dispatcher pagina errore
        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        // Parametri: codice mancante
        when(req.getParameter("codice")).thenReturn("0");

        // Altri campi validi (non devono influire, qui fallisce prima sul parsing codice)
        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Il codice articolo deve essere positivo.",
                captor.getValue()
        );
    }


    @Test
    void TC_4_1_7_nomeNonCorretto() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("2");

        when(req.getParameter("nome")).thenReturn("se");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Il nome deve avere almeno 3 caratteri.",
                captor.getValue()
        );

    }


    @Test
    void TC_4_1_8_prezzoErrato() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("2");

        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("0.00");
        when(req.getParameter("prezzo")).thenReturn("0");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Il prezzo deve essere maggiore di zero.",
                captor.getValue()
        );
    }

    @Test
    void TC_4_1_9_scontoErrato() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("3");

        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("200");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Lo sconto non è valido (Deve essere tra 0% e 99%).",
                captor.getValue()
        );
    }

    @Test
    void TC_4_1_10_codiceGiàInserito() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(Admin.class);

        when(req.getRequestDispatcher("erroreInserimento.jsp")).thenReturn(dispatcher);

        when(req.getParameter("codice")).thenReturn("2");

        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("50");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");


        servlet.doPost(req, res);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(req).setAttribute(eq("errore"), captor.capture());

        assertEquals(
                "Errore: Esiste già un articolo con il codice 2",
                captor.getValue()
        );
    }

    @Test
    void TC_4_1_11_inserimentoOK_redirectConferma() throws ServletException, IOException {
        AggiungiArticoloServlet servlet = new AggiungiArticoloServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(new Object());

        when(req.getParameter("codice")).thenReturn("234");
        when(req.getParameter("nome")).thenReturn("Sedia relax");
        when(req.getParameter("descrizione")).thenReturn("Sedia relax da giardino, munita di schienale reclinabile.");
        when(req.getParameter("colore")).thenReturn("Nero");
        when(req.getParameter("sconto")).thenReturn("10");
        when(req.getParameter("prezzo")).thenReturn("19.99");
        when(req.getParameter("peso")).thenReturn("7");
        when(req.getParameter("dimensione")).thenReturn("100x80");
        when(req.getParameter("id_categoria")).thenReturn("2");

        try (MockedConstruction<ArticoloDAO> articoloCtor =
                     mockConstruction(ArticoloDAO.class, (dao, ctx) -> {
                         when(dao.doRetrieveById(233)).thenReturn(null);
                         doNothing().when(dao).doSave(any());
                     })) {

            servlet.doPost(req, res);

            ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);
            verify(res).sendRedirect(redirectCaptor.capture());

            assertEquals("confermaInserimento.jsp", redirectCaptor.getValue());

        }
    }
}









