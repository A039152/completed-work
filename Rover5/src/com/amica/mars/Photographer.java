package com.amica.mars;

public class Photographer extends ReportingRover {
    public Photographer(String id, int x, int y, Direction direction) {
        super(id, x, y, direction);
    }

    @Override
    public void move() {
        super.move();
        report("Took a photograph!");
    }

    @Override
    public void turnLeft() {
        super.turnLeft();
        report("Turned left for better angle.");
    }

    @Override
    public void turnRight() {
        super.turnRight();
        report("Turned right for better angle.");
    }
}

