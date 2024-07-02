/*
 * Creating abstract class TetrisBrick
 * Prajeet Bohara
 * 11/28/2023
 */

import java.awt.*;

public abstract class TetrisBrick 
{

    protected int numSegments = 4;
    protected int[][] position = new int[numSegments][2];
    protected int orientation;
    protected Color color;
    protected int colorNum;
    
    public TetrisBrick(int colorNum, int centerCol) {
        this.colorNum = colorNum;
        this.position = initPosition(centerCol);
        this.orientation = 1;
    }
    
     public String toString() {
        String result = String.format("%d\n%d\n%d,%d\n%d,%d\n%d,%d\n%d,%d\n",
                colorNum, orientation,
                position[0][0], position[0][1],
                position[1][0], position[1][1],
                position[2][0], position[2][1],
                position[3][0], position[3][1]);
        return result;
    }

    public void moveDown() {
        for (int segmentNo = 0; segmentNo < numSegments; segmentNo++) {
            position[segmentNo][0] += 1;
        }
    }

    public void moveUp() {
        for (int segmentNo = 0; segmentNo < numSegments; segmentNo++) {
            position[segmentNo][0] -= 1;
        }
    }

    public void moveLeft() {
        for (int segmentNo = 0; segmentNo < numSegments; segmentNo++) {
            position[segmentNo][1] -= 1;
        }
    }

    public void moveRight() {
        for (int segmentNo = 0; segmentNo < numSegments; segmentNo++) {
            position[segmentNo][1] += 1;
        }
    }

    public int getColorNum() {
        return colorNum;
    }

    public abstract int[][] initPosition(int centerCol);

    public abstract void rotate();

    public abstract void unrotate();

    public int getNumSegments() {
        return numSegments;

    }

    public int[][] getPosition() {
        return position;
    }
}
