//改成這種package形式的原因是因為接下來作import時比較容易(可以直接按Alt+Enter就可以import了)
package com.company.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import com.company.Main;
import com.company.Sprite.*;

public abstract class GameView {
    protected ArrayList<Sprite> elements; //在每一個GameView裡面,都會有非常多的Sprite,且每一個view有它們各自的elements
    protected Door door; //每一個view都會有一個door可以進入然後往下一個view前進
    protected ImageIcon img; //每一個view都有自己的背景圖片

    //在Main.java裡面,每一次一個新的遊戲,它就是一個新的GameView,那每一次新的GameView,我都要能夠draw我自己
    public void drawView(Graphics g){
        img.paintIcon(null, g, 0, 0); //我:每一個遊戲各自的背景圖片

        //作一個透明黑色的遮罩-->只是美工而已@@
        g.setColor(new Color(0f, 0f, 0f, .3f)); //我:.3f等於0.3f
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        //我:如果elements是一個空的集合(例如:new ArrayList<>()),則不會進入迴圈,
        //因為集合的大小是0。在這種情況下,for迴圈的條件不成立,所以不會執行迴圈體內的任何程式碼(但編譯不會錯誤)
        for(Sprite s : elements){
            s.draw(g); //讓每一個Sprite畫他自己
        }
    }

    public Door getDoor() {
        return this.door;
    }

    public ArrayList<Sprite> getElements() {
        return this.elements;
    }
}
