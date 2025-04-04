package com.company.Sprite.DisasterViewSprite;

import com.company.Sprite.Sprite;

import javax.swing.*;

public class Ice extends Sprite {
    public Ice(int x, int y){
        setPosition(x, y);
        img = new ImageIcon(getClass().getResource("/resources/ice.png"));
    }

    @Override
    public String overlap(int x, int y) {
        return "";
    }
}
