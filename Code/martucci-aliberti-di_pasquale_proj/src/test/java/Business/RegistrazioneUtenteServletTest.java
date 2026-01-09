package Business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dataAccess.DAO.ClienteDAO;
import dataAccess.JavaBeans.Cliente;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class RegistrazioneUtenteServletTest {
// set parametri "validi" di base (TC_1.1_15)
        private void stubValidParams(HttpServletRequest req) {
            when(req.getParameter("nome")).thenReturn("Antonio");
            when(req.getParameter("cognome")).thenReturn("Aliberti");
            when(req.getParameter("email")).thenReturn("antonio1@gmail.com");
            when(req.getParameter("username")).thenReturn("Antonio99");
            when(req.getParameter("password")).thenReturn("Ant12345");
            when(req.getParameter("confermaPassword")).thenReturn("Ant12345");
            when(req.getParameter("sesso")).thenReturn("M");
        }

        @Test
        void TC_1_1_1_cognomeMancante_redirectCampi() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("cognome")).thenReturn(""); // mancante

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=campi", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_2_nomeMancante_redirectCampi() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("nome")).thenReturn(""); // mancante

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);
                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=campi", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_3_sessoMancante() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("sesso")).thenReturn(null); // mancante (attenzione: servlet controlla anche null)

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=campi", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_4_emailMancante_redirectCampi() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("email")).thenReturn(" "); // blank

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=campi", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_5_usernameMancante_redirectCampi() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("username")).thenReturn(""); // blank

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=campi", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_6_passwordMancante_redirectCampi() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("password")).thenReturn(""); // blank

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=campi", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_7_confermaPasswordMancante_redirectCampi() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("confermaPassword")).thenReturn(""); // blank

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=campi", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_8_passwordNonCoincidenti_redirectPassword() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("confermaPassword")).thenReturn("Alib223"); // mismatch

            try (MockedConstruction<ClienteDAO> mocked = mockConstruction(ClienteDAO.class)) {
                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=password", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_11_usernameGiaPresente_redirectUsername() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);

            try (MockedConstruction<ClienteDAO> mocked =
                         mockConstruction(ClienteDAO.class, (daoMock, ctx) -> {
                             when(daoMock.doRetrieveByUsername("Antonio99")).thenReturn(mock(Cliente.class));
                         })) {

                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=username", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_12_emailGiaPresente_redirectEmail() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);
            when(req.getParameter("email")).thenReturn("mario.rossi@email.com");

            try (MockedConstruction<ClienteDAO> mocked =
                         mockConstruction(ClienteDAO.class, (daoMock, ctx) -> {
                             when(daoMock.doRetrieveByUsername("Antonio99")).thenReturn(null);
                             when(daoMock.doRetrieveByEmail("mario.rossi@email.com")).thenReturn(mock(Cliente.class));
                         })) {

                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("registrazione.jsp?errore=email", urlCaptor.getValue());
            }
        }

        @Test
        void TC_1_1_15_registrazioneOk_redirectLoginSuccess() throws Exception {
            RegistrazioneUtenteServlet servlet = new RegistrazioneUtenteServlet();
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse resp = mock(HttpServletResponse.class);

            stubValidParams(req);

            try (MockedConstruction<ClienteDAO> mocked =
                         mockConstruction(ClienteDAO.class, (daoMock, ctx) -> {
                             when(daoMock.doRetrieveByUsername("Antonio99")).thenReturn(null);
                             when(daoMock.doRetrieveByEmail("antonio1@gmail.com")).thenReturn(null);
                         })) {

                servlet.doPost(req, resp);

                ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

                verify(resp).sendRedirect(urlCaptor.capture());

                assertEquals("login.jsp?success=reg", urlCaptor.getValue());
            }
        }
    }


