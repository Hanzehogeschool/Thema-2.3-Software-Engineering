package model.environment;

import java.awt.Color;
import java.awt.Polygon;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 * @author Dustin Meijer        (2012)
 * @author Alexander Jeurissen  (2012)
 * @author Davide Brugali       (2002)
 * @version 2.0
 */

public class Obstacle {

    private static final Color STANDARD_FOREGROUND_COLOR = Color.ORANGE;
    private static final Color STANDARD_BACKGROUND_COLOR = Color.BLUE;

    private static final Color STANDARD_OPAQUE_BACKGROUND_COLOR = Color.MAGENTA;
    private static final Color STANDARD_OPAQUE_FOREGROUND_COLOR = Color.DARK_GRAY;

    // This polygon contains the corner points of the obstacle.
    protected final Polygon polygon;

	private String name;
    // This boolean indicates if the obstacle is opaque or not.
	protected boolean opaque;

	private Color foregroundColor;
	private Color backgroundColor;

	private Color opaqueBackgroundColor;
	private Color opaqueForegroundColor;

	public Obstacle() {
		this.name = "Obstacle";

		this.polygon = new Polygon();
		this.opaque = false;

		this.foregroundColor = Obstacle.STANDARD_FOREGROUND_COLOR;
		this.backgroundColor = Obstacle.STANDARD_BACKGROUND_COLOR;

		this.opaqueBackgroundColor = Obstacle.STANDARD_OPAQUE_BACKGROUND_COLOR;
		this.opaqueForegroundColor = Obstacle.STANDARD_OPAQUE_FOREGROUND_COLOR;

	}


	public Obstacle(Color foregroundColor, Color backgroundColor, Color opaqueBackgroundColor, Color opaqueForegroundColor) {
		this();
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;

		this.opaqueBackgroundColor = opaqueBackgroundColor;
		this.opaqueForegroundColor = opaqueForegroundColor;
	}

	/**
	 * Reads the <POINT X=".." Y=".."/> lines from the file and adds those points to the polygon
	 *
	 * @param line       The <OBSTACLE> line read in Environment.
	 * @param lineReader The BufferedReader from which to read the <POINT ...> lines.
	 * @return Whether or not the obstacle was built succesfully.
	 */
	public boolean parseXML(String line, BufferedReader lineReader) {
		// Extracts the parameters
		name = parseAttribute(line, "NAME");
		opaque = Boolean.valueOf(parseAttribute(line, "OPAQUE"));

		String docLine;
		try {
			while ((docLine = lineReader.readLine()) != null) {
				// reads the file line by line
				if (docLine.contains("</OBSTACLE"))
					return true;
				else if (docLine.contains("<POINT")) {
					// extracts the coordinates of a vertex
					int px = Integer.parseInt(parseAttribute(docLine, "X"));
					int py = Integer.parseInt(parseAttribute(docLine, "Y"));
					// adds the new point to the shape
					polygon.addPoint(px, py);
				} else {
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}


	public String toString() {
		String xml = "  <OBSTACLE NAME=" + '"' + name + '"' +
				" OPAQUE=" + '"' + opaque + '"' + ">\n";
		for (int j = 0; j < polygon.npoints; j++)
			xml += "    <POINT X=" + '"' + polygon.xpoints[j] + '"' + " Y=" + '"' + polygon.ypoints[j] + '"' + "/>\n";
		xml += "  </OBSTACLE>";
		return xml;
	}

	public Polygon getPolygon() {
		return this.polygon;
	}

	public boolean getOpaque() {
		return this.opaque;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getOpaqueBackgroundColor() {
		return opaqueBackgroundColor;
	}

	public Color getOpaqueForegroundColor() {
		return opaqueForegroundColor;
	}

	private String parseAttribute(String line, String attribute) {
		int indexInit = line.indexOf(attribute + "=");
		String parameter = line.substring(indexInit + attribute.length() + 2);
		int indexEnd = parameter.indexOf('"');

		return parameter.substring(0, indexEnd);
	}

}
