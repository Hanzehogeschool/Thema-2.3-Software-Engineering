package models.robot;

import models.virtualmap.OccupancyMap;
import utils.ANSI;
import utils.Debugger;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Class representing a mobile robot ai.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class MobileRobotAI implements Runnable {

    /* Variables */

    /* Directions the mobile robot is facing. */

    /**
     * The north.
     */
    private static final int NORTH = 270;

    /**
     * The east.
     */
    private static final int EAST = 360;

    /**
     * The south.
     */
    private static final int SOUTH = 90;

    /**
     * The west.
     */
    private static final int WEST = 180;

    /* Directions the mobile robot moves to. */

    /**
     * The forward.
     */
    private static final int FORWARD = 0;

    /**
     * The right.
     */
    private static final int RIGHT = 90;

    /**
     * The max view distance.
     */
    private static final int MAX_VIEW_DISTANCE = 10;

    /* Accessories for the mobile robot ai. */

    /**
     * The occupancy map.
     */
    private final OccupancyMap occupancyMap;

    /**
     * The mobile robot.
     */
    private final MobileRobot mobileRobot;

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
    String result;

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

    /* Thread. */

    /**
     * The running.
     */
    private boolean running;

    /* Constructors */

    /**
     * Mobile robot ai constructor.
     * Creates a new mobile robot ai.
     *
     * @param mobileRobot  The mobile robot.
     * @param occupancyMap The occupancy map.
     */
    public MobileRobotAI(MobileRobot mobileRobot, OccupancyMap occupancyMap) {
        // Debugging mode.
        Debugger.debug = false;

        // Debugging.
        Debugger.print("MobileRobotAI", "MobileRobotAI", "executing");

        // Initialize the accessories for the mobile robot ai.
        this.occupancyMap = occupancyMap;
        this.mobileRobot = mobileRobot;

        // Try.
        try {
            // Initialize the readers and writers.
            this.pipeIn = new PipedInputStream();
            this.input = new BufferedReader(new InputStreamReader(pipeIn));
            this.output = new PrintWriter(new PipedOutputStream(pipeIn), true);
            this.result = "";
        // Catch the io exception.
        } catch (IOException ioException) {
            System.err.println("Mobile Robot AI: Something went wrong while initializing.");
        }

        // Set the output of the mobile robot.
        mobileRobot.setOutput(output);

        // Initialize the positions and measures.
        this.position = new double[3];
        this.measures = new double[360];
        this.startX = 0;
        this.startY = 0;
        this.firstPosition = true;

        // Initialize the running.
        this.running = true;
    }

    /* Methods */

    /* Process. */

    /**
     * Runs the mobile robot ai.
     */
    public void run() {
        // Debugging.
        Debugger.print("MobileRobotAI", "run", "executing");

        // Keeps running when running is set to true.
        while (running) {
            // Try.
            try {
                // Start processing.
                process();

                // If the map is scanned, quit the mobile robot.
                if (mapScanned()) {
                    mobileRobot.quit();
                }
            // Catch the io exception.
            } catch (IOException ioException) {
                System.err.println("Mobile Robot AI: Execution stopped.");
                running = false;
            }
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "run", "finished executing");
    }

    /**
     * Go though the process.
     *
     * @throws IOException
     */
    private void process() throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "process", "executing");

        // Scan the area.
        scanArea();

        // The coordinates of the mobile robot.
        int xCoordinate = (int) position[0] / occupancyMap.getCellDimension();
        int yCoordinate = (int) position[1] / occupancyMap.getCellDimension();

        // Debugging.
        Debugger.print("MobileRobotAI", "process", "xCoordinate: " + xCoordinate);
        Debugger.print("MobileRobotAI", "process", "yCoordinate: " + yCoordinate);

        // Search directions.
        int[] searchDirections = determineSearchDirection(FORWARD);

        // Debugging..
        Debugger.print("MobileRobotAI", "process", "searchDirections: " + Arrays.toString(searchDirections));

        // Search direction coordinates.
        int xSearchDirection = searchDirections[0];
        int ySearchDirection = searchDirections[1];

        // If the end is reached.
        boolean reachedEnd = false;

        // The known map.
        char[][] knownMap = occupancyMap.getGrid();

        // Amount of steps to take.
        int stepsToTake = 0;

        // While the steps to take are lesser than the max view distance and the reached end is not true.
        while (stepsToTake < MAX_VIEW_DISTANCE && !reachedEnd) {
            // If the right wall is found.
            boolean rightWallFound = searchWallToTheRight(xCoordinate, yCoordinate);

            // If the right wall is found and the known map equals empty from the occupancy map, increase the steps to take.
            if (rightWallFound && knownMap[xCoordinate][yCoordinate] == occupancyMap.getEmpty()) {
                stepsToTake++;
            // Else if the right wall is found and the known map equals unknown from the occupancy map, move forward and set the reached end to true.
            } else if (rightWallFound && knownMap[xCoordinate][yCoordinate] == occupancyMap.getUnknown()) {
                moveForward(stepsToTake - 2);
                reachedEnd = true;
            // Else if the right wall is found and the known map equals obstacle from the occupancy map, move forward, rotate to the left and set the reached end to true.
            } else if (rightWallFound && knownMap[xCoordinate][yCoordinate] == occupancyMap.getObstacle()) {
                moveForward(stepsToTake - 3);
                rotate("Left");
                reachedEnd = true;
            // Else if the right wall is not found and the known map equals unknown from the occupancy map, move forward and set the reached end to true.
            } else if (!rightWallFound && knownMap[xCoordinate][yCoordinate] == occupancyMap.getUnknown()) {
                moveForward(stepsToTake - 2);
                reachedEnd = true;
            // Else if the right wall is not found and the known map equals empty from the occupancy map, corner right and set the reached end to true.
            } else if (!rightWallFound && knownMap[xCoordinate][yCoordinate] == occupancyMap.getEmpty()) {
                cornerRight(stepsToTake);
                reachedEnd = true;
            }

            // Increase the coordinates.
            xCoordinate += xSearchDirection;
            yCoordinate += ySearchDirection;
        }

        // If the end is not reached, move forward.
        if (!reachedEnd) {
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
        int xCoordinate = (int) position[0] / occupancyMap.getCellDimension();
        int yCoordinate = (int) position[1] / occupancyMap.getCellDimension();

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
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "xCoordinate: " + xCoordinate);
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "yCoordinate: " + yCoordinate);

        // The search directions.
        int[] searchDirections = determineSearchDirection(RIGHT);
        int xSearchDirection = searchDirections[0];
        int ySearchDirection = searchDirections[1];

        // Debugging.
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "xSearchDirection: " + xSearchDirection);
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "ySearchDirection: " + ySearchDirection);

        // The known map.
        char[][] knownMap = occupancyMap.getGrid();

        // If the end is reached.
        boolean reachedEnd = false;

        // The steps until the wall.
        int stepsUntilWall = 0;

        // While the steps until the wall are lower than 6 and the reached end is not true.
        while (stepsUntilWall < 6 && !reachedEnd) {
            // If the known map equals unknown from the occupancy map, set the reached end to true and the right wall found to false.
            if (knownMap[xCoordinate][yCoordinate] == occupancyMap.getUnknown()) {
                reachedEnd = true;
                rightWallFound = false;
            // Else if the known map equals obstacle from the occupancy map, set the reached end to true and the right wall found to true.
            } else if (knownMap[xCoordinate][yCoordinate] == occupancyMap.getObstacle()) {
                reachedEnd = true;
                rightWallFound = true;
            }

            // Increase the steps to take.
            stepsUntilWall++;

            // Increase the coordinates.
            xCoordinate += xSearchDirection;
            yCoordinate += ySearchDirection;
        }

        // If the end is not reached, set the right wall found to false.
        if (!reachedEnd) {
            rightWallFound = false;
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "searchWallToTheRight", "returns: " + rightWallFound);

        // Return if the right wall is found.
        return rightWallFound;
    }

    /* Commands. */

    /**
     * Moves the mobile robot forward.
     *
     * @param tiling The tiling.
     * @throws IOException
     */
    private void moveForward(int tiling) throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "moveForward", "executing");
        Debugger.print("MobileRobotAI", "moveForward", "tiling: " + tiling);

        // Move the mobile robot forward to the given direction.
        mobileRobot.sendCommand("P1.MOVEFW " + tiling * occupancyMap.getCellDimension());
        result = input.readLine();
    }

    /**
     * Rotates the mobile robot.
     *
     * @param direction The direction.
     * @throws IOException
     */
    private void rotate(String direction) throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "rotate", "executing");

        // The command.
        String command;

        // Switch the direction to the left or to the right.
        switch (direction) {
            // Case left, set the command to the left.
            case "Left":
                command = "LEFT";
                break;
            // Case left, set the command to the right.
            case "Right":
                command = "RIGHT";
                break;
            // Default, throw a new illegal argument exception.
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "rotate", "rotating to the " + command.toLowerCase());

        // Rotate the mobile robot to the given direction.
        mobileRobot.sendCommand("P1.ROTATE" + command.toUpperCase() + " 90");
        result = input.readLine();
    }

    /**
     * Scans the area.
     *
     * @throws IOException
     */
    private void scanArea() throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "scanArea", "executing");

        // Get the current position and scan with the laser.
        currentPosition();
        scan("Laser");

        // Get the current position and scan with the sonar.
        currentPosition();
        scan("Sonar");

        // If the x position not equals the start x coordinate and the y coordinate not equals the start y coordinate, set the first position to false.
        if (position[0] != startX && position[1] != startY) {
            firstPosition = false;
        }

        // If the first position is not true, set the start x coordinate to the mobile robot position x and the star y coordinate to the mobile robot position y.
        if (!firstPosition) {
            startX = mobileRobot.getPlatform().getRobotPosition().getX();
            startY = mobileRobot.getPlatform().getRobotPosition().getY();
        }
    }

    /**
     * Scans.
     *
     * @param with The with.
     * @throws IOException
     */
    private void scan(String with) throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "scan", "executing");

        // The command.
        String command;

        // Switch the with to the laser or the sonar.
        switch (with) {
            // Case sonar, set the command to l.
            case "Laser":
                command = "L";
                break;
            // Case sonar, set the command to s.
            case "Sonar":
                command = "S";
                break;
            // Default, throw a new illegal argument exception.
            default:
                throw new IllegalArgumentException("Invalid with: " + with);
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "scan", "with: " + with);

        // Command the mobile robot to scan.
        mobileRobot.sendCommand(command + "1.SCAN");
        result = input.readLine();
        parseMeasures(result, measures);

        // If the command equals laser, draw the laser scan.
        if (command.equals("L")) {
            occupancyMap.drawLaserScan(position, measures);
        // Else if the command equals sonar, draw the sonar scan.
        } else if (command.equals("S")) {
            occupancyMap.drawSonarScan(position, measures);
        }
    }

    /**
     * The current position of the mobile robot.
     *
     * @throws IOException
     */
    private void currentPosition() throws IOException {
        // Debugging.
        Debugger.print("MobileRobotAI", "currentPosition", "executing");

        // Get the current position of the mobile robot.
        mobileRobot.sendCommand("R1.GETPOS");
        result = input.readLine();
        parsePosition(result, position);
    }

    /* Helpers */

    /**
     * Determines the search direction.
     *
     * @param lookingDirection The looking direction.
     * @return The determined search directions.
     */
    private int[] determineSearchDirection(int lookingDirection) {
        // Debugging.
        Debugger.print("MobileRobotAI", "determineSearchDirection", "executing");

        // The current direction.
        int currentDirection = determineClosestDirection(position[2]);

        // If the current direction is 360 and the looking direction is greater than 0, set the current direction to 0.
        if (currentDirection == 360 && lookingDirection > 0) {
            currentDirection = 0;
        }

        // Increase the current direction.
        currentDirection += lookingDirection;

        // The search directions.
        int[] searchDirections = new int[2];

        // Switch the with to the north, east, south or west.
        switch (currentDirection) {
            // Case north, set the x direction to 0 and the y direction to -1.
            case NORTH:
                searchDirections[0] = 0;
                searchDirections[1] = -1;
                break;
            // Case east, set the x direction to 1 and the y direction to 0.
            case EAST:
                searchDirections[0] = 1;
                searchDirections[1] = 0;
                break;
            // Case south, set the x direction to 0 and the y direction to 1.
            case SOUTH:
                searchDirections[0] = 0;
                searchDirections[1] = 1;
                break;
            // Case west, set the x direction to -1 and the y direction to 0.
            case WEST:
                searchDirections[0] = -1;
                searchDirections[1] = 0;
                break;
            // Default, throw a new illegal argument exception.
            default:
                throw new IllegalArgumentException(currentDirection + " is not a known direction.");
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "determineSearchDirection", "xSearchDirection: " + searchDirections[0]);
        Debugger.print("MobileRobotAI", "determineSearchDirection", "ySearchDirection: " + searchDirections[1]);

        // Return the search directions.
        return searchDirections;
    }

    /**
     * Determines the closest direction.
     *
     * @param numberToRound The number to round.
     * @return The determined closest direction.
     */
    private int determineClosestDirection(double numberToRound) {
        // Debugging.
        Debugger.print("MobileRobotAI", "determineClosestDirection", "executing");

        // The closest direction.
        int closestDirection;

        // The north, east, south and west.
        int north = (int) (NORTH - numberToRound);
        int east = (int) (EAST - numberToRound);
        int south = (int) (SOUTH - numberToRound);
        int west = (int) (WEST - numberToRound);

        // If the north is lesser than 2 and the north is greater than -2, set the closest direction to the north.
        if (north < 2 && north > -2) {
            closestDirection = NORTH;
        // Else if the north is lesser than 2 and the east is greater than -2 or the east is lesser than 362 and the east is greater than 358, set the closest direction to the east.
        } else if (east < 2 && east > -2 || east < 362 && east > 358) {
            closestDirection = EAST;
        // Else if the south is lesser than 2 and the south is greater than -2, set the closest direction to the south.
        } else if (south < 2 && south > -2) {
            closestDirection = SOUTH;
        // Else if the west is lesser than 2 and the west greater than -2, set the closest direction to the west.
        } else if (west < 2 && west > -2) {
            closestDirection = WEST;
        // Else, throw a new illegal argument exception.
        } else {
            throw new IllegalArgumentException("The number provided is outside the predefined boundaries.");
        }

        // Debugging.
        Debugger.print("MobileRobotAI", "determineClosestDirection", "returns: " + closestDirection);

        // Return the closest direction.
        return closestDirection;
    }

    /**
     * Checks if the map is scanned.
     *
     * @return If the map is scanned.
     */
    private boolean mapScanned() {
        // Debugging.
        Debugger.print("MobileRobotAI", "mapScanned", "executing");

        // If the map is scanned.
        boolean mapScanned = false;

        // The coordinates.
        int xCoordinate = (int) position[0] / occupancyMap.getCellDimension();
        int yCoordinate = (int) position[1] / occupancyMap.getCellDimension();

        // If the wall to the right is found.
        if (searchWallToTheRight(xCoordinate, yCoordinate)) {
            // Set the search directions.
            int[] searchDirections = determineSearchDirection(RIGHT);
            int xSearchDirection = searchDirections[0];
            int ySearchDirection = searchDirections[1];

            // The known map.
            char[][] knownMap = occupancyMap.getGrid();

            // While the known map not equals obstacle from the occupancy map, increase the coordinates with the search direction coordinates.
            while (knownMap[xCoordinate][yCoordinate] != occupancyMap.getObstacle()) {
                xCoordinate += xSearchDirection;
                yCoordinate += ySearchDirection;
            }

            // If the search continues.
            boolean continueSearch = true;

            // The start coordinates.
            int startXCoordinate = xCoordinate;
            int startYCoordinate = yCoordinate;

            // The current coordinates.
            int currentXCoordinate = xCoordinate;
            int currentYCoordinate = yCoordinate;

            // The previous coordinates.
            int previousXCoordinate = startXCoordinate;
            int previousYCoordinate = startYCoordinate;

            // Do, while the current x coordinate not equals the start x coordinate or the current y coordinate not equals the start y coordinate and if the search continues is true.
            do {
                // Try.
                try {
                    // The adjacent wall coordinates.
                    int[] adjacentWallCoordinates = searchAdjacentWall(currentXCoordinate, currentYCoordinate, previousXCoordinate, previousYCoordinate);

                    // Set the previous coordinates to the current coordinates.
                    previousXCoordinate = currentXCoordinate;
                    previousYCoordinate = currentYCoordinate;

                    // Set the current coordinates to the adjacent wall coordinates.
                    currentXCoordinate = adjacentWallCoordinates[0];
                    currentYCoordinate = adjacentWallCoordinates[1];
                // Catch the illegal argument exception.
                } catch (IllegalArgumentException illegalArgumentException) {
                    continueSearch = false;
                }
            } while ((currentXCoordinate != startXCoordinate || currentYCoordinate != startYCoordinate) && continueSearch);

            // If the search continues is true, set the map scanned to true.
            if (continueSearch) {
                mapScanned = true;
            }
        }

        // Return if the map is scanned.
        return mapScanned;
    }

    /**
     * Searches the adjacent wall.
     *
     * @param xCoordinate         The x coordinate.
     * @param yCoordinate         The y coordinate.
     * @param previousXCoordinate The previous x coordinate.
     * @param previousYCoordinate The previous y coordinate.
     * @return The adjacent wall.
     */
    private int[] searchAdjacentWall(int xCoordinate, int yCoordinate, int previousXCoordinate, int previousYCoordinate) {
        // Debugging.
        Debugger.print("MobileRobotAI", "searchAdjacentWall", "executing");

        // The adjacent wall.
        int[] adjacentWall = new int[2];

        // The known map.
        char[][] knownMap = occupancyMap.getGrid();

        // If the x coordinate is greater than 0 and the known map equals the obstacle from the occupancy map and the x coordinate not equals the previous x coordinate or the y coordinate not equals the previous y coordinate, set the adjacent wall x to the x coordinate and the adjacent wall y to the y coordinate.
        if (xCoordinate > 0 && knownMap[xCoordinate - 1][yCoordinate] == occupancyMap.getObstacle() && (xCoordinate - 1 != previousXCoordinate || yCoordinate != previousYCoordinate)) {
            adjacentWall[0] = xCoordinate - 1;
            adjacentWall[1] = yCoordinate;
        // Else if the known map equals the obstacle from the occupancy map and the x coordinate not equals the previous x coordinate or the y coordinate not equals the previous y coordinate, set the adjacent wall x to the x coordinate and the adjacent wall y to the y coordinate.
        } else if (knownMap[xCoordinate + 1][yCoordinate] == occupancyMap.getObstacle() && (xCoordinate + 1 != previousXCoordinate || yCoordinate != previousYCoordinate)) {
            adjacentWall[0] = xCoordinate + 1;
            adjacentWall[1] = yCoordinate;
        // Else if the y coordinate is greater than 0 and the known map equals the obstacle from the occupancy map and the x coordinate not equals the previous x coordinate or the y coordinate not equals the previous y coordinate, set the adjacent wall x to the x coordinate and the adjacent wall y to the y coordinate.
        } else if (yCoordinate > 0 && knownMap[xCoordinate][yCoordinate - 1] == occupancyMap.getObstacle() && (xCoordinate != previousXCoordinate || yCoordinate - 1 != previousYCoordinate)) {
            adjacentWall[0] = xCoordinate;
            adjacentWall[1] = yCoordinate - 1;
        // Else if the known map equals the obstacle from the occupancy map and the x coordinate not equals the previous x coordinate or the y coordinate not equals the previous y coordinate, set the adjacent wall x to the x coordinate and the adjacent wall y to the y coordinate.
        } else if (knownMap[xCoordinate][yCoordinate + 1] == occupancyMap.getObstacle() && (xCoordinate != previousXCoordinate || yCoordinate + 1 != previousYCoordinate)) {
            adjacentWall[0] = xCoordinate;
            adjacentWall[1] = yCoordinate + 1;
        // Else, throw a new illegal argument exception.
        } else {
            throw new IllegalArgumentException("Wall is not complete.");
        }

        // Return the adjacent adjacentWall.
        return adjacentWall;
    }

    /* Parsers */

    /**
     * Parses the position.
     *
     * @param value    The value.
     * @param position The position.
     */
    private void parsePosition(String value, double position[]) {
        // Debugging.
        Debugger.print("MobileRobotAI", "parsePosition", "executing");

        // The index init and index end.
        int indexInit;
        int indexEnd;

        // The parameter.
        String parameter;

        // Parse the x.
        indexInit = value.indexOf("X=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[0] = Double.parseDouble(parameter.substring(0, indexEnd));

        // Parse the y.
        indexInit = value.indexOf("Y=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[1] = Double.parseDouble(parameter.substring(0, indexEnd));

        // Parse the dir.
        indexInit = value.indexOf("DIR=");
        parameter = value.substring(indexInit + 4);
        position[2] = Double.parseDouble(parameter);
    }

    /**
     * Parses the measures.
     *
     * @param value    The value.
     * @param measures The position.
     */
    private void parseMeasures(String value, double measures[]) {
        // Debugging.
        Debugger.print("MobileRobotAI", "parseMeasures", "executing");

        // Loop while i is lesser than 360, set the measures to 100.0.
        for (int i = 0; i < 360; i++) {
            measures[i] = 100.0;
        }

        // If the value length is greater or equals 5.
        if (value.length() >= 5) {
            // Print the measurements.
            System.out.println(ANSI.ANSI_MAGENTA + "Measurements:");

            // Set the value and the tokenizer.
            value = value.substring(5);
            StringTokenizer tokenizer = new StringTokenizer(value, " ");

            // The distance and direction.
            double distance;
            int direction;

            // While the tokenizer has more tokens.
            while (tokenizer.hasMoreTokens()) {
                // Set the distance and direction.
                distance = Double.parseDouble(tokenizer.nextToken().substring(2));
                direction = (int) Math.round(Math.toDegrees(Double.parseDouble(tokenizer.nextToken().substring(2))));

                // If the direction equals 360, set the direction to 0.
                if (direction == 360) {
                    direction = 0;
                }

                // Set the measures to the distance.
                measures[direction] = distance;

                // Print the measurements.
                System.out.println("direction: " + direction + "\t distance: " + distance);
            }
        }
    }

}
