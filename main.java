import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class Vehicle_Management extends JFrame implements ActionListener {
    private JLabel label1, label2, label3, label4, label5;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton;
    private JPanel panel;
    private Connection connection;
    
    public Vehicle_Management() {
        setTitle("Vehicle Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Initialize database connection
        initializeDatabase();
        
        // Create table if not exists
        createTableIfNotExists();
        
        // GUI components
        initializeComponents();
        
        setVisible(true);
    }
    
    private void initializeDatabase() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/vehicle_db";
            String username = "root";
            String password = "password"; // Change to your MySQL password
            
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            
            // Create database if not exists
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS vehicle_db");
            stmt.execute("USE vehicle_db");
            
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void createTableIfNotExists() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS vehicles (" +
                         "id VARCHAR(20) PRIMARY KEY, " +
                         "type VARCHAR(50), " +
                         "brand VARCHAR(50), " +
                         "model VARCHAR(50), " +
                         "capacity DECIMAL(10,2))";
            stmt.execute(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error creating table: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeComponents() {
        label1 = new JLabel("Vehicle ID");
        label2 = new JLabel("Vehicle Type");
        label3 = new JLabel("Brand");
        label4 = new JLabel("Model");
        label5 = new JLabel("Capacity (tons)");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(10);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel = new JPanel(new GridLayout(7, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        panel.add(textField5);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(exitButton);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addButton) {
                addVehicle();
            } 
            else if (e.getSource() == viewButton) {
                viewVehicles();
            } 
            else if (e.getSource() == editButton) {
                editVehicle();
            } 
            else if (e.getSource() == deleteButton) {
                deleteVehicle();
            } 
            else if (e.getSource() == clearButton) {
                clearFields();
            } 
            else if (e.getSource() == exitButton) {
                closeConnection();
                System.exit(0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addVehicle() throws SQLException {
        String id = textField1.getText();
        String type = textField2.getText();
        String brand = textField3.getText();
        String model = textField4.getText();
        String capacityStr = textField5.getText();
        
        if (id.isEmpty() || type.isEmpty() || brand.isEmpty() || model.isEmpty() || capacityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double capacity = Double.parseDouble(capacityStr);
            
            String sql = "INSERT INTO vehicles (id, type, brand, model, capacity) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, type);
            pstmt.setString(3, brand);
            pstmt.setString(4, model);
            pstmt.setDouble(5, capacity);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "✅ Vehicle added successfully!");
                clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid capacity value. Please enter a number.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewVehicles() throws SQLException {
        String sql = "SELECT * FROM vehicles";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        ArrayList<String[]> vehicles = new ArrayList<>();
        while (rs.next()) {
            String[] vehicle = new String[5];
            vehicle[0] = rs.getString("id");
            vehicle[1] = rs.getString("type");
            vehicle[2] = rs.getString("brand");
            vehicle[3] = rs.getString("model");
            vehicle[4] = rs.getString("capacity");
            vehicles.add(vehicle);
        }
        
        if (vehicles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No vehicles found in database.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] columns = {"Vehicle ID", "Type", "Brand", "Model", "Capacity (tons)"};
        Object[][] data = new Object[vehicles.size()][5];
        for (int i = 0; i < vehicles.size(); i++) {
            data[i][0] = vehicles.get(i)[0];
            data[i][1] = vehicles.get(i)[1];
            data[i][2] = vehicles.get(i)[2];
            data[i][3] = vehicles.get(i)[3];
            data[i][4] = vehicles.get(i)[4];
        }
        
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("View Vehicles");
        frame.add(scrollPane);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }
    
    private void editVehicle() throws SQLException {
        String id = textField1.getText();
        String type = textField2.getText();
        String brand = textField3.getText();
        String model = textField4.getText();
        String capacityStr = textField5.getText();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Vehicle ID to edit", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double capacity = Double.parseDouble(capacityStr);
            
            String sql = "UPDATE vehicles SET type=?, brand=?, model=?, capacity=? WHERE id=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, type);
            pstmt.setString(2, brand);
            pstmt.setString(3, model);
            pstmt.setDouble(4, capacity);
            pstmt.setString(5, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "✅ Vehicle updated successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Vehicle not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid capacity value. Please enter a number.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteVehicle() throws SQLException {
        String id = textField1.getText();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Vehicle ID to delete", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String sql = "DELETE FROM vehicles WHERE id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "✅ Vehicle deleted successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Vehicle not found.");
        }
    }
    
    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }
    
    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Vehicle_Management());
    }
}
