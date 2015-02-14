package gui.views;


import model.virtualmap.OccupancyMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 * Project : Leertaak 4 Mobile Robot Explorer
 * Package : gui.views
 * <p/>
 * User: Dustin Meijer
 * Date: 13-12-12
 * Time: 18:15
 */
public class OccupancyMapView extends JPanel implements ActionListener {

	private static final char UNKNOWN = 'n';
	private static final char EMPTY = 'e';
	private static final char OBSTACLE = 'o';
	private static final char ROBOT = 'r';

	private static final Color MAP_UNKNOWN_COLOR = Color.YELLOW;
	private static final Color MAP_OBSTACLE_COLOR = Color.BLUE;
	private static final Color MAP_ROBOT_COLOR = Color.BLACK;

	private static final Color GRID_COLOR = Color.LIGHT_GRAY;

	private int width;
	private int height;
	private int cellDimension;

	private char[][] grid;

	private final OccupancyMap model;


	public OccupancyMapView(OccupancyMap model) {
		this.model = model;
		this.width = model.getMapWidth();
		this.height = model.getMapHeight();
		this.cellDimension = model.getCellDimension();
		this.grid = model.getGrid();

		this.setBackground(SystemColor.window);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Image img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics image = img.getGraphics();

		this.drawMap(image);
		this.drawGrid(image);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), SystemColor.window, null);
	}


	private void drawMap(Graphics g) {
		for (int row = 0; row < width / cellDimension; row++) {
			for (int column = 0; column < height / cellDimension; column++) {
				if (grid[row][column] == UNKNOWN) {
					g.setColor(OccupancyMapView.MAP_UNKNOWN_COLOR);
					g.fillRect(row * cellDimension, column * cellDimension, cellDimension, cellDimension);
				} else if (grid[row][column] == OBSTACLE) {
					g.setColor(OccupancyMapView.MAP_OBSTACLE_COLOR);
					g.fillRect(row * cellDimension, column * cellDimension, cellDimension, cellDimension);
				} else if (grid[row][column] == ROBOT) {
					g.setColor(OccupancyMapView.MAP_ROBOT_COLOR);
					g.fillRect(row * cellDimension, column * cellDimension, cellDimension, cellDimension);
				}

			}
		}
	}

	private void drawGrid(Graphics g) {
		g.setColor(OccupancyMapView.GRID_COLOR);
		for (int i = 0; i <= width / cellDimension; i++)
			g.drawLine(i * cellDimension, 0, i * cellDimension, height);
		for (int j = 0; j <= height / cellDimension; j++)
			g.drawLine(0, j * cellDimension, width, j * cellDimension);
	}

	/**
	 * Invoked when an action occurs.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof OccupancyMap) {
			OccupancyMap model = (OccupancyMap) e.getSource();
			this.width = model.getMapWidth();
			this.height = model.getMapHeight();
			this.cellDimension = model.getCellDimension();
			this.grid = model.getGrid();
		}

		repaint();
	}
}
