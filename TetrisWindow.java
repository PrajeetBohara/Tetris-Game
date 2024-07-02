/*
 * Main Executable class for Tetris Game
 * Prajeet Bohara
 * 11/28/2023
 */

import java.awt.event.*;
import javax.swing.*;

public class TetrisWindow extends JFrame {

    private TetrisGame game;
    private TetrisDisplay display;
    private LeaderBoard highscores;
    private int win_width = 700;
    private int win_height = 700;
    private int game_rows = 20;
    private int game_cols = 13;

    public TetrisWindow() {

        this.setTitle("My Tetris Game: Prajeet Bohara");
        this.setSize(win_width, win_height);

        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game);
        highscores = new LeaderBoard();
        addMenu();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(display);
        this.setVisible(true);
    }

    // Helper Class to add menu
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game Menu");
        JMenu boardMenu = new JMenu("Leaderboard Menu");
        JMenuItem newGameItem = new JMenuItem("Start New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Current Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game From File");
        JMenuItem showHighScoreItem = new JMenuItem("Show Highscores");
        JMenuItem clearHighScoreItem = new JMenuItem("Clear Highscores");
        gameMenu.add(newGameItem);
        gameMenu.add(loadGameItem);
        gameMenu.add(saveGameItem);
        boardMenu.add(showHighScoreItem);
        boardMenu.add(clearHighScoreItem);
        menuBar.add(gameMenu);
        menuBar.add(boardMenu);
        this.setJMenuBar(menuBar);

        newGameItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                game.newGame();
                display.setPause(false);
            }
        });

        saveGameItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                game.saveToFile();
                display.setPause(true);
            }
        });

        loadGameItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                game.retrieveFromFile();
                display.repaint();
            }
        });

        showHighScoreItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                highscores.displayHighScores();
                display.setPause(true);
            }
        });

        clearHighScoreItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                highscores.deleteHighScores();
            }
        });
    }

    public static void main(String[] args) 
    {
       new TetrisWindow();
    }
}
