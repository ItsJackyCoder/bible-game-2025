package com.company.GameView;

import com.company.Sprite.TenCommandmentSprite.TenCommandment;

import javax.swing.*;
import java.util.ArrayList;

public class TenCommandmentsView extends GameView{
    //原始寫法:(只撿一顆十誡)
    //private TenCommandment stone;

    //優化寫法:(撿10顆十誡)
    private ArrayList<TenCommandment> stones = new ArrayList<>();
    private int count; //計算Mose撿了多少十誡(目前設定總共10個)

    public ArrayList<TenCommandment> getStones() {
        return stones;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count += count;
    }

    //優化寫法:(撿10顆十誡)
    public TenCommandmentsView(){
        img = new ImageIcon(getClass().getResource("/resources/mountain.jpg"));
        elements = new ArrayList<>();
        count = 0;

        stones.add(new TenCommandment(1, 4));
        stones.add(new TenCommandment(2, 6));
        stones.add(new TenCommandment(3, 8));
        stones.add(new TenCommandment(4, 5));
        stones.add(new TenCommandment(5, 6));
        stones.add(new TenCommandment(6, 1));
        stones.add(new TenCommandment(7, 10));
        stones.add(new TenCommandment(8, 4));
        stones.add(new TenCommandment(9, 6));
        stones.add(new TenCommandment(10, 3));

        elements.addAll(stones);
    }
}
