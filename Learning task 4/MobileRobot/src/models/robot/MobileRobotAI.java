package models.robot;

import utils.ANSI;
import utils.Debugger;
import models.virtualmap.OccupancyMap;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Class representing a Mobile Robot AI.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class MobileRobotAI implements Runnable {

    /* Variables */


    /* Directions the mobile mobile robot is facing. */

    /**
     * The north direction coordinates.
     */
    private static final int NORTH = 270; // Up

    /**
     * The east direction coordinates.
     */
    private static final int EAST = 360; // Right

    /**
     * The south direction coordinates.
     */
    private static final int SOUTH = 90; // Down

    /**
     * The west direction coordinates.
     */
    private static final int WEST = 180; // Left


    /* Directions the mobile mobile robot moves to. */

    /**
     * The forward moving direction coordinates.
     */
    private static final int FORWARD = 0;

    /**
     * The right direction coordinates.
     */
    private static final int RIGHT = 90;

    /**
     * The max view distance.
     */
    private static final int MAX_VIEW_DISTANCE = 10;


    /* Accessories for the mobile robot ai. */

    /**
     * the occupancy map.
     */
    private final OccupancyMap occupancyMap;

    /**
     * The mobile robot.
     */
    private final MobileRobot mobileRobot;

    /**
     * The running.
     */
    private boolean running;


    /* Readers and writers. */

    /**
     * The pipe in.
     */
    PipedInputStream pipeIn;

    /**
     * The input.
     */
    BufferedReader input;

    /**
     * The output.
     */
    PrintWriter output;

    /**
     * The result.
     */
    String result = "";


    /* Positions and measures. */

    /**
     * The position.
     */
    private double[] position;

    /**
     * The measures.
     */
    private double[] measures;

    /**
     * The start x coordinates.
     */
    private double startX;

    /**
     * The start y coordinates.
     */
    private double startY;

    /**
     * The first position.
     */
    private boolean firstPosition;


    /* Methods */


    /* Constructors. */

    /**
     * Mobile Robot AI constructor.
     * Creates a new Mobile Robot AI.
     *
     * @param mobileRobot The mobile robot.
     * @param occupancyMap The occupancy map.
     */
    public MobileRobotAI(MobileRobot mobileRobot, OccupancyMap occupancyMap) {
        // Debugging.
        Debugger.print("MobileRobotAI", "MobileRobotAI", "executing");

        // Initialize the accessories for the mobile robot ai.
        this.occupancyMap = occupancyMap;
        this.mobileRobot = mobileRobot;

        try {
            // Initialize the readers and writers.
            this.pipeIn = new PipedInputStream();
            this.input = new BufferedReader(new InputStreamReader(pipeIn));
            this.output = new PrintWriter(new PipedOutputStream(pipeIn), true);
            this.result = "";

            // Initialize the positions
            this.position = new double[3];
            this.measures = new double[360];
            this.firstPosition = true;

            // Set running to true.
            this.running = true;

            // Set the output of the mobile robot.
            mobileRobot.setOutput(output);
        } catch (IOException ex) {
            System.err.println("MobileRobotAI: Something went wrong while initializing.");
        }
    }


    /* Process. */

    /**
     * Runs the Mobile Robot AI.
     */
    public void run() {
        // Debugging.
        Debugger.print("MobileRobotAI", "run", "executing");

        // Keeps running when running is set to true.
        while (running) {
            try {
                // Start processing.
                process();

                // If the map is scanned, quit the robot.
                if (mapScanned()) {
                    mobileRobot.quit();
                }
            } catch (IOException ioe) {
                System.err.println("MobileRobotAI: execution stopped.");
                running = false;
            }
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "run", "finished executing");
    }

    /**
     * The process that the mobile robot passes.
     *
     * @throws IOException
     */
    private void process() throws IOException {
        // Debugging..
        Debugger.print("MobileRobotAI", "process", "executing");

        // Scan the current area.
        scanArea();

        // The coordinates of the mobile robot.
        int xCoordinate = (int) this.position[0] / occupancyMap.getCellDimension();
        int yCoordinate = (int) this.position[1] / occupancyMap.getCellDimension();

        // Debugging..
        Debugger.print("MobileRobotAI", "process", "x coordinate: " + xCoordinate);
        Debugger.print("MobileRobotAI", "process", "y coordinate: " + yCoordinate);

        // Search directions.
        int[] searchDirections = determineDirection(FORWARD);

        // Debugging..
        Debugger.print("MobileRobotAI", "process", "searchDirections returns x direction, y direction > " + Arrays.toString(searchDirections));

        // Search direction coordinates.
        int xSearchDirection = searchDirections[0];
        int ySearchDirection = searchDirections[1];

        // If the end is reached.
        boolean reachedEnd = false;

        // The current known map.
        char[][] knownMap = this.occupancyMap.getGrid();

        // Amount of steps to take.
        int stepsToTake = 0;

        // Loop till the max view distance has been reached and the reached end is not true.
        while (stepsToTake < MAX_VIEW_DISTANCE && !reachedEnd) {

            // If the right wall is found.
            boolean rightWallFound = searchWallToTheRight(xCoordinate, yCoordinate);

            // If the right wall is found and the known map equals a empty from the occupancy map.
            if (rightWallFound && knownMap[xCoordinate][yCoordinate] == OccupancyMap.EMPTY) {

                // Increase the steps to take.
                stepsToTake++;

            // Else if the right wall is found and the known map equals a unknown from the occupancy map.
            } else if (rightWallFound && knownMap[xCoordinate][yCoordinate] == OccupancyMap.UNKNOWN) {

                // Debugging.
                Debugger.print("MobileRobotAI", "process", "x search found: UNKNOWN");

                // Move forward and set the reached end to true.
                moveForward(stepsToTake - 2);
                reachedEnd = true;

            // Else if the right wall is found and the known map equals a obstacle from the occupancy map.
            } else if (rightWallFound && knownMap[xCoordinate][yCoordinate] == OccupancyMap.OBSTACLE) {

                // Debugging.
                Debugger.print("MobileRobotAI", "process", "x search found: OBSTACLE");

                // Move forward, rotate to the left and set the reached end to true.
                moveForward(stepsToTake - 3);
                rotate("Left");
                reachedEnd = true;

            // Else if the right wall is not found and the known map equals a unknown from the occupancy map.
            } else if (!rightWallFound && knownMap[xCoordinate][yCoordinate] == OccupancyMap.UNKNOWN) {

                // Debugging.
                Debugger.print("MobileRobotAI", "process", "x search found: UNKNOWN");

                // Move forward and set the reached end to true.
                moveForward(stepsToTake - 2);
                reachedEnd = true;

            // Else if the right wall is not found and the known map equals a empty from the occupancy map.
            } else if (!rightWallFound && knownMap[xCoordinate][yCoordinate] == OccupancyMap.EMPTY) {

                // Debugging.
                Debugger.print("MobileRobotAI", "process", "xSearch: found EMPTY");

                // Cornet right and set the reached end to true.
                cornerRight(stepsToTake);
                reachedEnd = true;

            }

            // Increase the coordinates.
            xCoordinate += xSearchDirection;
            yCoordinate += ySearchDirection;
        }

        // If the end is not reached.
        if (!reachedEnd) {
            // Move forward.
            moveForward(stepsToTake - 2);
        }
    }

    /**
     * Corner right.
     *
     * @param stepsBeforeCorner The steps before cornet.
     * @throws IOException
     */
    private void cornerRight(int stepsBeforeCorner) throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "cornerRight", "executing");

        // Move forward and scan the area.
        moveForward(stepsBeforeCorner + 3);
        scanArea();

        // The coordinates of the mobile robot.
        int xCoordinate = (int) this.position[0] / occupancyMap.getCellDimension();
        int yCoordinate = (int) this.position[1] / occupancyMap.getCellDimension();

        // If the right wall is found.
        boolean rightWallFound = searchWallToTheRight(xCoordinate, yCoordinate);

        // If the right wall is not found, rotate to the right and move forward.
        if (!rightWallFound) {
            rotate("Right");
            moveForward(2);
        }
    }

    /**
     * Searches a wall to the right.
     *
     * @param xCoordinate The x coordinate.
     * @param yCoordinate The y coordinate.
     * @return If the right wall is found.
     */
    private boolean searchWallToTheRight(int xCoordinate, int yCoordinate) {
        // Debugging.
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "executing");

        // If the right wall is found.
        boolean rightWallFound = true;

        // Debugging.
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "Starting");
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "xCoordinate: " + xCoordinate);
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "yCoordinate: " + yCoordinate);

        // The search directions.
        int[] searchDirections = determineDirection(RIGHT);
        int xSearchDirection = searchDirections[0];
        int ySearchDirection = searchDirections[1];

        // Debugging.
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "xSearchDirection: " + xSearchDirection);
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "ySearchDirection: " + ySearchDirection);

        // The known map.
        char[][] knownMap = this.occupancyMap.getGrid();

        // If the end is reached.
        boolean reachedEnd = false;

        // The steps until the wall.
        int stepsUntilWall = 0;

        // Loop till the steps until the wall are lower than 6 and the reached end is not true.
        while (stepsUntilWall < 6 && !reachedEnd) {

            // If the known map equals a unknown from the occupancy map.
            if (knownMap[xCoordinate][yCoordinate] == OccupancyMap.UNKNOWN) {
                reachedEnd = true;
                rightWallFound = false;

            // Else If the known map equals a obstacle from the occupancy map.
            } else if (knownMap[xCoordinate][yCoordinate] == OccupancyMap.OBSTACLE) {
                reachedEnd = true;
                rightWallFound = true;
            }

            // Increase the steps to take.
            stepsUntilWall++;

            // Increase the coordinates.
            xCoordinate += xSearchDirection;
            yCoordinate += ySearchDirection;

        }

        // If the end is not reached.
        if (!reachedEnd) {
            // Right wall is not found.
            rightWallFound = false;
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "returns " + rightWallFound);

        return rightWallFound;
    }


    /* Commands. */

    /**
     *
     *
     * @param direction
     * @throws IOException
     */
    private void rotate(String direction) throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "rotate", "executing");

        String command;

        switch (direction) {
            case "Left":
                command = "LEFT";
                break;
            case "Right":
                command = "RIGHT";
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "rotate", "rotating to the " + command.toLowerCase());

        mobileRobot.sendCommand("P1.ROTATE" + command.toUpperCase() + " 90");
        result = input.readLine();
    }

    /**
     *
     *
     * @param tiles
     * @throws IOException
     */
    private void moveForward(int tiles) throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "moveForward", "executing");
        Debugger.print("MobileRobotAI", "moveForward", "Moving forward " + tiles);

        mobileRobot.sendCommand("P1.MOVEFW " + tiles * occupancyMap.getCellDimension());
        result = input.readLine();
    }

    /**
     *
     *
     * @throws IOException
     */
    private void currentPosition() throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "currentPosition", "executing");

        mobileRobot.sendCommand("R1.GETPOS");
        result = input.readLine();
        parsePosition(result, position);
    }

    /**
     *
     *
     * @throws IOException
     */
    private void scanArea() throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "scanArea", "executing");
        Debugger.print("MobileRobotAI", "scanArea", "with laser");

        currentPosition();
        scan("Laser");

        if (position[0] != startX && position[1] != startY) {
            firstPosition = false;
        }

        if (!firstPosition) {
            startX = mobileRobot.getPlatform().getRobotPosition().getX();
            startY = mobileRobot.getPlatform().getRobotPosition().getY();
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "scanArea", "with sonar");

        currentPosition();
        scan("Sonar");
    }

    /**
     *
     *
     * @param with
     * @throws IOException
     */
    private void scan(String with) throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "scan", "executing");

        String command;

        switch (with) {
            case "Laser":
                command = "L";
                break;
            case "Sonar":
                command = "S";
                break;
            default:
                throw new IllegalArgumentException("Invalid with: " + with);
        }

        mobileRobot.sendCommand(command + "1.SCAN");
        result = input.readLine();
        parseMeasures(result, measures);
        occupancyMap.drawLaserScan(position, measures);
    }


    /* Helpers */

    /**
     *
     *
     * @param lookDirection
     * @return
     */
    private int[] determineDirection(int lookDirection) {
        // Debugging.
        Debugger.print("MobileRobotAI", "determineDirection", "executing");

        int currentDirection = closestDirection(position[2]);

        if (currentDirection == 360 && lookDirection > 0) {
            currentDirection = 0;
        }

        currentDirection += lookDirection;

        int[] searchDirections = new int[2];

        int xDirection;
        int yDirection;

        switch (currentDirection) {
            case NORTH:
                xDirection = 0;
                yDirection = -1;
                searchDirections[0] = xDirection;
                searchDirections[1] = yDirection;
                break;
            case EAST:
                xDirection = 1;
                yDirection = 0;
                searchDirections[0] = xDirection;
                searchDirections[1] = yDirection;
                break;
            case SOUTH:
                xDirection = 0;
                yDirection = 1;
                searchDirections[0] = xDirection;
                searchDirections[1] = yDirection;
                break;
            case WEST:
                xDirection = -1;
                yDirection = 0;
                searchDirections[0] = xDirection;
                searchDirections[1] = yDirection;
                break;
            default:
                throw new IllegalArgumentException("The currentDirection: " + currentDirection + " is not a known direction.");
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "determineDirection", "xSearchDirection" + searchDirections[0]);
        Debugger.print("MobileRobotAI", "determineDirection", "ySearchDirection" + searchDirections[1]);

        return searchDirections;
    }

    /**
     *
     *
     * @param numberToRound
     * @return
     */
    private int closestDirection(double numberToRound) {
        // Debugging.
        Debugger.print("MobileRobotAI", "closestDirection", "executing");

        int closestDirection;

        int n = (int) (NORTH - numberToRound);
        int e = (int) (EAST - numberToRound);
        int s = (int) (SOUTH - numberToRound);
        int w = (int) (WEST - numberToRound);

        if (n < 2 && n > -2) {
            closestDirection = NORTH;
        } else if (e < 2 && e > -2 || e < 362 && e > 358) {
            closestDirection = EAST;
        } else if (s < 2 && s > -2) {
            closestDirection = SOUTH;
        } else if (w < 2 && w > -2) {
            closestDirection = WEST;
        } else {
            throw new IllegalArgumentException("The number provided is outside the predefined boundaries.");
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "closestDirection", "is " + closestDirection);

        return closestDirection;
    }

    /**
     *
     *
     * @return
     */
    private boolean mapScanned() {
        // Debugging.
        Debugger.print("MobileRobotAI", "mapScanned", "executing");

        boolean mapScanned = false;

        int xCoordinate = (int) position[0] / occupancyMap.getCellDimension();
        int yCoordinate = (int) position[1] / occupancyMap.getCellDimension();

        if (searchWallToTheRight(xCoordinate, yCoordinate)) {
            int[] searchDirections = determineDirection(RIGHT);
            int xSearchDirection = searchDirections[0];
            int ySearchDirection = searchDirections[1];

            char[][] knownMap = this.occupancyMap.getGrid();

            int x = xCoordinate;
            int y = yCoordinate;

            while (knownMap[x][y] != OccupancyMap.OBSTACLE) {
                x += xSearchDirection;
                y += ySearchDirection;
            }

            boolean continueSearch = true;

            int startXCoordinate = x;
            int startYCoordinate = y;

            int currentXCoordinate = x;
            int currentYCoordinate = y;

            int previousXCoordinate = startXCoordinate;
            int previousYCoordinate = startYCoordinate;

            do {
                try {
                    int[] adjacentWallPieceCoordinates = findAdjacentWall(currentXCoordinate, currentYCoordinate, previousXCoordinate, previousYCoordinate);

                    previousXCoordinate = currentXCoordinate;
                    previousYCoordinate = currentYCoordinate;

                    currentXCoordinate = adjacentWallPieceCoordinates[0];
                    currentYCoordinate = adjacentWallPieceCoordinates[1];
                } catch (IllegalArgumentException ex) {
                    continueSearch = false;
                }
            } while ((currentXCoordinate != startXCoordinate || currentYCoordinate != startYCoordinate) && continueSearch);

            if (continueSearch) {
                mapScanned = true;
            }
        }

        return mapScanned;
    }

    /**
     *
     *
     * @param xCoordinate
     * @param yCoordinate
     * @param previousXCoordinate
     * @param previousYCoordinate
     * @return
     */
    private int[] findAdjacentWall(int xCoordinate, int yCoordinate, int previousXCoordinate, int previousYCoordinate) {
        // Debugging.
        Debugger.print("MobileRobotAI", "findAdjacentWall", "executing");

        int[] wall = new int[2];
        char[][] knownMap = this.occupancyMap.getGrid();

        if (xCoordinate > 0 && knownMap[xCoordinate - 1][yCoordinate] == OccupancyMap.OBSTACLE && (xCoordinate - 1 != previousXCoordinate || yCoordinate != previousYCoordinate)) {
            wall[0] = xCoordinate - 1;
            wall[1] = yCoordinate;
        } else if (knownMap[xCoordinate + 1][yCoordinate] == OccupancyMap.OBSTACLE && (xCoordinate + 1 != previousXCoordinate || yCoordinate != previousYCoordinate)) {
            wall[0] = xCoordinate + 1;
            wall[1] = yCoordinate;
        } else if (yCoordinate > 0 && knownMap[xCoordinate][yCoordinate - 1] == OccupancyMap.OBSTACLE && (xCoordinate != previousXCoordinate || yCoordinate - 1 != previousYCoordinate)) {
            wall[0] = xCoordinate;
            wall[1] = yCoordinate - 1;
        } else if (knownMap[xCoordinate][yCoordinate + 1] == OccupancyMap.OBSTACLE && (xCoordinate != previousXCoordinate || yCoordinate + 1 != previousYCoordinate)) {
            wall[0] = xCoordinate;
            wall[1] = yCoordinate + 1;
        } else {
            throw new IllegalArgumentException("Wall is not complete.");
        }

        return wall;
    }


    /* Parsers */

    /**
     *
     *
     * @param value
     * @param position
     */
    private void parsePosition(String value, double position[]) {
        // Debugging.
        Debugger.print("MobileRobotAI", "parsePosition", "executing");

        int indexInit;
        int indexEnd;
        String parameter;

        indexInit = value.indexOf("X=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[0] = Double.parseDouble(parameter.substring(0, indexEnd));

        indexInit = value.indexOf("Y=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[1] = Double.parseDouble(parameter.substring(0, indexEnd));

        indexInit = value.indexOf("DIR=");
        parameter = value.substring(indexInit + 4);
        position[2] = Double.parseDouble(parameter);
    }

    /**
     *
     *
     * @param value
     * @param measures
     */
    private void parseMeasures(String value, double measures[]) {
        // Debugging.
        Debugger.print("MobileRobotAI", "parseMeasures", "executing");

        for (int i = 0; i < 360; i++) {
            measures[i] = 100.0;
        }

        if (value.length() >= 5) {
            System.out.print(ANSI.ANSI_MAGENTA + "Measurements: ");

            value = value.substring(5);
            StringTokenizer tokenizer = new StringTokenizer(value, " ");

            double distance;
            int direction;

            while (tokenizer.hasMoreTokens()) {
                distance = Double.parseDouble(tokenizer.nextToken().substring(2));
                direction = (int) Math.round(Math.toDegrees(Double.parseDouble(tokenizer.nextToken().substring(2))));

                if (direction == 360) {
                    direction = 0;
                }

                measures[direction] = distance;
                System.out.print("direction = " + direction + " distance = " + distance);
            }

            System.out.print("\n");
        }
    }

}
