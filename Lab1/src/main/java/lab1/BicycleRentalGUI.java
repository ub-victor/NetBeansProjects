package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class BicycleRentalGUI extends JFrame {

    private final JTextField startField;
    private final JTextField endField;
    private final JTextArea resultArea;

    public BicycleRentalGUI() {

        setTitle("Bicycle Rental System");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Thanks message when closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Thanks for using Bicycle Rental System!");
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());

        // ===== Input Panel =====
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Rental Time"));

        inputPanel.add(new JLabel("Starting hour (0–23):"));
        startField = new JTextField();
        inputPanel.add(startField);

        inputPanel.add(new JLabel("Ending hour (1–24):"));
        endField = new JTextField();
        inputPanel.add(endField);

        add(inputPanel, BorderLayout.NORTH);

        // ===== Result Area =====
        resultArea = new JTextArea(6, 30);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Result"));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel();

        JButton calculateBtn = new JButton("Calculate");
        JButton okBtn = new JButton("OK");
        JButton saveBtn = new JButton("Save");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(calculateBtn);
        buttonPanel.add(okBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        calculateBtn.addActionListener(e -> calculateCost());
        okBtn.addActionListener(e -> calculateCost());
        saveBtn.addActionListener(e -> saveResult());
        exitBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Thanks for using Bicycle Rental System!");
            System.exit(0);
        });
    }

    private void calculateCost() {
        try {
            int startTime = Integer.parseInt(startField.getText());
            int endTime = Integer.parseInt(endField.getText());

            if (startTime < 0 || startTime > 23 || endTime < 1 || endTime > 24 || startTime >= endTime) {
                JOptionPane.showMessageDialog(this, "Please enter valid hours!");
                return;
            }

            int totalCost = 0;

            for (int hour = startTime; hour < endTime; hour++) {

                if ((hour >= 0 && hour < 7) || (hour >= 21 && hour < 24)) {
                    totalCost += 500;
                }
                else if ((hour >= 7 && hour < 14) || (hour >= 19 && hour < 21)) {
                    totalCost += 1000;
                }
                else if (hour >= 14 && hour < 19) {
                    totalCost += 1500;
                }
            }

            int timeUsed = endTime - startTime;

            resultArea.setText(
                    "Start Time : " + startTime + ":00\n" +
                    "End Time   : " + endTime + ":00\n" +
                    "Time Used  : " + timeUsed + " hour(s)\n\n" +
                    "Amount to Pay : " + totalCost + " RWF"
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter numbers only!");
        }
    }

    private void saveResult() {
        if (resultArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to save!");
            return;
        }

        try (FileWriter writer = new FileWriter("bicycle_rental_result.txt")) {
            writer.write(resultArea.getText());
            JOptionPane.showMessageDialog(this, "Result saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BicycleRentalGUI().setVisible(true);
        });
    }
}