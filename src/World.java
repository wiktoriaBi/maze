import java.util.ArrayList;

/**
 * World class representing the grid world.
 */
public class World {
    private int cols = 50;
    private int rows = 50;
    private ArrayList<Cell> cells = new ArrayList<>();

    /**
     * Default constructor for the World class.
     */
    public World() {
    }

    /**
     * Gets the list of cells in the world.
     *
     * @return The list of cells.
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * Gets the number of columns in the world.
     *
     * @return The number of columns.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the number of rows in the world.
     *
     * @return The number of rows.
     */
    public int getRows() {
        return rows;
    }
}
