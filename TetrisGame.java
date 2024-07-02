/*
 * Creating Class Tetris Game where the game logic houses
 * Prajeet Bohara
 * 11/28/2023
 */

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TetrisGame {

    private TetrisBrick fallingBrick;
    private int[][] background;
    private int rows; //well rows
    private int cols; //well columns
    private int state;
    private int score;
    private int numBrickTypes = 7;
    private Random randomGen;
    private LeaderBoard leaderBoard;

    public TetrisGame(int wellRows, int wellCols) {
        this.rows = wellRows;
        this.cols = wellCols;
        randomGen = new Random();
        leaderBoard = new LeaderBoard();
        initBackground();
        spawnBrick();
    }

    private int Normalize(int readFromickNo) {
        boolean hasSpace = true;
        for (int i = 0; i < cols; i++) {
            if (background[2][i] != 0) {
                hasSpace = false;
            }
        }
        if (!hasSpace) {
            return 3;
        }
        return readFromickNo;
    }

    private void spawnBrick() {
        int randomBrick = randomGen.nextInt(numBrickTypes);
        int centerCol = cols / 2;
        randomBrick = Normalize(randomBrick);
        switch (randomBrick) {
            case 0:
                fallingBrick = new ElBrick(0, centerCol);
                break;
            case 1:
                fallingBrick = new EssBrick(1, centerCol);
                break;
            case 2:
                fallingBrick = new JayBrick(2, centerCol);
                break;
            case 3:
                fallingBrick = new LongBrick(3, centerCol);
                break;
            case 4:
                fallingBrick = new SquareBrick(4, centerCol);
                break;
            case 5:
                fallingBrick = new StackBrick(5, centerCol);
                break;
            case 6:
                fallingBrick = new ZeeBrick(6, centerCol);
                break;
        }
    }

    public void initBackground() {
        background = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                background[i][j] = 0;
            }
        }
    }

    private File getFile() {
        JFileChooser saveFile = new JFileChooser();
        saveFile.setFileFilter(new FileNameExtensionFilter("Text Files", 
                                                              "txt"));
        int select = saveFile.showOpenDialog(null);
        if (select == JFileChooser.APPROVE_OPTION) {
            File outputFile = saveFile.getSelectedFile();
            if (outputFile.exists()) 
            {
                return outputFile;
            }
        }
        return null;
    }

    public void retrieveFromFile() {
        File inputFile = getFile();
        if (inputFile != null) {
            try (BufferedReader readFrom = new BufferedReader(new FileReader
                                                               (inputFile)))
            {
                String[] lineData = readFrom.readLine().split(",");
                this.rows = Integer.parseInt(lineData[0]);
                this.cols = Integer.parseInt(lineData[1]);
                for (int i = 0; i < rows; i++) {
                    lineData = readFrom.readLine().split(",");
                    for (int j = 0; j < cols; j++) {
                        background[i][j] = Integer.parseInt(lineData[j]);
                    }
                }

                this.score = Integer.parseInt(readFrom.readLine());
                this.state = Integer.parseInt(readFrom.readLine());
                int brickC = Integer.parseInt(readFrom.readLine());
                int brickO = Integer.parseInt(readFrom.readLine());
                int[][] newBrick = new int[4][2];
                lineData = readFrom.readLine().split(",");
                newBrick[0][0] = Integer.parseInt(lineData[0]);
                newBrick[0][1] = Integer.parseInt(lineData[1]);
                lineData = readFrom.readLine().split(",");
                newBrick[1][0] = Integer.parseInt(lineData[0]);
                newBrick[1][1] = Integer.parseInt(lineData[1]);
                lineData = readFrom.readLine().split(",");
                newBrick[2][0] = Integer.parseInt(lineData[0]);
                newBrick[2][1] = Integer.parseInt(lineData[1]);
                lineData = readFrom.readLine().split(",");
                newBrick[3][0] = Integer.parseInt(lineData[0]);
                newBrick[3][1] = Integer.parseInt(lineData[1]);

                if (brickC == 0) {
                    fallingBrick = new ElBrick(brickC, cols / 2);
                } else if (brickC == 1) {
                    fallingBrick = new EssBrick(brickC, cols / 2);
                } else if (brickC == 2) {
                    fallingBrick = new JayBrick(brickC, cols / 2);
                } else if (brickC == 3) {
                    fallingBrick = new LongBrick(brickC, cols / 2);
                } else if (brickC == 4) {
                    fallingBrick = new SquareBrick(brickC, cols / 2);
                } else if (brickC == 5) {
                    fallingBrick = new StackBrick(brickC, cols / 2);
                } else if (brickC == 6) {
                    fallingBrick = new ZeeBrick(brickC, cols / 2);
                }
                fallingBrick.position = newBrick;
                fallingBrick.orientation = brickO;

            } catch (IOException e) {
               JOptionPane.showMessageDialog(null,"Error occured while reading "
                        + "the file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(this.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("File saved: " + fileToSave.getAbsolutePath());
        }
    }

    public void newGame() 
    {
        this.score = 0;
        this.state = 0;
        // Clear the background
        initBackground(); // Reset all colors to 0
        spawnBrick(); 
    }

    public int fetchBoardPosition(int row, int col) {
        return background[row][col];
    }

    public void makeMove(char moveBrick) {
        if (moveBrick == 'D') {
            fallingBrick.moveDown();
            if (!validateMove(moveBrick)) {
                fallingBrick.moveUp();
                spawnBrick();
            }
        } else if (moveBrick == 'L') {
            fallingBrick.moveLeft();
            if (!validateMove(moveBrick)) {
                fallingBrick.moveRight();
            }
        } else if (moveBrick == 'R') {
            fallingBrick.moveRight();
            if (!validateMove(moveBrick)) {
                fallingBrick.moveLeft();
            }
        } else if (moveBrick == 'U') {
            fallingBrick.rotate();
            if (!validateMove(moveBrick)) {
                fallingBrick.unrotate();
            }
        }
    }

    private boolean checkCompleteRow(int index) {
        for (int i = 0; i < cols; i++) {
            if (background[index][i] == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isGameOver() {
        for (int i = 0; i < cols; i++) {
            if (background[0][i] != 0) {
                return true;
            }
        }
        return false;
    }

    private void clearFilledRows() {
        int rowCounter = 0;
        for (int i = rows - 1; i > 0; i--) {
            if (checkCompleteRow(i)) {
                for (int j = i; j > 0; j--) {
                    int copyAbove[] = new int[cols];
                    System.arraycopy(background[j - 1], 0, copyAbove, 0, cols);
                    background[j] = copyAbove;
                }
                i = i + 1;
                rowCounter = rowCounter + 1;
            }
        }
        updateScore(rowCounter);
    }

    private void updateScore(int filledRows) {
        switch (filledRows) {
            case 1:
                score = score + 100;
                break;
            case 2:
                score = score + 300;
                break;
            case 3:
                score = score + 600;
                break;
            case 4:
                score = score + 1200;
                break;
            default:
        }
    }

    public int getCurrentScore() {
        return score;
    }

    public void setCurrentScore(int currentScore) {
        this.score = currentScore;
    }

    public int getCurrentState() {
        return state;
    }

    public void setCurrentState(int currentState) {
        this.state = currentState;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public String toString() {
        String result = "";
        result += String.format("%d,%d\n", rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result += background[i][j] + ",";
            }
            result += "\n";
        }
        result += String.format("%d\n%d\n%s\n", score, state, 
                  fallingBrick.toString());
        return result;
    }

    private boolean validateMove(char moveBrick) {
        for (int segment = 0; segment < fallingBrick.getNumSegments(); 
                segment++) 
        {
            if (moveBrick == 'D' && fallingBrick.getPosition()[segment][0] >= 
                    getWellRows())
            {
                transferColor();
                clearFilledRows();
                if (isGameOver()) {
                    state = 1;
                    leaderBoard.updateLeaderBoard(score);
                    state = 2;
                }
                return false;
            } else if (moveBrick == 'D' && background[fallingBrick.getPosition()
                    [segment][0]][fallingBrick.getPosition()[segment][1]] != 0)
            {
                transferColor();
                clearFilledRows();
                if (isGameOver()) {
                    state = 1;
                    leaderBoard.updateLeaderBoard(score);
                    state = 2;
                }
                return false;
            } else if (moveBrick == 'L' && fallingBrick.getPosition()
                       [segment][1] == -1) 
            {
                return false;
            } else if (moveBrick == 'L' && background[fallingBrick.getPosition()
                     [segment][0]][fallingBrick.getPosition()[segment][1]] != 0) 
            {
                return false;
            } else if (moveBrick == 'R' && fallingBrick.getPosition()[segment]
                        [1] == getWellCols()) 
            {
                return false;
            } else if (moveBrick == 'R' && background[fallingBrick.getPosition()
                     [segment][0]][fallingBrick.getPosition()[segment][1]] != 0)
            {
                return false;
            } else if (moveBrick == 'U') {
                if (fallingBrick.getPosition()[segment][0] < 0
                        || fallingBrick.getPosition()[segment][0] > rows - 1
                        || fallingBrick.getPosition()[segment][1] < 0
                        || fallingBrick.getPosition()[segment][1] > cols - 1
                        || background[fallingBrick.getPosition()[segment][0]]
                           [fallingBrick.getPosition()[segment][1]] != 0) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    public int getFallingBrickColor() 
    {
        return fallingBrick.colorNum;
    }

    public int getNumSegs() 
    {
        return fallingBrick.numSegments;
    }

    public int getSegRow(int readFromickSegment) 
    {
        return fallingBrick.position[readFromickSegment][0];
    }

    public int getSegCol(int readFromickSegment) 
    {
        return fallingBrick.position[readFromickSegment][1];
    }

    public int getWellRows() 
    {
        return rows;
    }

    public int getWellCols()
    {
        return cols;
    }

    public int getNumBrickTypes()
    {
        return numBrickTypes;
    }

    public int[][] getBackground() 
    {
        return background;
    }

    public void setBackground(int[][] background) 
    {
        this.background = background;
    }

    private void transferColor() 
    {
        int[][] position = fallingBrick.getPosition();
        for (int i = 0; i < fallingBrick.numSegments; i++)
        {
           background[position[i][0] - 1][position[i][1]] = fallingBrick.
                                                            getColorNum() + 1;
        }
    }
}
