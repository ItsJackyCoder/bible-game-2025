package com.company.GameView;

import com.company.Sprite.DisasterViewSprite.Bug;
import com.company.Sprite.DisasterViewSprite.Frog;
import com.company.Sprite.DisasterViewSprite.Ice;
import com.company.Sprite.DisasterViewSprite.Tombstone;
import com.company.Sprite.Door;

import javax.swing.*;
import java.util.ArrayList;

public class DisasterView extends GameView{ //災難的視窗
    private ArrayList<Bug> bugs = new ArrayList<>();
    private ArrayList<Frog> frogs = new ArrayList<>();
    private ArrayList<Ice> iceCubes = new ArrayList<>();
    private ArrayList<Tombstone> stones = new ArrayList<>();

    public ArrayList<Bug> getBugs() {
        return bugs;
    }

    public ArrayList<Frog> getFrogs() {
        return frogs;
    }

    public ArrayList<Ice> getIceCubes() {
        return iceCubes;
    }

    public ArrayList<Tombstone> getStones() {
        return stones;
    }

    public DisasterView(){
        img = new ImageIcon(getClass().getResource("/resources/egypt.jpg"));
        elements = new ArrayList<>();
        door = new Door(10,10);

        //可以依自己的設計去新增更多的這些sprite～
        //蟲
        bugs.add(new Bug(10, 1));
        bugs.add(new Bug(6, 4));
        bugs.add(new Bug(5, 1));

        //青蛙
        frogs.add(new Frog(3, 3));
        frogs.add(new Frog(4, 9));
        frogs.add(new Frog(9, 4));
        frogs.add(new Frog(8, 3));

        //冰塊
        iceCubes.add(new Ice(7, 2));
        iceCubes.add(new Ice(3, 5));
        iceCubes.add(new Ice(5, 8));
        iceCubes.add(new Ice(4, 2));
        iceCubes.add(new Ice(8, 6));

        //墓碑
        stones.add(new Tombstone(6, 6));
        stones.add(new Tombstone(7, 8));
        stones.add(new Tombstone(8, 8));
        stones.add(new Tombstone(9, 8));
        stones.add(new Tombstone(3, 8));
        stones.add(new Tombstone(10, 5));
        stones.add(new Tombstone(1, 4));
        stones.add(new Tombstone(2, 7));

        //allAll():加入另一個ArrayList中的所有元素
        elements.addAll(bugs);
        elements.addAll(frogs);
        elements.addAll(iceCubes);
        elements.addAll(stones);
        elements.add(door);
    }
}
