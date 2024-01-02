import java.awt.*;
import javax.swing.JPanel;

/**
 * Canvas class representing a panel for displaying a maze and its solution path.
 */
public class Canvas extends JPanel {
    private MazeA maze;
    private PathA path;

    /**
     * Constructor for the Canvas class, setting up the initial configuration.
     */
    public Canvas() {
        setup();
    }

    /**
     * Method to set up the initial configuration of the canvas.
     */
    public void setup() {
        setSize(420, 420);
        setPreferredSize(new Dimension(400, 400));
        setBackground(new Color(122, 119, 119));

        World world = new World();

        int cellWidth = getWidth() / world.getCols();

        for (int i = 0; i < world.getRows(); i++) {
            for (int j = 0; j < world.getCols(); j++) {
                Cell cell = new Cell(i, j, cellWidth, world);
                world.getCells().add(cell);
            }
        }

        for (int i = 0; i < world.getCols(); i++) {
            for (int j = 0; j < world.getRows(); j++) {
                if (!(i % 2 == 0) || !(j % 2 == 0)) {
                    world.getCells().get(j + i * world.getCols()).setWall(true);
                }
            }
        }
        world.getCells().get(0).setWall(false);
        world.getCells().get(world.getCells().size() - world.getCols() - 2).setWall(false);

        this.maze = new MazeA(world);
        this.path = new PathA(world);
    }

    /**
     * Method to paint the canvas, drawing the maze and its solution path.
     *
     * @param g The graphics context to paint on.
     */
    public void paint(Graphics g) {
        super.paint(g);

        maze.drawMaze(g);

        if (maze.isDone()) {
            path.drawPath(g);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        repaint();
    }
}
