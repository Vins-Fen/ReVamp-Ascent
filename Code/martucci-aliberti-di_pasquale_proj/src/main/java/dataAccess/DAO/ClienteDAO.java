package dataAccess.DAO;

import java.sql.*;
import dataAccess.JavaBeans.*;
import dataAccess.ConPool;
import java.util.List;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClienteDAO {

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nell'hashing della password", e);
        }
    }

    public Cliente doRetrieveByUsername(String nome_utente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente WHERE nome_utente = ?");
            ps.setString(1, nome_utente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("nome_utente"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("sesso"),
                        rs.getInt("eta"),
                        rs.getString("num_telefono")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> doRetrieveAll() {
        List<Cliente> clienti = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cliente");
            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("nome_utente"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("sesso"),
                        rs.getInt("eta"),
                        rs.getString("num_telefono")
                );
                clienti.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clienti;
    }

    public Cliente doRetrieveByEmail(String email) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente WHERE LOWER(email) = ?");
            ps.setString(1, email.toLowerCase());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("nome_utente"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("sesso"),
                        rs.getInt("eta"),
                        rs.getString("num_telefono")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean doSave(Cliente cliente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO cliente(nome_utente, pass, nome, cognome, email, sesso, eta, num_telefono) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, cliente.getNomeUtente());
            ps.setString(2, hashPassword(cliente.getPass()));
            ps.setString(3, cliente.getNome());
            ps.setString(4, cliente.getCognome());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getSesso());
            ps.setInt(7, cliente.getEta());
            ps.setString(8, cliente.getNumTelefono());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doDelete(String nomeUtente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM cliente WHERE nome_utente = ?");
            ps.setString(1, nomeUtente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente checkLogin(String nome_utente, String pass) {
        try (Connection con = ConPool.getConnection()) {
            String hashedPass = hashPassword(pass);
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM cliente WHERE nome_utente = ? AND pass = ?"
            );
            ps.setString(1, nome_utente);
            ps.setString(2, hashedPass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("nome_utente"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("sesso"),
                        rs.getInt("eta"),
                        rs.getString("num_telefono")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdate(Cliente cliente, String vecchioNomeUtente, boolean cambiaPassword) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            String sql = cambiaPassword
                    ? "UPDATE cliente SET nome_utente = ?, pass = ? WHERE nome_utente = ?"
                    : "UPDATE cliente SET nome_utente = ? WHERE nome_utente = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNomeUtente());

            if (cambiaPassword) {
                ps.setString(2, hashPassword(cliente.getPass()));
                ps.setString(3, vecchioNomeUtente);
            } else {
                ps.setString(2, vecchioNomeUtente);
            }

            ps.executeUpdate();
        }
    }

    public List<Cliente> doRetrieveByUsernamePartial(String ricerca) {
        List<Cliente> clienti = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM cliente WHERE LOWER(nome_utente) LIKE ?"
            );
            ps.setString(1, "%" + ricerca.toLowerCase() + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("nome_utente"),
                        rs.getString("pass"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("sesso"),
                        rs.getInt("eta"),
                        rs.getString("num_telefono")
                );
                clienti.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clienti;
    }

}


