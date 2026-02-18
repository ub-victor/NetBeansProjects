package com.mycompany.ggroupui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SimpleExample extends JFrame {

    // Constructor
    public SimpleExample() {
        setTitle("Simple GUI Frame");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleExample ex = new SimpleExample();
                ex.setVisible(true);
            }
        });
    }
}
