import java.awt.*;
import java.util.ArrayList;

/**
 * PathA class representing a pathfinding algorithm.
 */
public class PathA {
    private World myWorld;
    private ArrayList<Cell> openSet = new ArrayList<>();
    private ArrayList<Cell> closedSet = new ArrayList<>();
    private ArrayList<Cell> path = new ArrayList<>();
    private Cell start;
    private Cell end;
    private boolean noSolution;
    private Cell current;

    /**
     * Constructor for the PathA class, initializing the pathfinding algorithm.
     *
     * @param w The World for which the path is found.
     */
    public PathA(World w) {
        this.myWorld = w;
        this.start = myWorld.getCells().get(0);
        this.end = myWorld.getCells().get(myWorld.getCells().size() - myWorld.getCols() - 2);
        this.noSolution = false;
        addNeighbors();
        openSet.add(start);
    }

    /**
     * Adds neighbors to each cell in the world.
     */
    public void addNeighbors() {
        for (Cell c : myWorld.getCells()) {
            c.addNeighbors(c.getNeighborsFP(), myWorld.getCells(), myWorld.getCols(), myWorld.getRows());
        }
    }

    /**
     * Computes the heuristic distance between two cells.
     *
     * @param a The first cell.
     * @param b The second cell.
     * @return The heuristic distance between the cells.
     */
    public double heuristic(Cell a, Cell b) {
        int i = Math.abs(a.getI() - b.getI());
        int j = Math.abs(a.getJ() - b.getJ());

        return Math.sqrt(i * i + j * j);
    }

    /**
     * Finds the path from the current cell to the start cell.
     *
     * @param temp The current cell.
     */
    public void findThePath(Cell temp) {
        path.clear();
        if (!noSolution) {
            path.add(temp);
            while (temp.getPrevious() != null) {
                path.add(temp.getPrevious());
                temp = temp.getPrevious();
            }
        }
    }

    /**
     * The main algorithm for finding the path.
     */
    public void algorithm() {
        if (openSet.size() > 0) {

            int winner = 0; // assume that the lowest one is 0
            for (int i = 0; i < openSet.size(); i++) {
                if (openSet.get(i).getF() < openSet.get(winner).getF()) {
                    winner = i;
                }
            }

            current = openSet.get(winner);
            findThePath(current);

            if (current == end) {
                findThePath(end);
                return;
            }

            openSet.remove(current);
            closedSet.add(current);

            ArrayList<Cell> neighbors = current.getNeighborsFP();
            for (int i = 0; i < neighbors.size(); i++) {
                Cell neighbor = neighbors.get(i);

                if (!closedSet.contains(neighbor) && !neighbor.isWall()) {
                    double tempG = current.getG() + 1;

                    boolean newPath = false;
                    if (openSet.contains(neighbor)) {
                        if (tempG < neighbor.getG()) {
                            neighbor.setG(tempG + 1);
                            newPath = true;
                        }
                    } else {
                        neighbor.setG(tempG);
                        newPath = true;
                        openSet.add(neighbor);
                    }

                    if (newPath) {
                        neighbor.setH(heuristic(neighbor, end));
                        neighbor.setF(neighbor.getG() + neighbor.getH());
                        neighbor.setPrevious(current);
                    }
                }
            }

        } else {
            noSolution = true;
        }
    }

    /**
     * Draws the path on the given graphics context.
     *
     * @param g The graphics context to draw on.
     */
    public void drawPath(Graphics g) {
        this.algorithm();
        for (int i = 0; i < path.size(); i++) {
            path.get(i).draw(g, new Color(0, 0, 255));
        }
    }
}
