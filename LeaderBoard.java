/*
 * Creating Leaderboard Class
 * Prajeet Bohara
 * 11/28/2023
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

public class LeaderBoard {

    private class Player implements Comparable<Player> {

        String name;
        Integer score;

        public Player() {
        }

        public Player(String name, Integer score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public void setSScore(String score) {
            this.score = Integer.valueOf(score);
        }

        public int compareTo(Player player2) {
            return Integer.compare(this.score, player2.score);
        }

        public String toString() {
            return name + "," + score + "\n";
        }

    }

    private ArrayList<Player> players;

    public LeaderBoard() {
        loadFileData();
    }

    private void loadFileData() {
        players = new ArrayList<>();
        File savingFile = new File("Scores.csv");
        if (!savingFile.exists()) {
            try {
                savingFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader bfRdr = new BufferedReader(new FileReader
                                    (savingFile))) 
        {
            String buffer;
            while ((buffer = bfRdr.readLine()) != null) {
                String[] values = buffer.split(",");
                if (values.length > 1) {
                    Player newPlayer = new Player();
                    newPlayer.setName(values[0]);
                    newPlayer.setSScore(values[1]);
                    players.add(newPlayer);
                }
            }
        } catch (IOException e) {
           JOptionPane.showMessageDialog(null,"Error occured while reading the "
                                  + "file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteHighScores() {
        File savingFile = new File("Scores.csv");
        if (savingFile.exists()) {
            savingFile.delete();
        } else {
            System.err.println("NO Highscores recorded!");
        }
    }

    public void updateLeaderBoard(int score) {
        String TempUserName = "Default";
        Player currPlayer = new Player(TempUserName, score);
        File savingFile = new File("Scores.csv");
        if (!savingFile.exists()) {
            try {
                savingFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadFileData();
        if (players.isEmpty()) {
            TempUserName = JOptionPane.showInputDialog("Enter Name: ");
            if (TempUserName != null && !TempUserName.trim().isEmpty()) {
                currPlayer.setName(TempUserName);
            }
            players.add(currPlayer);
        } else if (currPlayer.score >= Collections.min(players).score) {
            for (int i = 0; i < players.size(); i++) {
                if (currPlayer.score >= players.get(i).score) {
                    TempUserName = JOptionPane.showInputDialog("Enter Name: ");
                    if (TempUserName != null && !TempUserName.trim().isEmpty())
                    {
                        currPlayer.setName(TempUserName);
                    }
                    players.add(i, currPlayer);
                    break;
                }
            }
        } else if (players.size() < 10) {
            TempUserName = JOptionPane.showInputDialog("Enter Name: ");
            if (TempUserName != null && !TempUserName.trim().isEmpty()) {
                currPlayer.setName(TempUserName);
            }
            players.add(currPlayer);
        }
        
        if (players.size() > 10) {
            players.remove(10);
        }

        try (BufferedWriter writePlayer = new BufferedWriter(new FileWriter
                                         (savingFile))) 
        {
            for (int i = 0; i < players.size(); i++) {
                writePlayer.write(players.get(i).toString());
            }
        } catch (IOException e) {
           JOptionPane.showMessageDialog(null,"Error occured while reading the "
                                  + "file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayHighScores()
    {
        int width = 300;
        int height = 500;
        loadFileData();
        JFrame highScoreFrame = new JFrame();
        highScoreFrame.setSize(width, height);
        String[] dataList = new String[11];
        dataList[0] = "Names        Scores\n";
        for (int i = 0; i < players.size(); i++) {
            dataList[i + 1] = players.get(i).toString().replace(",", ",     ");
        }
        JList<String> displayData = new JList(dataList);
        JScrollPane scrollPane = new JScrollPane(displayData);
        highScoreFrame.add(scrollPane);
        highScoreFrame.setVisible(true);
    }
}
