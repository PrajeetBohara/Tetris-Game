/*
 * Creating Sub-Class ZeeBrick
 * Prajeet Bohara
 * 11/28/2023
 */

public class ZeeBrick extends TetrisBrick {

    public ZeeBrick(int colorNum, int centerCol) {

        super(colorNum, centerCol);

    }

    public int[][] initPosition(int centerCol) {

        position[0][0] = 0;
        position[0][1] = centerCol;

        position[1][0] = 0;
        position[1][1] = centerCol + 1;

        position[2][0] = 1;
        position[2][1] = centerCol + 1;

        position[3][0] = 1;
        position[3][1] = centerCol + 2;

        return position;
    }

    public void rotate() {
        int[][] newPos = new int[4][2];
        if (orientation == 1) {
            orientation += 1;
            newPos[0][0] = position[0][0] - 1;
            newPos[0][1] = position[0][1] + 2;
            newPos[1][0] = position[1][0];
            newPos[1][1] = position[1][1] + 1;
            newPos[2][0] = position[2][0] - 1;
            newPos[2][1] = position[2][1];
            newPos[3][0] = position[3][0];
            newPos[3][1] = position[3][1] - 1;
            this.position = newPos;
        } else if (orientation == 2) {
            orientation -= 1;
            newPos[0][0] = position[0][0] + 1;
            newPos[0][1] = position[0][1] - 2;
            newPos[1][0] = position[1][0];
            newPos[1][1] = position[1][1] - 1;
            newPos[2][0] = position[2][0] + 1;
            newPos[2][1] = position[2][1];
            newPos[3][0] = position[3][0];
            newPos[3][1] = position[3][1] + 1;
            this.position = newPos;
        }
    }

    public void unrotate() {
        rotate();
    }
}
