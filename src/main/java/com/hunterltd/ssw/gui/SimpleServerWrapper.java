package com.hunterltd.ssw.gui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimpleServerWrapper {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            WrapperGUI wrapperGUI = new WrapperGUI();
            // If server is running, it needs to be shut down before the wrapper closes
            wrapperGUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            wrapperGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JFrame frame = (JFrame) e.getSource();

                    if (wrapperGUI.getServer() != null &&
                            wrapperGUI.getServer().isRunning()) {
                        int result = JOptionPane.showConfirmDialog(
                                frame,
                                "Are you sure you want to exit the server wrapper? This will stop the server.",
                                "Exit Server Wrapper",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (result == JOptionPane.YES_OPTION) {
                            try {
                                wrapperGUI.getServer().stop(10L, TimeUnit.SECONDS);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        }
                    } else {
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }

                }
            });
            wrapperGUI.pack();
            wrapperGUI.setVisible(true);
        });
    }
}
