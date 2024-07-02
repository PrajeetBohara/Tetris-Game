/*
 * Creating Sub-Class SquareBrick
 * Prajeet Bohara
 * 11/28/2023
 */

public class SquareBrick extends TetrisBrick {

    public SquareBrick(int colorNum, int centerCol) {

        super(colorNum, centerCol);
    }

    public int[][] initPosition(int centerCol) {

        position[0][0] = 0;
        position[0][1] = centerCol;

        position[1][0] = 0;
        position[1][1] = centerCol + 1;

        position[2][0] = 1;
        position[2][1] = centerCol;

        position[3][0] = 1;
        position[3][1] = centerCol + 1;

        return position;
    }

    public void rotate() {
    }

    public void unrotate() {
    }
}
