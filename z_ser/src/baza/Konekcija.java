/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

/**
 *
 * @author USER
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Konekcija {
    private Connection conn;
    private static Konekcija instance;

    private Konekcija() {
        try {
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3316/rmt_domaci", "root", "root");
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Konekcija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Konekcija getInstance() {
    if(instance==null)
        instance=new Konekcija();
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

}
