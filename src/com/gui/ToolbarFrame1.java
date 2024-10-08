package com.gui;

import java.awt.*;
import java.awt.event.*;

public class ToolbarFrame1 extends Frame {
    Button cutButton, copyButton, pasteButton;

    public ToolbarFrame1() {
        super("Toolbar Example (AWT)");
        setSize(450, 250);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        ActionListener printListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
            }
        };
        Panel toolbar = new Panel();
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        cutButton = new Button("Cut");
        cutButton.addActionListener(printListener);
        toolbar.add(cutButton);

        copyButton = new Button("Copy");
        copyButton.addActionListener(printListener);
        toolbar.add(copyButton);

        pasteButton = new Button("Paste");
        pasteButton.addActionListener(printListener);
        toolbar.add(pasteButton);
        add(toolbar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        var tf1 = new ToolbarFrame1();
        tf1.setVisible(true);
    }
}
