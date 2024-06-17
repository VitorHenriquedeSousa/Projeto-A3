package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexaodb.DatabaseConnection;
import contato.Contact;

public class ContactDAO {
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                contacts.add(new Contact(id, name, phone, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void addContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, phone, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhone());
            pstmt.setString(3, contact.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, phone = ?, email = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhone());
            pstmt.setString(3, contact.getEmail());
            pstmt.setInt(4, contact.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> searchContacts(String keyword) {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE name LIKE ? OR phone LIKE ? OR email LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String query = "%" + keyword + "%";
            pstmt.setString(1, query);
            pstmt.setString(2, query);
            pstmt.setString(3, query);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");

                    contacts.add(new Contact(id, name, phone, email));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}

