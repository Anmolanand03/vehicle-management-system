import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Vehicle_Management extends JFrame implements ActionListener {
    private JLabel label1, label2, label3, label4, label5;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton;
    private JPanel panel;
    private ArrayList<String[]> vehicles = new ArrayList<>();

    public Vehicle_Management() {
        setTitle("Vehicle Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String[] vehicle = new String[5];
            vehicle[0] = textField1.getText();
            vehicle[1] = textField2.getText();
            vehicle[2] = textField3.getText();
            vehicle[3] = textField4.getText();
            vehicle[4] = textField5.getText();
            vehicles.add(vehicle);
            JOptionPane.showMessageDialog(this, "✅ Vehicle added successfully!");
            clearFields();
        } 
        else if (e.getSource() == viewButton) {
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
        else if (e.getSource() == editButton) {
            String vehicleID = JOptionPane.showInputDialog(this, "Enter Vehicle ID to edit:");
            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i)[0].equals(vehicleID)) {
                    String[] vehicle = new String[5];
                    vehicle[0] = vehicleID;
                    vehicle[1] = textField2.getText();
                    vehicle[2] = textField3.getText();
                    vehicle[3] = textField4.getText();
                    vehicle[4] = textField5.getText();
                    vehicles.set(i, vehicle);
                    JOptionPane.showMessageDialog(this, "✅ Vehicle edited successfully!");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "❌ Vehicle not found.");
        } 
        else if (e.getSource() == deleteButton) {
            String vehicleID = JOptionPane.showInputDialog(this, "Enter Vehicle ID to delete:");
            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i)[0].equals(vehicleID)) {
                    vehicles.remove(i);
                    JOptionPane.showMessageDialog(this, "✅ Vehicle deleted successfully!");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "❌ Vehicle not found.");
        } 
        else if (e.getSource() == clearButton) {
            clearFields();
        } 
        else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }

    public static void main(String[] args) {
        new Vehicle_Management();
    }
}
