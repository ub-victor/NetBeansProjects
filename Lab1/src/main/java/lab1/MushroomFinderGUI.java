package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class MushroomFinderGUI extends JFrame {

    private JRadioButton gillsYes, gillsNo;
    private JRadioButton forestYes, forestNo;
    private JRadioButton ringYes, ringNo;
    private JRadioButton convexYes, convexNo;

    private JTextArea resultArea;

    public MushroomFinderGUI() {

        setTitle("Mushroom Finder");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Thank-you message when closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Thanks for using Mushroom Finder!");
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());

        JPanel questionPanel = new JPanel(new GridLayout(4, 1));

        // Gills
        questionPanel.add(createQuestionPanel(
                "Does your mushroom have gills?", gillsYes = new JRadioButton("Yes"),
                gillsNo = new JRadioButton("No")));

        // Forest
        questionPanel.add(createQuestionPanel(
                "Does it grow in a forest?", forestYes = new JRadioButton("Yes"),
                forestNo = new JRadioButton("No")));

        // Ring
        questionPanel.add(createQuestionPanel(
                "Does it have a ring?", ringYes = new JRadioButton("Yes"),
                ringNo = new JRadioButton("No")));

        // Convex cup
        questionPanel.add(createQuestionPanel(
                "Does it have a convex cup?", convexYes = new JRadioButton("Yes"),
                convexNo = new JRadioButton("No")));

        add(questionPanel, BorderLayout.CENTER);

        // Result area
        resultArea = new JTextArea(3, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton okButton = new JButton("OK");
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(okButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // OK button action
        okButton.addActionListener(e -> findMushroom());

        // Save button action
        saveButton.addActionListener(e -> saveResult());

        // Exit button action
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Thanks for using Mushroom Finder!");
            System.exit(0);
        });
    }

    private JPanel createQuestionPanel(String question, JRadioButton yes, JRadioButton no) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(question);

        ButtonGroup group = new ButtonGroup();
        group.add(yes);
        group.add(no);

        panel.add(label);
        panel.add(yes);
        panel.add(no);

        return panel;
    }

    private void findMushroom() {

        String gills = gillsYes.isSelected() ? "yes" : "no";
        String forest = forestYes.isSelected() ? "yes" : "no";
        String ring = ringYes.isSelected() ? "yes" : "no";
        String convex = convexYes.isSelected() ? "yes" : "no";

        String result;

        if (gills.equals("no")) {
            result = "Cepe de bordeau";
        }
        else if (forest.equals("no") && ring.equals("yes")) {
            result = "Agaric jaunissant";
        }
        else if (forest.equals("no") && ring.equals("yes") && convex.equals("no")) {
            result = "Coprin chevelu";
        }
        else if (convex.equals("yes") && ring.equals("yes")) {
            result = "Amanite tue-mouche";
        }
        else if (convex.equals("yes") && ring.equals("no")) {
            result = "Pied bleu";
        }
        else {
            result = "Girolle";
        }

        resultArea.setText("Your mushroom is: " + result);
    }

    private void saveResult() {
        try (FileWriter writer = new FileWriter("mushroom_result.txt")) {
            writer.write(resultArea.getText());
            JOptionPane.showMessageDialog(this, "Result saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MushroomFinderGUI().setVisible(true);
        });
    }
}