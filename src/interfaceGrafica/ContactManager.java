package interfaceGrafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import contato.Contact;
import dao.ContactDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ContactManager extends JFrame {
    private ContactDAO contactDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;

    public ContactManager() {
        contactDAO = new ContactDAO();
        setTitle("Agenda de Contatos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Telefone", "Email"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel searchLabel = new JLabel("Pesquisar:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");

        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Telefone:");
        phoneField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JButton addButton = new JButton("Adicionar");
        JButton updateButton = new JButton("Atualizar");
        JButton deleteButton = new JButton("Deletar");

        // Adding components to the input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(searchLabel, gbc);
        
        gbc.gridx = 1;
        inputPanel.add(searchField, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(addButton, gbc);

        gbc.gridx = 1;
        inputPanel.add(updateButton, gbc);

        gbc.gridx = 2;
        inputPanel.add(deleteButton, gbc);

        panel.add(inputPanel, BorderLayout.SOUTH);

        add(panel);

        loadContacts();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                searchContacts(keyword);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                contactDAO.addContact(new Contact(name, phone, email));
                loadContacts();

                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    String name = nameField.getText();
                    String phone = phoneField.getText();
                    String email = emailField.getText();

                    contactDAO.updateContact(new Contact(id, name, phone, email));
                    loadContacts();

                    nameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    contactDAO.deleteContact(id);
                    loadContacts();
                }
            }
        });
    }

    private void loadContacts() {
        tableModel.setRowCount(0);
        List<Contact> contacts = contactDAO.getAllContacts();
        for (Contact contact : contacts) {
            tableModel.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail()});
        }
    }

    private void searchContacts(String keyword) {
        tableModel.setRowCount(0);
        List<Contact> contacts = contactDAO.searchContacts(keyword);
        for (Contact contact : contacts) {
            tableModel.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContactManager().setVisible(true);
            }
        });
    }
}


