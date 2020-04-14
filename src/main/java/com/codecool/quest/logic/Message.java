package com.codecool.quest.logic;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Message extends Frame{
    public Message(String title) {
        super(title);
        setSize(500, 140);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Font sansSerifLarge = new Font("SansSerif", Font.BOLD,18);
        Font sansSerifSmall = new Font("SansSerif", Font.BOLD,12);
        g.setFont(sansSerifLarge);
        g.drawString("Congratulation!!!", 60, 60);
        g.setFont(sansSerifSmall);
        g.drawString("Enjoy next level :)", 60, 100);
    }
}
