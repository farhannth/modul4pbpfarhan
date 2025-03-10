/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul4contoh1;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *

 */
public class UserDAO {
    
    public static ObservableList<User> getUsers(){
        ObservableList<User> userList = FXCollections.observableArrayList();
        String query = "SELECT * FROM users";
        
        try (
                Connection koneksi = DBConnection.getConnection();
                Statement stmt = koneksi.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                
                userList.add(new User(
                    username,
                    password,
                    fullname
                ));
            }
            
            rs.close();
            stmt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userList;
    }
     public static void addUser(User user) {
        String query = "INSERT INTO users (username, password, fullname) VALUES (?, MD5(?), ?)";
    
    try {
        Connection koneksi = DBConnection.getConnection();
        PreparedStatement smt = koneksi.prepareStatement(query);
        
        smt.setString(1, user.getUsername());
        smt.setString(2, user.getPassword());
        smt.setString(3, user.getFullname());
        
        smt.executeUpdate();
        
        smt.close();
        koneksi.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void updateuser(User user) {
        String query = "UPDATE users SET password = MD5(?), fullname = ? WHERE username = ?";

        try {
            Connection koneksi = DBConnection.getConnection();
            PreparedStatement smt = koneksi.prepareStatement(query);

            smt.setString(1, user.getPassword());
            smt.setString(2, user.getFullname());
            smt.setString(3, user.getUsername());

            smt.executeUpdate();

            smt.close();
            koneksi.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String username) {
    String query = "DELETE FROM users WHERE username = ?";

    try (
        Connection koneksi = DBConnection.getConnection();
        PreparedStatement smt = koneksi.prepareStatement(query)) {
        
        smt.setString(1, username);

        smt.executeUpdate();
        smt.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}