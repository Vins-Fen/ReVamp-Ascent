package Business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dataAccess.DAO.MetodiPagamentoDAO;
import dataAccess.JavaBeans.MetodiPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

    class metodiPagamentoTest {

        private HttpServletRequest req;
        private HttpServletResponse res;

        private StringWriter buffer;
        private PrintWriter writer;

        @BeforeEach
        void setup_TC_1_4_10() throws Exception {
            req = mock(HttpServletRequest.class);
            res = mock(HttpServletResponse.class);

            buffer = new StringWriter();
            writer = new PrintWriter(buffer, true);
            when(res.getWriter()).thenReturn(writer);

            when(req.getParameter("numcarta")).thenReturn("2234 4439 2929 2929");
            when(req.getParameter("scadenza")).thenReturn("09/30");
            when(req.getParameter("proprietario")).thenReturn("Mario Rossi");
            when(req.getParameter("nome_utente")).thenReturn("marioRossi");
        }

        private String run() throws Exception {
            new InserisciMetodoPagamentoAjax().doPost(req, res);
            writer.flush();
            return buffer.toString();
        }


        @Test
        void TC_1_4_9_cartaGiaRegistrata_KO_daDoSaveFalse() throws Exception {

            try (MockedConstruction<MetodiPagamentoDAO> daoCtor =
                         mockConstruction(MetodiPagamentoDAO.class, (dao, ctx) -> {
                             when(dao.doSave(any(MetodiPagamento.class))).thenReturn(false);
                         })) {

                String body = run();

                assertTrue(body.contains("\"success\":false"),
                        "Atteso success=false. Body=" + body);
                assertTrue(body.contains("Impossibile salvare il metodo di pagamento"),
                        "Atteso message di salvataggio KO. Body=" + body);

            }
        }

        // -----------------------------
        // TC_1.4_1: numcarta NON inserito
        // IMPORTANTISSIMO: nella servlet il controllo è su NULL, non su stringa vuota.
        // Quindi il KO "Parametri mancanti" si ottiene con numcarta = null.
        // -----------------------------
        @Test
        void TC_1_4_1_numCartaMancante_null_KO_parametriMancanti() throws Exception {
            when(req.getParameter("numcarta")).thenReturn(null);

            try (MockedConstruction<MetodiPagamentoDAO> daoCtor =
                         mockConstruction(MetodiPagamentoDAO.class)) {

                String body = run();

                assertTrue(body.contains("\"success\":false"),
                        "Atteso success=false. Body=" + body);
                assertTrue(body.contains("Parametri mancanti"),
                        "Atteso message Parametri mancanti. Body=" + body);

            }
        }

        // -----------------------------
        // TC_1.4_2: scadenza mancante
        // Anche qui: KO solo se scadenza = null (parametro mancante).
        // -----------------------------
        @Test
        void TC_1_4_2_scadenzaMancante_null_KO_parametriMancanti() throws Exception {
            when(req.getParameter("scadenza")).thenReturn(null);

            String body = run();

            assertTrue(body.contains("\"success\":false"), "Body=" + body);
            assertTrue(body.contains("Parametri mancanti"), "Body=" + body);
        }
        @Test
        void TC_1_4_3_IntestatarioMancante_null_KO_parametriMancanti() throws Exception {
            when(req.getParameter("proprietario")).thenReturn(null);

            String body = run();

            assertTrue(body.contains("\"success\":false"), "Body=" + body);
            assertTrue(body.contains("Parametri mancanti"), "Body=" + body);
        }

       @Test
        void TC_1_4_7_KO_formatoNonValido() throws Exception {
            when(req.getParameter("scadenza")).thenReturn("05/2026");

            String body = run();

            assertTrue(body.contains("\"success\":false"), "Body=" + body);
            assertTrue(body.contains("Formato scadenza non valido"), "Body=" + body);
        }

    }


