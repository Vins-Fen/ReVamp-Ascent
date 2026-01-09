package Business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dataAccess.JavaBeans.Cliente;
import dataAccess.JavaBeans.Indirizzo;
import dataAccess.DAO.IndirizzoDAO;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AggiungiIndirizzoServletTest {

    HttpServletRequest req;
    HttpServletResponse res;
    HttpSession session;
    Cliente fakeCliente;

    private static final String VIA = "Via Nola 92";
    private static final String CITTA = "Somma Vesuviana";
    private static final String CAP = "80049";
    private static final String PROVINCIA = "NA";
    private static final String PAESE = "Italia";

    private PrintStream originalOut;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        req = mock(HttpServletRequest.class);
        res = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        fakeCliente = mock(Cliente.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("utenteLoggato")).thenReturn(fakeCliente);
        when(fakeCliente.getNomeUtente()).thenReturn("marioRossi");

        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void TC_1_5_1_viaNull_parametriMancanti() throws Exception {
        when(req.getParameter("via")).thenReturn(null);
        when(req.getParameter("citta")).thenReturn("Roma");
        when(req.getParameter("cap")).thenReturn("80049");
        when(req.getParameter("provincia")).thenReturn("RM");
        when(req.getParameter("paese")).thenReturn("Italia");
        when(req.getParameter("preferito")).thenReturn(null);

        new AggiungiIndirizzoServlet().doPost(req, res);

        assertEquals("{\"success\":false, \"message\":\"Parametri mancanti\"}", outContent.toString().trim());
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void TC_1_5_2_paeseNull_parametriMancanti() throws Exception {
        when(req.getParameter("via")).thenReturn("Via Nola");
        when(req.getParameter("citta")).thenReturn("Roma");
        when(req.getParameter("cap")).thenReturn("80049");
        when(req.getParameter("provincia")).thenReturn("RM");
        when(req.getParameter("paese")).thenReturn(null);
        when(req.getParameter("preferito")).thenReturn(null);

        new AggiungiIndirizzoServlet().doPost(req, res);

        assertEquals("{\"success\":false, \"message\":\"Parametri mancanti\"}", outContent.toString().trim());
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void TC_1_5_3_cittaNull_parametriMancanti() throws Exception {
        when(req.getParameter("via")).thenReturn("Via Nola");
        when(req.getParameter("citta")).thenReturn(null);
        when(req.getParameter("cap")).thenReturn("80049");
        when(req.getParameter("provincia")).thenReturn("RM");
        when(req.getParameter("paese")).thenReturn("Italia");
        when(req.getParameter("preferito")).thenReturn(null);

        new AggiungiIndirizzoServlet().doPost(req, res);

        assertEquals("{\"success\":false, \"message\":\"Parametri mancanti\"}", outContent.toString().trim());
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void TC_1_5_4_capNull_parametriMancanti() throws Exception {
        when(req.getParameter("via")).thenReturn("Via Nola");
        when(req.getParameter("citta")).thenReturn("Roma");
        when(req.getParameter("cap")).thenReturn(null);
        when(req.getParameter("provincia")).thenReturn("RM");
        when(req.getParameter("paese")).thenReturn("Italia");
        when(req.getParameter("preferito")).thenReturn(null);

        new AggiungiIndirizzoServlet().doPost(req, res);

        assertEquals("{\"success\":false, \"message\":\"Parametri mancanti\"}", outContent.toString().trim());
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void TC_1_5_5_provinciaNull_parametriMancanti() throws Exception {
        when(req.getParameter("via")).thenReturn("Via Nola");
        when(req.getParameter("citta")).thenReturn("Roma");
        when(req.getParameter("cap")).thenReturn("80049");
        when(req.getParameter("provincia")).thenReturn(null);
        when(req.getParameter("paese")).thenReturn("Italia");
        when(req.getParameter("preferito")).thenReturn(null);

        new AggiungiIndirizzoServlet().doPost(req, res);

        assertEquals("{\"success\":false, \"message\":\"Parametri mancanti\"}", outContent.toString().trim());
        verify(res, never()).sendRedirect(anyString());
    }

    private final IndirizzoDAO indirizzoDAO = new IndirizzoDAO();



    @Test
    void TC_1_5_12_inserimentoOk_salvaSuDB_eRedirect() throws Exception {
        when(req.getParameter("via")).thenReturn(VIA);
        when(req.getParameter("citta")).thenReturn(CITTA);
        when(req.getParameter("cap")).thenReturn(CAP);
        when(req.getParameter("provincia")).thenReturn(PROVINCIA);
        when(req.getParameter("paese")).thenReturn(PAESE);
        when(req.getParameter("preferito")).thenReturn("true"); // checkbox spuntato => non null

        new AggiungiIndirizzoServlet().doPost(req, res);

        verify(res, times(1)).sendRedirect("VisualizzaIndirizziServlet");

        List<Indirizzo> lista = indirizzoDAO.doRetrieveByUser(fakeCliente.getNomeUtente());

        Indirizzo trovato = lista.stream()
                .filter(i ->
                        VIA.equals(i.getVia()) &&
                                CITTA.equals(i.getCitta()) &&
                                CAP.equals(i.getCap()) &&
                                PROVINCIA.equals(i.getProvincia()) &&
                                PAESE.equals(i.getPaese()))
                .findFirst()
                .orElse(null);

        assertNotNull(trovato, "Indirizzo non trovato su DB tramite IndirizzoDAO.doRetrieveByUser()");
    }




}


