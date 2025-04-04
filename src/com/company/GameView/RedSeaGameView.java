package com.company.GameView;

import com.company.Sprite.Door;
import com.company.Sprite.RedSeaViewSprite.Anubis;
import com.company.Sprite.RedSeaViewSprite.Cat;
import com.company.Sprite.RedSeaViewSprite.Pharaoh;

import javax.swing.*;
import java.util.ArrayList;

public class RedSeaGameView extends GameView{
    private ArrayList<Cat> cats = new ArrayList<>();
    private ArrayList<Anubis> anubis = new ArrayList<>();
    private ArrayList<Pharaoh> pharaohs = new ArrayList<>();

    //getter methods
    public ArrayList<Cat> getCats() {
        return cats;
    }

    public ArrayList<Anubis> getAnubis() {
        return anubis;
    }

    public ArrayList<Pharaoh> getPharaohs() {
        return pharaohs;
    }

    public RedSeaGameView(){
        img = new ImageIcon(getClass().getResource("/resources/sea.jpg"));
        elements = new ArrayList<>();
        door = new Door(10, 10);

        cats.add(new Cat(3, 4));
        cats.add(new Cat(4, 7));
        cats.add(new Cat(6, 5));
        cats.add(new Cat(2, 7));
        cats.add(new Cat(6, 2));
        cats.add(new Cat(6, 8));
        cats.add(new Cat(9, 4));
        cats.add(new Cat(9, 9));
        cats.add(new Cat(8, 7));

        pharaohs.add(new Pharaoh(8, 8));
        pharaohs.add(new Pharaoh(3, 8));
        pharaohs.add(new Pharaoh(10, 8));
        pharaohs.add(new Pharaoh(7, 6));
        pharaohs.add(new Pharaoh(4, 2));

        anubis.add(new Anubis(1, 4));
        anubis.add(new Anubis(7, 3));
        anubis.add(new Anubis(5, 10));
        anubis.add(new Anubis(8, 4));
        anubis.add(new Anubis(10, 1));

        elements.addAll(anubis);
        elements.addAll(pharaohs);
        elements.addAll(cats);
        elements.add(door);
    }
}
