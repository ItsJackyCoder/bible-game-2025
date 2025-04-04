package  com.company.Sprite;

import com.company.GameView.DisasterView;
import com.company.GameView.RedSeaGameView;
import com.company.GameView.TenCommandmentsView;
import com.company.Main;
import com.company.Sprite.DisasterViewSprite.Bug;
import com.company.Sprite.DisasterViewSprite.Frog;
import com.company.Sprite.DisasterViewSprite.Ice;
import com.company.Sprite.DisasterViewSprite.Tombstone;
import com.company.Sprite.RedSeaViewSprite.Anubis;
import com.company.Sprite.RedSeaViewSprite.Cat;
import com.company.Sprite.RedSeaViewSprite.Pharaoh;
import com.company.Sprite.TenCommandmentSprite.TenCommandment;

import javax.swing.*;
import java.util.ArrayList;

public class Moses extends Sprite{
    public Moses(int x, int y){ //設定摩西一開始的位置在哪裡
        setPosition(x, y);
        img = new ImageIcon(getClass().getResource("/resources/Moses.png"));
    }

    @Override
    public String overlap(int x, int y) { //確認摩西有沒有撞到其他東西所用的method
        if(Main.gameView instanceof DisasterView){ //確認目前是否在玩DisasterView的遊戲
            //check for bugs and frogs

            //因為getFrogs()此方法是在DisasterView.java裡面,所以要從GameView型態
            //轉為DisasterView型態,才可以取得此方法
            ArrayList<Frog> frogs = ((DisasterView) Main.gameView).getFrogs();
            ArrayList<Bug> bugs = ((DisasterView) Main.gameView).getBugs();

            for(Frog f : frogs){ //遇到frogs的狀況
                //f.getRelativePosition() != null一定要放在最前面(如果和後面位置對調,會發生問題),
                //因為f.getRelativePosition(),可能是一個null,那如果它是null的話,
                //它就沒有後面的.x和.y這些屬性可以去使用
                //-->這和Short Circuit有關
                if(f.getRelativePosition() != null && x == f.getRelativePosition().x &&
                y == f.getRelativePosition().y){
                    return "Die";
                }
            }

            for(Bug b : bugs){ //遇到bugs的狀況
                if(b.getRelativePosition() != null && x == b.getRelativePosition().x &&
                        y == b.getRelativePosition().y){
                    return "Die";
                }
            }

            //check for ice and tombstones
            ArrayList<Ice> ices = ((DisasterView) Main.gameView).getIceCubes();
            ArrayList<Tombstone> tombstones = ((DisasterView) Main.gameView).getStones();

            for(Tombstone s : tombstones){ //遇到tombstones的狀況
                if(s.getRelativePosition() != null && x == s.getRelativePosition().x &&
                        y == s.getRelativePosition().y){
                    return "Cannot move";
                }
            }

            for(Ice i : ices){ //遇到ices的狀況
                if(i.getRelativePosition() != null && x == i.getRelativePosition().x &&
                        y == i.getRelativePosition().y){
                    return "Cannot move";
                }
            }

            //check for door
            Door door = Main.gameView.getDoor();

            if(x == door.getRelativePosition().x && y == door.getRelativePosition().y){
                return "Next level";
            }
        }else if(Main.gameView instanceof RedSeaGameView){
            //check for cats
            ArrayList<Cat> cats = ((RedSeaGameView) Main.gameView).getCats();

            for(Cat c : cats){ //遇到cats的狀況
                if(c.getRelativePosition() != null && x == c.getRelativePosition().x &&
                        y == c.getRelativePosition().y){
                    return "Cannot move";
                }
            }

            //check for pharaoh and anubis
            ArrayList<Pharaoh> pharaohs = ((RedSeaGameView) Main.gameView).getPharaohs();
            ArrayList<Anubis> anubis = ((RedSeaGameView) Main.gameView).getAnubis();

            for(Pharaoh p : pharaohs){ //遇到pharaohs的狀況
                if(p.getRelativePosition() != null && x == p.getRelativePosition().x &&
                        y == p.getRelativePosition().y){
                    return "Die";
                }
            }

            for(Anubis a : anubis){ //遇到anubis的狀況
                if(a.getRelativePosition() != null && x == a.getRelativePosition().x &&
                        y == a.getRelativePosition().y){
                    return "Die";
                }
            }

            //check for door
            Door door = Main.gameView.getDoor();

            if(x == door.getRelativePosition().x && y == door.getRelativePosition().y){
                return "Next level";
            }
        }else if(Main.gameView instanceof TenCommandmentsView){
            //優化寫法:(撿10顆十誡)
            ArrayList<TenCommandment> stones = ((TenCommandmentsView) Main.gameView).getStones();

            for(TenCommandment stone : stones){
                if(stone.getRelativePosition() != null && stone.getRelativePosition().x == x &&
                        stone.getRelativePosition().y == y){
                    stone.setNullPosition(); //讓十誡從畫面消失
                    ((TenCommandmentsView) Main.gameView).setCount(1);

                    if(((TenCommandmentsView) Main.gameView).getCount() == 10){
                        return "Game over";
                    }else{
                        return "none";
                    }
                }
            }
        }

        //如果寫null,會導致Main.java裡的110～112行code中的result為null,而導致它無法使用.equals() method
        return "none";
    }
}
