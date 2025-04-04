package com.company.Sprite.RedSeaViewSprite;

import com.company.GameView.RedSeaGameView;
import com.company.Main;
import com.company.Sprite.Door;
import com.company.Sprite.Sprite;

import javax.swing.*;
import java.util.ArrayList;

public class Pharaoh extends Sprite {
    public Pharaoh(int x, int y){
        setPosition(x, y);
        img = new ImageIcon(getClass().getResource("/resources/pharaoh.png"));
    }

    @Override
    public String overlap(int x, int y) {
        if (Main.gameView instanceof RedSeaGameView) {
            //check for cats
            ArrayList<Cat> cats = ((RedSeaGameView) Main.gameView).getCats();

            for (Cat c : cats) { //遇到cats的狀況
                if (c.getRelativePosition() != null && x == c.getRelativePosition().x &&
                        y == c.getRelativePosition().y) {
                    return "HasObject";
                }
            }

            //check for pharaoh and anubis
            ArrayList<Pharaoh> pharaohs = ((RedSeaGameView) Main.gameView).getPharaohs();
            ArrayList<Anubis> anubis = ((RedSeaGameView) Main.gameView).getAnubis();

            for (Pharaoh p : pharaohs) { //遇到pharaohs的狀況
                if (p.getRelativePosition() != null && x == p.getRelativePosition().x &&
                        y == p.getRelativePosition().y) {
                    return "HasObject";
                }
            }

            for (Anubis a : anubis) { //遇到anubis的狀況
                if (a.getRelativePosition() != null && x == a.getRelativePosition().x &&
                        y == a.getRelativePosition().y) {
                    return "HasObject";
                }
            }

            //check for door
            Door door = Main.gameView.getDoor();

            if (x == door.getRelativePosition().x && y == door.getRelativePosition().y) {
                return "HasObject";
            }
        }

        return "none";
    }
}
