package models.robot;

import utils.ANSI;
import utils.Debugger;
import models.virtualmap.OccupancyMap;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MobileRobotAI implements Runnable {

    private static final int NORTH = 270;
    private static final int EAST = 360;
    private static final int SOUTH = 90;
    private static final int WEST = 180;
    private static final int FORWARD = 0;
    private static final int RIGHT = 90;
    private static final int MAX_VIEW_DISTANCE = 10;
    private final OccupancyMap map;
    private final MobileRobot robot;
    private boolean running;
    PipedInputStream pipeIn;
    BufferedReader input;
    PrintWriter output;
    private String result = "";
    private double[] position;
    private double[] measures;
    private boolean firstPosition;
    private double startX;
    private double startY;

    public MobileRobotAI(MobileRobot robot, OccupancyMap map) {
        this.map = map;
        this.robot = robot;

        try {
            this.pipeIn = new PipedInputStream();
            this.input = new BufferedReader(new InputStreamReader(pipeIn));
            this.output = new PrintWriter(new PipedOutputStream(pipeIn), true);
            this.result = "";
            this.position = new double[3];
            this.measures = new double[360];
            this.firstPosition = true;
            this.running = true;
            robot.setOutput(output);
        } catch (IOException ex) {
            System.err.println("MobileRobotAI: Something went wrong while initializing.");
        }
    }

    public void run() {
        Debugger.print("MobileRobotAI", "run", "Running");

        while (running) {
            try {
                scanAreaWithLaser();
                scanAreaWithSonar();
                courseOfAction();

                if (isMapComplete()) {
                    robot.quit();
                }
            } catch (IOException ioe) {
                System.err.println("Execution stopped.");
                running = false;
            }
        }

        Debugger.print("MobileRobotAI", "run", "Finished running");
    }

    private void courseOfAction() throws IOException {
        Debugger.print("MobileRobotAI", "courseOfAction", "Started");

        int xCoordinate = (int) this.position[0] / map.getCellDimension();
        int yCoordinate = (int) this.position[1] / map.getCellDimension();

        Debugger.print("MobileRobotAI", "courseOfAction", "xCoordinate: " + xCoordinate);
        Debugger.print("MobileRobotAI", "courseOfAction", "yCoordinate: " + yCoordinate);

        int[] searchDirections = determineDirection(FORWARD);

        Debugger.print("MobileRobotAI", "courseOfAction", "searchDirections returns Xdir, Ydir > " + Arrays.toString(searchDirections));

        int xSearchDirection = searchDirections[0];
        int ySearchDirection = searchDirections[1];
        boolean reachedEnd = false;
        char[][] knownMap = this.map.getGrid();
        int stepsToTake = 0;
        int x = xCoordinate;
        int y = yCoordinate;

        while (stepsToTake < MAX_VIEW_DISTANCE && !reachedEnd) {
            boolean rightWallFound = findWallToTheRight(x, y);

            if (rightWallFound && knownMap[x][y] == OccupancyMap.EMPTY) {
                stepsToTake++;
            } else if (rightWallFound && knownMap[x][y] == OccupancyMap.UNKNOWN) {
                Debugger.print("MobileRobotAI", "courseOfAction", "xSearch: found UNKNOWN");
                moveForward(stepsToTake - 2);
                reachedEnd = true;
            } else if (rightWallFound && knownMap[x][y] == OccupancyMap.OBSTACLE) {
                Debugger.print("MobileRobotAI", "courseOfAction", "xSearch: found OBSTACLE");
                moveForward(stepsToTake - 3);
                rotateLeft();
                reachedEnd = true;
            } else if (!rightWallFound && knownMap[x][y] == OccupancyMap.UNKNOWN) {
                Debugger.print("MobileRobotAI", "courseOfAction", "xSearch: found UNKNOWN");
                moveForward(stepsToTake - 2);
                reachedEnd = true;
            } else if (!rightWallFound && knownMap[x][y] == OccupancyMap.EMPTY) {
                Debugger.print("MobileRobotAI", "courseOfAction", "xSearch: found EMPTY");
                cornerRight(stepsToTake);
                reachedEnd = true;
            }

            x += xSearchDirection;
            y += ySearchDirection;
        }

        if (!reachedEnd) {
            moveForward(stepsToTake - 2);
        }
    }

    private void cornerRight(int stepsBeforeCorner) throws IOException {
        moveForward(stepsBeforeCorner + 3);
        scanAreaWithSonar();
        scanAreaWithLaser();

        int xCoordinate = (int) this.position[0] / map.getCellDimension();
        int yCoordinate = (int) this.position[1] / map.getCellDimension();

        boolean rightWallFound;

        rightWallFound = findWallToTheRight(xCoordinate, yCoordinate);

        if (!rightWallFound) {
            rotateRight();
            moveForward(2);
        }
    }

    private boolean findWallToTheRight(int xCoordinate, int yCoordinate) {
        boolean rightWallFound = true;

        Debugger.print("MobileRobotAI", "findWallToTheRight", "Starting");
        Debugger.print("MobileRobotAI", "findWallToTheRight", "xCoordinate: " + xCoordinate);
        Debugger.print("MobileRobotAI", "findWallToTheRight", "yCoordinate: " + yCoordinate);

        int[] searchDirections = determineDirection(RIGHT);
        int xSearchDirection = searchDirections[0];
        int ySearchDirection = searchDirections[1];

        Debugger.print("MobileRobotAI", "findWallToTheRight", "xSearchDirection: " + xSearchDirection);
        Debugger.print("MobileRobotAI", "findWallToTheRight", "ySearchDirection: " + ySearchDirection);

        char[][] knownMap = this.map.getGrid();
        boolean reachedEnd = false;

        int stepsUntilWall = 0;
        int x = xCoordinate;
        int y = yCoordinate;

        while (stepsUntilWall < 6 && !reachedEnd) {
            if (knownMap[x][y] == OccupancyMap.UNKNOWN) {
                reachedEnd = true;
                rightWallFound = false;
            } else if (knownMap[x][y] == OccupancyMap.OBSTACLE) {
                reachedEnd = true;
                rightWallFound = true;
            }

            stepsUntilWall++;

            x += xSearchDirection;
            y += ySearchDirection;
        }

        if (!reachedEnd) {
            rightWallFound = false;
        }

        Debugger.print("MobileRobotAI", "findWallToTheRight", "returns " + rightWallFound);

        return rightWallFound;
    }

    private int[] determineDirection(int lookDirection) {
        int currentDirection = roundToClosestDirection(this.position[2]);

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

                return searchDirections;
            case EAST:
                xDirection = 1;
                yDirection = 0;
                searchDirections[0] = xDirection;
                searchDirections[1] = yDirection;

                return searchDirections;
            case SOUTH:
                xDirection = 0;
                yDirection = 1;
                searchDirections[0] = xDirection;
                searchDirections[1] = yDirection;

                return searchDirections;
            case WEST:
                xDirection = -1;
                yDirection = 0;
                searchDirections[0] = xDirection;
                searchDirections[1] = yDirection;

                return searchDirections;
            default:
                throw new IllegalArgumentException("The currentDirection: " + currentDirection + " is not a known direction.");
        }
    }

    private int roundToClosestDirection(double numToRound) {
        int nDif = (int) (NORTH - numToRound);
        int eDif = (int) (EAST - numToRound);
        int sDif = (int) (SOUTH - numToRound);
        int wDif = (int) (WEST - numToRound);

        if (nDif < 2 && nDif > -2) {
            Debugger.print("MobileRobotAI", "roundToClosestDirection", "returns 'NORTH'");

            return NORTH;
        } else if (eDif < 2 && eDif > -2 || eDif < 362 && eDif > 358) {
            Debugger.print("MobileRobotAI", "roundToClosestDirection", "returns 'EAST'");

            return EAST;
        } else if (sDif < 2 && sDif > -2) {
            Debugger.print("MobileRobotAI", "roundToClosestDirection", "returns 'SOUTH'");

            return SOUTH;
        } else if (wDif < 2 && wDif > -2) {
            Debugger.print("MobileRobotAI", "roundToClosestDirection", "returns 'WEST'");

            return WEST;
        } else {
            throw new IllegalArgumentException("The number provided is outside the predefined boundaries.");
        }
    }

    private void rotateLeft() throws IOException {
        Debugger.print("MobileRobotAI", "rotateLeft", "rotating to the left");
        robot.sendCommand("P1.ROTATELEFT 90");
        result = input.readLine();
    }

    private void rotateRight() throws IOException {
        Debugger.print("MobileRobotAI", "rotateRight", "rotating to the right");
        robot.sendCommand("P1.ROTATERIGHT 90");
        result = input.readLine();
    }

    private void moveForward(int tiles) throws IOException {
        Debugger.print("MobileRobotAI", "moveForward", "Moving forward " + tiles);
        this.robot.sendCommand("P1.MOVEFW " + tiles * map.getCellDimension());
        result = input.readLine();
    }

    private void scanAreaWithLaser() throws IOException {
        this.robot.sendCommand("R1.GETPOS");
        this.result = input.readLine();
        this.parsePosition(result, position);

        this.robot.sendCommand("L1.SCAN");
        this.result = input.readLine();
        this.parseMeasures(result, measures);
        this.map.drawLaserScan(position, measures);

        if (position[0] != startX && position[1] != startY) {
            firstPosition = false;
        }

        if (!firstPosition) {
            startX = robot.getPlatform().getRobotPosition().getX();
            startY = robot.getPlatform().getRobotPosition().getY();
        }
    }

    private void scanAreaWithSonar() throws IOException {
        this.robot.sendCommand("R1.GETPOS");
        this.result = input.readLine();
        this.parsePosition(result, position);

        this.robot.sendCommand("S1.SCAN");
        this.result = input.readLine();
        this.parseMeasures(result, measures);
        this.map.drawSonarScan(position, measures);
    }

    private void parsePosition(String value, double position[]) {
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

    private void parseMeasures(String value, double measures[]) {
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

    private boolean isMapComplete() {
        boolean completeMap = false;

        int xCoordinate = (int) position[0] / map.getCellDimension();
        int yCoordinate = (int) position[1] / map.getCellDimension();

        if (findWallToTheRight(xCoordinate, yCoordinate)) {
            int[] searchDirections = determineDirection(RIGHT);
            int xSearchDirection = searchDirections[0];
            int ySearchDirection = searchDirections[1];

            char[][] knownMap = this.map.getGrid();

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
                completeMap = true;
            }
        }

        return completeMap;
    }

    private int[] findAdjacentWall(int xCoordinate, int yCoordinate, int previousXCoordinate, int previousYCoordinate) {
        int[] toReturn = new int[2];
        char[][] knownMap = this.map.getGrid();

        if (xCoordinate > 0 && knownMap[xCoordinate - 1][yCoordinate] == OccupancyMap.OBSTACLE && (xCoordinate - 1 != previousXCoordinate || yCoordinate != previousYCoordinate)) {
            toReturn[0] = xCoordinate - 1;
            toReturn[1] = yCoordinate;
        } else if (knownMap[xCoordinate + 1][yCoordinate] == OccupancyMap.OBSTACLE && (xCoordinate + 1 != previousXCoordinate || yCoordinate != previousYCoordinate)) {
            toReturn[0] = xCoordinate + 1;
            toReturn[1] = yCoordinate;
        } else if (yCoordinate > 0 && knownMap[xCoordinate][yCoordinate - 1] == OccupancyMap.OBSTACLE && (xCoordinate != previousXCoordinate || yCoordinate - 1 != previousYCoordinate)) {
            toReturn[0] = xCoordinate;
            toReturn[1] = yCoordinate - 1;
        } else if (knownMap[xCoordinate][yCoordinate + 1] == OccupancyMap.OBSTACLE && (xCoordinate != previousXCoordinate || yCoordinate + 1 != previousYCoordinate)) {
            toReturn[0] = xCoordinate;
            toReturn[1] = yCoordinate + 1;
        } else {
            throw new IllegalArgumentException("Wall is not complete!");
        }

        return toReturn;
    }

}
