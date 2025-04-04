package com.company;
import com.company.GameView.DisasterView;
import com.company.GameView.GameView;
import com.company.GameView.RedSeaGameView;
import com.company.GameView.TenCommandmentsView;
import com.company.Sprite.DisasterViewSprite.Bug;
import com.company.Sprite.DisasterViewSprite.Frog;
import com.company.Sprite.DisasterViewSprite.Ice;
import com.company.Sprite.DisasterViewSprite.Tombstone;
import com.company.Sprite.Moses;
import com.company.Sprite.RedSeaViewSprite.Anubis;
import com.company.Sprite.RedSeaViewSprite.Cat;
import com.company.Sprite.RedSeaViewSprite.Pharaoh;
import com.company.Sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main extends JPanel implements KeyListener {
    public static final int CELL = 50; //每一格像素為50
    public static final int WIDTH = 500; //視窗寬度
    public static final int HEIGHT = 500; //視窗高度
    public static final int ROW = HEIGHT / CELL; //總共有10列
    public static final int COLUMN = WIDTH / CELL; //總共有10行

    public static Moses moses;
    public static GameView gameView;

    private int level; //目前關卡在哪一關

    private Boolean hasMoved = false; //確認有沒有成功移動

    //開頭的"""必須獨立成一行,否則會報錯
    private static final String message = """
            <html>
            Hello Adventurers!<br>
            <br>
            <div style="border: 2px solid black; padding: 10px;">
            Use the arrow keys to move your character, and press W, A, S, D to shoot<br> 
            in four directions to eliminate bugs, frogs, pharaohs, and anubis!
            </div>
            <br>
            Level 1:<br>
            Ice blocks and tombstones are obstacles—you cannot pass through them.<br>
            If you touch a bug or a frog, you will die. With each step you take, frogs<br>
            and bugs will also move one step. Reach the door in the bottom-right corner<br> 
            to advance to the next level!<br>
            <br>
            Level 2:<br>
            Cats are obstacles—you cannot pass through them. If you touch a Pharaoh<br> 
            or Anubis, you will die. With each step you take, Pharaohs and Anubis will<br>
            also move one step. Reach the door in the bottom-right corner to move on to<br> 
            the next level!<br>
            <br>
            Level 3:<br>
            Collect ten stones to complete the level and finish the game.<br> 
            <br>
            Good luck!<br>
            <br>
            <i>Developed by Jacky Fang</i>
            </html>
            """;

    public Main(){
        level = 1;
        resetGame(new DisasterView());
        addKeyListener(this);
    }

    public void resetGame(GameView game){
        moses = new Moses(1, 1);
        gameView = game;

        repaint();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(WIDTH, HEIGHT); //參數放等一下要設width和height
    }

    @Override
    public void paintComponent(Graphics g) {
        gameView.drawView(g);
        moses.draw(g);
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Bible Game");
        JLabel label = new JLabel(message);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 22)); // 設定字體為 Arial、粗體、大小 20

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null);

        JOptionPane.showMessageDialog(
                null,
                label,
                "How to Play",
                JOptionPane.INFORMATION_MESSAGE);

        window.setVisible(true);
        window.setResizable(false);
    }

    private Boolean changeLevel(String result){ //Mose每走一格,都去確認說要不要改地圖的level(也就是不同地圖)
        if(result.equals("Next level")){
            level++;

            if(level == 2){
                resetGame(new RedSeaGameView());
            }else if(level == 3){
                resetGame(new TenCommandmentsView());
            }

            return true; //有成功change到Next level
        }

        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Point mosesPoint = moses.getRelativePosition(); //複製一份新的moses's relativePosition的copy
        String result;

        String[] spritesMoveDirection = {"Up", "Down", "Left", "Right"};
        int index = (int) (Math.random() * spritesMoveDirection.length);

        //frogs
        ArrayList<Frog> frogs = new ArrayList<>();
        ArrayList<Point> frogsPoint = new ArrayList<>();

        //bugs
        ArrayList<Bug> bugs = new ArrayList<>();
        ArrayList<Point> bugsPoint = new ArrayList<>();

        //pharaohs
        ArrayList<Pharaoh> pharaohs = new ArrayList<>();
        ArrayList<Point> pharaohsPoint = new ArrayList<>();

        //anubis
        ArrayList<Anubis> anubises = new ArrayList<>();
        ArrayList<Point> anubisesPoint = new ArrayList<>();

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                if(mosesPoint.y > 1){
                    result = moses.overlap(mosesPoint.x, mosesPoint.y - 1);

                    if(result.equals("Die")){
                        //reset game
                        level = 1; //把level的值改回初始值1
                        JOptionPane.showMessageDialog(
                                this,
                                "You die. Please try again.");

                        resetGame(new DisasterView());
                        return; //後面的code都不再被執行
                    }

                    if(!result.equals("Cannot move")){ //非不可動的情況(也就是可以移動的情況)
                        mosesPoint.y -= 1;
                        moses.setPosition(mosesPoint);
                        hasMoved = true;
                    }

                    if(result.equals("Game over")){
                        repaint();

                        JOptionPane.showMessageDialog(
                                this,
                                "You win the game!!!",
                                "Congregation!",
                                JOptionPane.INFORMATION_MESSAGE
                                );

                        return;
                    }

                    //在Mose走得每一格都去確認他有沒有碰到門
                    if(changeLevel(result)) return;
                }

                //DisasterView
                moveFrogs(frogs, frogsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveBugs(bugs, bugsPoint, index, spritesMoveDirection);

                //RedSeaGameView
                movePharaohs(pharaohs, pharaohsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveAnubis(anubises, anubisesPoint, index, spritesMoveDirection);

                break;
            case KeyEvent.VK_DOWN:
                if(mosesPoint.y < ROW){
                    result = moses.overlap(mosesPoint.x, mosesPoint.y + 1);

                    if(result.equals("Die")){
                        //reset game
                        level = 1; //把level的值改回初始值1
                        JOptionPane.showMessageDialog(
                                this,
                                "You die. Please try again.");

                        resetGame(new DisasterView());
                        return; //後面的code都不再被執行
                    }

                    if(!result.equals("Cannot move")){ //非不可動的情況(也就是可以移動的情況)
                        mosesPoint.y += 1;
                        moses.setPosition(mosesPoint);
                        hasMoved = true;
                    }

                    if(result.equals("Game over")){
                        repaint();

                        JOptionPane.showMessageDialog(
                                this,
                                "You win the game!!!",
                                "Congregation!",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        return;
                    }

                    //在Mose走得每一格都去確認他有沒有碰到門
                    if(changeLevel(result)) return;
                }

                //DisasterView
                moveFrogs(frogs, frogsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveBugs(bugs, bugsPoint, index, spritesMoveDirection);

                //RedSeaGameView
                movePharaohs(pharaohs, pharaohsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveAnubis(anubises, anubisesPoint, index, spritesMoveDirection);


                break;
            case KeyEvent.VK_RIGHT:
                if(mosesPoint.x < COLUMN){
                    result = moses.overlap(mosesPoint.x + 1, mosesPoint.y);

                    if(result.equals("Die")){
                        //reset game
                        level = 1; //把level的值改回初始值1
                        JOptionPane.showMessageDialog(
                                this,
                                "You die. Please try again.");

                        resetGame(new DisasterView());
                        return; //後面的code都不再被執行
                    }

                    if(!result.equals("Cannot move")){ //非不可動的情況(也就是可以移動的情況)
                        mosesPoint.x += 1;
                        moses.setPosition(mosesPoint);
                        hasMoved = true;
                    }

                    if(result.equals("Game over")){
                        repaint();

                        JOptionPane.showMessageDialog(
                                this,
                                "You win the game!!!",
                                "Congregation!",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        return;
                    }

                    //在Mose走得每一格都去確認他有沒有碰到門
                    if(changeLevel(result)) return;
                }

                //DisasterView
                moveFrogs(frogs, frogsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveBugs(bugs, bugsPoint, index, spritesMoveDirection);

                //RedSeaGameView
                movePharaohs(pharaohs, pharaohsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveAnubis(anubises, anubisesPoint, index, spritesMoveDirection);

                break;
            case KeyEvent.VK_LEFT:
                if(mosesPoint.x > 1){
                    result = moses.overlap(mosesPoint.x - 1, mosesPoint.y);

                    if(result.equals("Die")){
                        //reset game
                        level = 1; //把level的值改回初始值1
                        JOptionPane.showMessageDialog(
                                this,
                                "You die. Please try again.");

                        resetGame(new DisasterView());
                        return; //後面的code都不再被執行
                    }

                    if(!result.equals("Cannot move")){ //非不可動的情況(也就是可以移動的情況)
                        mosesPoint.x -= 1;
                        moses.setPosition(mosesPoint);
                        hasMoved = true;
                    }

                    if(result.equals("Game over")){
                        repaint();

                        JOptionPane.showMessageDialog(
                                this,
                                "You win the game!!!",
                                "Congregation!",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        return;
                    }

                    //在Mose走得每一格都去確認他有沒有碰到門
                    if(changeLevel(result)) return;
                }

                //DisasterView
                moveFrogs(frogs, frogsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveBugs(bugs, bugsPoint, index, spritesMoveDirection);

                //RedSeaGameView
                movePharaohs(pharaohs, pharaohsPoint, index, spritesMoveDirection);
                index = (int) (Math.random() * spritesMoveDirection.length);
                moveAnubis(anubises, anubisesPoint, index, spritesMoveDirection);

                break;

            //根據Mose目前的位置往上去檢查所有的Sprites,然後如果是不可以射穿的的Sprites的話,就射擊不過去
            case KeyEvent.VK_W: //設定W,A,S,D為往哪邊射擊的按鈕
                for(int i = mosesPoint.y; i > 0; i--){ //最上面也就最多y=1
                    //System.out.println("檢查位置" + mosesPoint.x + ", " + i); //我:測試用

                    for(Sprite s : gameView.getElements()){
                        if(s.getRelativePosition() != null &&
                                s.getRelativePosition().x == mosesPoint.x &&
                                s.getRelativePosition().y == i){
                            //檢查有無射到一些不可射穿的Sprite
                            if(s instanceof Cat || s instanceof Ice || s instanceof Tombstone){
                                return; //就不會在繼續往下檢查
                            }

                            //我:因為有return,所以一次只能射殺一隻,要是沒return的話,
                            //就會變成一次把上面所有可以射殺的Sprites全都射殺完
                            if(s instanceof Anubis || s instanceof Pharaoh ||
                                    s instanceof Bug || s instanceof Frog){
                                shootAndExplode(s);
                                return;
                            }
                        }
                    }
                }

                break;

            case KeyEvent.VK_S: //設定W,A,S,D為往哪邊射擊的按鈕
                for(int i = mosesPoint.y; i <= ROW; i++){ //最下面是ROW
                    for(Sprite s : gameView.getElements()){
                        if(s.getRelativePosition() != null &&
                                s.getRelativePosition().x == mosesPoint.x &&
                                s.getRelativePosition().y == i){
                            //檢查有無射到一些不可射穿的Sprite
                            if(s instanceof Cat || s instanceof Ice || s instanceof Tombstone){
                                return; //就不會在繼續往下檢查
                            }

                            //我:因為有return,所以一次只能射殺一隻,要是沒return的話,
                            //就會變成一次把上面所有可以射殺的Sprites全都射殺完
                            if(s instanceof Anubis || s instanceof Pharaoh ||
                                    s instanceof Bug || s instanceof Frog){
                                shootAndExplode(s);
                                return;
                            }
                        }
                    }
                }

                break;

            case KeyEvent.VK_D: //設定W,A,S,D為往哪邊射擊的按鈕
                for(int i = mosesPoint.x; i <= COLUMN; i++){ //最右邊是COLUMN
                    for(Sprite s : gameView.getElements()){
                        if(s.getRelativePosition() != null &&
                                s.getRelativePosition().x == i &&
                                s.getRelativePosition().y == mosesPoint.y){
                            //檢查有無射到一些不可射穿的Sprite
                            if(s instanceof Cat || s instanceof Ice || s instanceof Tombstone){
                                return; //就不會在繼續往下檢查
                            }

                            //我:因為有return,所以一次只能射殺一隻,要是沒return的話,
                            //就會變成一次把上面所有可以射殺的Sprites全都射殺完
                            if(s instanceof Anubis || s instanceof Pharaoh ||
                                    s instanceof Bug || s instanceof Frog){
                                shootAndExplode(s);
                                return;
                            }
                        }
                    }
                }

                break;

            case KeyEvent.VK_A: //設定W,A,S,D為往哪邊射擊的按鈕
                for(int i = mosesPoint.x; i > 0; i--){ //最左邊也就最多x=1
                    for(Sprite s : gameView.getElements()){
                        if(s.getRelativePosition() != null &&
                                s.getRelativePosition().x == i &&
                                s.getRelativePosition().y == mosesPoint.y){
                            //檢查有無射到一些不可射穿的Sprite
                            if(s instanceof Cat || s instanceof Ice || s instanceof Tombstone){
                                return; //就不會在繼續往下檢查
                            }

                            //我:因為有return,所以一次只能射殺一隻,要是沒return的話,
                            //就會變成一次把上面所有可以射殺的Sprites全都射殺完
                            if(s instanceof Anubis || s instanceof Pharaoh ||
                                    s instanceof Bug || s instanceof Frog){
                                shootAndExplode(s);
                                return;
                            }
                        }
                    }
                }

                break;
        }

        repaint();
        hasMoved = false;

        //檢查Moses有沒有碰到frogs
        for(Frog frog : frogs) {
            if (frog.getRelativePosition() != null &&
                    frog.getRelativePosition().x == moses.getRelativePosition().x &&
                    frog.getRelativePosition().y == moses.getRelativePosition().y) {
                //reset game
                level = 1; //把level的值改回初始值1
                JOptionPane.showMessageDialog(
                        this,
                        "You die. Please try again.");

                resetGame(new DisasterView());
            }
        }

        //檢查Moses有沒有碰到bugs
        for(Bug bug : bugs) {
            if (bug.getRelativePosition() != null &&
                    bug.getRelativePosition().x == moses.getRelativePosition().x &&
                    bug.getRelativePosition().y == moses.getRelativePosition().y) {
                //reset game
                level = 1; //把level的值改回初始值1
                JOptionPane.showMessageDialog(
                        this,
                        "You die. Please try again.");

                resetGame(new DisasterView());
            }
        }

        //檢查Moses有沒有碰到pharaohs
        for(Pharaoh pharaoh : pharaohs) {
            if (pharaoh.getRelativePosition() != null &&
                    pharaoh.getRelativePosition().x == moses.getRelativePosition().x &&
                    pharaoh.getRelativePosition().y == moses.getRelativePosition().y) {
                //reset game
                level = 1; //把level的值改回初始值1
                JOptionPane.showMessageDialog(
                        this,
                        "You die. Please try again.");

                resetGame(new DisasterView());
            }
        }

        //檢查Moses有沒有碰到anubis
        for(Anubis anubis : anubises) {
            if (anubis.getRelativePosition() != null &&
                    anubis.getRelativePosition().x == moses.getRelativePosition().x &&
                    anubis.getRelativePosition().y == moses.getRelativePosition().y) {
                //reset game
                level = 1; //把level的值改回初始值1
                JOptionPane.showMessageDialog(
                        this,
                        "You die. Please try again.");

                resetGame(new DisasterView()); //改成DisasterView!!!!
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveFrogs(ArrayList<Frog> frogs, ArrayList<Point> frogsPoint, int index, String[] spritesMoveDirection){
        String result;

        //確保當前的gameView是指向DisasterView
        if(gameView instanceof DisasterView) {
            DisasterView d = (DisasterView) gameView;
            frogs.addAll(d.getFrogs()); //注意:這樣才可以改到keyPressed裡面frogs
            frogsPoint = new ArrayList<>();

            for(Frog frog : d.getFrogs()){
                frogsPoint.add(frog.getRelativePosition());
            }
        }

        for (int i = 0; i < frogs.size(); i++) {
            if (frogsPoint.get(i) != null && spritesMoveDirection[index].equals("Down") &&
                    frogsPoint.get(i).y < ROW && hasMoved) {
                result = frogs.get(i).overlap(frogsPoint.get(i).x, frogsPoint.get(i).y + 1);

                if (result.equals("none")) { //下面沒有物體
                    frogsPoint.get(i).y += 1;
                    frogs.get(i).setPosition(frogsPoint.get(i)); //更新frog的新位置(為了讓下一個frog去比較會不會互相撞到)

                }
            } else if (frogsPoint.get(i) != null && spritesMoveDirection[index].equals("Up") &&
                    frogsPoint.get(i).y > 1 && hasMoved) {
                result = frogs.get(i).overlap(frogsPoint.get(i).x, frogsPoint.get(i).y - 1);

                if (result.equals("none")) { //上面沒有物體
                    frogsPoint.get(i).y -= 1;
                    frogs.get(i).setPosition(frogsPoint.get(i)); //更新frog的新位置
                }
            } else if (frogsPoint.get(i) != null && spritesMoveDirection[index].equals("Right") &&
                    frogsPoint.get(i).x < COLUMN && hasMoved) {
                result = frogs.get(i).overlap(frogsPoint.get(i).x + 1, frogsPoint.get(i).y);

                if (result.equals("none")) { //右邊沒有物體
                    frogsPoint.get(i).x += 1;
                    frogs.get(i).setPosition(frogsPoint.get(i)); //更新frog的新位置
                }
            } else if (frogsPoint.get(i) != null && spritesMoveDirection[index].equals("Left") &&
                    frogsPoint.get(i).x > 1 && hasMoved) {
                result = frogs.get(i).overlap( frogsPoint.get(i).x - 1,  frogsPoint.get(i).y);

                if (result.equals("none")) { //左邊沒有物體
                    frogsPoint.get(i).x -= 1;
                    frogs.get(i).setPosition(frogsPoint.get(i)); //更新frog的新位置
                }
            }

            index = (int) (Math.random() * spritesMoveDirection.length);
        }
    }

    public void moveBugs(ArrayList<Bug> bugs, ArrayList<Point> bugsPoint, int index, String[] spritesMoveDirection){
        String result;

        //確保當前的gameView是指向DisasterView
        if(gameView instanceof DisasterView) {
            DisasterView d = (DisasterView) gameView;
            bugs.addAll(d.getBugs()); //注意:這樣才可以改到keyPressed裡面bugs
            bugsPoint = new ArrayList<>();

            for(Bug bug : d.getBugs()){
                bugsPoint.add(bug.getRelativePosition());
            }
        }

        for (int i = 0; i < bugs.size(); i++) {
            if (bugsPoint.get(i) != null && spritesMoveDirection[index].equals("Down") &&
                    bugsPoint.get(i).y < ROW && hasMoved) {
                result = bugs.get(i).overlap(bugsPoint.get(i).x, bugsPoint.get(i).y + 1);

                if (result.equals("none")) { //下面沒有物體
                    bugsPoint.get(i).y += 1;
                    bugs.get(i).setPosition(bugsPoint.get(i)); //更新bug的新位置(為了讓下一個bug去比較會不會互相撞到)

                }
            } else if (bugsPoint.get(i) != null && spritesMoveDirection[index].equals("Up") &&
                    bugsPoint.get(i).y > 1 && hasMoved) {
                result = bugs.get(i).overlap(bugsPoint.get(i).x, bugsPoint.get(i).y - 1);

                if (result.equals("none")) { //上面沒有物體
                    bugsPoint.get(i).y -= 1;
                    bugs.get(i).setPosition(bugsPoint.get(i)); //更新bug的新位置
                }
            } else if (bugsPoint.get(i) != null && spritesMoveDirection[index].equals("Right") &&
                    bugsPoint.get(i).x < COLUMN && hasMoved) {
                result = bugs.get(i).overlap(bugsPoint.get(i).x + 1, bugsPoint.get(i).y);

                if (result.equals("none")) { //右邊沒有物體
                    bugsPoint.get(i).x += 1;
                    bugs.get(i).setPosition(bugsPoint.get(i)); //更新bug的新位置
                }
            } else if (bugsPoint.get(i) != null && spritesMoveDirection[index].equals("Left") &&
                    bugsPoint.get(i).x > 1 && hasMoved) {
                result = bugs.get(i).overlap( bugsPoint.get(i).x - 1, bugsPoint.get(i).y);

                if (result.equals("none")) { //左邊沒有物體
                    bugsPoint.get(i).x -= 1;
                    bugs.get(i).setPosition(bugsPoint.get(i)); //更新bug的新位置
                }
            }

            index = (int) (Math.random() * spritesMoveDirection.length);
        }
    }

    public void movePharaohs(ArrayList<Pharaoh> pharaohs, ArrayList<Point> pharaohsPoint, int index, String[] spritesMoveDirection){
        String result;

        //確保當前的gameView是指向RedSeaGameView
        if(gameView instanceof RedSeaGameView) {
            RedSeaGameView r = (RedSeaGameView) gameView;
            pharaohs.addAll(r.getPharaohs()); //注意:這樣才可以改到keyPressed裡面pharaohs
            pharaohsPoint = new ArrayList<>();

            for(Pharaoh pharaoh : r.getPharaohs()){
                pharaohsPoint.add(pharaoh.getRelativePosition());
            }
        }

        for (int i = 0; i < pharaohs.size(); i++) {
            if (pharaohsPoint.get(i) != null && spritesMoveDirection[index].equals("Down") &&
                    pharaohsPoint.get(i).y < ROW && hasMoved) {
                result = pharaohs.get(i).overlap(pharaohsPoint.get(i).x, pharaohsPoint.get(i).y + 1);

                if (result.equals("none")) { //下面沒有物體
                    pharaohsPoint.get(i).y += 1;
                    pharaohs.get(i).setPosition(pharaohsPoint.get(i)); //更新pharaoh的新位置(為了讓下一個pharaoh去比較會不會互相撞到)

                }
            } else if (pharaohsPoint.get(i) != null && spritesMoveDirection[index].equals("Up") &&
                    pharaohsPoint.get(i).y > 1 && hasMoved) {
                result = pharaohs.get(i).overlap(pharaohsPoint.get(i).x, pharaohsPoint.get(i).y - 1);

                if (result.equals("none")) { //上面沒有物體
                    pharaohsPoint.get(i).y -= 1;
                    pharaohs.get(i).setPosition(pharaohsPoint.get(i)); //更新pharaoh的新位置
                }
            } else if (pharaohsPoint.get(i) != null && spritesMoveDirection[index].equals("Right") &&
                    pharaohsPoint.get(i).x < COLUMN && hasMoved) {
                result = pharaohs.get(i).overlap(pharaohsPoint.get(i).x + 1, pharaohsPoint.get(i).y);

                if (result.equals("none")) { //右邊沒有物體
                    pharaohsPoint.get(i).x += 1;
                    pharaohs.get(i).setPosition(pharaohsPoint.get(i)); //更新pharaoh的新位置
                }
            } else if (pharaohsPoint.get(i) != null && spritesMoveDirection[index].equals("Left") &&
                    pharaohsPoint.get(i).x > 1 && hasMoved) {
                result = pharaohs.get(i).overlap(pharaohsPoint.get(i).x - 1, pharaohsPoint.get(i).y);

                if (result.equals("none")) { //左邊沒有物體
                    pharaohsPoint.get(i).x -= 1;
                    pharaohs.get(i).setPosition(pharaohsPoint.get(i)); //更新pharaoh的新位置
                }
            }

            index = (int) (Math.random() * spritesMoveDirection.length);
        }
    }

    public void moveAnubis(ArrayList<Anubis> anubises, ArrayList<Point> anubisesPoint, int index, String[] spritesMoveDirection){
        String result;

        //確保當前的gameView是指向RedSeaGameView
        if(gameView instanceof RedSeaGameView) {
            RedSeaGameView r = (RedSeaGameView) gameView;
            anubises.addAll(r.getAnubis()); //注意:這樣才可以改到keyPressed裡面anubises
            anubisesPoint = new ArrayList<>();

            for(Anubis anubis : r.getAnubis()){
                anubisesPoint.add(anubis.getRelativePosition());
            }
        }

        for (int i = 0; i < anubises.size(); i++) {
            if (anubisesPoint.get(i) != null && spritesMoveDirection[index].equals("Down") &&
                    anubisesPoint.get(i).y < ROW && hasMoved) {
                result = anubises.get(i).overlap(anubisesPoint.get(i).x, anubisesPoint.get(i).y + 1);

                if (result.equals("none")) { //下面沒有物體
                    anubisesPoint.get(i).y += 1;
                    anubises.get(i).setPosition(anubisesPoint.get(i)); //更新anubis的新位置(為了讓下一個anubis去比較會不會互相撞到)

                }
            } else if (anubisesPoint.get(i) != null && spritesMoveDirection[index].equals("Up") &&
                    anubisesPoint.get(i).y > 1 && hasMoved) {
                result = anubises.get(i).overlap(anubisesPoint.get(i).x, anubisesPoint.get(i).y - 1);

                if (result.equals("none")) { //上面沒有物體
                    anubisesPoint.get(i).y -= 1;
                    anubises.get(i).setPosition(anubisesPoint.get(i)); //更新anubis的新位置
                }
            } else if (anubisesPoint.get(i) != null && spritesMoveDirection[index].equals("Right") &&
                    anubisesPoint.get(i).x < COLUMN && hasMoved) {
                result = anubises.get(i).overlap(anubisesPoint.get(i).x + 1, anubisesPoint.get(i).y);

                if (result.equals("none")) { //右邊沒有物體
                    anubisesPoint.get(i).x += 1;
                    anubises.get(i).setPosition(anubisesPoint.get(i)); //更新anubis的新位置
                }
            } else if (anubisesPoint.get(i) != null && spritesMoveDirection[index].equals("Left") &&
                    anubisesPoint.get(i).x > 1 && hasMoved) {
                result = anubises.get(i).overlap(anubisesPoint.get(i).x - 1, anubisesPoint.get(i).y);

                if (result.equals("none")) { //左邊沒有物體
                    anubisesPoint.get(i).x -= 1;
                    anubises.get(i).setPosition(anubisesPoint.get(i)); //更新anubis的新位置
                }
            }

            index = (int) (Math.random() * spritesMoveDirection.length);
        }
    }

    public void shootAndExplode(Sprite s){
        s.setBombImg();
        repaint();

        //延遲0.1秒後再執行setNullPosition()
        //不然換上bomb圖片後,再setNullPosition,只經過不到１豪米時間,
        //所以視覺上只會看到frog消失而已,不會有bomb的圖片
        Timer timer = new Timer(1500, a ->{
            //利用setNullPosition()把relativePosition和absolutePosition
            //都設定為null,這樣在draw()時,因為relativePosition是null,
            //所以就不會被畫出來了!
            s.setNullPosition();
            repaint();
        });

        timer.setRepeats(false); // 只執行一次
        timer.start();
    }
}
