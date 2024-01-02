import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Cell class representing a cell in a maze.
 */
public class Cell {
    private int i, j, x, y, w;
    private double f, g, h;
    private Color color;
    private boolean visited = false;
    private boolean wall;
    private World myWorld;

    private ArrayList<Cell> neighbors = new ArrayList<>();
    private ArrayList<Cell> neighborsFP = new ArrayList<>();

    private Cell previous;

    /**
     * Constructor for the Cell class.
     *
     * @param i      The row index of the cell.
     * @param j      The column index of the cell.
     * @param w      The width of the cell.
     * @param mine   The World to which the cell belongs.
     */
    public Cell(int i, int j, int w, World mine) {
        this.i = i;
        this.j = j;
        this.w = w;

        this.f = 0;
        this.g = 0;
        this.h = 0;

        this.x = this.i * w;
        this.y = this.j * w;

        this.previous = null;
        this.myWorld = mine;
    }

    /**
     * Gets the row index of the cell.
     *
     * @return The row index.
     */
    public int getI() {
        return i;
    }

    /**
     * Gets the column index of the cell.
     *
     * @return The column index.
     */
    public int getJ() {
        return j;
    }

    /**
     * Gets the F value of the cell.
     *
     * @return The F value.
     */
    public double getF() {
        return f;
    }

    /**
     * Sets the F value of the cell.
     *
     * @param f The new F value.
     */
    public void setF(double f) {
        this.f = f;
    }

    /**
     * Gets the G value of the cell.
     *
     * @return The G value.
     */
    public double getG() {
        return g;
    }

    /**
     * Sets the G value of the cell.
     *
     * @param g The new G value.
     */
    public void setG(double g) {
        this.g = g;
    }

    /**
     * Gets the H value of the cell.
     *
     * @return The H value.
     */
    public double getH() {
        return h;
    }

    /**
     * Sets the H value of the cell.
     *
     * @param h The new H value.
     */
    public void setH(double h) {
        this.h = h;
    }

    /**
     * Gets the previous cell in the path.
     *
     * @return The previous cell.
     */
    public Cell getPrevious() {
        return previous;
    }

    /**
     * Sets the previous cell in the path.
     *
     * @param previous The previous cell.
     */
    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    /**
     * Checks if the cell is a wall.
     *
     * @return True if the cell is a wall, false otherwise.
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * Sets whether the cell is a wall.
     *
     * @param wall True if the cell is a wall, false otherwise.
     */
    public void setWall(boolean wall) {
        this.wall = wall;
    }

    /**
     * Sets the visited status of the cell.
     *
     * @param visited True if the cell is visited, false otherwise.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
        this.setColor(new Color(255, 0, 255, 255));
    }

    /**
     * Gets the color of the cell.
     *
     * @return The color of the cell.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the cell.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the list of neighbors for the cell.
     *
     * @return The list of neighbors.
     */
    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }

    /**
     * Gets the list of neighbors for the cell used in the final path.
     *
     * @return The list of neighbors for the final path.
     */
    public ArrayList<Cell> getNeighborsFP() {
        return neighborsFP;
    }

    /**
     * Computes the index of a cell given its row and column indices.
     *
     * @param i The row index.
     * @param j The column index.
     * @return The computed index.
     */
    public int index(int i, int j) {
        return j + i * myWorld.getCols();
    }

    /**
     * Checks if a given cell has been visited and adds it to the list of neighbors if not.
     *
     * @param c The cell to check.
     */
    public void isVisited(Cell c) {
        if (!c.visited) {
            neighbors.add(c);
        }
    }

    /**
     * Checks if a given row and column index is within the valid range of indices for the world.
     *
     * @param i The row index.
     * @param j The column index.
     * @return True if the indices are valid, false otherwise.
     */
    public boolean isValidIndex(int i, int j) {
        return i >= 0 && j >= 0 && i <= myWorld.getRows() - 1 && j <= myWorld.getCols() - 1;
    }

    /**
     * Checks neighboring cells and returns a random unvisited neighbor.
     *
     * @return A random unvisited neighbor or null if no such neighbor exists.
     */
    public Cell checkNeighbors() {
        if (isValidIndex(i, j - 2)) {
            Cell top = myWorld.getCells().get(index(i, j - 2));
            isVisited(top);
        }

        if (isValidIndex(i + 2, j)) {
            Cell right = myWorld.getCells().get(index(i + 2, j));
            isVisited(right);
        }

        if (isValidIndex(i, j + 2)) {
            Cell bottom = myWorld.getCells().get(index(i, j + 2));
            isVisited(bottom);
        }

        if (isValidIndex(i - 2, j)) {
            Cell left = myWorld.getCells().get(index(i - 2, j));
            isVisited(left);
        }

        if (neighbors.size() > 0) {
            int r = ThreadLocalRandom.current().nextInt(0, neighbors.size());
            return neighbors.get(r);
        }
        return null;
    }

    /**
     * Adds valid neighbors to the given list for the current cell.
     *
     * @param neighbors The list to add valid neighbors to.
     * @param grid      The grid of cells.
     * @param cols      The number of columns in the grid.
     * @param rows      The number of rows in the grid.
     */
    public void addNeighbors(ArrayList<Cell> neighbors, ArrayList<Cell> grid, int cols, int rows) {
        if (i < cols - 1) {
            neighbors.add(grid.get(index(i + 1, j)));
        }
        if (i > 0) {
            neighbors.add(grid.get(index(i - 1, j)));
        }
        if (j < rows - 1) {
            neighbors.add(grid.get(index(i, j + 1)));
        }
        if (j > 0) {
            neighbors.add(grid.get(index(i, j - 1)));
        }
    }

    /**
     * Draws the cell on the given graphics context with the specified color.
     *
     * @param g     The graphics context to draw on.
     * @param color The color to use for drawing.
     */
    public void draw(Graphics g, Color color) {
        if (this.visited) {
            g.setColor(color);
            g.fillRect(x, y, w, w);
            g.drawRect(x, y, w, w);
        } else if (isWall()) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, w, w);
            g.drawRect(x, y, w, w);
        } else {
            g.setColor(Color.yellow);
            g.fillRect(x, y, w, w);
            g.drawRect(x, y, w, w);
        }
    }
}
