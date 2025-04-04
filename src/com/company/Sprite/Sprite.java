package com.company.Sprite;

import javax.swing.*;
import java.awt.*;
import com.company.Main;

public abstract class Sprite {
    protected ImageIcon img; //每一個Sprite都有它自己的圖片

    //Java中內建的Class(每一個Point它的x和y是多少)
    protected Point relativePosition; //相對位置(最左上角是(1,1), (1,2)....(1,5))
    protected Point absolutePosition; //絕對位置(畫真正的位置用的!)

    public ImageIcon getImg() {
        return img;
    }

    public void setBombImg() {
        this.img = new ImageIcon(getClass().getResource("/resources/bomb.png"));
    }

    public void draw(Graphics g){
        //因為遊戲中可以殺死青蛙、蟲等等,也就是我們會把青蛙、蟲的relativePosition設定為null,
        //這樣它們就不會被畫出來了
        if(relativePosition != null) {
            img.paintIcon(null, g, absolutePosition.x, absolutePosition.y);
        }
    }

    //method overload
    public void setPosition(Point p){
        setPosition(p.x, p.y);
    }

    //會作兩件事情:1.設定relativePosition 2.設定absolutePosition
    public void setPosition(int x, int y){
        relativePosition = new Point(x, y);
        absolutePosition = new Point((x - 1) * Main.CELL, (y - 1) * Main.CELL);
    }

    public void setNullPosition(){
        relativePosition = null;
        absolutePosition = null;
    }

    //getter method
    public Point getRelativePosition(){
        if(relativePosition == null){
            return null;
        }else{
            //return this.relativePosition-->copy by reference
            //這樣寫(new Point(relativePosition);)的原因是為了避免它用copy by reference

            //我:Point Class的其中一個constructor(Point(Point p))
            return new Point(relativePosition);
        }
    }

    public abstract String overlap(int x, int y);
}