import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean startGame = false;
    private int score = 0;
    private int totalBricks = 30;
    private Timer timer;
    private int delay = 8;
    private int playerX = 960;
    private int ballPositionX = 800 ;
    private int ballPositionY = 480;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3,10);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
        timer = new Timer(delay,this);
        timer.start();

    }
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1920,1080);

        map.draw((Graphics2D) g);
        g.setColor(Color.YELLOW);
        g.drawRect(0,0,1920,1080);

        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,50));
        g.drawString(""+score,1800,150);

        g.setColor(Color.YELLOW);
        g.fillRect(playerX,900,200,50);

        g.setColor(Color.PINK);
        g.fillOval(ballPositionX,ballPositionY,50,50);

        if(ballPositionY>1000){
            startGame = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.RED);

            g.setFont(new Font("Serif",Font.BOLD,40));
            g.drawString("  Game Over !!! Your Score : " +score,960,540);

            g.setFont(new Font("Serif",Font.BOLD,40));
            g.drawString("  Press Enter to Restart  " ,960,600);
        }
        if(totalBricks == 0){
            startGame = false;
            ballXDir = -1;
            ballYDir = -2;
            g.setColor(Color.RED);

            g.setFont(new Font("Serif",Font.BOLD,40));
            g.drawString("  Game Over!!! Your Score : " +score,960,540);

            g.setFont(new Font("Serif",Font.BOLD,40));
            g.drawString("  Press Enter to Restart ",960,600);

        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(startGame==true) {
            if(new Rectangle(ballPositionX,ballPositionY,50,50).intersects(new Rectangle(playerX,900,200,50)))
                    ballYDir = -ballYDir;
        }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int bricksWidth = map.brickWidth;
                        int bricksHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballPositionX, ballPositionY, 50, 50);
                        Rectangle brickrect = rect;

                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            if (ballPositionX + 19 <= brickrect.x || ballPositionX + 1 >= brickrect.x + bricksWidth) {
                                ballXDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }
                            break A;
                        }
                    }


                }
            }

            ballPositionX += ballXDir;
            ballPositionY += ballYDir;
            if (ballPositionX < 0) {
                ballXDir = -ballXDir;
            }
            if (ballPositionY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballPositionX > 1890) {
                ballXDir = -ballXDir;
            }
            repaint();
        }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 1900) {
                playerX = 1900;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!startGame) {
                ballPositionX = 800;
                ballPositionY = 480;
                ballXDir = -1;
                ballYDir = -2;
                score = 0;
                playerX = 960;
                totalBricks = 30;
                map = new MapGenerator(3, 10);

                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void moveRight ()
    {
        startGame = true;
        playerX += 20;
    }
    public void moveLeft ()
    {
        startGame = true;
        playerX -= 20;
    }
}
