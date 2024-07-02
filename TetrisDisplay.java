/*
 * Creating Tetris display that houses the display of the game
 * Prajeet Bohara
 * 11/28/2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel {

    private TetrisGame game;
    private int start_x = 180;
    private int start_y = 180;
    private int cell_size = 20;
    private int speed = 400;
    private boolean pause;
    private Timer timer;
    private Color[] colors ={Color.ORANGE, Color.YELLOW, Color.GREEN, Color.RED,
        Color.BLUE, Color.MAGENTA, Color.CYAN};

    public TetrisDisplay(TetrisGame game) {
        this.game = game;
        this.pause = false;
        cycleMove();

        addKeyListener(new KeyListener() 
        {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                translateKey(e.getKeyCode());
            }

            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void translateKey(int keyCode) 
    {
        int a = 32;
        int b = 37;
        int c = 39;
        int d = 38;
        int e = 40;
        int f = 78;
        
        if (keyCode == a && game.getCurrentState() == 0)
        { 
            this.pause = !pause;
        } else if (keyCode == b && game.getCurrentState() == 0 && !isPause())
        { 
            game.makeMove('L');
        } else if (keyCode == c && !isPause() && game.getCurrentState() == 0)
        { 
            game.makeMove('R');
        } else if (keyCode == d && !pause && game.getCurrentState() == 0) 
        { 
            game.makeMove('U');
        } else if (keyCode == e && game.getCurrentState() == 0 && !isPause())
        { 
            game.makeMove('D');
        } else if (keyCode == f)
        {
            game.newGame();
            pause = false;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(start_x + cell_size, start_y, game.getWellCols() * cell_size,
                game.getWellRows() * cell_size);
        
        g.setColor(Color.BLUE);
        g.setFont(g.getFont().deriveFont(20.0f));
        g.drawString("Your Score: " + game.getCurrentScore(), cell_size, 
                      cell_size);
        drawWell(g);
        drawBackground(g);
        drawFallingBrick(g);
        if (game.getCurrentState() != 0) {
            gameOverMessage(g);
        }
    }

    private void drawFallingBrick(Graphics g) {
        for (int brickSeg = 0; brickSeg < 4; brickSeg++) {
            int drawX = ((game.getSegCol(brickSeg) + 1) * cell_size) + start_x;
            int drawY = (game.getSegRow(brickSeg) * cell_size) + start_y;

            g.setColor(colors[game.getFallingBrickColor()]);
            g.fillRect(drawX, drawY, cell_size, cell_size);

            g.setColor(Color.BLACK);
            g.drawRect(drawX, drawY, cell_size, cell_size);

        }
    }

    private void drawWell(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(start_x, start_y, cell_size, game.getWellRows() * cell_size);
        //left boundary
        g.fillRect(start_x, start_y + (game.getWellRows() * cell_size),
                (cell_size * 2) + (game.getWellCols() * cell_size),
                cell_size);
        // bottom boundary
        g.fillRect(start_x + (game.getWellCols() * cell_size) + cell_size,
                start_y, cell_size, game.getWellRows() * cell_size);
        // right boundary

    }

    private void drawBackground(Graphics g) {
        for (int i = 0; i < game.getWellRows(); i++) {
            for (int j = 0; j < game.getWellCols(); j++) {
                if (game.fetchBoardPosition(i, j) != 0) {
                    int drawX = ((j + 1) * cell_size) + start_x;
                    int drawY = (i * cell_size) + start_y;
                    g.setColor(colors[game.fetchBoardPosition(i, j) - 1]);
                    g.fillRect(drawX, drawY, cell_size, cell_size);

                    g.setColor(Color.BLACK);
                    g.drawRect(drawX, drawY, cell_size, cell_size);
                }
            }
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    private void cycleMove() {
        timer = new Timer(speed, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!isPause() && game.getCurrentState() == 0) {
                    game.makeMove('D'); //Here,TetrisGame handles the validation
                    repaint();
                }
            }
        });
        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
    }

    public void gameOverMessage(Graphics g) 
    {
        int messageWidth = 280;
        int messageHeight = 50;
        int game_x = (getWidth() - messageWidth)/2;
        int game_y = (getHeight()- messageHeight) / 2;
        int border_thickness = 4;
        int font_size = 40;
        int x_alignment = game_x + 25;
        int y_alignment = game_y + 35;
        
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(border_thickness));
        
        g.setColor(Color.BLACK);
        g.drawRect(game_x,game_y, messageWidth, messageHeight);
        g.setColor(Color.WHITE);
        g.fillRect(game_x,game_y, messageWidth, messageHeight);
        
        Font originalFont = g.getFont();
        g.setFont(originalFont.deriveFont(Font.BOLD, font_size));
        
        g.setColor(Color.BLUE);
        g.drawString("GAME OVER",x_alignment , y_alignment);
        
        g2d.setStroke(originalStroke);
        g.setFont(originalFont);
        
    }
}
