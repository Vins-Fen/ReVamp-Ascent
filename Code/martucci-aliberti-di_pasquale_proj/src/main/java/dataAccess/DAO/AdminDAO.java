package dataAccess.DAO;

import java.security.*;
import dataAccess.JavaBeans.*;
import java.sql.*;
import dataAccess.ConPool;
import java.util.*;

public class AdminDAO{

    private String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for(byte b : hashedBytes){
                sb.append(String.format("%02x", b));
            }

            String hash = sb.toString();
            System.out.println("[DEBUG] Hash password calcolato: " + hash);
            return hash;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nell'hashing della password", e);
        }
    }


    public Admin doLogin(String username,String password) throws SQLException{
        String query= "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement(query);

            ps.setString(1,username);
            ps.setString(2,hashPassword(password));

            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return new Admin(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("nome"),
                            rs.getString("cognome")
                    );
                }
            }
        }
        return null;
    }

    public Admin doRetrieveByUsername(String username) throws SQLException{
        String query = "SELECT * FROM Admin WHERE username = ?";
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,username);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return new Admin(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("nome"),
                            rs.getString("cognome")
                    );
                }
            }
        }
        return null;
    }

    public List<Admin> doRetrieveByAll(){
        List<Admin> admins=new ArrayList<>();
        String query = "SELECT * FROM Admin";
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                admins.add(new Admin(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nome"),
                        rs.getString("cognome")
                ));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return admins;
    }

    public boolean doSave(Admin admin){
        String query="INSERT INTO Admin (username, password, nome, cognome) VALUES (?, ?, ?, ?)";
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,admin.getUsername());
            ps.setString(2,hashPassword(admin.getPassword()));
            ps.setString(3,admin.getNome());
            ps.setString(4,admin.getCognome());

            return ps.executeUpdate() > 0;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean doUpdate(Admin admin){
        String query = "UPDATE Admin SET password = ?, nome = ?, cognome = ? WHERE username = ?";
        try(Connection con=ConPool.getConnection()){
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,hashPassword(admin.getPassword()));
            ps.setString(2,admin.getNome());
            ps.setString(3,admin.getCognome());
            ps.setString(4,admin.getUsername());

            return ps.executeUpdate() > 0;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean doDelete(Admin admin){
        String query = "DELETE FROM Admin WHERE username = ?";
        try (Connection con = ConPool.getConnection()){
             PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, admin.getUsername());
            return ps.executeUpdate() > 0;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


}
