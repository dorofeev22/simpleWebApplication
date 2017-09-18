package ru.medlinesoft.simplewebapplication.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ru.medlinesoft.simplewebapplication.entity.Part;

/**
 * Репозиторий методов доступа к базе данных.
 */
public class PartRepository {
    
    private Connection getConnection() throws ClassNotFoundException {
        // TODO create application.properties;
        Class.forName("org.postgresql.Driver");
        String userName = "postgres";
        String password = "W#nlt^c0w";
        String url = "jdbc:postgresql://localhost/medlinesoft";
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Part> findParts() throws ClassNotFoundException {
        String textQuery = "SELECT part_name, part_number, vendor, qty, shipped, receive FROM medlinesoft.part";
        List<Part> parts = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(textQuery);
            while (rs.next()) {
                Part part = new Part();
                part.setPartName(rs.getString("part_name"));
                part.setPartNumber(rs.getString("part_number"));
                part.setVendor(rs.getString("vendor"));
                part.setQty(rs.getInt("qty"));
                part.setShipped(rs.getDate("shipped"));
                part.setReceive(rs.getDate("receive"));
                parts.add(part);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            
        }
        return parts;
    }

}
