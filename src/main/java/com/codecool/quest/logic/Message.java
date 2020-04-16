package com.codecool.quest.logic;

import com.codecool.quest.Main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Message extends Frame {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


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
        g.drawString(Main.getPlayerName() + " " + getMessage(), 60, 60);
        g.setFont(sansSerifSmall);
    }


}
