package model.DAO;

import model.ConPool;
import model.JavaBeans.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.*;

public class OrdineDAO {

    public Ordine doRetrieveById(int id_ordine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ordine WHERE id_ordine = ?");
            ps.setInt(1, id_ordine);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                GregorianCalendar data = new GregorianCalendar();
                data.setTime(rs.getDate("data"));
                return new Ordine(
                        rs.getInt("id_ordine"),
                        rs.getInt("num_articoli"),
                        data,
                        rs.getDouble("importo_totale"),
                        rs.getInt("id_indirizzo"),
                        rs.getString("nome_utente")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public double calcolaImportoTotale(int idOrdine){
        double totale = 0.0;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("""
            SELECT SUM(c.quantita * c.prezzo_unitario * (1 - COALESCE(c.sconto, 0))) AS totale
            FROM Contenimento c
            WHERE c.id_ordine = ?
        """);
            ps.setInt(1, idOrdine);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totale = rs.getDouble("totale");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totale;
    }


    public List<Ordine> doRetrieveAll() {
        List<Ordine> lista = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ordine");

            while (rs.next()) {
                GregorianCalendar data = new GregorianCalendar();
                data.setTime(rs.getDate("data"));
                int idOrdine = rs.getInt("id_ordine");

                double importoTotale = calcolaImportoTotale(idOrdine);

                Ordine o = new Ordine(
                        idOrdine,
                        rs.getInt("num_articoli"),
                        data,
                        importoTotale,
                        rs.getInt("id_indirizzo"),
                        rs.getString("nome_utente")
                );
                lista.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


    public void doSave(Ordine ordine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ordine (num_articoli, data, importo_totale, id_indirizzo, nome_utente) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, ordine.getNum_articoli());
            ps.setDate(2, new java.sql.Date(ordine.getData().getTimeInMillis()));
            ps.setDouble(3, ordine.getImporto_totale());
            ps.setInt(4, ordine.getId_indirizzo());
            ps.setString(5, ordine.getNome_utente());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ordine.setId_ordine(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doDelete(int id_ordine) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM ordine WHERE id_ordine = ?");
            ps.setInt(1, id_ordine);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ordine> doRetrieveByDate(GregorianCalendar data1, GregorianCalendar data2) {
        List<Ordine> lista = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("""
            SELECT * FROM ordine
            WHERE data BETWEEN ? AND ?
            ORDER BY data DESC
        """);
            ps.setDate(1, new java.sql.Date(data1.getTimeInMillis()));
            ps.setDate(2, new java.sql.Date(data2.getTimeInMillis()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GregorianCalendar data = new GregorianCalendar();
                data.setTime(rs.getDate("data"));
                int idOrdine = rs.getInt("id_ordine");

                double importoTotale = calcolaImportoTotale(idOrdine);

                Ordine o = new Ordine(
                        idOrdine,
                        rs.getInt("num_articoli"),
                        data,
                        importoTotale,
                        rs.getInt("id_indirizzo"),
                        rs.getString("nome_utente")
                );
                lista.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public int getQuantitaTotaleOrdine(int idOrdine){
        int totale=0;
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement("SELECT SUM(quantita) FROM contenimento WHERE id_ordine = ?");
            ps.setInt(1,idOrdine);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                totale = rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return totale;
    }

    public Map<Integer, List<Map<String, Object>>> doRetrieveStoricoUtente(String nomeUtente) {
        Map<Integer, List<Map<String, Object>>> storico = new LinkedHashMap<>();//Usiamo una LinkedHashMap per mantenere l’ordine di inserimento degli ordini

        String sql = """
        SELECT o.id_ordine, o.data, 
               c.nome_articolo, c.codice,
               c.quantita, c.prezzo_unitario, COALESCE(c.sconto, 0) AS sconto,
               ia.url AS immagine,
               COUNT(*) OVER (PARTITION BY o.id_ordine) AS num_articoli,
               SUM((c.prezzo_unitario * (1 - COALESCE(c.sconto, 0))) * c.quantita) OVER (PARTITION BY o.id_ordine) AS importo_totale
        FROM ordine o
        JOIN contenimento c ON o.id_ordine = c.id_ordine
        LEFT JOIN immaginearticolo ia ON ia.codice_articolo = c.codice AND ia.is_principale = TRUE
        WHERE o.nome_utente = ?
        ORDER BY o.id_ordine DESC, c.codice ASC
    """;

        try (Connection conn = ConPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idOrdine = rs.getInt("id_ordine");

                    List<Map<String, Object>> lista; //per ogni riga nel risultato controlla se quell ordine è gia nella mappa
                    if (storico.containsKey(idOrdine)) {  //se si prende la lista esistente
                        lista = storico.get(idOrdine);
                    } else {
                        lista = new ArrayList<>();
                        storico.put(idOrdine, lista); //altrimenti crea una nuova e la aggiunge
                    }

                    Map<String, Object> mappa = new HashMap<>(); //crea una mappa per un singolo prodotto dell ordine
                    mappa.put("nome_articolo", rs.getString("nome_articolo"));
                    mappa.put("codice", rs.getInt("codice"));
                    mappa.put("quantita", rs.getInt("quantita"));
                    mappa.put("prezzo_unitario", rs.getDouble("prezzo_unitario"));
                    mappa.put("sconto", rs.getDouble("sconto"));
                    mappa.put("immagine", rs.getString("immagine") != null ? rs.getString("immagine") : "assets/img/default.jpg");

                    if (lista.isEmpty()) { //questi sono attributi comuni a tutti gli articoli di quell ordine, quindi vengono inseriti una sola volta
                        mappa.put("data", rs.getDate("data"));
                        mappa.put("num_articoli", rs.getInt("num_articoli"));
                        mappa.put("importo_totale", rs.getDouble("importo_totale"));
                    }

                    lista.add(mappa);  //aggiunge il prodotto alla lista
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return storico;
    }

}
