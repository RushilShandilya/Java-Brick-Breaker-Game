import javax.swing.*;

public class Game {
    public static void main(String []args){
        JFrame myFrame = new JFrame("Brick Breaker Game");
        Gameplay gameplay = new Gameplay();
        myFrame.setSize(1920,1080);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        myFrame.add(gameplay);
    }
}
