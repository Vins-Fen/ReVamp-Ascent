package Business;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dataAccess.DAO.AdminDAO;
import dataAccess.DAO.CarrelloDAO;
import dataAccess.DAO.ClienteDAO;
import dataAccess.JavaBeans.Admin;
import dataAccess.JavaBeans.Cliente;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    class LoginTest {

        @Test
        void TC_1_2_3_passwordErrata() throws Exception {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);

            when(req.getParameter("username")).thenReturn("Michela122");
            when(req.getParameter("password")).thenReturn("assdsasd");
            when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

            try (MockedConstruction<ClienteDAO> clienteCtor =
                         mockConstruction(ClienteDAO.class, (dao, ctx) -> {
                             when(dao.checkLogin("Michela122", "assdsasd")).thenReturn(null);
                         });
                 // admin KO -> forward con messaggio "Nome utente o password errati"
                 MockedConstruction<AdminDAO> adminCtor =
                         mockConstruction(AdminDAO.class, (dao, ctx) -> {
                             when(dao.doLogin("Michela122", "assdsasd")).thenReturn(null);
                         })) {

                new login().doPost(req, res);


                ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

                verify(req).setAttribute(eq("erroreLogin"), captor.capture());


                assertEquals("Nome utente o password errati", captor.getValue());

            }
        }

        // TC_1.2_4: username NON registrato -> cliente null, admin null -> forward login.jsp con erroreLogin
        @Test
        void TC_1_2_4_usernameNonRegistrato_loginKO() throws Exception {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);

            when(req.getParameter("username")).thenReturn("Ssjsjsj21");
            when(req.getParameter("password")).thenReturn("michi12345");
            when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

            try (MockedConstruction<ClienteDAO> clienteCtor =
                         mockConstruction(ClienteDAO.class, (dao, ctx) -> {
                             when(dao.checkLogin("Ssjsjsj21", "michi12345")).thenReturn(null);
                         });
                 MockedConstruction<AdminDAO> adminCtor =
                         mockConstruction(AdminDAO.class, (dao, ctx) -> {
                             when(dao.doLogin("Ssjsjsj21", "michi12345")).thenReturn(null);
                         })) {

                new login().doPost(req, res);

                ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

                verify(req).setAttribute(eq("erroreLogin"), captor.capture());


                assertEquals("Nome utente o password errati", captor.getValue());
            }
        }

        // TC_1.2_5: login utente OK -> session utenteLoggato + redirect home.jsp
        @Test
        void TC_1_2_5_loginUtenteOK_redirectHome() throws Exception {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            HttpSession session = mock(HttpSession.class);

            when(req.getParameter("username")).thenReturn("Michela122");
            when(req.getParameter("password")).thenReturn("michi12345");
            when(req.getSession()).thenReturn(session);
            when(session.getAttribute("carrelloAnonimo")).thenReturn(null); // niente trasferimento

            Cliente cliente = mock(Cliente.class);
            when(cliente.getNomeUtente()).thenReturn("Michela122");

            try (MockedConstruction<ClienteDAO> clienteCtor =
                         mockConstruction(ClienteDAO.class, (dao, ctx) -> {
                             when(dao.checkLogin("Michela122", "michi12345")).thenReturn(cliente);
                         });
                 MockedConstruction<AdminDAO> adminCtor =
                         mockConstruction(AdminDAO.class);
                 MockedConstruction<CarrelloDAO> carrelloCtor =
                         mockConstruction(CarrelloDAO.class)) {

                new login().doPost(req, res);

                ArgumentCaptor<Object> userCaptor = ArgumentCaptor.forClass(Object.class);
                verify(session).setAttribute(eq("utenteLoggato"), userCaptor.capture());
                assertEquals(cliente, userCaptor.getValue());

            }
        }
        @Test
        void TC_1_3_3_passwordErrata_forwardLoginConErrore() throws Exception {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);

            when(req.getParameter("username")).thenReturn("Admin1");
            when(req.getParameter("password")).thenReturn("ajajaja");
            when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

            try (MockedConstruction<ClienteDAO> clienteCtor =
                         mockConstruction(ClienteDAO.class, (dao, ctx) -> {
                             when(dao.checkLogin("Admin1", "ajajaja")).thenReturn(null);
                         });
                 MockedConstruction<AdminDAO> adminCtor =
                         mockConstruction(AdminDAO.class, (dao, ctx) -> {
                             when(dao.doLogin("Admin1", "ajajaja")).thenReturn(null);
                         })) {

                new login().doPost(req, res);

                ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

                verify(req).setAttribute(eq("erroreLogin"), captor.capture());


                assertEquals("Nome utente o password errati", captor.getValue());

            }
        }

        @Test
        void TC_1_3_4_usernameNonRegistrato_loginKO() throws Exception {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            RequestDispatcher dispatcher = mock(RequestDispatcher.class);

            when(req.getParameter("username")).thenReturn("ajajajajajaj");
            when(req.getParameter("password")).thenReturn("Adminpass1");
            when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

            try (MockedConstruction<ClienteDAO> clienteCtor =
                         mockConstruction(ClienteDAO.class, (dao, ctx) -> {
                             when(dao.checkLogin("ajajajajajaj", "Adminpass1")).thenReturn(null);
                         });
                 MockedConstruction<AdminDAO> adminCtor =
                         mockConstruction(AdminDAO.class, (dao, ctx) -> {
                             when(dao.doLogin("ajajajajajaj", "Adminpass1")).thenReturn(null);
                         })) {

                new login().doPost(req, res);
                ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

                verify(req).setAttribute(eq("erroreLogin"), captor.capture());


                assertEquals("Nome utente o password errati", captor.getValue());

            }
        }



        // TC_1.3_5: admin OK -> session admin + redirect admin.jsp
        @Test
        void TC_1_3_5_adminLoginOK_redirectAdmin() throws Exception {
            HttpServletRequest req = mock(HttpServletRequest.class);
            HttpServletResponse res = mock(HttpServletResponse.class);
            HttpSession session = mock(HttpSession.class);

            when(req.getParameter("username")).thenReturn("admin1");
            when(req.getParameter("password")).thenReturn("adminpass1");
            when(req.getSession()).thenReturn(session);

            Admin admin = mock(Admin.class);

            try (MockedConstruction<ClienteDAO> clienteCtor =
                         mockConstruction(ClienteDAO.class, (dao, ctx) -> {
                             when(dao.checkLogin("admin1", "adminpass1")).thenReturn(null);
                         });
                 MockedConstruction<AdminDAO> adminCtor =
                         mockConstruction(AdminDAO.class, (dao, ctx) -> {
                             when(dao.doLogin("admin1", "adminpass1")).thenReturn(admin);
                         })) {

                new login().doPost(req, res);

                ArgumentCaptor<Object> userCaptor = ArgumentCaptor.forClass(Object.class);
                verify(session).setAttribute(eq("admin"), userCaptor.capture());
                assertEquals(admin, userCaptor.getValue());

            }
        }

    }



