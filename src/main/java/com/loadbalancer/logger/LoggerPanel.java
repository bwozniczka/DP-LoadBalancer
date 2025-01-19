package com.loadbalancer.logger;

import javax.swing.*;
import java.awt.*;

public class LoggerPanel extends JPanel {
    private final JTextArea loggerArea;

    public LoggerPanel() {
        setLayout(new BorderLayout());
        loggerArea = new JTextArea(10, 50);
        loggerArea.setEditable(false);
        add(new JScrollPane(loggerArea), BorderLayout.CENTER);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            loggerArea.append(message + "\n");
            loggerArea.setCaretPosition(loggerArea.getDocument().getLength());
        });
    }
}