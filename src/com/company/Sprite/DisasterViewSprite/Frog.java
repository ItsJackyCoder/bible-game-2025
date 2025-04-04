package com.company.Sprite.DisasterViewSprite;

import com.company.GameView.DisasterView;
import com.company.Main;
import com.company.Sprite.Door;
import com.company.Sprite.Moses;
import com.company.Sprite.Sprite;

import javax.swing.*;
import java.util.ArrayList;

public class Frog extends Sprite {
    public Frog(int x, int y) {
        setPosition(x, y);
        img = new ImageIcon(getClass().getResource("/resources/frog.png"));
    }

    @Override
    public String overlap(int x, int y) {
        if (Main.gameView instanceof DisasterView) {
            ArrayList<Frog> frogs = ((DisasterView) Main.gameView).getFrogs();
            ArrayList<Bug> bugs = ((DisasterView) Main.gameView).getBugs();

            for(Frog f : frogs){ //遇到frogs的狀況
                if(f.getRelativePosition() != null && x == f.getRelativePosition().x &&
                        y == f.getRelativePosition().y){
                    return "HasObject";
                }
            }

            for (Bug b : bugs) { //遇到bugs的狀況
                if (b.getRelativePosition() != null && x == b.getRelativePosition().x &&
                        y == b.getRelativePosition().y) {
                    return "HasObject";
                }
            }

            //check for ice and tombstones
            ArrayList<Ice> ices = ((DisasterView) Main.gameView).getIceCubes();
            ArrayList<Tombstone> tombstones = ((DisasterView) Main.gameView).getStones();

            for (Tombstone s : tombstones) { //遇到tombstones的狀況
                if (s.getRelativePosition() != null && x == s.getRelativePosition().x &&
                        y == s.getRelativePosition().y) {
                    return "HasObject";
                }
            }

            for (Ice i : ices) { //遇到ices的狀況
                if (i.getRelativePosition() != null && x == i.getRelativePosition().x &&
                        y == i.getRelativePosition().y) {
                    return "HasObject";
                }
            }

            //check for door
            Door door = Main.gameView.getDoor();

            if (x == door.getRelativePosition().x && y == door.getRelativePosition().y) {
                return "HasObject";
            }

//            Moses moses = Main.moses;
//
//            if(x == moses.getRelativePosition().x && y == moses.getRelativePosition().y){
//                return "Die";
//            }
        }
        return "none";
    }
}
