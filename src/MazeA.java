import java.awt.*;
import java.util.Stack;

/**
 * MazeA class representing a maze generation algorithm.
 */
public class MazeA {
    private Stack<Cell> stack = new Stack<>();
    private World myWorld;
    private boolean done = false;
    public Cell current;
    public Cell next;

    /**
     * Checks if the maze generation is complete.
     *
     * @return True if the maze generation is complete, false otherwise.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * MazeA constructor initializing the maze generation with the given world.
     *
     * @param w The World for which the maze is generated.
     */
    public MazeA(World w) {
        this.myWorld = w;
        this.current = w.getCells().get(0);
    }

    /**
     * Removes walls between two cells.
     *
     * @param c The current cell.
     * @param n The next cell.
     */
    public void removeWalls(Cell c, Cell n) {
        int x = c.getI() - n.getI();
        if (x == 2) {
            int i = c.getI() - 1;
            int j = c.getJ();
            myWorld.getCells().get(myWorld.getCols() * i + j).setWall(false);
            myWorld.getCells().get(myWorld.getCols() * i + j).setVisited(true);
            myWorld.getCells().get(myWorld.getCols() * i + j).setColor(new Color(255, 0, 255, 255));
        } else if (x == -2) {
            int i = c.getI() + 1;
            int j = c.getJ();
            myWorld.getCells().get(myWorld.getCols() * i + j).setWall(false);
            myWorld.getCells().get(myWorld.getCols() * i + j).setVisited(true);
            myWorld.getCells().get(myWorld.getCols() * i + j).setColor(new Color(255, 0, 255, 255));
        }

        int y = c.getJ() - n.getJ();
        if (y == 2) {
            int i = c.getI();
            int j = c.getJ() - 1;
            myWorld.getCells().get(myWorld.getCols() * i + j).setWall(false);
            myWorld.getCells().get(myWorld.getCols() * i + j).setVisited(true);
            myWorld.getCells().get(myWorld.getCols() * i + j).setColor(new Color(255, 0, 255, 255));
        } else if (y == -2) {
            int i = c.getI();
            int j = c.getJ() + 1;
            myWorld.getCells().get(myWorld.getCols() * i + j).setWall(false);
            myWorld.getCells().get(myWorld.getCols() * i + j).setVisited(true);
            myWorld.getCells().get(myWorld.getCols() * i + j).setColor(new Color(255, 0, 255, 255));
        }
    }

    /**
     * Generates a random pink color.
     *
     * @return A random pink color.
     */
    public Color randomPink() {
        return new Color(205 + (int) (Math.random() * 50), 0, 205 + (int) (Math.random() * 50), 255);
    }

    /**
     * Performs steps in the maze generation algorithm.
     */
    public void change() {
        //STEP 1
        next = current.checkNeighbors();
        if (next != null) {
            next.setVisited(true);

            //STEP 2
            stack.push(current);

            //STEP 3
            removeWalls(current, next);

            //STEP 4
            current.setColor(randomPink());
            current.getNeighbors().clear();
            current = next;
        } else if (!stack.isEmpty()) {
            current.setColor(new Color(255, 0, 255, 255));
            current = stack.pop();
        } else {
            done = true;
        }
    }

    /**
     * Draws the maze on the given graphics context.
     *
     * @param g The graphics context to draw on.
     */
    public void drawMaze(Graphics g) {
        current.setVisited(true);
        current.setColor(Color.GREEN);
        for (Cell c : myWorld.getCells()) {
            c.draw(g, c.getColor());
        }

        change();
    }
}
